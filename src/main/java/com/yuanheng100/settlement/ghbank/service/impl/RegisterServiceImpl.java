package com.yuanheng100.settlement.ghbank.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00041Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00042Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00043Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00044Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.IDType;
import com.yuanheng100.settlement.ghbank.consts.TrsType;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00042Mapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00044Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00043Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00043Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;
import com.yuanheng100.settlement.ghbank.service.IRegisterService;

public class RegisterServiceImpl implements IRegisterService
{

    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00042Mapper ogw00042mapper;
    
    @Autowired
    private GhbankOGW00044Mapper ogw00044mapper;

    private static Logger logger = Logger.getLogger(RegisterServiceImpl.class);
    
    @Override
    public OGW00041Resp getVerificationCode(AppId app, TrsType trsType, String acNo, Long mobileNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00041Req ogw00041Req = new OGW00041Req(sequenceId.getId(), trsType.getType());
        ogw00041Req.setAppId(app);
        ogw00041Req.setTrsType(trsType.getType());
        ogw00041Req.setAcNo(acNo);
        ogw00041Req.setMobileNo(mobileNo);

        OGW00041Adapter adapter = new OGW00041Adapter();
        OGW00041Resp resp = (OGW00041Resp)adapter.postAndReceive(ogw00041Req);
        
        return resp;
    }

    @Override
    public String getRegisterParam(AppId app, String acName, String idNo, Long mobile)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00042Req ogw00042req = new OGW00042Req(sequenceId.getId());
        
        ogw00042req.setAppId(app);
        ogw00042req.setAcName(acName);
        ogw00042req.setIdType(IDType.ID_CARD.getType());
        ogw00042req.setIdNo(idNo);
        ogw00042req.setMobile(mobile);
        ogw00042req.setEmail("");
        
        ogw00042mapper.insert(ogw00042req);
        logger.info("已将待发送的request存入数据库，"+ogw00042req);
        
        OGW00042Adapter adapter = new OGW00042Adapter();
        String xmlParam = adapter.getPostString(ogw00042req);
        return xmlParam;
    }
    

    @Override
    public void updateRegisterResponse(OGW00042Resp resp42)
    {
        ogw00042mapper.updateByChannelFlow(resp42);
        logger.info("已将response存入数据库，"+resp42);
    }

    @Override
    public OGW00043Resp queryOpenAccountResult(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00043Req request43 = new OGW00043Req(sequenceId.getId());
        request43.setAppId(app);
        request43.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00043Adapter adapter43 = new OGW00043Adapter();
        return (OGW00043Resp) adapter43.postAndReceive(request43);
    }

    @Override
    public String getBindBankCardXML(AppId app, String acNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00044Req request44 = new OGW00044Req(sequenceId.getId());
        
        request44.setAppId(app);
        request44.setAcNo(acNo);
        
        ogw00044mapper.insert(request44);
        logger.info("已将待发送的request存入数据库，"+request44);
        
        OGW00044Adapter adapter44 = new OGW00044Adapter();
        return adapter44.getPostString(request44);
    }


    @Override
    public void updateBindCardResponse(OGW00044Resp resp44)
    {
        logger.info("Service收到Response，"+resp44);
        ogw00044mapper.updateByChannelFlow(resp44);
    }
    
    

}
