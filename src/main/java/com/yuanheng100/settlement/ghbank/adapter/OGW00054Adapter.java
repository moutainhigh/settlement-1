package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00054Adapter extends AbstractGHBankAdapter<OGW00054Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00054Adapter.class);


    @Override
    protected Document createXml(OGW00054Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("BWACNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcName()));
        xmlpara.addElement("BWACNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcNo()));
        xmlpara.addElement("AMOUNT").addText(StringUtils.trimToEmpty(ghBankReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
        xmlpara.addElement("TOTALNUM").addText(String.valueOf(ghBankReq.getTotalNum()));
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));

        Element feedbackList = xmlpara.addElement("FEEDBACKLIST");
        for(OGW00054Req.Feedback feedback :ghBankReq.getFeedbackList()){
            feedbackList.addElement("SUBSEQNO").addText(StringUtils.trimToEmpty(feedback.getSubSeqNo()));
            feedbackList.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(feedback.getOldReqSeqNo()));
            feedbackList.addElement("ACNO").addText(StringUtils.trimToEmpty(feedback.getAcNo()));
            feedbackList.addElement("ACNAME").addText(StringUtils.trimToEmpty(feedback.getAcName()));
            feedbackList.addElement("AMOUNT").addText(StringUtils.trimToEmpty(feedback.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
            feedbackList.addElement("REMARK").addText(StringUtils.trimToEmpty(feedback.getRemark()));
            feedbackList.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(feedback.getExtFiled3()));
        }
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00054Resp ogw00054Resp = new OGW00054Resp();

        super.parseXmlToGHBankResp(document, ogw00054Resp);

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

        ogw00054Resp.setMerchantId(merchantId.getText());
        ogw00054Resp.setTransCode(transCode.getText());
        ogw00054Resp.setBankId(bankId.getText());

        ogw00054Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00054Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? null : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }

        ogw00054Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00054Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00054Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00054Resp;
    }


}
