package com.yuanheng100.settlement.ghbank.model.dao;

import java.math.BigDecimal;
import java.util.Date;

public class GhbankOGW00074RepayItem {
    /**
     *子流水号
     */
    private String subSeqNo;

    /**
     *原还款收益明细提交交易流水号
     */
    private String oldReqSeqNo;

    /**
     *投资人账号
     */
    private String acNo;

    /**
     *投资人账号户名
     */
    private String acName;

    /**
     *该收益所属截止日期
     */
    private Date incomeDate;

    /**
     *还款总金额
     */
    private BigDecimal amount;

    /**
     *本次还款本金
     */
    private BigDecimal principalAmt;

    /**
     *本次还款收益
     */
    private BigDecimal incomeAmt;

    /**
     *本次还款费用
     */
    private BigDecimal feeAmt;

    /**
     *状态 L 待处理 R正在处理 N 未明 F失败 S成功
     */
    private String status;

    /**
     *错误原因
     */
    private String errorMsg;

    /**
     *银行交易日期+时间
     */
    private Date transDateTime;

    /**
     *银行交易流水号
     */
    private String hostJnlNo;

    /**
     *备用字段3
     */
    private String extFiled3;

    /**
     *子流水号
     */
    public String getSubSeqNo() {
        return subSeqNo;
    }

    /**
     *子流水号
     */
    public void setSubSeqNo(String subSeqNo) {
        this.subSeqNo = subSeqNo == null ? null : subSeqNo.trim();
    }

    /**
     *原还款收益明细提交交易流水号
     */
    public String getOldReqSeqNo() {
        return oldReqSeqNo;
    }

    /**
     *原还款收益明细提交交易流水号
     */
    public void setOldReqSeqNo(String oldReqSeqNo) {
        this.oldReqSeqNo = oldReqSeqNo == null ? null : oldReqSeqNo.trim();
    }

    /**
     *投资人账号
     */
    public String getAcNo() {
        return acNo;
    }

    /**
     *投资人账号
     */
    public void setAcNo(String acNo) {
        this.acNo = acNo == null ? null : acNo.trim();
    }

    /**
     *投资人账号户名
     */
    public String getAcName() {
        return acName;
    }

    /**
     *投资人账号户名
     */
    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    /**
     *该收益所属截止日期
     */
    public Date getIncomeDate() {
        return incomeDate;
    }

    /**
     *该收益所属截止日期
     */
    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    /**
     *还款总金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *还款总金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     *本次还款本金
     */
    public BigDecimal getPrincipalAmt() {
        return principalAmt;
    }

    /**
     *本次还款本金
     */
    public void setPrincipalAmt(BigDecimal principalAmt) {
        this.principalAmt = principalAmt;
    }

    /**
     *本次还款收益
     */
    public BigDecimal getIncomeAmt() {
        return incomeAmt;
    }

    /**
     *本次还款收益
     */
    public void setIncomeAmt(BigDecimal incomeAmt) {
        this.incomeAmt = incomeAmt;
    }

    /**
     *本次还款费用
     */
    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    /**
     *本次还款费用
     */
    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     *状态 L 待处理 R正在处理 N 未明 F失败 S成功
     */
    public String getStatus() {
        return status;
    }

    /**
     *状态 L 待处理 R正在处理 N 未明 F失败 S成功
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     *错误原因
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *错误原因
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    /**
     *银行交易日期+时间
     */
    public Date getTransDateTime() {
        return transDateTime;
    }

    /**
     *银行交易日期+时间
     */
    public void setTransDateTime(Date transDateTime) {
        this.transDateTime = transDateTime;
    }

    /**
     *银行交易流水号
     */
    public String getHostJnlNo() {
        return hostJnlNo;
    }

    /**
     *银行交易流水号
     */
    public void setHostJnlNo(String hostJnlNo) {
        this.hostJnlNo = hostJnlNo == null ? null : hostJnlNo.trim();
    }

    /**
     *备用字段3
     */
    public String getExtFiled3() {
        return extFiled3;
    }

    /**
     *备用字段3
     */
    public void setExtFiled3(String extFiled3) {
        this.extFiled3 = extFiled3 == null ? null : extFiled3.trim();
    }
}