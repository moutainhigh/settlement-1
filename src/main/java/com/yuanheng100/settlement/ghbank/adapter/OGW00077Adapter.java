package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00077Adapter extends AbstractGHBankAdapter<OGW00077Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00077Adapter.class);


    @Override
    protected Document createXml(OGW00077Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OPERFLAG").addText(String.valueOf(ghBankReq.getOperFlag()));
        xmlpara.addElement("CHECKDATE").addText(DateUtil.formatToyyyyMMdd(ghBankReq.getCheckDate()));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00077Resp ogw00077Resp = new OGW00077Resp();

        super.parseXmlToGHBankResp(document, ogw00077Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        if (body != null && body.hasContent())
        {
            Element operFlag = body.element("OPERFLAG");
            Element checkDate = body.element("CHECKDATE");
            Element returnStatus = body.element("RETURN_STATUS");
            Element returnErrorMsg = body.element("ERRORMSG");
            Element fileName = body.element("FILENAME");
            Element fileContext = body.element("FILECONTEXT");

            ogw00077Resp.setOperFlag(ConstUtils.getOperFlag(Short.parseShort(operFlag.getTextTrim())));
            try
            {
                ogw00077Resp.setCheckDate(dataFormat.parse(checkDate.getTextTrim()));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
            ogw00077Resp.setReturnStatus(ConstUtils.getReturnStatus(returnStatus.getTextTrim()));
            ogw00077Resp.setReturnErrorMsg(returnErrorMsg.getTextTrim());
            ogw00077Resp.setFileName(fileName.getTextTrim());
            ogw00077Resp.setFileContext(fileContext.getTextTrim());
        }
        

        return ogw00077Resp;
    }
}
