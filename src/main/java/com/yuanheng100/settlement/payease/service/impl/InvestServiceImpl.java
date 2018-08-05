package com.yuanheng100.settlement.payease.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.consts.Currency;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.mapper.TRS001002Mapper;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Req;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.service.IInvestService;

public class InvestServiceImpl extends AbstractMessageService<TRS001002Req> implements IInvestService
{

    private static Logger logger = Logger.getLogger(InvestServiceImpl.class);
    
    @Autowired
    private TRS001002Mapper trs001002Mapper;

    
    @Override
    public TRS001002Resp investFreeze(TRS001002Req freezeReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(freezeReq);
        freezeReq.setOperationCode(OperationCode.TRS001002.FREEZE.getCode());
        logger.info("开始在首信易冻结资金："+freezeReq);
        trs001002Mapper.insert(freezeReq);
        TRS001002Resp response = (TRS001002Resp)super.syncSend(freezeReq);
        response.setMsgid(freezeReq.getMsgid());
        response.setReturnTime(new Date());
        trs001002Mapper.updateByPrimaryKey(response);
        logger.info("收到冻结资金返回"+response);
        return response;
    }


    @Override
    public TRS001002Resp unfreeze(TRS001002Req unfreezeReq) throws IllegalPlatformAugumentException
    {
        unfreezeReq.setOperationCode(OperationCode.TRS001002.UNFREEZE.getCode());
        logger.info("开始解冻资金："+unfreezeReq);
        trs001002Mapper.insert(unfreezeReq);
        TRS001002Resp response = (TRS001002Resp)super.syncSend(unfreezeReq);
        response.setMsgid(unfreezeReq.getMsgid());
        response.setReturnTime(new Date());
        trs001002Mapper.updateByPrimaryKey(response);
        logger.info("收到解冻资金返回"+response);
        return response;
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = trs001002Mapper.selectInvestCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = trs001002Mapper.selectInvestListByCondition(searchConditions);
        page.setResult(list);
    }

    private void setDefaultParameter(TRS001002Req unfreezeReq)
    {
        //默认参数设置：
        if (unfreezeReq.getCurr() == null)
        {
            unfreezeReq.setCurr(Currency.RMB.getCode());
        }
    }
}
