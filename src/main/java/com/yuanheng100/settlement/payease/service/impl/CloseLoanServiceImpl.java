package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.callback.ICloseLoanCallbackListener;
import com.yuanheng100.settlement.payease.consts.Currency;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.mapper.TRS001003Mapper;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Req;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;
import com.yuanheng100.settlement.payease.service.ICloseLoanService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CloseLoanServiceImpl extends AbstractMessageService<TRS001003Req> implements ICloseLoanService
{
    private static Logger logger = Logger.getLogger(CloseLoanServiceImpl.class);

    @Autowired
    private TRS001003Mapper trs001003Mapper;

    @Override
    public TRS001003Resp closeLoan(TRS001003Req closeLoanReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(closeLoanReq);
        closeLoanReq.setOperationCode(OperationCode.TRS001003.CLOSE_LOAN.getCode());
        logger.info("开始通知首信易标的结束，转账给借款人：" + closeLoanReq);
        trs001003Mapper.insert(closeLoanReq);
        TRS001003Resp response = (TRS001003Resp) super.syncSend(closeLoanReq);
        response.setMsgid(closeLoanReq.getMsgid());
        response.setReturnTime(new Date());
        trs001003Mapper.updateByPrimaryKey(response);
        logger.info("收到通知首信易标的结束返回" + response);
        return response;
    }

    @Override
    public TRS001003Resp closeLoan(TRS001003Req closeLoanReq, ICloseLoanCallbackListener closeLoanCallbackListener) throws IllegalPlatformAugumentException
    {
        TRS001003Resp trs001003Resp = closeLoan(closeLoanReq);
        CallbackListenerContainer.putCloseLoanCallbackListener(closeLoanReq.getSerlNum(),closeLoanCallbackListener);
        return trs001003Resp;
    }

    @Override
    public TRS001003Resp queryCloseLoan(TRS001003Req queryCloseLoanReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(queryCloseLoanReq);
        queryCloseLoanReq.setOperationCode(OperationCode.TRS001003.QUERY_CLOSE_LOAN.getCode());
        logger.info("开始查询标的转出结果：" + queryCloseLoanReq);
        TRS001003Resp response = (TRS001003Resp) super.syncSend(queryCloseLoanReq);
        logger.info("收到查询结果" + response);
        return response;
    }

    @Override
    public void updateResult(TRS001003Resp trs001003Resp)
    {
        //根据流水号更新代扣
        trs001003Mapper.updateBySerlNum(trs001003Resp);
    }

    private void setDefaultParameter(TRS001003Req trs001003Req)
    {
        //默认参数设置：
        if (trs001003Req.getCurr() == null)
        {
            trs001003Req.setCurr(Currency.RMB.getCode());
        }
    }


}
