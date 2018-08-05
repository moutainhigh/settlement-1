package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00062Resp;

/**
 * 债权交易相关接口
 * 
 * @author Baisong
 */
public interface IBondTradeService
{

    /**
     * 6.22 债券转让申请(OGW00061) （可选，跳转我行页面处理）<br>
     * 由第三方公司发起。此接口只能在标的放款后发起。<br>
     * 投资人在原投资的标的放款后把原投资项目剩余的金额全额转让，可通过此接口向我行发起申请，客户在跳转到我行系统进行授权，<br>
     * 我行登记此申请记录，返回结果到第三方公司。<br>
     * <br>
     * @param app          应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo  原投标流水
     * @param oldReqNumber 原标的编号（即loanNo）
     * @param oldReqName   原标的名称
     * @param accNo        转让人华兴e账户账号
     * @param custName     转让人名称
     * @param amount       剩余金额
     * @param preIncome    预计剩余收益
     * @return 经过加密，签名后的xml，用来放在页面ogwForm表单名为的RequestData的input域中（参见华兴文档3.2）
     */
    String getTradeReqXmlParam(AppId appId, String oldReqSeqNo, int oldReqNumber, String oldReqName, String accNo, String custName, BigDecimal amount, BigDecimal preIncome);
    
    
    void updateBondTradeRespone(OGW00061Resp resp61);
    
    /**
     * 6.23 债券转让结果查询(OGW00062)
     * <p>
     * 由第三方公司发起。交易提交我行5分钟后，可通过该接口查询银行处理结果。客户在页面流程操作共不可超过20分钟，否则请求超时。
     * @param app         应用标识
     * @param oldReqSeqNo 原债券转让申请流水
     * @return
     */
    OGW00062Resp queryTransferResult(AppId app, String oldReqSeqNo);
    

}
