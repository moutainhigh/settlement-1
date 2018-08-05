package com.yuanheng100.settlement.ghbank.adapter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00042Adapter extends AbstractGHBankAdapter<OGW00042Req>
{
    
    private static Logger logger = Logger.getLogger(OGW00042Adapter.class);
    
    
    @Override
    protected Document createXml(OGW00042Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("TTRANS").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.gettTrans())));
        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));
        xmlpara.addElement("ACNAME").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcName())));
        xmlpara.addElement("IDTYPE").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getIdType())));
        xmlpara.addElement("IDNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getIdNo())));
        xmlpara.addElement("MOBILE").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getMobile())));
        xmlpara.addElement("EMAIL").addText(ghBankReq.getEmail()==null?"":ghBankReq.getEmail());
        xmlpara.addElement("RETURNURL").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getReturnUrl())));
        xmlpara.addElement("CUSTMNGRNO").addText(ghBankReq.getCustMngrNo()==null?"":ghBankReq.getCustMngrNo());
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    public GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00042Resp ogw00042Resp = new OGW00042Resp();

        super.parseXmlToGHBankResp(document, ogw00042Resp);

        if (ogw00042Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {
            // 处理Response报文中的body部分
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");

            Element oldReqSeqNo = body.element("OLDREQSEQNO");
            Element acName = body.element("ACNAME");
            Element idType = body.element("IDTYPE");
            Element idNo = body.element("IDNO");
            Element mobile = body.element("MOBILE");
            Element acNo = body.element("ACNO");

            ogw00042Resp.setOldReqSeqNo(oldReqSeqNo.getText());
            ogw00042Resp.setRespAcName(acName.getText());
            ogw00042Resp.setRespIdType(Short.parseShort(idType.getText()));
            ogw00042Resp.setRespIdNo(idNo.getText());
            ogw00042Resp.setRespMobile(Long.parseLong(mobile.getText()));
            ogw00042Resp.setAcNo(acNo.getText());
        }
        return ogw00042Resp;
    }


}
