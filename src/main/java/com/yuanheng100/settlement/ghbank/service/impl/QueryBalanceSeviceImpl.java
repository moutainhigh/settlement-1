package com.yuanheng100.settlement.ghbank.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00049Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00077Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Req;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Resp;
import com.yuanheng100.settlement.ghbank.service.IQueryBalanceService;

public class QueryBalanceSeviceImpl implements IQueryBalanceService
{
    
    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;

    @Override
    public OGW00049Resp queryBalance(AppId app, String acNo, String acName)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00049Req ogw00049Req = new OGW00049Req(sequenceId.getId());
        ogw00049Req.setAppId(app);
        ogw00049Req.setAcNo(acNo);
        ogw00049Req.setAcName(acName);
        
        OGW00049Adapter adapter49 = new OGW00049Adapter();
        OGW00049Resp resp49 = (OGW00049Resp)adapter49.postAndReceive(ogw00049Req);
        return resp49;
    }

    @Override
    public OGW00077Resp dayEndCheck(AppId app, Short operFlag, Date checkDate)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00077Req ogw00077Req = new OGW00077Req(sequenceId.getId());
        
        ogw00077Req.setAppId(app);
        ogw00077Req.setOperFlag(operFlag);
        ogw00077Req.setCheckDate(checkDate);
        
        OGW00077Adapter adapter77 = new OGW00077Adapter();
        OGW00077Resp resp77 = (OGW00077Resp)adapter77.postAndReceive(ogw00077Req);
        return resp77;
    }

}
