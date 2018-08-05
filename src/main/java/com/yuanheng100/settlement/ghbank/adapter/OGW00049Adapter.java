package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Req;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00049Adapter extends AbstractGHBankAdapter<OGW00049Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00049Adapter.class);


    @Override
    protected Document createXml(OGW00049Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("BUSTYPE").addText(StringUtils.trimToEmpty(ghBankReq.getBusType()));
        xmlpara.addElement("ACNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcNo())));
        xmlpara.addElement("ACNAME").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcName())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00049Resp ogw00049Resp = new OGW00049Resp();
        super.parseXmlToGHBankResp(document, ogw00049Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element acNo = body.element("ACNO");
        Element acName = body.element("ACNAME");
        Element acctBal = body.element("ACCTBAL");
        Element availableBal = body.element("AVAILABLEBAL");
        Element frozBl = body.element("FROZBL");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00049Resp.setMerchantId(merchantId.getText());
        ogw00049Resp.setTransCode(transCode.getText());
        ogw00049Resp.setBankId(bankId.getText());

        ogw00049Resp.setAcNo(acNo.getText());
        ogw00049Resp.setAcName(acName.getText());
        ogw00049Resp.setAcctBal(acctBal == null ? null : new BigDecimal(acctBal.getText()));
        ogw00049Resp.setAvailableBal(availableBal == null ? null : new BigDecimal(availableBal.getText()));
        ogw00049Resp.setFrozBl(frozBl == null ? null : new BigDecimal(frozBl.getText()));

        ogw00049Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00049Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00049Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00049Resp;
    }


}
