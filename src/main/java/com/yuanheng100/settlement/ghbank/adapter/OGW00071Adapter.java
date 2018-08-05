package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00071Req;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00071Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00071Adapter extends AbstractGHBankAdapter<OGW00071Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00071Adapter.class);

    @Override
    protected Document createXml(OGW00071Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OTPSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOtpSeqNo())));
        xmlpara.addElement("OTPNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOtpNo())));
        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("BWACNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcName()));
        xmlpara.addElement("BWACNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcNo()));
        xmlpara.addElement("REMARK").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getRemark())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00071Resp ogw00071Resp = new OGW00071Resp();

        super.parseXmlToGHBankResp(document, ogw00071Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element resJnlNo = body.element("RESJNLNO");
        Element transDt = body.element("TRANSDT");
        Element transTm = body.element("TRANSTM");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00071Resp.setMerchantId(merchantId.getText());
        ogw00071Resp.setTransCode(transCode.getText());
        ogw00071Resp.setBankId(bankId.getText());

        ogw00071Resp.setResJnlNo(resJnlNo.getText());
        try
        {
            ogw00071Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(transTm.getText())));
        }
        catch (ParseException e)
        {
            logger.error("交易时间解析错误！", e);
        }

        ogw00071Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00071Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00071Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00071Resp;
    }


}
