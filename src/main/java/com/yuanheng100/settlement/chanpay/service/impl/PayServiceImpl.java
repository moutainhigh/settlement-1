package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IPayCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjInfoRetcode;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.mapper.G10002BeanMapper;
import com.yuanheng100.settlement.chanpay.mapper.G40001BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.model.G40001Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.IPayService;
import com.yuanheng100.settlement.chanpay.util.CallbackListenerContainer;
import com.yuanheng100.settlement.chanpay.util.U;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/10.
 */
public class PayServiceImpl extends GwMsgBaseService<G10001Bean> implements IPayService {

    private static final Logger LOG = Logger.getLogger(PayServiceImpl.class);
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;
    @Autowired
    private G10002BeanMapper g10002BeanMapper;
    @Autowired
    private G40001BeanMapper g40001BeanMapper;

    public boolean pay(G10001Bean bean) throws IllegalPlatformAugumentException {
        //校验数据合法性
        verifyRequiredParameters(bean);
        SequenceId sequenceId = new SequenceId();
        chanpaySequenceIdMapper.insert(sequenceId);
        bean.setReqSn(Cj.CJ_MERCHANT_ID+Cj.FMT_yyMMddHHmmss.format(new Date())+ String.format("%05d",sequenceId.getId()));
        bean.setTimestamp(new Date());
        buildCjmsgAndSend(bean);
        LOG.info("畅捷通G10002单笔实时付款："+ JSON.toJSONString(bean));
        if(!bean.getRetCode().equals(CjInfoRetcode.$0000_处理成功.getCode())){
            bean.setTradeCode(bean.getRetCode());
            bean.setTradeMsg(bean.getErrMsg());
        }
        g10002BeanMapper.insertSelective(bean);
        return bean.getRetCode().equals(CjInfoRetcode.$0000_处理成功.getCode());
    }

    @Override
    public G10001Bean pay(G10001Bean bean, IPayCallbackListener payCallbackListener) throws IllegalPlatformAugumentException
    {
        if(pay(bean)){
            CallbackListenerContainer.putPayCallbackListener(bean.getReqSn(),payCallbackListener);
        }
        return bean;
    }

    private void verifyRequiredParameters(G10001Bean bean) throws IllegalPlatformAugumentException {
        if(StringUtils.isEmpty(bean.getBankGeneralName()))
            throw new IllegalPlatformAugumentException("银行通用名称不能为空");
        if(StringUtils.isEmpty(bean.getAccountNo()))
            throw new IllegalPlatformAugumentException("银行卡或存折号码不能为空");
        if(StringUtils.isEmpty(bean.getAccountName()))
            throw new IllegalPlatformAugumentException("账户名称不能为空");
        if(StringUtils.isEmpty(bean.getBankName()))
            throw new IllegalPlatformAugumentException("开户行名称不能为空");
        if(StringUtils.isEmpty(bean.getBankCode()))
            throw new IllegalPlatformAugumentException("开户行行号不能为空");
        G40001Bean g40001Bean = g40001BeanMapper.selectByAccountBankCode(bean.getBankCode());
        if(g40001Bean==null){
            throw new IllegalPlatformAugumentException("开户行行号有误");
        }
        if(bean.getAmount()==null||bean.getAmount().doubleValue() <= 0)
            throw new IllegalPlatformAugumentException("付款金额有误");
    }

    @Override
    protected String buildCjmsg(G10001Bean data) {

        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_单笔实时付款);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");
        bodyEl.addElement("BUSINESS_CODE").setText(Cj.CJ_BUSINESS_CODE);
        bodyEl.addElement("CORP_ACCT_NO").setText(Cj.CJ_CORP_ACC_NO);
        bodyEl.addElement("PRODUCT_CODE").setText(Cj.XMLMSG_PRODUCT_CODE_单笔付款);
        bodyEl.addElement("ACCOUNT_PROP").setText(Cj.ACCOUNT_PROP_对私);
        bodyEl.addElement("BANK_GENERAL_NAME").setText(U.nvl(data.getBankGeneralName()));
        bodyEl.addElement("ACCOUNT_TYPE").setText(Cj.ACCOUNT_TYPE_借记卡);
        bodyEl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
        bodyEl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
        bodyEl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
        bodyEl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
        bodyEl.addElement("DRCT_BANK_CODE").setText(U.nvl(data.getDrctBankCode()));
        bodyEl.addElement("CURRENCY").setText(Cj.CURRENCY_人民币);
        bodyEl.addElement("AMOUNT").setText(U.money_yuan2cent(data.getAmount()).toString());
        bodyEl.addElement("ID_TYPE").setText(Cj.CERT_ID_TYPE_身份证);
        bodyEl.addElement("ID").setText(U.nvl(data.getId()));
        bodyEl.addElement("TEL").setText(U.nvl(data.getTel()));
        bodyEl.addElement("CORP_FLOW_NO").setText(U.nvl(data.getCorpFlowNo()));
        bodyEl.addElement("SUMMARY").setText(U.nvl(data.getSummary()));
        bodyEl.addElement("POSTSCRIPT").setText(U.nvl(data.getPostscript()));
        String xml = Cj.formatXml_UTF8(doc);
        LOG.debug("产生G10002单笔实时付款：" + U.substringByByte(xml, 512));
        return xml;
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = g10002BeanMapper.selectPayCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex",(pageNo-1)*pageSize);
        searchConditions.put("endIndex",pageSize);

        List<Map<String,Object>> list = g10002BeanMapper.selectPayListCondition(searchConditions);
        page.setResult(list);
    }

}
