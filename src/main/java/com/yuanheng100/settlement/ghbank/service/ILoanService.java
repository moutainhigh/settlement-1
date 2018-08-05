package com.yuanheng100.settlement.ghbank.service;

import java.math.BigDecimal;
import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.OperFlag;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00060Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00063Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00064Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00066Resp;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;

/**
 * 发标，流标接口
 * @author Baisong
 *
 */
public interface ILoanService
{
    /**
     * 6.11 单笔发标信息通知(OGW00051)<br>
     * <br>
     * 此接口支持新标的通知和债券转让标的通知。<br>
     * @param ogw00051Req 此方法字段较多，故传POJO类，具体字段参考华兴文档“6.11单笔发标信息通知(OGW00051)”
     * @return
     */
    OGW00051Resp createLoan(OGW00051Req ogw00051Req);

    /**
     * 单笔撤标(OGW00060)<br>
     * <br>
     * 标的放款前，由第三方公司发起。撤标金额须与投标金额一致，不可部分撤标。<br>
     * @param app          应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo       借款编号
     * @param oldReqSeqNo  原投标流水号
     * @param acNo         投资人的华兴e账户账号
     * @param acName       投资人账号户名
     * @param cancelReason 撤标原因（可空）
     * @return
     */
    OGW00060Resp cancelLoan(AppId app, long loanNo, String oldReqSeqNo, String acNo, String acName,
            String cancelReason);

    /**
    * 6.24   流标(OGW00063)<br>
    * <br>
    * 标的放款前，由第三方公司发起。流标完成后，不允许再次流标。<br>
    * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
    * @param loanNo 借款编号
    * @param disableReason 流标原因（可空）
    * @return
    */
    OGW00063Resp disableLoan(AppId app, long loanNo, String disableReason);

    /**
     * 6.26  流标结果查询 (OGW00064)<br>
     * <br>
     * 第三方公司发起。当收不到返回时（5~10分钟后），可通过该接口查询银行处理结果。
     * <br>
     * @param app         应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo 原流标交易流水号
     * @param oldTTJnl    原投标流水号
     * @return
     */
    OGW00064Resp queryCancelResult(AppId app, String oldReqSeqNo, String oldTTJnl);

    /**
     * 6.27  放款(OGW00065)<br>
     * <br>
     * 由第三方公司发起。当第三方公司认为标的已满，即可发起此接口，将投标人冻结资金放款至借款人账户。此接口适用于正常标的放款和转让标的放款。
     * <br>
     * @param app        应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param loanNo     借款编号
     * @param bwAcName   借款人姓名
     * @param bwAcNo     借款人的华兴e账户账号
     * @param acMngAmt   账户管理费
     * @param guarantAmt 风险保证金
     * @return
     */
    OGW00065Resp releaseLoan(AppId app, long loanNo, String bwAcName, String bwAcNo, BigDecimal acMngAmt,
            BigDecimal guarantAmt);

    /**
     * 放款结果查询 (OGW00066)<br>
     * <br>
     * 第三方公司发起。交易提交我行5~10分钟后，可通过该接口查询银行处理结果。<br>
     * @param app         应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo 原放款交易流水号
     * @param loanNo      借款编号
     * @param oldTTJnl    原投标流水号
     * @return
     */
    OGW00066Resp queryReleaseLoanResult(AppId app, String oldReqSeqNo, long loanNo, String oldTTJnl);

    /**
     * 日终对账请求(OGW00077)
    * <p>
    * 由第三方公司发起，如没收到结果请再次发起请求。
     * @param app       应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param operFlag  对账类型
     * @param checkDate 对账日期
     * @return
     */
    OGW00077Resp dailyTradeCheck(AppId app, OperFlag operFlag, Date checkDate);
    
    /**
     * 获取sequenceId
     * @return
     */
    int getSequenceId();
}
