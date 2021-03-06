package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00053Adapter extends AbstractGHBankAdapter<OGW00053Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00053Adapter.class);


    @Override
    protected Document createXml(OGW00053Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));
        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00053Resp ogw00053Resp = new OGW00053Resp();

        super.parseXmlToGHBankResp(document, ogw00053Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element returnStatus = body.element("RETURN_STATUS");
        Element returnErrorMsg = body.element("ERRORMSG");
        Element resJnlNo = body.element("RESJNLNO");
        Element transDt = body.element("TRANSDT");
        Element transTm = body.element("TRANSTM");
        Element hostDt = body.element("HOSTDT");
        Element hostJnlNo = body.element("HOSTJNLNO");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00053Resp.setMerchantId(merchantId.getText());
        ogw00053Resp.setTransCode(transCode.getText());
        ogw00053Resp.setBankId(bankId.getText());

        ogw00053Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        ogw00053Resp.setReturnStatus(returnStatus == null ? null : ConstUtils.getReturnStatus(returnStatus.getText()));
        ogw00053Resp.setReturnErrorMsg(returnErrorMsg == null ? null : returnErrorMsg.getText());
        ogw00053Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00053Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? null : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }
        ogw00053Resp.setHostDt(hostDt == null ? null : hostDt.getText());
        ogw00053Resp.setHostJnlNo(hostJnlNo == null ? null : hostJnlNo.getText());

        ogw00053Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00053Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00053Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00053Resp;
    }


}
