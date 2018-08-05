package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IAsynAuthenticationCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjInfoRetcode;
import com.yuanheng100.settlement.chanpay.mapper.G60001BeanMapper;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationRequestService;
import com.yuanheng100.settlement.chanpay.task.AsynAuthenticationQueryTask;
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
 * Created by jlqian on 2016/9/8.
 */
public class AsynAuthenticationRequestServiceImpl extends GwMsgBaseService<G60001Bean> implements IAsynAuthenticationRequestService
{

    private static final Logger LOG = Logger.getLogger(AsynAuthenticationRequestServiceImpl.class);

    @Autowired
    private G60001BeanMapper g60001BeanMapper;
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;

    public boolean authenticate(G60001Bean g60001Bean, IAsynAuthenticationCallbackListener listener) throws IllegalPlatformAugumentException
    {
        //校验必填字段
        verifyRequiredParameters(g60001Bean);

        SequenceId sequenceId = new SequenceId();
        chanpaySequenceIdMapper.insert(sequenceId);
        g60001Bean.setReqSn(Cj.CJ_MERCHANT_ID + Cj.FMT_yyMMddHHmmss.format(new Date()) + String.format("%05d", sequenceId.getId()));
        g60001Bean.setSn(U.createUUID());
        g60001Bean.setTimestamp(new Date());
        buildCjmsgAndSend(g60001Bean);
        LOG.info("畅捷通异步身份认证：" + JSON.toJSONString(g60001Bean));
        g60001BeanMapper.insertSelective(g60001Bean);
        AsynAuthenticationQueryTask.addListener(g60001Bean.getReqSn(), listener);
        return true;
    }

    @Override
    public List<G60001Bean> withoutQuery()
    {
        return g60001BeanMapper.withoutQuery();
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = g60001BeanMapper.selectAuthenticationByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = g60001BeanMapper.selectAuthenticationListByCondition(searchConditions);
        page.setResult(list);
    }

    /**
     * 校验必填字段
     *
     * @param g60001Bean
     * @throws IllegalPlatformAugumentException
     */
    private void verifyRequiredParameters(G60001Bean g60001Bean) throws IllegalPlatformAugumentException
    {
        if (StringUtils.isEmpty(g60001Bean.getBankGeneralName()))
        {
            throw new IllegalPlatformAugumentException("银行通用名称不能为空");
        }
        if (StringUtils.isEmpty(g60001Bean.getAccountType()))
        {
            throw new IllegalPlatformAugumentException("账号类型不能为空");
        }
        if (StringUtils.isEmpty(g60001Bean.getAccountNo()))
        {
            throw new IllegalPlatformAugumentException("银行卡或存折号码不能为空");
        }
        if (StringUtils.isEmpty(g60001Bean.getAccountName()))
        {
            throw new IllegalPlatformAugumentException("账户名称不能为空");
        }
        if (StringUtils.isEmpty(g60001Bean.getIdType()))
        {
            throw new IllegalPlatformAugumentException("开户证件类型不能为空");
        }
    }

    @Override
    protected String buildCjmsg(G60001Bean data)
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实名认证);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");

        Element batch = bodyEl.addElement("BATCH");
        batch.addElement("VALIDATE_MODE").setText("V001");

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

}
