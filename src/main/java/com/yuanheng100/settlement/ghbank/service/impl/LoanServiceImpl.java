package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00051Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00060Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00063Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00065Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00066Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.OperFlag;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00051Mapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00060Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00060Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00060Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00063Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00063Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00064Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00066Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00066Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;
import com.yuanheng100.settlement.ghbank.service.ILoanService;

public class LoanServiceImpl implements ILoanService
{

    private static Logger logger = Logger.getLogger(LoanServiceImpl.class);
    
    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00051Mapper ghbankOGW00051Mapper;
    
    @Autowired
    private GhbankOGW00060Mapper ghbankOGW00060Mapper;
    
    
    @Override
    public OGW00051Resp createLoan(OGW00051Req ogw00051Req)
    {
        
        ghbankOGW00051Mapper.insert(ogw00051Req);
        logger.info("已将待发送的request存入数据库，"+ogw00051Req);
        
        OGW00051Adapter adapter51 = new OGW00051Adapter();
        OGW00051Resp resp51 = (OGW00051Resp)adapter51.postAndReceive(ogw00051Req);
        
        ghbankOGW00051Mapper.updateByPrimaryKey(resp51);
        logger.info("收到的resp已存入数据库，"+resp51);
        
        return resp51;
    }

    @Override
    public OGW00060Resp cancelLoan(AppId app, long loanNo, String oldReqSeqNo, String acNo, String acName,
            String cancelReason)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00060Req req60 = new OGW00060Req(sequenceId.getId());
        
        req60.setAppId(app);
        req60.setLoanNo(loanNo);
        req60.setOldReqSeqNo(oldReqSeqNo);
        req60.setAcNo(acNo);
        req60.setAcName(acName);
        req60.setCancelReason(cancelReason);
        
        ghbankOGW00060Mapper.insert(req60);
        logger.info("已将待发送的OGW00060Req存入数据库，"+req60);
        
        OGW00060Adapter adapter60 = new OGW00060Adapter();
        OGW00060Resp resp60 = (OGW00060Resp)adapter60.postAndReceive(req60);
        
        ghbankOGW00060Mapper.updateByChannelFlow(resp60);
        logger.info("已将收到的撤标响应存入数据库"+resp60);
        return resp60;
    }

    @Override
    public OGW00063Resp disableLoan(AppId app, long loanNo, String cancelReason)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00063Req req63 = new OGW00063Req(sequenceId.getId());
        
        req63.setAppId(app);
        req63.setLoanNo(loanNo);
        req63.setCancelReason(cancelReason);
        
        OGW00063Adapter adapter63 = new OGW00063Adapter();
        OGW00063Resp resp63 = (OGW00063Resp)adapter63.postAndReceive(req63);
        return resp63;
    }

    @Override
    public OGW00064Resp queryCancelResult(AppId app, String oldReqSeqNo, String oldTTJnl)
    {
        return null;
    }

    @Override
    public OGW00065Resp releaseLoan(AppId app, long loanNo, String bwAcName, String bwAcNo, BigDecimal acMngAmt,
            BigDecimal guarantAmt)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00065Req req65 = new OGW00065Req(sequenceId.getId());
        
        req65.setAppId(app);
        req65.setLoanNo(loanNo);
        req65.setBwAcName(bwAcName);
        req65.setBwAcNo(bwAcNo);
        req65.setAcMngAmt(acMngAmt);
        req65.setGuarantAmt(guarantAmt);
        
        OGW00065Adapter adapter65 = new OGW00065Adapter();
        OGW00065Resp resp65 = (OGW00065Resp)adapter65.postAndReceive(req65);
        return resp65;
    }

    @Override
    public OGW00066Resp queryReleaseLoanResult(AppId app, String oldReqSeqNo, long loanNo, String oldTTJnl)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00066Req req66 = new OGW00066Req(sequenceId.getId());
        
        req66.setAppId(app);
        req66.setOldReqSeqNo(oldReqSeqNo);
        req66.setLoanNo(loanNo);
        req66.setOldTTJnl(oldTTJnl);
        
        OGW00066Adapter adapter66 = new OGW00066Adapter();
        OGW00066Resp resp66 = (OGW00066Resp)adapter66.postAndReceive(req66);
        return resp66;
    }

    @Override
    public OGW00077Resp dailyTradeCheck(AppId app, OperFlag operFlag, Date checkDate)
    {
        return null;
    }


    @Override
    public int getSequenceId()
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        return sequenceId.getId();
    }
    
    

}
