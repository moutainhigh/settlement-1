package com.yuanheng100.settlement.ghbank.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.ghbank.adapter.OGW00067Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00068Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00073Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00074Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00075Adapter;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.mapper.GHBankSequenceIdMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00067Mapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00073Mapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00074Mapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00074RepayItemMapper;
import com.yuanheng100.settlement.ghbank.mapper.GhbankOGW00075Mapper;
import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00068Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00068Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00073Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00073Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req.RepayItem;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00076Resp;
import com.yuanheng100.settlement.ghbank.service.IRepayService;

public class RepayServiceImpl implements IRepayService
{

    private static Logger logger = Logger.getLogger(RepayServiceImpl.class);
    
    @Autowired
    private GHBankSequenceIdMapper ghBankSequenceIdMapper;
    
    @Autowired
    private GhbankOGW00067Mapper ghbankOGW00067Mapper;
    
    @Autowired
    private GhbankOGW00073Mapper ghbankOGW00073Mapper;

    @Autowired
    private GhbankOGW00074Mapper ghbankOGW00074Mapper;
    
    @Autowired
    private GhbankOGW00074RepayItemMapper ghbankOGW00074RepayItemMapper;
    
    @Autowired
    private GhbankOGW00075Mapper ghbankOGW00075Mapper;
    
    @Override
    public String getRepayXmlparam(AppId app, DFFlag dfFlag, String oldReqSeqNo, long loanNo, String bwAcName,
            String bwAcNo, BigDecimal amount, String remark, BigDecimal feeAmt)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00067Req ogw00067Req = new OGW00067Req(sequenceId.getId());
        ogw00067Req.setAppId(app);
        ogw00067Req.setDfFlag(dfFlag.getFlag());
        ogw00067Req.setOldReqSeqNo(oldReqSeqNo);
        ogw00067Req.setLoanNo(loanNo);
        ogw00067Req.setBwAcName(bwAcName);
        ogw00067Req.setBwAcNo(bwAcNo);
        ogw00067Req.setAmount(amount);
        ogw00067Req.setRemark(remark);
        ogw00067Req.setFeeAmt(feeAmt);
        
        ghbankOGW00067Mapper.insert(ogw00067Req);
        logger.info("待发送的OGW00067Req请求已存入数据库"+ogw00067Req);
        
        OGW00067Adapter adapter67 = new OGW00067Adapter();
        String xmlParam = adapter67.getPostString(ogw00067Req);
        return xmlParam;
    }

    
    @Override
    public void updateRepayResponse(OGW00067Resp resp67)
    {
        ghbankOGW00067Mapper.updateByChannelFlow(resp67);
        logger.info("已将还款response存入数据库，"+resp67);
    }


    @Override
    public OGW00068Resp queryRepayResult(AppId app, String oldReqSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00068Req request68 = new OGW00068Req(sequenceId.getId());
        
        request68.setAppId(app);
        request68.setOldReqSeqNo(oldReqSeqNo);
        
        OGW00068Adapter adapter68 = new OGW00068Adapter();
        return (OGW00068Resp) adapter68.postAndReceive(request68);
    }

    @Override
    public OGW00073Resp payForBorrower(AppId app, long loanNo, String bwAcName, String bwAcNo, BigDecimal amount,
            BigDecimal feeAmt)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00073Req ogw00073Req = new OGW00073Req(sequenceId.getId());
        
        ogw00073Req.setAppId(app);
        ogw00073Req.setLoanNo(loanNo);
        ogw00073Req.setBwAcName(bwAcName);
        ogw00073Req.setBwAcNo(bwAcNo);
        ogw00073Req.setAmount(amount);
        ogw00073Req.setFeeAmt(feeAmt);
        
        ghbankOGW00073Mapper.insert(ogw00073Req);
        logger.info("待发送的OGW00073Req请求已存入数据库"+ogw00073Req);
        
        OGW00073Adapter adapter73 = new OGW00073Adapter();
        OGW00073Resp resp73 = (OGW00073Resp)adapter73.postAndReceive(ogw00073Req);
        
        ghbankOGW00073Mapper.updateByChannelFlow(resp73);
        logger.info("收到的OGW00073Resp已存入数据库"+resp73);
        return resp73;
    }


    @Override
    public OGW00074Resp repayInterestDetail(AppId app, String oldReqSeqNo, short dfFlag, long loanNo,
            String bwAcName, String bwAcNo,  List<RepayItem> repayList)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00074Req ogw00074Req = new OGW00074Req(sequenceId.getId());
        
        ogw00074Req.setAppId(app);
        ogw00074Req.setOldReqSeqNo(oldReqSeqNo);
        ogw00074Req.setDfFlag(dfFlag);
        ogw00074Req.setLoanNo(loanNo);
        ogw00074Req.setBwAcName(bwAcName);
        ogw00074Req.setBwAcNo(bwAcNo);
        ogw00074Req.setTotalNum(repayList.size());
        
        ogw00074Req.setRepayList(repayList);
        
        ghbankOGW00074Mapper.insert(ogw00074Req);
        logger.info("待发送的OGW00074Req请求已存入数据库"+ogw00074Req);
        
        int i=1;
        if(repayList!=null && repayList.size()>0)
        {
            for(RepayItem eachRepayItem:repayList)
            {
                ghbankOGW00074RepayItemMapper.insert(eachRepayItem);
                i++;
                logger.info("  "+i+"\t待发送的RepayItem请求已存入数据库"+eachRepayItem);
            }
        }
        
        OGW00074Adapter adapter74 = new OGW00074Adapter();
        OGW00074Resp resp74 = (OGW00074Resp)adapter74.postAndReceive(ogw00074Req);
        ghbankOGW00074Mapper.updateByChannelFlow(resp74);
        logger.info("收到的OGW00074Resp已存入数据库"+resp74);
        
        return resp74;
    }


    @Override
    public OGW00075Resp queryRepayInterestResult(AppId app, String oldReqSeqNo, long loanNo, String subSeqNo)
    {
        GHBankSequenceId sequenceId = new GHBankSequenceId();
        ghBankSequenceIdMapper.getSequenceId(sequenceId);
        OGW00075Req ogw00075Req = new OGW00075Req(sequenceId.getId());
        
        ogw00075Req.setAppId(app);
        ogw00075Req.setOldReqSeqNo(oldReqSeqNo);
        ogw00075Req.setLoanNo(loanNo);
        ogw00075Req.setSubSeqNo(subSeqNo);
        
        ghbankOGW00075Mapper.insert(ogw00075Req);
        logger.info("待发送的OGW00075Req请求已存入数据库"+ogw00075Req);
        
        OGW00075Adapter adapter75 = new OGW00075Adapter();
        OGW00075Resp resp75 = (OGW00075Resp)adapter75.postAndReceive(ogw00075Req);
        
        ghbankOGW00075Mapper.updateByChannelFlow(resp75);
        logger.info("收到的OGW00075Resp已存入数据库"+resp75);
        return resp75;
    }

    @Override
    public OGW00076Resp investorBonus(AppId app, String acNo, String acName, BigDecimal amount, String remark)
    {
        return null;
    }

    
}
