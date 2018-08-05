package com.yuanheng100.settlement.ghbank.adapter;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.consts.ErrorCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00044Adapter extends AbstractGHBankAdapter<OGW00044Req>
{
    @Override
    protected Document createXml(OGW00044Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("TTRANS").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.gettTrans())));
        xmlpara.addElement("ACNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getAcNo())));
        xmlpara.addElement("RETURNURL").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getReturnUrl())));
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    public GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00044Resp ogw00044Resp = new OGW00044Resp();

        super.parseXmlToGHBankResp(document, ogw00044Resp);

        if (ogw00044Resp.getErrorCode().equals(ErrorCode.SUCCESS))
        {
            // 处理Response报文中的body部分
            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");

            Element oldReqSeqNo = body.element("OLDREQSEQNO");

            ogw00044Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        }

        return ogw00044Resp;
    }
}
