package com.yuanheng100.settlement.unspay.model;

import java.math.BigDecimal;
import java.util.Date;

public class UnspayPay {
    /**
     *自增主键，作为具有唯一性的订单号
     */
    private Integer orderId;

    /**
     *导入文件名
     */
    private String filename;

    /**
     *导入时间
     */
    private Date uploadDate;

    /**
     *操作人ID，上传人
     */
    private Integer operator;

    /**
     *操作人ID,审核人
     */
    private Integer auditor;

    /**
     *进件编号，与导入者的业务有关，保证唯一性
     */
    private Long loanApplyId;

    /**
     * 收款人姓名
     */
    private String name;

    /**
     * 收款人银行卡号
     */
    private String cardNo;

    /**
     *付款金额
     */
    private BigDecimal amount;

    /**
     *付款目的
     */
    private String purpose;

    /**
     *审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    private Short verifyStatus;

    /**
     *代付计划发送时间，没有则立即发送
     */
    private Date planDate;

    /**
     *代付发送时间
     */
    private Date sendDate;

    /**
     *代付返回时间
     */
    private Date responseDate;

    /**
     *付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    private String payResult;

    /**
     *交易结果描述信息（主动查询获取）
     */
    private String desc;

    /**
     *自增主键，作为具有唯一性的订单号
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     *自增主键，作为具有唯一性的订单号
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     *导入文件名
     */
    public String getFilename() {
        return filename;
    }

    /**
     *导入文件名
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     *导入时间
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     *导入时间
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     *操作人ID，上传人
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     *操作人ID，上传人
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    /**
     *操作人ID,审核人
     */
    public Integer getAuditor() {
        return auditor;
    }

    /**
     *操作人ID,审核人
     */
    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    /**
     *进件编号，与导入者的业务有关，保证唯一性
     */
    public Long getLoanApplyId() {
        return loanApplyId;
    }

    /**
     *进件编号，与导入者的业务有关，保证唯一性
     */
    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    /**
     * 收款人姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 收款人姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 收款人银行卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 收款人银行卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     *付款金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *付款金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     *付款目的
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     *付款目的
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    /**
     * 审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    public Short getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * 审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    public void setVerifyStatus(Short verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    /**
     *代扣计划发送时间，没有则立即发送
     */
    public Date getPlanDate() {
        return planDate;
    }

    /**
     *代付计划发送时间，没有则立即发送
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    /**
     *代付发送时间
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     *代扣发送时间
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     *代付返回时间
     */
    public Date getResponseDate() {
        return responseDate;
    }

    /**
     *代付返回时间
     */
    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    /**
     *付款结果，银生宝通过回调地址返回付款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    public String getPayResult() {
        return payResult;
    }

    /**
     *付款结果，银生宝通过回调地址返回付款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    public void setPayResult(String payResult) {
        this.payResult = payResult == null ? null : payResult.trim();
    }

    /**
     *交易结果描述信息（主动查询获取）
     */
    public String getDesc() {
        return desc;
    }

    /**
     *交易结果描述信息（主动查询获取）
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}