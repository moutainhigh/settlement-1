package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00046Resp;

/**
 * 华兴银行，充值服务接口
 */
public interface IRechargeService
{
    /**
     * 单笔专属账户充值(OGW00045)<br>
    * 获取充值页面的xmlparam
    * 
    * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
    * @param acNo 华兴银行e账户账号
    * @param acName 账号户名
    * @param amount 交易金额
    * @param remark 备注（可空）
    * 
    * @return 经过加密，签名后的xml，用来放在页面ogwForm表单名为的RequestData的input域中（参见华兴文档3.2）
    */
    String getRechargeXmlParam(AppId appId, String acNo, String acName, BigDecimal amount, String remark);
    
    
    void updateRechargeResponse(OGW00045Resp resp45);

    /**
    *单笔充值结果查询 (OGW00046)
    *<p>
    * @param app 应用标识（电脑：PC；手机：APP；微信：WX）
    * @param oldReqSeqNo 原充值交易流水号
    * @return 
    */
    OGW00046Resp queryRechargeResult(AppId app, String oldReqSeqNo);

}
