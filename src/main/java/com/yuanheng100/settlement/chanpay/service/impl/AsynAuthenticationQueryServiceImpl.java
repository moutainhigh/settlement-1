package com.yuanheng100.settlement.chanpay.service.impl;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.mapper.G60001BeanMapper;
import com.yuanheng100.settlement.chanpay.mapper.G60002BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.chanpay.model.G60002Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationQueryService;
import com.yuanheng100.settlement.chanpay.util.U;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by jlqian on 2016/9/28.
 */
public class AsynAuthenticationQueryServiceImpl extends GwMsgBaseService<G60002Bean> implements IAsynAuthenticationQueryService
{
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;
    @Autowired
    private G60001BeanMapper g60001BeanMapper;
    @Autowired
    private G60002BeanMapper g60002BeanMapper;

    @Override
    public G60002Bean query(String accountName, String accountNo, String id, String tel) throws IllegalPlatformAugumentException
    {
        G60001Bean g60001Bean = g60001BeanMapper.selectByFourElement(accountName, accountNo, id, tel);
        if(g60001Bean==null){
            throw new IllegalPlatformAugumentException("要查询的用户不存在，请检查四要素是否正确。");
        }
        return query(g60001Bean.getReqSn());
    }

    @Override
    public G60002Bean query(String qryReqSn)
    {
        G60002Bean g60002Bean = g60002BeanMapper.selectByQryReqSn(qryReqSn);
        if(g60002Bean==null){
            SequenceId sequenceId = new SequenceId();
            chanpaySequenceIdMapper.insert(sequenceId);
            g60002Bean = new G60002Bean();
            g60002Bean.setReqSn(Cj.CJ_MERCHANT_ID + Cj.FMT_yyMMddHHmmss.format(new Date()) + String.format("%05d", sequenceId.getId()));
            g60002Bean.setTimestamp(new Date());
            g60002Bean.setQryReqSn(qryReqSn);
            //请求数据存盘
            g60002BeanMapper.insert(g60002Bean);
            buildCjmsgAndSend(g60002Bean);
            //结果存盘
            g60002BeanMapper.update(g60002Bean);

        }else if(g60002Bean.getDtlRetCode()==null || CjDetailRetcode.$0001_代理系统受理成功.getCode().equals(g60002Bean.getDtlRetCode())||CjDetailRetcode.$0002_提交银行成功等待查询结果.getCode().equals(g60002Bean.getDtlRetCode())){
            SequenceId sequenceId = new SequenceId();
            chanpaySequenceIdMapper.insert(sequenceId);
            g60002Bean.setReqSn(Cj.CJ_MERCHANT_ID + Cj.FMT_yyMMddHHmmss.format(new Date()) + String.format("%05d", sequenceId.getId()));
            g60002Bean.setTimestamp(new Date());
            g60002Bean.setQryReqSn(qryReqSn);

            buildCjmsgAndSend(g60002Bean);
            //结果存盘
            g60002BeanMapper.update(g60002Bean);
        }
        return g60002Bean;
    }

    @Override
    protected String buildCjmsg(G60002Bean data)
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实名认证结果查询);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");

        bodyEl.addElement("QRY_REQ_SN").setText(data.getQryReqSn());

        String xml = Cj.formatXml_UTF8(doc);
        return xml;
    }

    @Override
    protected void parseCjMsgToDto(String cjRequmsg, G60002Bean bean) throws DocumentException
    {
        Document reqDoc = DocumentHelper.parseText(cjRequmsg);

        Element msgEl = reqDoc.getRootElement();
        Element infoEl = msgEl.element("INFO");

        bean.setTrxCode(infoEl.elementText("TRX_CODE"));
        bean.setMertid(infoEl.elementText("MERCHANT_ID"));
        bean.setReqSn(infoEl.elementText("REQ_SN"));
        bean.setRetCode(infoEl.elementText("RET_CODE"));
        bean.setErrMsg(infoEl.elementText("ERR_MSG"));
        bean.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));

        Element bodyEl = msgEl.element("BODY");

        Element batchEl = bodyEl.element("BATCH");
        bean.setBatchQryReqSn(batchEl.elementText("QRY_REQ_SN"));
        bean.setBatchRetCode(batchEl.elementText("RET_CODE"));
        bean.setBatchErrMsg(batchEl.elementText("ERR_MSG"));

        Element transDetailsEl = bodyEl.element("TRANS_DETAILS");
        Element dtlEl = transDetailsEl.element("DTL");

        bean.setDtlsn(dtlEl.elementText("SN"));
        bean.setDtlRetCode(dtlEl.elementText("RET_CODE"));
        bean.setDtlErrMsg(dtlEl.elementText("ERR_MSG"));
        bean.setDtlaccountName(dtlEl.elementText("ACCOUNT_NAME"));
        bean.setDtlaccountNo(dtlEl.elementText("ACCOUNT_NO"));
    }
}
