package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00052Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00053Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00054Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00055Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00052Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Resp;
import com.yuanheng100.settlement.ghbank.service.IInvestService;

public class InvestServiceImpl implements IInvestService
{

    private static Logger logger = Logger.getLogger(InvestServiceImpl.class);
    
    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00052Mapper ogw00052mapper;
    
    @Override
    public String getInvestXmlParam(AppId appId, long loanNo, String acNo, String acName, BigDecimal amount,
            String remark)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00052Req ogw00052Req = new OGW00052Req(sequenceId.getId());
        
        ogw00052Req.setAppId(appId);
        ogw00052Req.setLoanNo(loanNo);
        ogw00052Req.setAcNo(acNo);
        ogw00052Req.setAcName(acName);
        ogw00052Req.setAmount(amount);
        ogw00052Req.setRemark(remark);
        
        ogw00052mapper.insert(ogw00052Req);
        logger.info("已将待发送的request存入数据库，"+ogw00052Req);
        
        OGW00052Adapter adapter52 = new OGW00052Adapter();
        String xmlParam = adapter52.getPostString(ogw00052Req);
        return xmlParam;
    }
    

    @Override
    public void updateInvestResponse(OGW00052Resp resp52)
    {
        ogw00052mapper.updateBycChannelFlow(resp52);
        
        logger.info("投资异步应答已存入数据库"+resp52);
    }



    @Override
    public OGW00053Resp queryInvestResult(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00053Req req53 = new OGW00053Req(sequenceId.getId());
        req53.setAppId(app);
        req53.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00053Adapter adapter53 = new OGW00053Adapter();
        OGW00053Resp resp53 = (OGW00053Resp)adapter53.postAndReceive(req53);
        return resp53;
    }

    @Override
    public OGW00054Resp investBonus(OGW00054Req ogw00054Req)
    {
        OGW00054Adapter adapter54 = new OGW00054Adapter();
        OGW00054Resp resp54 = (OGW00054Resp)adapter54.postAndReceive(ogw00054Req);
        return resp54;
    }

    @Override
    public OGW00055Resp queryInvestBonus(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00055Req req55 = new OGW00055Req(sequenceId.getId());
        req55.setAppId(app);
        req55.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00055Adapter adapter55 = new OGW00055Adapter();
        OGW00055Resp resp55 = (OGW00055Resp)adapter55.postAndReceive(req55);
        return resp55;
    }

}
