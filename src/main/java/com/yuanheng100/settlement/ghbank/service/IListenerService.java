package com.yuanheng100.settlement.ghbank.service;

import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;

/**
 * 供P2P系统参数回调listener使用的接口
 * @author Baisong
 *
 */
public interface IListenerService
{
    /**
     * 增加开户回调listener
     * @param resp42
     */
    void add42RegisterListener(OGW00042Resp resp42);
    
    /**
     * 增加绑卡回调listener
     * @param resp44
     */
    void add44BindCardListener(OGW00044Resp resp44);
    
    /**
     * 增加充值回调listener
     * @param resp45
     */
    void add45RechargeListener(OGW00045Resp resp45);
    
    /**
     * 增加提现回调listener
     * @param resp47
     */
    void add47WithdrawListener(OGW00047Resp resp47);
    
    /**
     * 增加投标回调listener
     * @param resp52
     */
    void add52InvestListener(OGW00052Resp resp52);
    
    /**
     * 增加债权转让回调listener
     * @param resp61
     */
    void add61BondTradeListener(OGW00061Resp resp61);
    
    /**
     * 增加借款人单标还款回调Listener
     * @param resp67
     */
    void add67RepayListener(OGW00067Resp resp67);
}
