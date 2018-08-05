package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/19.
 */
public enum ReturnStatus
{
    /**
     * 成功
     */
    S,
    
    /**
     * 失败
     */
    F,
    
    /**
     * 页面处理中（客户仍停留在页面操作，25分钟后仍收到此状态可置交易为失败）
     */
    R,
    
    /**
     * 未知（已提交后台，商户需再次发查询接口。）
     */
    N,
    
    /**
     * 预授权成功（目前未到账，下一工作日到账，当天无需再进行查询，下一工作日上午6点再进行查询，状态会变成S，如状态不变则也无需再查询，可在下一工作日在对账文件中确认交易状态。）
     */
    P,
    /**
     * 后台支付系统处理中（如果 ERRORMSG值为“ORDER_CREATED”，并超过25分钟未变，则可置交易是失败。其他情况商户需再次发查询接口。但2小时后状态仍未变的则可置为异常，无需再发起查询，后续在对账文件中确认交易状态或线下人工处理）
     */
    D,
    /**
     * 交易处理中
     */
    L;

}
