package com.yuanheng100.settlement.ghbank.model.dao;

import java.util.Date;

public class GhbankOGW00042 {
    /**
     *自增主键，无业务含义
     */
    private Integer msgid;

    /**
     *优先级 1：普通 2：紧急 3：特急
     */
    private Short priority;

    /**
     *调用方式 1：同步 2：异步（需主动回查）
     */
    private Short invokeMethod;

    /**
     *同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）
     */
    private String channelFlow;

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
     *姓名
     */
    private String acName;

    /**
     *证件类型，目前只能是身份证
     */
    private Integer idType;

    /**
     *证件号码,只支持身份证
     */
    private String idNo;

    /**
     *手机号码
     */
    private Integer mobile;

    /**
     *用户邮箱
     */
    private String email;

    /**
     *客户经理编号
     */
    private String custMngrNo;

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
     *原开户交易流水号
     */
    private String oldReqSeqNo;

    /**
     *客户姓名
     */
    private String respAcName;

    /**
     *证件类型，目前只能是身份证
     */
    private Integer respIdType;

    /**
     *证件号码,只支持身份证
     */
    private String respIdNo;

    /**
     *手机号码
     */
    private Integer respMobile;

    /**
     *银行账号
     */
    private String respAcNo;

    /**
     *交易码，本例中应为OGWR0001
     */
    private String returnTransCode;

    /**
     *响应码，000000标识成功
     */
    private String returnCode;

    /**
     *响应信息
     */
    private String returnMsg;

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
     *自增主键，无业务含义
     */
    public Integer getMsgid() {
        return msgid;
    }

    /**
     *自增主键，无业务含义
     */
    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
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
     *姓名
     */
    public String getAcName() {
        return acName;
    }

    /**
     *姓名
     */
    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    /**
     *证件类型，目前只能是身份证
     */
    public Integer getIdType() {
        return idType;
    }

    /**
     *证件类型，目前只能是身份证
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    /**
     *证件号码,只支持身份证
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     *证件号码,只支持身份证
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    /**
     *手机号码
     */
    public Integer getMobile() {
        return mobile;
    }

    /**
     *手机号码
     */
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    /**
     *用户邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     *用户邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     *客户经理编号
     */
    public String getCustMngrNo() {
        return custMngrNo;
    }

    /**
     *客户经理编号
     */
    public void setCustMngrNo(String custMngrNo) {
        this.custMngrNo = custMngrNo == null ? null : custMngrNo.trim();
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
     *原开户交易流水号
     */
    public String getOldReqSeqNo() {
        return oldReqSeqNo;
    }

    /**
     *原开户交易流水号
     */
    public void setOldReqSeqNo(String oldReqSeqNo) {
        this.oldReqSeqNo = oldReqSeqNo == null ? null : oldReqSeqNo.trim();
    }

    /**
     *客户姓名
     */
    public String getRespAcName() {
        return respAcName;
    }

    /**
     *客户姓名
     */
    public void setRespAcName(String respAcName) {
        this.respAcName = respAcName == null ? null : respAcName.trim();
    }

    /**
     *证件类型，目前只能是身份证
     */
    public Integer getRespIdType() {
        return respIdType;
    }

    /**
     *证件类型，目前只能是身份证
     */
    public void setRespIdType(Integer respIdType) {
        this.respIdType = respIdType;
    }

    /**
     *证件号码,只支持身份证
     */
    public String getRespIdNo() {
        return respIdNo;
    }

    /**
     *证件号码,只支持身份证
     */
    public void setRespIdNo(String respIdNo) {
        this.respIdNo = respIdNo == null ? null : respIdNo.trim();
    }

    /**
     *手机号码
     */
    public Integer getRespMobile() {
        return respMobile;
    }

    /**
     *手机号码
     */
    public void setRespMobile(Integer respMobile) {
        this.respMobile = respMobile;
    }

    /**
     *银行账号
     */
    public String getRespAcNo() {
        return respAcNo;
    }

    /**
     *银行账号
     */
    public void setRespAcNo(String respAcNo) {
        this.respAcNo = respAcNo == null ? null : respAcNo.trim();
    }

    /**
     *交易码，本例中应为OGWR0001
     */
    public String getReturnTransCode() {
        return returnTransCode;
    }

    /**
     *交易码，本例中应为OGWR0001
     */
    public void setReturnTransCode(String returnTransCode) {
        this.returnTransCode = returnTransCode == null ? null : returnTransCode.trim();
    }

    /**
     *响应码，000000标识成功
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     *响应码，000000标识成功
     */
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode == null ? null : returnCode.trim();
    }

    /**
     *响应信息
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     *响应信息
     */
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg == null ? null : returnMsg.trim();
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