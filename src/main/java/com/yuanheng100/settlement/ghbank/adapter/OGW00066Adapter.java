package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00066Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00066Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00066Adapter extends AbstractGHBankAdapter<OGW00066Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00066Adapter.class);

    
    @Override
    protected Document createXml(OGW00066Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));
        xmlpara.addElement("LOANNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getLoanNo())));
        xmlpara.addElement("OLDTTJNL").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldTTJnl())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00066Resp ogw00066Resp = new OGW00066Resp();

        super.parseXmlToGHBankResp(document, ogw00066Resp);
        
        if(ogw00066Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {

            // 解析当前业务的内容
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");


            Element returnStatus = body.element("RETURN_STATUS");
            Element returnErrorMsg = body.element("ERRORMSG");
            Element oldReqSeqNo = body.element("OLDREQSEQNO");
            Element loanNo = body.element("LOANNO");
            Element bwAcName = body.element("BWACNAME");
            Element bwAcNo = body.element("BWACNO");
            Element acMngAmt = body.element("ACMNGAMT");
            Element guarantAmt = body.element("GUARANTAMT");


            Element rsList = body.element("RSLIST");
            Iterator<Element> iterator = rsList.elementIterator();
            ArrayList<OGW00066Resp.RS> rss = new ArrayList<OGW00066Resp.RS>();
            while (iterator.hasNext())
            {
                Element rsReqSeqNo = iterator.next();
                Element rsLoanNo = iterator.next();
                Element rsAcNo = iterator.next();
                Element rsAcName = iterator.next();
                Element rsAmount = iterator.next();
                Element rsStatus = iterator.next();
                Element rsErrorMsg = iterator.next();
                Element rsHostDt = iterator.next();
                Element rsHostJnlNo = iterator.next();
                Element rsExtFiled3 = iterator.next();

                OGW00066Resp.RS rs = new OGW00066Resp.RS();
                rs.setReqSeqNo(rsReqSeqNo.getTextTrim());
                rs.setLoanNo(Long.parseLong(rsLoanNo.getTextTrim()));
                rs.setAcNo(rsAcNo.getTextTrim());
                rs.setAcName(rsAcName.getTextTrim());
                rs.setAmount(new BigDecimal(rsAmount.getTextTrim()));
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

            ogw00066Resp.setReturnStatus(ConstUtils.getReturnStatus(returnStatus.getTextTrim()));
            ogw00066Resp.setReturnErrorMsg(returnErrorMsg.getTextTrim());
            ogw00066Resp.setOldReqSeqNo(oldReqSeqNo.getTextTrim());
            ogw00066Resp.setLoanNo(Long.parseLong(loanNo.getTextTrim()));
            ogw00066Resp.setBwAcNo(bwAcNo.getTextTrim());
            ogw00066Resp.setBwAcName(bwAcName.getTextTrim());
            ogw00066Resp.setGuarantAmt(new BigDecimal(guarantAmt.getTextTrim()));
            ogw00066Resp.setRsList(rss);

        }
            
        return ogw00066Resp;
    }


}
