package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00047Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00048Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00047Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Req;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Req;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Resp;
import com.yuanheng100.settlement.ghbank.service.IWithdrawService;

public class WithdrawServiceImpl implements IWithdrawService
{

    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00047Mapper ghbankOGW00047Mapper;
    
    private static Logger logger = Logger.getLogger(WithdrawServiceImpl.class);
    
    @Override
    public String getWithdrawXmlparam(AppId app, String acNo, String acName, BigDecimal amount, String remark)
    {
        logger.info("getWithdrawXmlparam方法被唤起");
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00047Req ogw00047Req = new OGW00047Req(sequenceId.getId());
        ogw00047Req.setAppId(app);
        ogw00047Req.setAcNo(acNo);
        ogw00047Req.setAcName(acName);
        ogw00047Req.setAmount(amount);
        ogw00047Req.setRemark(remark);
        
        ghbankOGW00047Mapper.insert(ogw00047Req);
        logger.info("已将待发送的request存入数据库, "+ogw00047Req);
        
        OGW00047Adapter adapter47 = new OGW00047Adapter();
        String xmlParam = adapter47.getPostString(ogw00047Req);
        return xmlParam;
    }
    
    
    @Override
    public void updateWithdrawResponse(OGW00047Resp resp47)
    {
        ghbankOGW00047Mapper.updateByChannelFlow(resp47);
        logger.info("提现异步应答已存入数据库"+resp47);
    }


    @Override
    public OGW00048Resp queryWithdrawResult(AppId app, Date transDt, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00048Req req48 = new OGW00048Req(sequenceId.getId());
        req48.setAppId(app);
        req48.setTransDt(transDt);
        req48.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00048Adapter adapter48 = new OGW00048Adapter();
        OGW00048Resp resp48 = (OGW00048Resp)adapter48.postAndReceive(req48);
        return resp48;
    }

}
