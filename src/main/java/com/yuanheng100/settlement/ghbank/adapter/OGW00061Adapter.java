package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Req;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00061Adapter extends AbstractGHBankAdapter<OGW00061Req>
{

    @Override
    protected Document createXml(OGW00061Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));
        xmlpara.addElement("TTRANS").addText(String.valueOf(ghBankReq.gettTrans()));
        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(ghBankReq.getOldReqSeqNo()));
        xmlpara.addElement("OLDREQNUMBER").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqNumber())));
        xmlpara.addElement("OLDREQNAME").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqName())));
        xmlpara.addElement("ACCNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAccNo())));
        xmlpara.addElement("CUSTNAME").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getCustName())));
        xmlpara.addElement("AMOUNT").addText(ghBankReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        xmlpara.addElement("PREINCOME").addText(ghBankReq.getPreIncome().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        xmlpara.addElement("REMARK").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getRemark())));
        xmlpara.addElement("RETURNURL").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getReturnUrl())));
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    public GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00061Resp ogw00061Resp = new OGW00061Resp();
        super.parseXmlToGHBankResp(document, ogw00061Resp);
        
        if(ogw00061Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {
            // 解析当前业务的内容
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");

            Element oldReqSeqNo = body.element("OLDREQSEQNO");

            ogw00061Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        }

        return ogw00061Resp;
    }


}
