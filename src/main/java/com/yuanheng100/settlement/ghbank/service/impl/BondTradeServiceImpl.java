package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00061Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00062Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00061Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Req;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00062Req;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00062Resp;
import com.yuanheng100.settlement.ghbank.service.IBondTradeService;

public class BondTradeServiceImpl implements IBondTradeService
{

    private static Logger logger = Logger.getLogger(BondTradeServiceImpl.class);
    
    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00061Mapper ghbankOGW00061Mapper;
    
    @Override
    public String getTradeReqXmlParam(AppId appId, String oldReqSeqNo, int oldReqNumber, String oldReqName,
            String accNo, String custName, BigDecimal amount, BigDecimal preIncome)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00061Req ogw00061Req = new OGW00061Req(sequenceId.getId());
        
        ogw00061Req.setAppId(appId);
        ogw00061Req.setOldReqSeqNo(oldReqSeqNo);
        ogw00061Req.setOldReqNumber(oldReqNumber);
        ogw00061Req.setOldReqName(oldReqName);
        ogw00061Req.setAccNo(accNo);
        ogw00061Req.setCustName(custName);
        ogw00061Req.setAmount(amount);
        ogw00061Req.setPreIncome(preIncome);
        
        ghbankOGW00061Mapper.insert(ogw00061Req);
        logger.info("已将待发送的债权转让request存入数据库，"+ogw00061Req);
        
        OGW00061Adapter adapter61 = new OGW00061Adapter();
        String xmlParam = adapter61.getPostString(ogw00061Req);
        return xmlParam;
    }
    
    
    @Override
    public void updateBondTradeRespone(OGW00061Resp resp61)
    {
        ghbankOGW00061Mapper.updateByChannelFlow(resp61);
        logger.info("债权交易的异步应答已存入数据库"+resp61);
    }



    @Override
    public OGW00062Resp queryTransferResult(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00062Req req62 = new OGW00062Req(sequenceId.getId());
        
        req62.setAppId(app);
        req62.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00062Adapter adapter62 = new OGW00062Adapter();
        OGW00062Resp resp62 = (OGW00062Resp)adapter62.postAndReceive(req62);
        return resp62;
    }

}
