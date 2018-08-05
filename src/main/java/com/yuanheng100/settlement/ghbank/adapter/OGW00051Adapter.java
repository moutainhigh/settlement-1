package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Resp;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00051Adapter extends AbstractGHBankAdapter<OGW00051Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00051Adapter.class);

    
    @Override
    protected Document createXml(OGW00051Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("LOANNO").addText(String.valueOf((ghBankReq.getLoanNo())));
        xmlpara.addElement("INVESTID").addText(String.valueOf(ghBankReq.getInvestId()));
        xmlpara.addElement("INVESTOBJNAME").addText(StringUtils.trimToEmpty(ghBankReq.getInvestObjName()));
        if(ghBankReq.getInvestObjInfo()!=null)
        {
            xmlpara.addElement("INVESTOBJINFO").addText(StringUtils.trimToEmpty(ghBankReq.getInvestObjInfo()));
        }
        if(ghBankReq.getMinInvestAmt()!=null)
        {
            xmlpara.addElement("MININVESTAMT").addText(StringUtils.trimToEmpty(ghBankReq.getMinInvestAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
        }
        if(ghBankReq.getMaxInvestAmt()!=null)
        {
            xmlpara.addElement("MAXINVESTAMT").addText(StringUtils.trimToEmpty(ghBankReq.getMaxInvestAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
        }
        xmlpara.addElement("INVESTOBJAMT").addText(StringUtils.trimToEmpty(ghBankReq.getInvestObjAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
        xmlpara.addElement("INVESTBEGINDATE").addText(StringUtils.trimToEmpty(DateUtil.formatToyyyyMMdd(ghBankReq.getInvestBeginDate())));
        xmlpara.addElement("INVESTENDDATE").addText(StringUtils.trimToEmpty(DateUtil.formatToyyyyMMdd(ghBankReq.getInvestEndDate())));
        if(ghBankReq.getRepayDate()!=null)
        {
            xmlpara.addElement("REPAYDATE").addText(StringUtils.trimToEmpty(DateUtil.formatToyyyyMMdd(ghBankReq.getRepayDate())));
        }
        xmlpara.addElement("YEARRATE").addText(StringUtils.trimToEmpty(ghBankReq.getYearRate().setScale(6, BigDecimal.ROUND_HALF_EVEN).toString()));
        xmlpara.addElement("INVESTRANGE").addText(ghBankReq.getInvestRange().toString());
        
        if(ghBankReq.getRatesType()!=null)
        {
            xmlpara.addElement("RATESTYPE").addText(StringUtils.trimToEmpty(ghBankReq.getRatesType()));
        }
        
        if(ghBankReq.getRepaysType()!=null)
        {
            xmlpara.addElement("REPAYSTYPE").addText(StringUtils.trimToEmpty(ghBankReq.getRepaysType()));            
        }
        
        xmlpara.addElement("INVESTOBJSTATE").addText(String.valueOf(ghBankReq.getInvestObjState()));
        xmlpara.addElement("BWTOTALNUM").addText(ghBankReq.getBwTotalNum().toString());
        
        if(ghBankReq.getRemark()!=null)
        {
            xmlpara.addElement("REMARK").addText(StringUtils.trimToEmpty(ghBankReq.getRemark()));
        }
        
        
        if(ghBankReq.getZrFlag()!=null)
        {
            xmlpara.addElement("ZRFLAG").addText(StringUtils.trimToEmpty(ghBankReq.getZrFlag()?"1":"0"));
        }
        if(ghBankReq.getRefLoanNo()!=null)
        {
            xmlpara.addElement("REFLOANNO").addText(StringUtils.trimToEmpty(ghBankReq.getRefLoanNo()));            
        }
        
        if(ghBankReq.getOldReqSeq()!=null)
        {
            xmlpara.addElement("OLDREQSEQ").addText(StringUtils.trimToEmpty(ghBankReq.getOldReqSeq()));
        }
        
        if(ghBankReq.getExtFiled1()!=null)
        {
            xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        }
        

        Element bwlist = xmlpara.addElement("BWLIST");
        bwlist.addElement("BWACNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcName()));
        if(ghBankReq.getBwIdType()!=null)
        {
            bwlist.addElement("BWIDTYPE").addText(ghBankReq.getBwIdType().toString());            
        }
        
        if(ghBankReq.getBwIdNo()!=null)
        {
            bwlist.addElement("BWIDNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwIdNo()));            
        }
        
        bwlist.addElement("BWACNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcNo()));
        if(ghBankReq.getBwAcBankId()!=null)
        {
            bwlist.addElement("BWACBANKID").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcBankId()));
        }
        
        if(ghBankReq.getBwAcBankName()!=null)
        {
            bwlist.addElement("BWACBANKNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcBankName()));            
        }
        bwlist.addElement("BWAMT").addText(StringUtils.trimToEmpty(ghBankReq.getBwAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));

        bwlist.addElement("MORTGAGEID").addText(StringUtils.trimToEmpty(ghBankReq.getMortgageId()));
        bwlist.addElement("MORTGAGEINFO").addText(StringUtils.trimToEmpty(ghBankReq.getMortgageInfo()));
        bwlist.addElement("CHECKDATE").addText(StringUtils.trimToEmpty(DateUtil.formatToyyyyMMdd(ghBankReq.getCheckDate())));
        bwlist.addElement("REMARK").addText(StringUtils.trimToEmpty(ghBankReq.getBwRemark()));
        bwlist.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        bwlist.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));

        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00051Resp ogw00051Resp = new OGW00051Resp();

        super.parseXmlToGHBankResp(document, ogw00051Resp);

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

        ogw00051Resp.setMerchantId(merchantId.getText());
        ogw00051Resp.setTransCode(transCode.getText());
        ogw00051Resp.setBankId(bankId.getText());

        ogw00051Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00051Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? null : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }

        ogw00051Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00051Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00051Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00051Resp;
    }


}
