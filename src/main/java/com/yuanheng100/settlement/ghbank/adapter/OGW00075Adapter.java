package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00075Adapter extends AbstractGHBankAdapter<OGW00075Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00075Adapter.class);

    @Override
    protected Document createXml(OGW00075Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));
        xmlpara.addElement("LOANNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getLoanNo())));
        xmlpara.addElement("SUBSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getSubSeqNo())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00075Resp ogw00075Resp = new OGW00075Resp();

        super.parseXmlToGHBankResp(document, ogw00075Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element returnStatus = body.element("RETURN_STATUS");
        Element returnErrorMsg = body.element("ERRORMSG");
        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element dfFlag = body.element("DFFLAG");
        Element loanNo = body.element("LOANNO");
        Element bwAcName = body.element("BWACNAME");
        Element bwAcNo = body.element("BWACNO");
        Element totalNum = body.element("TOTALNUM");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");

        List<Element> rsList = body.elements("RSLIST");
        ArrayList<OGW00075Resp.RS> rss = new ArrayList<OGW00075Resp.RS>();
        for (Element rsElement : rsList)
        {
            Element rsSubSeqNo = rsElement.element("SUBSEQNO");
            Element rsAcNo = rsElement.element("ACNO");
            Element rsAcName = rsElement.element("ACNAME");
            Element rsIncomeDate = rsElement.element("INCOMEDATE");
            Element rsAmount = rsElement.element("AMOUNT");
            Element rsPrincipalAmt = rsElement.element("PRINCIPALAMT");
            Element rsIncomeAmt = rsElement.element("INCOMEAMT");
            Element rsFeeAmt = rsElement.element("FEEAMT");
            Element rsStatus = rsElement.element("STATUS");
            Element rsErrorMsg = rsElement.element("ERRMSG");
            Element rsHostDt = rsElement.element("HOSTDT");
            Element rsHostJnlNo = rsElement.element("HOSTJNLNO");
            Element rsExtFiled3 = rsElement.element("EXT_FILED3");

            OGW00075Resp.RS rs = new OGW00075Resp.RS();
            rs.setSubSeqNo(rsSubSeqNo.getTextTrim());
            rs.setAcNo(rsAcNo.getTextTrim());
            rs.setAcName(rsAcName.getTextTrim());
            try
            {
                rs.setIncomeDate(DateUtil.yyMMdd.parse(rsIncomeDate.getTextTrim()));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            rs.setAmount(new BigDecimal(rsAmount.getTextTrim()));
            rs.setPrincipalAmt(new BigDecimal(rsPrincipalAmt.getTextTrim()));
            rs.setIncomeAmt(new BigDecimal(rsIncomeAmt.getTextTrim()));
            rs.setFeeAmt(new BigDecimal(rsFeeAmt.getTextTrim()));
            rs.setStatus(StringUtils.isEmpty(rsStatus.getTextTrim()) ? null : ConstUtils.getRsStatus(rsStatus
                    .getTextTrim()));
            rs.setErrorMsg(rsErrorMsg.getTextTrim());
            try
            {
                rs.setHostDt(DateUtil.yyMMdd.parse(rsHostDt.getTextTrim()));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            rs.setHostJnlNo(rsHostJnlNo.getTextTrim());
            rs.setExtFiled3(rsExtFiled3.getTextTrim());
            rss.add(rs);
        }

        ogw00075Resp.setMerchantId(merchantId.getText());
        ogw00075Resp.setTransCode(transCode.getText());
        ogw00075Resp.setBankId(bankId.getText());

        ogw00075Resp.setReturnStatus(ConstUtils.getReturnStatus(returnStatus.getTextTrim()));
        ogw00075Resp.setReturnErrorMsg(returnErrorMsg.getTextTrim());
        ogw00075Resp.setOldReqSeqNo(oldReqSeqNo.getTextTrim());
        ogw00075Resp.setDfFlag(ConstUtils.getDFFlag(Short.parseShort(dfFlag.getTextTrim())));
        ogw00075Resp.setLoanNo(Long.parseLong(loanNo.getTextTrim()));
        ogw00075Resp.setBwAcNo(bwAcNo.getTextTrim());
        ogw00075Resp.setBwAcName(bwAcName.getTextTrim());
        ogw00075Resp.setTotalNum(Integer.valueOf(totalNum.getTextTrim()));
        ogw00075Resp.setRsList(rss);

        ogw00075Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00075Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        return ogw00075Resp;
    }


}
