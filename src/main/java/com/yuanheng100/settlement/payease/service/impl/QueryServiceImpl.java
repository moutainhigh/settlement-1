package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IQueryService;

public class QueryServiceImpl extends AbstractMessageService<TRS001010Req> implements IQueryService
{

    //private static Logger logger = Logger.getLogger(QueryServiceImpl.class);
    
    @Override
    public TRS001010Resp queryInvestAccount(TRS001010Req trs001010Req) throws IllegalArgumentException
    {
        //强制设置operationCode为60000
        trs001010Req.setOperationCode(OperationCode.TRS001010.QUERY_INVEST_ACCOUNT.getCode());
        TRS001010Resp response = (TRS001010Resp)syncSend(trs001010Req);
        return response;
    }

    @Override
    public TRS001010Resp queryLiabilityAccount(TRS001010Req trs001010Req) throws IllegalArgumentException
    {
      //强制设置operationCode为60001
        trs001010Req.setOperationCode(OperationCode.TRS001010.QUERY_LIABILITY_ACCOUNT.getCode());
        TRS001010Resp response = (TRS001010Resp)syncSend(trs001010Req);
        return response;
    }

}
