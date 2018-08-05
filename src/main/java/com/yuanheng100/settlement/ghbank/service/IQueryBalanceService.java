package com.yuanheng100.settlement.ghbank.service;

import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Resp;

/**
 * 余额查询接口
 * 
 * @author Baisong
 *
 */
public interface IQueryBalanceService
{

    /**
     * 账户余额查询（OGW00049）
     * @param app    应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param acNo   华兴e账户账号
     * @param acName 银行户名
     * @return
     */
    OGW00049Resp queryBalance(AppId app, String acNo, String acName);
    
    /**
     * 日终对账请求
     * @param app  应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param operFlag
     * @param checkDate
     * @return
     */
    OGW00077Resp dayEndCheck(AppId app, Short operFlag, Date checkDate);
}
