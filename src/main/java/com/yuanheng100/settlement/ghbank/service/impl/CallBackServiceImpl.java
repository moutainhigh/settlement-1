package com.yuanheng100.settlement.ghbank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TResp;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TResp;
import com.yuanheng100.settlement.ghbank.service.ICallBackService;

public class CallBackServiceImpl implements ICallBackService
{

    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Override
    public OGW0014TResp bankCancelLoan(OGW0014TReq ogw0014tReq)
    {
        
        System.out.println("ogw0014tReq="+ogw0014tReq);
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW0014TResp ogw0014TResp = new OGW0014TResp(sequenceId.getId()); 
        return ogw0014TResp;
    }


    @Override
    public OGW0015TResp bankRepealLoan(OGW0015TReq ogw0015tReq)
    {
        System.out.println("ogw0015tReq="+ogw0015tReq);
        
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW0015TResp ogw0015TResp = new OGW0015TResp(sequenceId.getId()); 
        
        return ogw0015TResp;
    }
    
    

}
