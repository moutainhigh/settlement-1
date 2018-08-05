package com.yuanheng100.settlement.ghbank.service.impl;

import org.apache.log4j.Logger;

import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;
import com.yuanheng100.settlement.ghbank.service.IListenerService;

public class ListenerServiceImpl implements IListenerService
{
    private static Logger logger = Logger.getLogger(ListenerServiceImpl.class);

    @Override
    public void add42RegisterListener(OGW00042Resp resp42)
    {
        logger.info("增加开户回调listener，"+resp42);
    }

    @Override
    public void add44BindCardListener(OGW00044Resp resp44)
    {
        logger.info("增加绑卡回调listener，"+resp44);
    }

    @Override
    public void add45RechargeListener(OGW00045Resp resp45)
    {
        logger.info("增加充值回调listener，"+resp45);
    }

    @Override
    public void add47WithdrawListener(OGW00047Resp resp47)
    {
        logger.info("增加提现回调listener，"+resp47);
    }

    @Override
    public void add52InvestListener(OGW00052Resp resp52)
    {
        logger.info("增加投标回调listener，"+resp52);
    }

    @Override
    public void add61BondTradeListener(OGW00061Resp resp61)
    {
        logger.info("增加债权转让回调listener，"+resp61);
    }

    @Override
    public void add67RepayListener(OGW00067Resp resp67)
    {
        logger.info("增加借款人单标还款回调Listener，"+resp67);
    }
    
}
