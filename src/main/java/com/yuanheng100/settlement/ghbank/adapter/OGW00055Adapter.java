package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Resp;
import com.yuanheng100.settlement.ghbank.utils.ConstUtils;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00055Adapter extends AbstractGHBankAdapter<OGW00055Req>
{
    private static Logger logger = LoggerFactory.getLogger(OGW00055Adapter.class);


    @Override
    protected Document createXml(OGW00055Req ghBankReq)
    {
        Document document = super.createXml(ghBankReq);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("MERCHANTNAME").addText(StringUtils.trimToEmpty(ghBankReq.getMerchantName()));

        xmlpara.addElement("OLDREQSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getOldReqSeqNo())));
        xmlpara.addElement("SUBSEQNO").addText(StringUtils.trimToEmpty(String.valueOf(ghBankReq.getSubSeqNo())));

        xmlpara.addElement("EXT_FILED1").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled1()));
        xmlpara.addElement("EXT_FILED2").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled2()));
        xmlpara.addElement("EXT_FILED3").addText(StringUtils.trimToEmpty(ghBankReq.getExtFiled3()));
        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        OGW00055Resp ogw00055Resp = new OGW00055Resp();

        super.parseXmlToGHBankResp(document, ogw00055Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element merchantId = body.element("MERCHANTID");
        Element transCode = body.element("TRANSCODE");
        Element bankId = body.element("BANKID");

        Element resJnlNo = body.element("RESJNLNO");
        Element transDt = body.element("TRANSDT");
        Element transTm = body.element("TRANSTM");
        Element loanNo = body.element("LOANNO");
        Element bwAcName = body.element("BWACNAME");
        Element bwAcNo = body.element("BWACNO");

        Element amount = body.element("AMOUNT");
        Element totalNum = body.element("TOTALNUM");
        Element returnStatus = body.element("RETURN_STATUS");
        Element returnErrorMsg = body.element("ERRORMSG");

        Element extFiled1 = body.element("EXT_FILED1");
        Element extFiled2 = body.element("EXT_FILED2");

        Element rsList = body.element("RSLIST");
        ArrayList<OGW00055Resp.RS> rss = null;
        if (rsList != null)
        {
            Iterator<Element> iterator = rsList.elementIterator();
            rss = new ArrayList<OGW00055Resp.RS>();
            while (iterator.hasNext())
            {
                Element rsSubSeqNo = iterator.next();
                Element rsOldReqSeqNo = iterator.next();
                Element rsAcNo = iterator.next();
                Element rsAcName = iterator.next();
                Element rsAmount = iterator.next();
                Element rsRemark = iterator.next();
                Element rsStatus = iterator.next();
                Element rsErrorMsg = iterator.next();
                Element rsExtFiled3 = iterator.next();

                OGW00055Resp.RS rs = new OGW00055Resp.RS(rsSubSeqNo.getTextTrim(), rsOldReqSeqNo.getTextTrim(),
                        rsAcNo.getTextTrim(), rsAcName.getTextTrim(), new BigDecimal(rsAmount.getTextTrim()));
                rs.setRemark(rsRemark.getTextTrim());
                rs.setStatus(StringUtils.isEmpty(rsStatus.getTextTrim()) ? null : ConstUtils.getRsStatus(rsStatus
                        .getTextTrim()));
                rs.setErrorMsg(rsErrorMsg.getTextTrim());
                rs.setExtFiled3(rsExtFiled3.getTextTrim());

                rss.add(rs);
            }
        }

        ogw00055Resp.setReturnStatus(returnStatus == null ? null : ConstUtils.getReturnStatus(returnStatus.getText()));
        ogw00055Resp.setReturnErrorMsg(returnErrorMsg == null ? null : returnErrorMsg.getText());
        ogw00055Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());

        ogw00055Resp.setMerchantId(merchantId.getText());
        ogw00055Resp.setTransCode(transCode.getText());
        ogw00055Resp.setBankId(bankId == null ? null : bankId.getText());

        ogw00055Resp.setLoanNo(Long.parseLong(loanNo.getText()));
        ogw00055Resp.setBwAcName(bwAcName == null ? null : bwAcName.getText());
        ogw00055Resp.setBwAcNo(bwAcNo == null ? null : bwAcNo.getText());

        ogw00055Resp.setAmount(amount == null ? null : new BigDecimal(amount.getText()));
        ogw00055Resp.setTotalNum(totalNum == null ? null : Integer.parseInt(totalNum.getText()));
        ogw00055Resp.setRsList(rss);

        ogw00055Resp.setResJnlNo(resJnlNo == null ? null : resJnlNo.getText());
        if (transDt != null)
        {
            try
            {
                ogw00055Resp.setTransDateTime(dataFormat.parse(transDt.getText().concat(
                        transTm == null ? null : transTm.getText())));
            }
            catch (ParseException e)
            {
                logger.error("交易时间解析错误！", e);
            }
        }

        ogw00055Resp.setExtFiled1(extFiled1 == null ? null : extFiled1.getText());
        ogw00055Resp.setExtFiled2(extFiled2 == null ? null : extFiled2.getText());
        return ogw00055Resp;
    }


}
