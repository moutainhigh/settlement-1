package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TResp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW0015TAdapter extends AbstractGHBankAdapter<OGW0015TResp>
{
    private static Logger logger = LoggerFactory.getLogger(OGW0015TAdapter.class);

    
    @Override
    protected Document createXml(OGW0015TResp ogw0015TResp)
    {
        Document document = super.createXml(ogw0015TResp);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("RETURNCODE").addText(StringUtils.trimToEmpty(ogw0015TResp.getReturnCode()));
        xmlpara.addElement("RETURNMSG").addText(StringUtils.trimToEmpty(ogw0015TResp.getReturnCode()));
        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(ogw0015TResp.getOldReqSeqNo()));

        return document;
    }
    
    @Override
    protected OGW00041Resp parseXmlToGHBankResp(Document document)
    {
        OGW00041Resp ogw00041Resp = new OGW00041Resp();

        super.parseXmlToGHBankResp(document, ogw00041Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");
        Element otpSeqNo = body.element("OTPSEQNO");
        Element otpIndex = body.element("OTPINDEX");
        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00041Resp.setMerchantId(merchantId.getText());
        ogw00041Resp.setTransCode(transCode.getText());
        ogw00041Resp.setBankId(bankId.getText());
        ogw00041Resp.setOtpSeqNo(otpSeqNo == null ? null : otpSeqNo.getText());
        ogw00041Resp.setOtpIndex(otpIndex == null ? null : otpIndex.getText());
        ogw00041Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00041Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00041Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00041Resp;
    }
    

}
