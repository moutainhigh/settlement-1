package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TTestReq;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Resp;
import com.yuanheng100.settlement.ghbank.utils.HttpUtils;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW0014TTestAdapter extends AbstractGHBankAdapter<OGW0014TTestReq>
{
    private static Logger logger = LoggerFactory.getLogger(OGW0014TAdapter.class);

    
    /**
     * 模拟银行方，制造一个request xml。
     * 
     * 此方法应属于单元测试，所以数据项用模拟的，而非真的
     * @return
     */
    @Override
    protected Document createXml(OGW0014TTestReq ogw0014TReq)
    {
        String dateFormat = dataFormat.format(new Date());

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Document");
        // 构建Header
        Element header = root.addElement("header");

        header.addElement("channelCode").addText(StringUtils.trimToEmpty(ogw0014TReq.getChannelCode()));
        header.addElement("channelFlow").addText(ogw0014TReq.getChannelFlow());
        header.addElement("channelDate").addText(StringUtils.trimToEmpty(dateFormat.substring(0, 8)));
        header.addElement("channelTime").addText(StringUtils.trimToEmpty(dateFormat.substring(8)));
        header.addElement("encryptData").addText(StringUtils.trimToEmpty(ogw0014TReq.getEncryptData()));

        // 构建Body
        Element body = root.addElement("body");
        body.addElement("MERCHANTID").addText(StringUtils.trimToEmpty(ogw0014TReq.getMerchantId()));
        body.addElement("APPID").addText(AppId.PC.getCode());
        body.addElement("TRANSCODE").addText(StringUtils.trimToEmpty(ogw0014TReq.getTransCode()));

        // 构建XMLPARA
        Element xmlpara = body.addElement("XMLPARA");
        xmlpara.addElement("LOANNO").addText(String.valueOf((ogw0014TReq.getLoanNo())));
        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(ogw0014TReq.getOldReqSeqNo()));
        xmlpara.addElement("ACNO").addText(StringUtils.trimToEmpty(ogw0014TReq.getAcNo()));
        xmlpara.addElement("ACNAME").addText(StringUtils.trimToEmpty(ogw0014TReq.getAcName()));
        xmlpara.addElement("CANCELREASON").addText(StringUtils.trimToEmpty(ogw0014TReq.getCancelReason()));
        xmlpara.addElement("HOSTDT").addText("");
        xmlpara.addElement("HOSTJNLNO").addText("");
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ogw0014TReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ogw0014TReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ogw0014TReq.getExtFiled3()));
        
        return document;
    }
    
    
    @Override
    public GHBankResp postAndReceive(OGW0014TTestReq ghBankReq)
    {
        String postString = getPostString(ghBankReq);
        
        System.out.println("postString="+postString);
        //发送请求，获取返回值
        String xml = HttpUtils.post("http://127.0.0.1:83/settlement/ghbank/callback/ogw0014t", postString);
        //解析XML获得GHBankReq对象
        
        GHBankResp resp = parseXml(xml);
        return resp;
    }



    @Override
    protected OGW00041Resp parseXmlToGHBankResp(Document document)
    {
        OGW00041Resp ogw00041Resp = new OGW00041Resp();

        super.parseXmlToGHBankResp(document, ogw00041Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");
        Element otpSeqNo = body.element("OTPSEQNO");
        Element otpIndex = body.element("OTPINDEX");
        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00041Resp.setMerchantId(merchantId.getText());
        ogw00041Resp.setTransCode(transCode.getText());
        ogw00041Resp.setBankId(bankId.getText());
        ogw00041Resp.setOtpSeqNo(otpSeqNo == null ? null : otpSeqNo.getText());
        ogw00041Resp.setOtpIndex(otpIndex == null ? null : otpIndex.getText());
        ogw00041Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00041Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00041Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00041Resp;
    }
    
    
    


}
