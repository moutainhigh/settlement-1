package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00068Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00068Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00068Adapter extends AbstractGHBankAdapter<OGW00068Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00068Adapter.class);

    @Override
    protected Document createXml(OGW00068Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00068Resp ogw00068Resp = new OGW00068Resp();

        super.parseXmlToGHBankResp(document, ogw00068Resp);

        if(ogw00068Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {
            // 解析当前业务的内容
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");


            Element resJnlNo = body.element("RESJNLNO");
            Element transDt = body.element("TRANSDT");
            Element transTm = body.element("TRANSTM");
            Element oldReqSeqNo = body.element("OLDREQSEQNO");
            Element returnStatus = body.element("RETURN_STATUS");
            Element returnErrorMsg = body.element("ERRORMSG");
            Element loanNo = body.element("LOANNO");
            Element bwAcName = body.element("BWACNAME");
            Element bwAcNo = body.element("BWACNO");
            Element amount = body.element("AMOUNT");
            //Element hostDt = body.element("HOSTDT");
            Element hostJnlNo = body.element("HOSTJNLNO");

            ogw00068Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
            ogw00068Resp.setOldReqSeqNo(oldReqSeqNo == null ? null : oldReqSeqNo.getTextTrim());
            if (transDt != null)
            {
                try
                {
                    ogw00068Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(transTm.getText())));
                }
                catch (ParseException e)
                {
                    logger.error("交易时间解析错误！", e);
                }
            }

            ogw00068Resp.setReturnStatus(returnStatus == null ? null : ConstUtils.getReturnStatus(returnStatus.getTextTrim()));
            ogw00068Resp.setReturnErrorMsg(returnErrorMsg == null ? null : returnErrorMsg.getTextTrim());
            ogw00068Resp.setLoanNo(loanNo == null ? 0 : Long.parseLong(loanNo.getTextTrim()));
            ogw00068Resp.setBwAcNo(bwAcNo == null ? null : bwAcNo.getTextTrim());
            ogw00068Resp.setBwAcName(bwAcName == null ? null : bwAcName.getTextTrim());
            ogw00068Resp.setAmount(amount == null ? BigDecimal.ZERO : new BigDecimal(amount.getTextTrim()));

            ogw00068Resp.setHostJnlNo(hostJnlNo==null?null:hostJnlNo.getTextTrim());
        }


        return ogw00068Resp;
    }


}
