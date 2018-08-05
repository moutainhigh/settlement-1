package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00070Resp;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00071Resp;
import com.yuanheng100.settlement.ghbank.model.autorepay.OGW00072Resp;

/**
 * 自动还款相关接口
 * 
 * @author Baisong
 */
public interface IAutoRepayService
{

    /**
     * 6.31 自动还款授权 (OGW00069) （可选，跳转我行页面处理）<br>
     * <p>
     * 由第三方公司发起，跳转到银行官网完成进行该操作。<br>
     * 此操作可在标的放款前或放款后都能发起授权。有自动还款授权后才能发起自动单笔还款。客户在页面流程操作共不可超过20分钟，否则请求超时。<br>
     * 客户账户为正常状态才能授权成功。<br>
     * @param app       应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo    借款编号
     * @param bwAcName  还款账号户名
     * @param bwAcNo    还款账号
     * @return 经过加密，签名后的xml，用来放在页面ogwForm表单名为的RequestData的input域中（参见华兴文档3.2）
     */
    String getAutoRepayAuthorizationXmlParam(AppId app, int loanNo, String bwAcName, String bwAcNo);
    
    /**
     * 6.32 自动还款授权结果查询（可选）(OGW00070)
     * <p>
     * 由第三方公司发起。请求收不到响应时，可通过该接口查询处理结果。交易提交我行5分钟后，可通过该接口查询银行处理结果。
     * @param app         应用标识
     * @param oldReqSeqNo 原自动还款授权交易流水号
     * @return
     */
    OGW00070Resp queryAutoRepayAuthorizationResult(AppId app, String oldReqSeqNo);

    /**
     * 自动还款授权撤销（可选）(OGW00071)
    * <p>
    * 由第三方公司发起。
    * <p>
    * 调此接口前需先调用“获取短信验证码(OGW00041)”发送短信验证码后再发起此接口进行授权撤销。
     * @param app      应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param otpSeqNo 动态密码唯一标识
     * @param otpNo    动态密码
     * @param loanNo   借款编号
     * @param bwAcName 还款账号户名
     * @param bwAcNo   还款账号
     * @return
     */
    OGW00071Resp queryAutoRepayAuthorizationResult(AppId app, String otpSeqNo, String otpNo, int loanNo,
            String bwAcName, String bwAcNo);
    
    /**
     * 6.34 自动单笔还款（可选）(OGW00072)
     * <p>
     * 由第三方公司发起。如交易未收到返回结果，可通过借款人单标还款结果查询结果查得。
     * @param app      应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo   借款编号
     * @param bwAcName 还款账号户名
     * @param bwAcNo   还款账号
     * @param feeAmt   手续费
     * @param amount   还款金额
     * @return
     */
    OGW00072Resp autoRepay(AppId app, int loanNo, String bwAcName, String bwAcNo, BigDecimal feeAmt, BigDecimal amount);
}
