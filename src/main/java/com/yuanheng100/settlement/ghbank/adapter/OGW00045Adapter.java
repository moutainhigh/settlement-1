package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Req;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00045Adapter extends AbstractGHBankAdapter<OGW00045Req>
{
    @Override
    protected Document createXml(OGW00045Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));
        xmlpara.addElement("TTRANS").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.gettTrans())));
        xmlpara.addElement("ACNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcNo())));
        xmlpara.addElement("ACNAME").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcName())));
        xmlpara.addElement("AMOUNT").addText(ghBankReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
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
        OGW00045Resp ogw00045Resp = new OGW00045Resp();

        super.parseXmlToGHBankResp(document, ogw00045Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element orderStatus = body.element("ORDERSTATUS");

        ogw00045Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        ogw00045Resp.setOrderStatus(orderStatus.getText());

        return ogw00045Resp;
    }


}
