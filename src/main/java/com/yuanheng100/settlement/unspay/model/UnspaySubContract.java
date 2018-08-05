package com.yuanheng100.settlement.unspay.model;

import java.io.Serializable;
import java.util.Date;

public class UnspaySubContract implements Serializable {
    /**
     *自增主键，无业务含义
     */
    private Integer id;

    /**
     *导入文件名
     */
    private String filename;

    /**
     *导入时间
     */
    private Date uploadDate;

    /**
     *操作人ID,上传人
     */
    private Integer operator;

    /**
     *进件编号，与导入者的业务有关，保证唯一性
     */
    private Long loanApplyId;

    /**
     *客户姓名
     */
    private String name;

    /**
     *客户身份证号
     */
    private String idCardNo;

    /**
     *客户银行卡号
     */
    private String cardNo;

    /**
     * 银行代码
     */
    private Short bankCode;

    /**
     *用户手机号
     */
    private Long phoneNo;

    /**
     *子协议开始时间
     */
    private Date startDate;

    /**
     *子协议结束时间
     */
    private Date endDate;

    /**
     *扣款频率：1：每年； 2：每月； 3：每日
     */
    private Short cycle;

    /**
     *扣款次数限制
     */
    private Integer triesLimit;

    /**
     *子协议发送时间
     */
    private Date sendDate;

    /**
     * 子协议签署状态
     */
    private String signStatus;

    /**
     * 子协议签署返回消息
     */
    private String signMsg;

    /**
     *子协议号，在调用子协议录入接口后返回或手动查询
     */
    private String subContractId;

    /**
     *自增主键，无业务含义
     */
    public Integer getId() {
        return id;
    }

    /**
     *自增主键，无业务含义
     */
    public void setId(Integer id) {
        this.id = id;
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
     *操作人ID,上传人
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     *操作人ID,上传人
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
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
     *客户姓名
     */
    public String getName() {
        return name;
    }

    /**
     *客户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     *客户身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     *客户身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    /**
     *客户银行卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     *客户银行卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * 银行代码
     */
    public Short getBankCode() {
        return bankCode;
    }

    /**
     * 银行代码
     */
    public void setBankCode(Short bankCode) {
        this.bankCode = bankCode;
    }

    /**
     *用户手机号
     */
    public Long getPhoneNo() {
        return phoneNo;
    }

    /**
     *用户手机号
     */
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *子协议开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *子协议开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     *子协议结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *子协议结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *扣款频率：1：每年； 2：每月； 3：每日
     */
    public Short getCycle() {
        return cycle;
    }

    /**
     *扣款频率：1：每年； 2：每月； 3：每日
     */
    public void setCycle(Short cycle) {
        this.cycle = cycle;
    }

    /**
     *扣款次数限制
     */
    public Integer getTriesLimit() {
        return triesLimit;
    }

    /**
     *扣款次数限制
     */
    public void setTriesLimit(Integer triesLimit) {
        this.triesLimit = triesLimit;
    }

    /**
     *子协议发送时间
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     *子协议发送时间
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     *子协议签署状态
     */
    public String getSignStatus() {
        return signStatus;
    }

    /**
     *子协议签署状态
     */
    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    /**
     *子协议签署返回消息
     */
    public String getSignMsg() {
        return signMsg;
    }

    /**
     *子协议签署返回消息
     */
    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    /**
     *子协议号，在调用子协议录入接口后返回或手动查询
     */
    public String getSubContractId() {
        return subContractId;
    }

    /**
     *子协议号，在调用子协议录入接口后返回或手动查询
     */
    public void setSubContractId(String subContractId) {
        this.subContractId = subContractId == null ? null : subContractId.trim();
    }
}