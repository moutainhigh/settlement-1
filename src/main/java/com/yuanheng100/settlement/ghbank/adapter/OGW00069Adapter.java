package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00069Req;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00069Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00069Adapter extends AbstractGHBankAdapter<OGW00069Req>
{

    @Override
    protected Document createXml(OGW00069Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));
        xmlpara.addElement("TTRANS").addText(String.valueOf(ghBankReq.gettTrans()));
        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("BWACNAME").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcName()));
        xmlpara.addElement("BWACNO").addText(StringUtils.trimToEmpty(ghBankReq.getBwAcNo()));
        xmlpara.addElement("AMOUNT").addText(ghBankReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        xmlpara.addElement("REMARK").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getRemark())));
        xmlpara.addElement("RETURNURL").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getReturnUrl())));
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00069Resp ogw00069Resp = new OGW00069Resp();

        super.parseXmlToGHBankResp(document, ogw00069Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element resJnlNo = body.element("RESJNLNO");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00069Resp.setMerchantId(merchantId.getText());
        ogw00069Resp.setTransCode(transCode.getText());
        ogw00069Resp.setBankId(bankId.getText());

        ogw00069Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        ogw00069Resp.setResJnlNo(resJnlNo.getText());

        ogw00069Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00069Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00069Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00069Resp;
    }


}
