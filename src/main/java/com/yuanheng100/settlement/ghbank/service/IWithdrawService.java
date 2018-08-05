package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;
import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Resp;

/**
 * 提现接口，包括单笔提现（OGW00047）和单笔提现结果查询 （OGW00048）
 * @author Baisong
 *
 */
public interface IWithdrawService
{
    
    /**
    * 单笔提现（OGW00047）<br>
    * 
    * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
    * @param acNo 华兴e账户账号
    * @param acName 账号户名
    * @param amount 交易金额
    * @param remark 备注（可空）
    * @return
    */
   String getWithdrawXmlparam(AppId app, String acNo, String acName, BigDecimal amount, String remark);
   
   
   void updateWithdrawResponse(OGW00047Resp resp47);
   
   /**
   * 单笔提现结果查询 （OGW00048）<br>
   * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
   * @param transDt 原提现交易日期（可空，YYYYMMDD）
   * @param oldReqSeqNo 原提现交易流水号
   * @return
   */
  OGW00048Resp queryWithdrawResult(AppId app, Date transDt, String oldReqSeqNo);

}
