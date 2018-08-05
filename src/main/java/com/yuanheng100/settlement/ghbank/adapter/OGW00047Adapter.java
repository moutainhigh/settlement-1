package com.yuanheng100.settlement.ghbank.adapter;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.ghbank.consts.WithdrawStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Req;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00047Adapter extends AbstractGHBankAdapter<OGW00047Req>
{

    private static Logger logger = LoggerFactory.getLogger(OGW00047Adapter.class);
    
    @Override
    protected Document createXml(OGW00047Req ghBankReq)
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
        OGW00047Resp ogw00047Resp = new OGW00047Resp();
        super.parseXmlToGHBankResp(document, ogw00047Resp);

        // 解析当前业务的内容
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");

        Element oldReqSeqNo = body.element("OLDREQSEQNO");
        Element orderStatus = body.element("ORDERSTATUS");

        ogw00047Resp.setOldReqSeqNo(oldReqSeqNo.getText());
        try
        {
            ogw00047Resp.setWithdrawStatus(WithdrawStatus.parseWithdrawStatusByText(orderStatus.getText()).getCode());
        }
        catch (IllegalPlatformAugumentException ipae)
        {
            logger.info("解析提现结果时出现异常", ipae);
        }

        return ogw00047Resp;
    }


}
