package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.mapper.G10002BeanMapper;
import com.yuanheng100.settlement.chanpay.mapper.G20001BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.model.G20001Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.IPayRemoteQueryService;
import com.yuanheng100.settlement.chanpay.util.U;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by jlqian on 2016/9/12.
 */
public class PayRemoteQueryServiceImpl extends GwMsgBaseService<G20001Bean> implements IPayRemoteQueryService
{
    private static final Logger LOG = Logger.getLogger(PayRemoteQueryServiceImpl.class);
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;
    @Autowired
    private G10002BeanMapper g10002BeanMapper;
    @Autowired
    private G20001BeanMapper g20001BeanMapper;

    @Override
    public boolean query(G20001Bean bean) throws IllegalPlatformAugumentException
    {
        //检验必要字段
        verifyRequiredParameters(bean);

        SequenceId sequenceId = new SequenceId();
        chanpaySequenceIdMapper.insert(sequenceId);
        bean.setReqSn(Cj.CJ_MERCHANT_ID + Cj.FMT_yyMMddHHmmss.format(new Date()) + String.format("%05d", sequenceId.getId()));
        bean.setTimestamp(new Date());
        buildCjmsgAndSend(bean);
        //检查是否存在当前交易
        G10001Bean g10001Bean = g10002BeanMapper.selectByReqSn(bean.getQryReqSn());
        if(g10001Bean==null){
            throw new IllegalPlatformAugumentException("订单编号"+bean.getQryReqSn()+"不存在");
        }
        //检验结果是否已经存在
        if(CjDetailRetcode.$0000_处理成功.getCode().equals(g10001Bean.getTradeCode())
            || CjDetailRetcode.$2013_收款行未开通业务.getCode().equals(g10001Bean.getTradeCode())
            || CjDetailRetcode.$3999_其他错误.getCode().equals(g10001Bean.getTradeCode())){
            LOG.warn("订单编号"+bean.getQryReqSn()+"结果已存在，不再更新数据库"+JSON.toJSONString(bean));
            return true;
        }
        //保存查询结果
        g20001BeanMapper.insertSelective(bean);
        //更新代付表G100002
        g10001Bean.setTradeCode(bean.getRetCode());
        g10001Bean.setTradeMsg(bean.getErrMsg());
        g10002BeanMapper.updateTradeResult(g10001Bean);
        return true;
    }
    /**
     * 检验必要字段：qryReqSn 不能为空 && 交易序列号存在
     *
     * @param bean
     * @throws IllegalPlatformAugumentException
     */
    private void verifyRequiredParameters(G20001Bean bean) throws IllegalPlatformAugumentException
    {
        String qryReqSn = bean.getQryReqSn();
        if (StringUtils.isEmpty(qryReqSn))
        {
            throw new IllegalPlatformAugumentException("原交易序列号不能为空");
        }
        G10001Bean g10001Bean = g10002BeanMapper.selectByReqSn(qryReqSn);
        if (g10001Bean == null)
        {
            throw new IllegalPlatformAugumentException("原交易序列号不存在");
        }
    }


    @Override
    protected String buildCjmsg(G20001Bean data)
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实时交易结果查询);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");
        bodyEl.addElement("QRY_REQ_SN").setText(U.nvl(data.getQryReqSn()));

        String xml = Cj.formatXml_UTF8(doc);
        LOG.debug("产生G20001单笔实时交易查询：" + U.substringByByte(xml, 512));
        return xml;
    }

    /**
     * 重写：解析畅捷通的响应报文
     *
     * @param cjRespmsg
     * @param bean
     */
    @Override
    protected void parseCjMsgToDto(String cjRespmsg, G20001Bean bean) throws DocumentException
    {
        Document reqDoc = DocumentHelper.parseText(cjRespmsg);

        Element msgEl = reqDoc.getRootElement();
        Element infoEl = msgEl.element("INFO");

        LOG.debug("交易查询基本响应信息：retcode[" + infoEl.elementText("RET_CODE") + "], errmsg[" + infoEl.elementText("ERR_MSG") + "]");

        Element bodyEl = msgEl.element("BODY");
        bean.setRetCode(bodyEl.elementText("RET_CODE"));
        bean.setErrMsg(bodyEl.elementText("ERR_MSG"));
        bean.setCharge(U.money_cent2yuan(bodyEl.elementText("CHARGE")));
        bean.setCorpAcctNo(bodyEl.elementText("CORP_ACCT_NO"));
        bean.setCorpAcctName(bodyEl.elementText("CORP_ACCT_NAME"));
        bean.setAccountNo(bodyEl.elementText("ACCOUNT_NO"));
        bean.setAccountName(bodyEl.elementText("ACCOUNT_NAME"));
        bean.setAmount(U.money_cent2yuan(bodyEl.elementText("AMOUNT")));
        bean.setCorpFlowNo(bodyEl.elementText("CORP_FLOW_NO"));
        bean.setSummary(bodyEl.elementText("SUMMARY"));
        bean.setPostscript(bodyEl.elementText("POSTSCRIPT"));

        LOG.debug("交易查询的具体响应信息：" + JSON.toJSONString(bean));
    }
}
