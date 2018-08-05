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
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00065Adapter extends AbstractGHBankAdapter<OGW00065Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00065Adapter.class);


    @Override
    protected Document createXml(OGW00065Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("BWACNAME").addText(String.valueOf(ghBankReq.getBwAcName()));
        xmlpara.addElement("BWACNO").addText(String.valueOf(ghBankReq.getBwAcNo()));
        xmlpara.addElement("ACMNGAMT").addText(ghBankReq.getAcMngAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        xmlpara.addElement("GUARANTAMT").addText(ghBankReq.getGuarantAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        xmlpara.addElement("REMARK").addText(StringUtils.trimToEmpty(ghBankReq.getRemark()));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));

        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00065Resp ogw00065Resp = new OGW00065Resp();

        super.parseXmlToGHBankResp(document, ogw00065Resp);

        if(ogw00065Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {
            // 解析当前业务的内容
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");

            Element resJnlNo = body.element("RESJNLNO");
            Element transDt = body.element("TRANSDT");
            Element transTm = body.element("TRANSTM");


            ogw00065Resp.setResJnlNo(resJnlNo.getText());
            try
            {
                ogw00065Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }

        return ogw00065Resp;
    }


}
