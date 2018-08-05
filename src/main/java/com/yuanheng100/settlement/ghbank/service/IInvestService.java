package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00054Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Resp;

public interface IInvestService
{

    /**
     * 单笔投标(OGW00052)<br>
     * <br>
     * 获取投标表单xml参数<br>
     * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo 借款编号
     * @param acNo 投资人华兴e账户账号
     * @param acName 投资人账号户名
     * @param amount 投标金额
     * @param remark 备注（可空）
     * @return 经过加密，签名后的xml，用来放在页面ogwForm表单名为的RequestData的input域中（参见华兴文档3.2）
     */
    String getInvestXmlParam(AppId appId, long loanNo, String acNo, String acName, BigDecimal amount, String remark);
    
    
    void updateInvestResponse(OGW00052Resp resp52);
    
    /**
     * 单笔投标结果查询(OGW00053)<br>
     * 
     * @param app         应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo 原投标交易流水号
     * @return
     */
    OGW00053Resp queryInvestResult(AppId app, String oldReqSeqNo);
    
    /**
     * 投标优惠返回（可选）(OGW00054)<br>
     * <br>
     * 放款后才能发起，借款人划扣优惠金额，分别划入投资人账户
     * @param ogw00054Req
     * @return
     */
    OGW00054Resp investBonus(OGW00054Req ogw00054Req);
    
    /**
     * 投标优惠返回结果查询（可选）(OGW00055)<br>
     * <br>
     * 由第三方公司发起。当收不到返回时（5分钟后），可通过该接口查询银行处理结果。
     * @param app 应用标识
     * @param oldReqSeqNo 原投标优惠返回交易流水号
     * @return
     */
    OGW00055Resp queryInvestBonus(AppId app, String oldReqSeqNo);
}
