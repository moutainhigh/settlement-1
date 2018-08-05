package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjInfoRetcode;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.mapper.G60009BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.ISyncAuthenticationService;
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

/**
 * Created by jlqian on 2016/9/8.
 */
public class SyncAuthenticationServiceImpl extends GwMsgBaseService<G60009Bean> implements ISyncAuthenticationService {

    private static final Logger LOG = Logger.getLogger(SyncAuthenticationServiceImpl.class);
    @Autowired
    private G60009BeanMapper g60009BeanMapper;
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;

    public boolean authenticate(G60009Bean g60009Bean) throws IllegalPlatformAugumentException {
        //校验必填字段
        verifyRequiredParameters(g60009Bean);

        SequenceId sequenceId = new SequenceId();
        chanpaySequenceIdMapper.insert(sequenceId);
        g60009Bean.setReqSn(Cj.CJ_MERCHANT_ID+Cj.FMT_yyMMddHHmmss.format(new Date())+ String.format("%05d",sequenceId.getId()));
        g60009Bean.setSubMerchantId(Cj.CJ_SUBMERT_ID);
        g60009Bean.setSn(U.createUUID());
        g60009Bean.setTimestamp(new Date());
        buildCjmsgAndSend(g60009Bean);
        LOG.info("畅捷通同步身份认证："+ JSON.toJSONString(g60009Bean));
        g60009BeanMapper.insertSelective(g60009Bean);
        return CjInfoRetcode.$0000_处理成功.getCode().equals(g60009Bean.getRetCode());
    }

    /**
     * 校验必填字段
     * @param g60009Bean
     * @throws IllegalPlatformAugumentException
     */
    private void verifyRequiredParameters(G60009Bean g60009Bean) throws IllegalPlatformAugumentException {
        if(StringUtils.isEmpty(g60009Bean.getBankGeneralName()))
            throw new IllegalPlatformAugumentException("银行通用名称不能为空");
        if(StringUtils.isEmpty(g60009Bean.getBankCode()))
            throw new IllegalPlatformAugumentException("开户行号不能为空");
        if(StringUtils.isEmpty(g60009Bean.getAccountNo()))
            throw new IllegalPlatformAugumentException("银行卡或存折号码不能为空");
        if(StringUtils.isEmpty(g60009Bean.getAccountName()))
            throw new IllegalPlatformAugumentException("账户名称不能为空");
        if(StringUtils.isEmpty(g60009Bean.getIdType())){
            g60009Bean.setIdType(Cj.CERT_ID_TYPE_身份证);
        }
        if(StringUtils.isEmpty(g60009Bean.getAccountType())){
            g60009Bean.setAccountType(Cj.ACCOUNT_TYPE_借记卡);
        }
    }

    @Override
    protected String buildCjmsg(G60009Bean data) {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_同步实名认证);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(data.getReqSn());
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");

        Element batch = bodyEl.addElement("BATCH");
        batch.addElement("VALIDATE_MODE").setText("V001");
        batch.addElement("SMS_CODE").setText(U.nvl(data.getSmsCode()));
        batch.addElement("ORG_REQ_SN").setText(U.nvl(data.getOrgReqSn()));
        batch.addElement("SUB_MERCHANT_ID").setText(Cj.CJ_SUBMERT_ID);

        Element details = bodyEl.addElement("TRANS_DETAILS");
        Element dtl = details.addElement("DTL");
        dtl.addElement("SN").setText(U.nvl(data.getSn()));
        dtl.addElement("BANK_GENERAL_NAME").setText(U.nvl(data.getBankGeneralName()));
        dtl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
        dtl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
        dtl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
        dtl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
        dtl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
        dtl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
        dtl.addElement("ID").setText(U.nvl(data.getId()));
        dtl.addElement("TEL").setText(U.nvl(data.getTel()));

        String xml = Cj.formatXml_UTF8(doc);
        return xml;
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<G60009Bean> page) {

        Long totalCount = g60009BeanMapper.selectAuthenticationByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex",(pageNo-1)*pageSize);
        searchConditions.put("endIndex",pageSize);

        List<G60009Bean> list = g60009BeanMapper.selectAuthenticationListByCondition(searchConditions);
        page.setResult(list);
    }
}
