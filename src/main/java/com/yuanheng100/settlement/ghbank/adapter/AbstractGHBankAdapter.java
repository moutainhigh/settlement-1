package com.yuanheng100.settlement.ghbank.adapter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.utils.DES3EncryptAndDecrypt;
import com.yuanheng100.settlement.ghbank.utils.GhbankRSA;
import com.yuanheng100.settlement.ghbank.utils.HttpUtils;
import com.yuanheng100.settlement.ghbank.utils.XMLUtils;
import com.yuanheng100.util.ConfigFile;

/**
 * Created by jlqian on 2017/4/17.
 */
public abstract class AbstractGHBankAdapter<M extends GHBankReq> 
{
    private static Logger logger = LoggerFactory.getLogger(AbstractGHBankAdapter.class);

    protected static final DateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    protected static final String GHBANK_URL = ConfigFile.getProperty("ghbank.url");

    private static final String PRIVATE_KEY = ConfigFile.getProperty("ghbank.merchant.private.key");

    private static final String PUBLIC_KEY = ConfigFile.getProperty("ghbank.merchant.public.key");

    public final static String MERCHANT_ID = ConfigFile.getProperty("ghbank.merchant.id");

    /**
     * 重要！发送请求，并解析响应。适用于同步发送请求
     * 
     * @param ghBankReq
     * @return
     * @throws Exception
     */
    public GHBankResp postAndReceive(M ghBankReq)
    {
        String postString = getPostString(ghBankReq);
        //发送请求，获取返回值
        String xml = HttpUtils.post(GHBANK_URL, postString);
        //解析XML获得GHBankReq对象
        
        GHBankResp resp = parseXml(xml);
        return resp;
    }
    
    /**
     * 生成经过加密，摘要，并加上报文头后的最终报文。适用于异步发送请求
     * @param ghBankReq
     * @return
     * @throws Exception
     */
    public String getPostString(M ghBankReq) 
    {
        //获取XML字符串
        Document document = createXml(ghBankReq);
        
        logger.info("待发送报文（明文）: \t"+document.asXML().replaceAll("\r|\n", ""));
        
        //取XML中XMLPARA节点的内容，并加密
        String encryptXml = createDES3EncryptXml(document);
        
        //签名字符串
        String sign = GhbankRSA.byteArrayToHexString(createPrivateSign(encryptXml));
        
        StringBuilder builder = new StringBuilder();
        
        //构造报文头
        builder.append(MessageHeader.VERSION_NUMBER)
            .append(MessageHeader.TYPE)
            .append(ghBankReq.getPriority())
            .append(ghBankReq.getInvokeMethod())
            .append(MessageHeader.RESERVEDDOMAIN);
        
        //构造安全域、签名、添加正文
        String fullXml = builder.append(String.format("%08d", sign.length())).append(sign).append(encryptXml).toString();
        logger.debug("待发送报文（加密）：\t"+fullXml);
        return fullXml;
    }


    protected Document createXml(M ghBankReq)
    {
        String dateFormat = dataFormat.format(ghBankReq.getChannelDateTime());

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Document");
        //构建Header
        Element header = root.addElement("header");
        
        header.addElement("channelCode").addText(StringUtils.trimToEmpty(ghBankReq.getChannelCode()));
        header.addElement("channelFlow").addText(ghBankReq.getChannelFlow());
        header.addElement("channelDate").addText(StringUtils.trimToEmpty(dateFormat.substring(0, 8)));
        header.addElement("channelTime").addText(StringUtils.trimToEmpty(dateFormat.substring(8)));
        header.addElement("encryptData").addText(StringUtils.trimToEmpty(ghBankReq.getEncryptData()));

        //构建Body
        Element body = root.addElement("body");
        body.addElement("TRANSCODE").addText(StringUtils.trimToEmpty(ghBankReq.getTransCode()));
        Element xmlpara = body.addElement("XMLPARA");
        
        xmlpara.addElement("MERCHANTID").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantId()));
        xmlpara.addElement("APPID").addText(StringUtils.trimToEmpty(ghBankReq.getAppId().getCode()));
        
        return document;
    }

    private String createDES3EncryptXml(Document document)
    {
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        String asXML = xmlpara.asXML();
        String substring = asXML.substring(9, asXML.length() - 10);
        
        //logger.info(ghBankReq.getTransCode()+"报文的xmlparam部分：\t"+substring);
        //字符串加密
        String s = DES3EncryptAndDecrypt.DES3EncryptMode(substring);
        xmlpara.clearContent();
        xmlpara.addText(s);
        return XMLUtils.formatXml_UTF8(document);
    }

    private byte[] createPrivateSign(String encryptXml)
    {
        byte[] bytes = null;
        try
        {
            String md5Result = GhbankRSA.MD5(encryptXml);
            bytes = GhbankRSA.RSAEncode(GhbankRSA.restorePrivateKey(GhbankRSA.decryptBASE64(PRIVATE_KEY)), md5Result.getBytes("UTF-8"));
        }
        catch(NoSuchAlgorithmException nsae)
        {
            logger.error("创建签名时出现NoSuchAlgorithmException", nsae);
        }
        catch(IOException ioe)
        {
            logger.error("创建签名时出现IOException", ioe);
        }

        return bytes;
    }
    
    public static Document parseResponseStringToXML(String responseString)
    {
        // 校验
        int xmlIndex = responseString.indexOf("<?xml");
        String sign = responseString.substring(24, xmlIndex);
        String xmlString = responseString.substring(xmlIndex);
        String xmlMd5 = null;
        String md5 = null;
        try
        {
            xmlMd5 = GhbankRSA.MD5(xmlString);
            md5 = decodeSignMd5(sign);
        }
        catch (Exception e)
        {
            logger.error("公钥解密失败！");
            return null;
        }

        // xmlSting MD5 与解析出的MD5对比
        if (!xmlMd5.equals(md5))
        {
            logger.error("签名校验失败！");
            return null;
        }
        
        Document document = null;
        Document document_temp = null;
        String decrypt = null;
        Element body = null;
        try
        {
            // 获取加密字符串并进行解析
            document = DocumentHelper.parseText(xmlString);
            Element rootElement = document.getRootElement();
            body = rootElement.element("body");
            Element xmlpara = body.element("XMLPARA");
            if(xmlpara!=null)
            {
                String asXML = xmlpara.asXML();

                decrypt = DES3EncryptAndDecrypt.DES3DecryptMode(asXML.substring(9, asXML.length() - 9));

                body.remove(xmlpara);
            }
            document_temp = DocumentHelper.parseText("<Document>" + decrypt + "</Document>");
        }
        catch(DocumentException de)
        {
            logger.error("",de);
        }
        
        List<Element> elements = document_temp.getRootElement().elements();
        for (Element e : elements)
        {
            body.add(e.createCopy());
        }
        
        return document;
    }

    /**
     * 解析收到的xml为GHBankResp
     * @param xml
     * @return
     */
    public GHBankResp parseXml(String responseString)
    {
        if(responseString==null || responseString.length()<1)
        {
            logger.error("收到的response为空！");
            return null;
        }
        else
        {
            Document document = parseResponseStringToXML(responseString);
            // 将XML转成MODEL
            logger.info("收到Response："+document.asXML());
            return parseXmlToGHBankResp(document);
        }

    }

    private static String decodeSignMd5(String sign) throws Exception
    {
        return GhbankRSA.RSADecode(GhbankRSA.restorePublicKey(GhbankRSA.decryptBASE64(PUBLIC_KEY)), GhbankRSA.hexStringToByte(sign));
    }

    protected abstract  GHBankResp parseXmlToGHBankResp(Document document);

    /**
     * 把xml报文解析成GHBankResp对象
     * 
     * @param document
     * @param ghBankResp
     * @return
     * @throws ParseException
     */
    public static GHBankResp parseXmlToGHBankResp(Document document, GHBankResp ghBankResp)
    {

        // 处理Response报文中header部分
        Element rootElement = document.getRootElement();
        Element header = rootElement.element("header");
        Element channelFlow = header.element("channelFlow");
        Element serverFlow = header.element("serverFlow");
        Element serverDate = header.element("serverDate");
        Element serverTime = header.element("serverTime");
        Element channelDate = header.element("channelDate");
        Element channelTime = header.element("channelTime");
        //Element encryptData = header.element("encryptData");
        Element status = header.element("status");
        Element errorCode = header.element("errorCode");
        Element errorMsg = header.element("errorMsg");

        // 向实体类中set字段
        ghBankResp.setChannelFlow(channelFlow == null ? serverFlow.getText() : channelFlow.getText());

        if (serverDate != null && serverTime != null)
        {
            try
            {
                ghBankResp.setServerDateTime(dataFormat.parse(serverDate.getText() + serverTime.getText()));
            }
            catch (ParseException pe)
            {
                logger.error("解析服务器传来的时间时出现异常", pe);
            }
        }
        else if (channelDate != null && channelTime != null)
        {
            try
            {
                ghBankResp.setServerDateTime(dataFormat.parse(channelDate.getText() + channelTime.getText()));
            }
            catch (ParseException pe)
            {
                logger.error("解析服务器传来的时间时出现异常", pe);
            }
        }
        else
        {
            logger.warn("此报文中即不包含serverDate，也不包含channelDate");
        }

        //ghBankResp.setEncryptData(encryptData == null ? null : encryptData.getText());
        ghBankResp.setStatus(status == null ? (short) 0 : Short.parseShort(status.getText()));
        ghBankResp.setErrorCode(errorCode == null ? ErrorCode.SUCCESS : errorCode.getText());
        ghBankResp.setErrorMsg(errorMsg == null ? null : errorMsg.getText());

        // Response中body部分
        Element body = rootElement.element("body");
        if (body != null && body.hasContent())
        {
            Element transCode = body.element("TRANSCODE");
            ghBankResp.setTransCode(transCode.getText());

            Element merchantId = body.element("MERCHANTID");
            if (merchantId != null && merchantId.getText() != null)
            {
                if (!merchantId.getText().equals(MERCHANT_ID))
                {
                    logger.warn("【警告】Response报文中，MERCHANTID字段不合法，期待为" + MERCHANT_ID + "，实际为：" + merchantId.getText());
                }
            }
        }
        return ghBankResp;
    }
    

}
