package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00063Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00063Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00063Adapter extends AbstractGHBankAdapter<OGW00063Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00063Adapter.class);

    @Override
    protected Document createXml(OGW00063Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("LOANNO").addText(String.valueOf(ghBankReq.getLoanNo()));
        xmlpara.addElement("CANCELREASON").addText(String.valueOf(ghBankReq.getCancelReason()));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));

        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00063Resp ogw00063Resp = new OGW00063Resp();

        super.parseXmlToGHBankResp(document, ogw00063Resp);

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

        ogw00063Resp.setMerchantId(merchantId.getText());
        ogw00063Resp.setTransCode(transCode.getText());
        ogw00063Resp.setBankId(bankId.getText());

        ogw00063Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00063Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? null : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }
        ogw00063Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00063Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00063Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00063Resp;
    }


}
