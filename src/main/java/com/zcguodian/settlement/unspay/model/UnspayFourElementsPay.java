package com.zcguodian.settlement.unspay.model;

import java.math.BigDecimal;
import java.util.Date;

public class UnspayFourElementsPay {
    /**
     * 自增主键，作为具有唯一性的订单号
     */
    private Integer orderId;

    /**
     * 导入文件名
     */
    private String filename;

    /**
     * 导入时间
     */
    private Date uploadDate;

    /**
     * 操作人ID，上传人
     */
    private Integer operator;

    /**
     * 操作人ID,审核人
     */
    private Integer auditor;

    /**
     * 进件编号，与导入者的业务有关，保证唯一性
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
     * 金额
     */
    private BigDecimal amount;

    /**
     * 付款目的
     */
    private String purpose;

    /**
     * 收款人身份证号
     */
    private String idCardNo;

    /**
     * 付款人付款摘要
     */
    private String summary;

    /**
     * 收款人手机号
     */
    private Long phoneNo;

    /**
     * 审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    private Short verifyStatus;

    /**
     * 代付发送时间
     */
    private Date sendDate;

    /**
     * 代付返回时间
     */
    private Date responseDate;

    /**
     * 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    private String payResult;

    /**
     * 交易结果描述信息（主动查询获取）
     */
    private String desc;

    /**
     * 自增主键，作为具有唯一性的订单号
     * @return orderId 自增主键，作为具有唯一性的订单号
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 自增主键，作为具有唯一性的订单号
     * @param orderId 自增主键，作为具有唯一性的订单号
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 导入文件名
     * @return filename 导入文件名
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 导入文件名
     * @param filename 导入文件名
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     * 导入时间
     * @return uploadDate 导入时间
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * 导入时间
     * @param uploadDate 导入时间
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 操作人ID，上传人
     * @return operator 操作人ID，上传人
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     * 操作人ID，上传人
     * @param operator 操作人ID，上传人
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    /**
     * 操作人ID,审核人
     * @return auditor 操作人ID,审核人
     */
    public Integer getAuditor() {
        return auditor;
    }

    /**
     * 操作人ID,审核人
     * @param auditor 操作人ID,审核人
     */
    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    /**
     * 进件编号，与导入者的业务有关，保证唯一性
     * @return loanApplyId 进件编号，与导入者的业务有关，保证唯一性
     */
    public Long getLoanApplyId() {
        return loanApplyId;
    }

    /**
     * 进件编号，与导入者的业务有关，保证唯一性
     * @param loanApplyId 进件编号，与导入者的业务有关，保证唯一性
     */
    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    /**
     * 收款人姓名
     * @return name 收款人姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 收款人姓名
     * @param name 收款人姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 收款人银行卡号
     * @return cardNo 收款人银行卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 收款人银行卡号
     * @param cardNo 收款人银行卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * 金额
     * @return amount 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 付款目的
     * @return purpose 付款目的
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * 付款目的
     * @param purpose 付款目的
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    /**
     * 收款人身份证号
     * @return idCardNo 收款人身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     * 收款人身份证号
     * @param idCardNo 收款人身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    /**
     * 付款人付款摘要
     * @return summary 付款人付款摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 付款人付款摘要
     * @param summary 付款人付款摘要
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * 收款人手机号
     * @return phoneNo 收款人手机号
     */
    public Long getPhoneNo() {
        return phoneNo;
    }

    /**
     * 收款人手机号
     * @param phoneNo 收款人手机号
     */
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * 审核状态，0：未审核 1：审核通过 2：审核拒绝
     * @return verifyStatus 审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    public Short getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * 审核状态，0：未审核 1：审核通过 2：审核拒绝
     * @param verifyStatus 审核状态，0：未审核 1：审核通过 2：审核拒绝
     */
    public void setVerifyStatus(Short verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    /**
     * 代付发送时间
     * @return sendDate 代付发送时间
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 代付发送时间
     * @param sendDate 代付发送时间
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 代付返回时间
     * @return responseDate 代付返回时间
     */
    public Date getResponseDate() {
        return responseDate;
    }

    /**
     * 代付返回时间
     * @param responseDate 代付返回时间
     */
    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    /**
     * 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     * @return payResult 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    public String getPayResult() {
        return payResult;
    }

    /**
     * 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     * @param payResult 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    public void setPayResult(String payResult) {
        this.payResult = payResult == null ? null : payResult.trim();
    }

    /**
     * 交易结果描述信息（主动查询获取）
     * @return desc 交易结果描述信息（主动查询获取）
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 交易结果描述信息（主动查询获取）
     * @param desc 交易结果描述信息（主动查询获取）
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}