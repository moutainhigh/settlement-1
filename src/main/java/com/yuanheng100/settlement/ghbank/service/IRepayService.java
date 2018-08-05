package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;
import java.util.List;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00068Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00073Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00076Resp;

/**
 * 还款相关接口
 * 
 * @author Baisong
 */
public interface IRepayService
{

    /**
     * 6.29 借款人单标还款 (OGW00067) （必须，跳转我行页面处理）<br>
     * <br>
     * @param app         应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param dfFlag      还款类型（1=正常还款  2=垫付后，借款人还款）
     * @param oldReqSeqNo 原垫付请求流水号
     * @param loanNo      借款编号
     * @param bwAcName    还款账号户名
     * @param bwAcNo      还款人华兴E账户账号
     * @param amount      还款金额
     * @param feeAmt      手续费（可空，默认为0）
     * @return
     */
    String getRepayXmlparam(AppId app, DFFlag dfFlag, String oldReqSeqNo, long loanNo, String bwAcName,
            String bwAcNo, BigDecimal amount, String remark, BigDecimal feeAmt);
    
    
    void updateRepayResponse(OGW00067Resp resp67);
    

    /**
     * 6.30 借款人单标还款结果查询(OGW00068)<br>
     * <br>
     * @param app         应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo 原借款人单标还款交易流水号
     * @return
     */
    OGW00068Resp queryRepayResult(AppId app, String oldReqSeqNo);

    /**
     * 单标公司垫付还款(OGW00073)
    * <p>
    * 由第三方发起。
    * 标的已放款后可发起，由公司的垫资账号代替借款人账号还款，后续借款人还款时需调用借款人单标还款（OGW0067）来还款，借款人还款金额会偿还公司垫付款。
     * @param app      应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo   借款编号
     * @param bwAcName 还款账号户名
     * @param bwAcNo   还款账号
     * @param amount   还款金额
     * @param feeAmt   手续费
     * @return
     */
    OGW00073Resp payForBorrower(AppId app, long loanNo, String bwAcName, String bwAcNo, BigDecimal amount,
            BigDecimal feeAmt);

    /**
     * 还款收益明细提交(OGW00074)
    * <p>
    * 由第三方发起。
    * 标的已放款后可发起，由公司的垫资账号代替借款人账号还款，后续借款人还款时需调用借款人单标还款（OGW0067）来还款，借款人还款金额会偿还公司垫付款。
     * @param app         应用标识
     * @param oldReqSeqNo 原还款交易流水号
     * @param dfFlag      还款类型
     * @param loanNo      借款编号
     * @param bwAcName    还款账号户名
     * @param bwAcNo      还款账号
     * @param repayList   循环列表,有多少个需发收益的投资人就有多少个Repay
     * @return
     */
    OGW00074Resp repayInterestDetail(AppId app, String oldReqSeqNo, short dfFlag, long loanNo, String bwAcName,
            String bwAcNo, List<OGW00074Req.RepayItem> repayList);

    /**
     * 还款收益结果查询 (OGW00075)
    * <p>
    * 第三方公司发起。交易提交我行5~10分钟后，可通过该接口查询银行处理结果。
     * @param app         应用标识
     * @param oldReqSeqNo 原流水号,原还款收益提交交易流水号
     * @param loanNo      借款编号
     * @param subSeqNo    子流水号
     * @return
     */
    OGW00075Resp queryRepayInterestResult(AppId app, String oldReqSeqNo, long loanNo, String subSeqNo);

    /**
     * 单笔奖励或分红（可选）(OGW00076)
    * <p>
    * 根据第三方公司指令，将第三方公司计算户的钱，分发给客户。
     * @param app    应用标识
     * @param acNo   收款账号
     * @param acName 收款户名
     * @param amount 交易金额
     * @param remark 备注
     * @return
     */
    OGW00076Resp investorBonus(AppId app, String acNo, String acName, BigDecimal amount, String remark);
}
