package com.yuanheng100.settlement.ghbank.model.dao;

import java.math.BigDecimal;
import java.util.Date;

public class GhbankOGW00067 {
    /**
     *同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）
     */
    private String channelFlow;

    /**
     *优先级 1：普通 2：紧急 3：特急
     */
    private Short priority;

    /**
     *调用方式 1：同步 2：异步（需主动回查）
     */
    private Short invokeMethod;

    /**
     *渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期
     */
    private Date channelDateTime;

    /**
     *交易码
     */
    private String transCode;

    /**
     *应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX
     */
    private String appId;

    /**
     *交易类型
     */
    private Short tTrans;

    /**
     *还款类型 1=正常还款 2=垫付后，借款人还款
     */
    private Short dfFlag;

    /**
     *原垫付请求流水号 垫付还款时必需
     */
    private String oldReqSeqNo;

    /**
     *借款编号
     */
    private Long loanNo;

    /**
     *借款人姓名
     */
    private String bwAcName;

    /**
     *借款人账号
     */
    private String bwAcNo;

    /**
     *还款金额
     */
    private BigDecimal amount;

    /**
     *备注
     */
    private String remark;

    /**
     *返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    private String returnUrl;

    /**
     *服务流水号
     */
    private String serverFlow;

    /**
     *服务日期 + 服务时间
     */
    private Date serverDateTime;

    /**
     *业务状态
     */
    private Short status;

    /**
     *错误代码
     */
    private String errorCode;

    /**
     *错误信息,错误代码非0时，返回具体的错误原因
     */
    private String errorMsg;

    /**
     *备用字段1
     */
    private String extFiled1;

    /**
     *备用字段2
     */
    private String extFiled2;

    /**
     *备用字段3
     */
    private String extFiled3;

    /**
     *同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）
     */
    public String getChannelFlow() {
        return channelFlow;
    }

    /**
     *同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）
     */
    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow == null ? null : channelFlow.trim();
    }

    /**
     *优先级 1：普通 2：紧急 3：特急
     */
    public Short getPriority() {
        return priority;
    }

    /**
     *优先级 1：普通 2：紧急 3：特急
     */
    public void setPriority(Short priority) {
        this.priority = priority;
    }

    /**
     *调用方式 1：同步 2：异步（需主动回查）
     */
    public Short getInvokeMethod() {
        return invokeMethod;
    }

    /**
     *调用方式 1：同步 2：异步（需主动回查）
     */
    public void setInvokeMethod(Short invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    /**
     *渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期
     */
    public Date getChannelDateTime() {
        return channelDateTime;
    }

    /**
     *渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期
     */
    public void setChannelDateTime(Date channelDateTime) {
        this.channelDateTime = channelDateTime;
    }

    /**
     *交易码
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     *交易码
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    /**
     *应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX
     */
    public String getAppId() {
        return appId;
    }

    /**
     *应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     *交易类型
     */
    public Short gettTrans() {
        return tTrans;
    }

    /**
     *交易类型
     */
    public void settTrans(Short tTrans) {
        this.tTrans = tTrans;
    }

    /**
     *还款类型 1=正常还款 2=垫付后，借款人还款
     */
    public Short getDfFlag() {
        return dfFlag;
    }

    /**
     *还款类型 1=正常还款 2=垫付后，借款人还款
     */
    public void setDfFlag(Short dfFlag) {
        this.dfFlag = dfFlag;
    }

    /**
     *原垫付请求流水号 垫付还款时必需
     */
    public String getOldReqSeqNo() {
        return oldReqSeqNo;
    }

    /**
     *原垫付请求流水号 垫付还款时必需
     */
    public void setOldReqSeqNo(String oldReqSeqNo) {
        this.oldReqSeqNo = oldReqSeqNo == null ? null : oldReqSeqNo.trim();
    }


    public Long getLoanNo()
    {
        return loanNo;
    }

    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }

    /**
     *借款人姓名
     */
    public String getBwAcName() {
        return bwAcName;
    }

    /**
     *借款人姓名
     */
    public void setBwAcName(String bwAcName) {
        this.bwAcName = bwAcName == null ? null : bwAcName.trim();
    }

    /**
     *借款人账号
     */
    public String getBwAcNo() {
        return bwAcNo;
    }

    /**
     *借款人账号
     */
    public void setBwAcNo(String bwAcNo) {
        this.bwAcNo = bwAcNo == null ? null : bwAcNo.trim();
    }

    /**
     *还款金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *还款金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     *备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     *备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     *返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     *返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
    }

    /**
     *服务流水号
     */
    public String getServerFlow() {
        return serverFlow;
    }

    /**
     *服务流水号
     */
    public void setServerFlow(String serverFlow) {
        this.serverFlow = serverFlow == null ? null : serverFlow.trim();
    }

    /**
     *服务日期 + 服务时间
     */
    public Date getServerDateTime() {
        return serverDateTime;
    }

    /**
     *服务日期 + 服务时间
     */
    public void setServerDateTime(Date serverDateTime) {
        this.serverDateTime = serverDateTime;
    }

    /**
     *业务状态
     */
    public Short getStatus() {
        return status;
    }

    /**
     *业务状态
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     *错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *错误代码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    /**
     *错误信息,错误代码非0时，返回具体的错误原因
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *错误信息,错误代码非0时，返回具体的错误原因
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    /**
     *备用字段1
     */
    public String getExtFiled1() {
        return extFiled1;
    }

    /**
     *备用字段1
     */
    public void setExtFiled1(String extFiled1) {
        this.extFiled1 = extFiled1 == null ? null : extFiled1.trim();
    }

    /**
     *备用字段2
     */
    public String getExtFiled2() {
        return extFiled2;
    }

    /**
     *备用字段2
     */
    public void setExtFiled2(String extFiled2) {
        this.extFiled2 = extFiled2 == null ? null : extFiled2.trim();
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