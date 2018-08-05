package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00045Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00046Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00045Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Req;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00046Req;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00046Resp;
import com.yuanheng100.settlement.ghbank.service.IRechargeService;

public class RechargeServiceImpl implements IRechargeService
{

    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00045Mapper ogw00045mapper;
    
    private static Logger logger = Logger.getLogger(RechargeServiceImpl.class);
    
    @Override
    public String getRechargeXmlParam(AppId appId, String acNo, String acName, BigDecimal amount, String remark)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00045Req request45 = new OGW00045Req(sequenceId.getId());
        request45.setAppId(appId);
        request45.setAcNo(acNo);
        request45.setAcName(acName);
        request45.setAmount(amount);
        request45.setRemark(remark);
        
        ogw00045mapper.insert(request45);
        logger.info("已将待发送的OGW00045Req（充值）存入数据库，"+request45);
        
        OGW00045Adapter adapter45 = new OGW00045Adapter();
        String xmlParam = adapter45.getPostString(request45);
        return xmlParam;
    }
    

    @Override
    public void updateRechargeResponse(OGW00045Resp resp45)
    {
        ogw00045mapper.updateByChannelFlow(resp45);
        logger.info("充值异步应答已存入数据库"+resp45);
    }


    @Override
    public OGW00046Resp queryRechargeResult(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00046Req request46 = new OGW00046Req(sequenceId.getId());
        request46.setAppId(app);
        request46.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00046Adapter adapter46 = new OGW00046Adapter();
        OGW00046Resp resp46 = (OGW00046Resp)adapter46.postAndReceive(request46);
        return resp46;
    }

}
