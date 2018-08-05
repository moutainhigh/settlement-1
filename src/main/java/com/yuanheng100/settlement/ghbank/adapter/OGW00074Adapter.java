package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Resp;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00074Adapter extends AbstractGHBankAdapter<OGW00074Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00074Adapter.class);

    @Override
    protected Document createXml(OGW00074Req ghBankReq)
    {
        
        Document document = super.createXml(ghBankReq);
        
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));
        xmlpara.addElement("DFFLAG").addText(String.valueOf(ghBankReq.getDfFlag()));
        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("BWACNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcName()));
        xmlpara.addElement("BWACNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcNo()));
        xmlpara.addElement("TOTALNUM").addText(String.valueOf(ghBankReq.getTotalNum()));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));

        System.out.println("---ghBankReq.getRepayList() = "+ghBankReq.getRepayList());
        
        if (ghBankReq.getRepayList() != null && ghBankReq.getRepayList().size() > 0)
        {
            for (OGW00074Req.RepayItem repay : ghBankReq.getRepayList())
            {
                Element repayList = xmlpara.addElement("REPAYLIST");
                repayList.addElement("SUBSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(repay.getSubSeqNo())));
                repayList.addElement("ACNO").addText(StringUtils.trimToEmpty(String.valueOf(repay.getAcNo())));
                repayList.addElement("ACNAME").addText(StringUtils.trimToEmpty(String.valueOf(repay.getAcName())));
                //repayList.addElement("INCOMEDATE").addText(repay.getIncomeDate()==null?"":DateUtil.formatToyyyyMMdd(repay.getIncomeDate()));
                repayList.addElement("AMOUNT").addText(repay.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                //repayList.addElement("PRINCIPALAMT").addText(repay.getPrincipalAmt()==null?"":repay.getPrincipalAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                //repayList.addElement("INCOMEAMT").addText(repay.getIncomeAmt()==null?"":repay.getIncomeAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                //repayList.addElement("FEEAMT").addText(repay.getFeeAmt()==null?"":repay.getFeeAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            }
        }

        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00074Resp ogw00074Resp = new OGW00074Resp();

        super.parseXmlToGHBankResp(document, ogw00074Resp);

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

        ogw00074Resp.setMerchantId(merchantId.getText());
        ogw00074Resp.setTransCode(transCode.getText());
        ogw00074Resp.setBankId(bankId.getText());

        ogw00074Resp.setResJnlNo(resJnlNo.getText());
        try
        {
            ogw00074Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(transTm.getText())));
        }
        catch (ParseException e)
        {
            logger.error("交易时间解析错误！", e);
        }

        ogw00074Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00074Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00074Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00074Resp;
    }


}
