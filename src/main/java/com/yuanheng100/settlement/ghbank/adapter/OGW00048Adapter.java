package com.yuanheng100.settlement.ghbank.adapter;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Req;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00048Adapter extends AbstractGHBankAdapter<OGW00048Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00048Adapter.class);


    @Override
    protected Document createXml(OGW00048Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("TRANSDT").addText(DateUtil.formatToyyyyMMdd(ghBankReq.getTransDt()));
        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(ghBankReq.getOldReqSeqNo()));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00048Resp ogw00048Resp = new OGW00048Resp();

        super.parseXmlToGHBankResp(document, ogw00048Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element resJnlNo = body.element("RESJNLNO");
        Element transDt = body.element("TRANSDT");
        Element transTm = body.element("TRANSTM");
        Element returnStatus = body.element("RETURN_STATUS");
        Element returnErrorMsg = body.element("ERRORMSG");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");
        Element extFiled3 = body.element("EXT_FILED3");

        ogw00048Resp.setMerchantId(merchantId.getText());
        ogw00048Resp.setTransCode(transCode.getText());
        ogw00048Resp.setBankId(bankId.getText());

        ogw00048Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        ogw00048Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00048Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? "000000" : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }
        ogw00048Resp.setReturnStatus(returnStatus == null ? null : ConstUtils.getReturnStatus(returnStatus.getText()));
        ogw00048Resp.setReturnErrorMsg(returnErrorMsg == null ? null : returnErrorMsg.getText());

        ogw00048Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00048Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        ogw00048Resp.setExtFiled3(extFiled3 == null ? null : extFiled3.getText());
        return ogw00048Resp;
    }


}
