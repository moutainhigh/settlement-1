package com.yuanheng100.settlement.ghbank.model.dao;

import java.util.Date;

public class GhbankOGW00051 {
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
     *借款编号
     */
    private Long loanNo;

    /**
     *借款编号
     */
    private Long investId;

    /**
     *标的名称
     */
    private String investObjName;

    /**
     *标的简介
     */
    private String investObjInfo;

    /**
     *最低投标金额
     */
    private Long minInvestAmt;

    /**
     *最高投标金额
     */
    private Long maxInvestAmt;

    /**
     *总标的金额
     */
    private Long investObjAmt;

    /**
     *招标开始日期
     */
    private Date investBeginDate;

    /**
     *招标到期日期
     */
    private Date investEndDate;

    /**
     *还款日期
     */
    private Date repayDate;

    /**
     *年利率
     */
    private Long yearRate;

    /**
     *期限 整型，天数，单位为天
     */
    private Integer investRange;

    /**
     *计息方式
     */
    private String ratesType;

    /**
     *还款方式
     */
    private String repaysType;

    /**
     *标的状态 0 正常 1 撤销
     */
    private Short investObjState;

    /**
     *借款人总数
     */
    private Integer bwTotalNum;

    /**
     *是否为债券转让标的
     */
    private Boolean zrFlag;

    /**
     *债券转让原标的编号
     */
    private String refLoanNo;

    /**
     *原投标第三方交易流水号
     */
    private String oldReqSeq;

    /**
     *借款人姓名
     */
    private String bwAcName;

    /**
     *借款人证件类型,身份证：1010
     */
    private Integer bwIdType;

    /**
     *借款人证件号码
     */
    private String bwIdNo;

    /**
     *借款人账号
     */
    private String bwAcNo;

    /**
     *借款人账号所属行号
     */
    private String bwAcBankId;

    /**
     *借款人账号所属行名
     */
    private String bwAcBankName;

    /**
     *借款人金额
     */
    private Long bwAmt;

    /**
     *借款人抵押品编号
     */
    private String mortgageId;

    /**
     *借款人抵押品简单描述
     */
    private String mortgageInfo;

    /**
     *借款人审批通过日期
     */
    private Date checkDate;

    /**
     *备注
     */
    private String remark;

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
     *银行交易流水号
     */
    private String resJnlNo;

    /**
     *交易日期 + 交易时间
     */
    private Date transDateTime;

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

    public Long getLoanNo()
    {
        return loanNo;
    }

    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }

    public Long getInvestId()
    {
        return investId;
    }

    public void setInvestId(Long investId)
    {
        this.investId = investId;
    }

    /**
     *标的名称
     */
    public String getInvestObjName() {
        return investObjName;
    }

    /**
     *标的名称
     */
    public void setInvestObjName(String investObjName) {
        this.investObjName = investObjName == null ? null : investObjName.trim();
    }

    /**
     *标的简介
     */
    public String getInvestObjInfo() {
        return investObjInfo;
    }

    /**
     *标的简介
     */
    public void setInvestObjInfo(String investObjInfo) {
        this.investObjInfo = investObjInfo == null ? null : investObjInfo.trim();
    }

    /**
     *最低投标金额
     */
    public Long getMinInvestAmt() {
        return minInvestAmt;
    }

    /**
     *最低投标金额
     */
    public void setMinInvestAmt(Long minInvestAmt) {
        this.minInvestAmt = minInvestAmt;
    }

    /**
     *最高投标金额
     */
    public Long getMaxInvestAmt() {
        return maxInvestAmt;
    }

    /**
     *最高投标金额
     */
    public void setMaxInvestAmt(Long maxInvestAmt) {
        this.maxInvestAmt = maxInvestAmt;
    }

    /**
     *总标的金额
     */
    public Long getInvestObjAmt() {
        return investObjAmt;
    }

    /**
     *总标的金额
     */
    public void setInvestObjAmt(Long investObjAmt) {
        this.investObjAmt = investObjAmt;
    }

    /**
     *招标开始日期
     */
    public Date getInvestBeginDate() {
        return investBeginDate;
    }

    /**
     *招标开始日期
     */
    public void setInvestBeginDate(Date investBeginDate) {
        this.investBeginDate = investBeginDate;
    }

    /**
     *招标到期日期
     */
    public Date getInvestEndDate() {
        return investEndDate;
    }

    /**
     *招标到期日期
     */
    public void setInvestEndDate(Date investEndDate) {
        this.investEndDate = investEndDate;
    }

    /**
     *还款日期
     */
    public Date getRepayDate() {
        return repayDate;
    }

    /**
     *还款日期
     */
    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    /**
     *年利率
     */
    public Long getYearRate() {
        return yearRate;
    }

    /**
     *年利率
     */
    public void setYearRate(Long yearRate) {
        this.yearRate = yearRate;
    }

    /**
     *期限 整型，天数，单位为天
     */
    public Integer getInvestRange() {
        return investRange;
    }

    /**
     *期限 整型，天数，单位为天
     */
    public void setInvestRange(Integer investRange) {
        this.investRange = investRange;
    }

    /**
     *计息方式
     */
    public String getRatesType() {
        return ratesType;
    }

    /**
     *计息方式
     */
    public void setRatesType(String ratesType) {
        this.ratesType = ratesType == null ? null : ratesType.trim();
    }

    /**
     *还款方式
     */
    public String getRepaysType() {
        return repaysType;
    }

    /**
     *还款方式
     */
    public void setRepaysType(String repaysType) {
        this.repaysType = repaysType == null ? null : repaysType.trim();
    }

    /**
     *标的状态 0 正常 1 撤销
     */
    public Short getInvestObjState() {
        return investObjState;
    }

    /**
     *标的状态 0 正常 1 撤销
     */
    public void setInvestObjState(Short investObjState) {
        this.investObjState = investObjState;
    }

    /**
     *借款人总数
     */
    public Integer getBwTotalNum() {
        return bwTotalNum;
    }

    /**
     *借款人总数
     */
    public void setBwTotalNum(Integer bwTotalNum) {
        this.bwTotalNum = bwTotalNum;
    }

    /**
     *是否为债券转让标的
     */
    public Boolean getZrFlag() {
        return zrFlag;
    }

    /**
     *是否为债券转让标的
     */
    public void setZrFlag(Boolean zrFlag) {
        this.zrFlag = zrFlag;
    }

    /**
     *债券转让原标的编号
     */
    public String getRefLoanNo() {
        return refLoanNo;
    }

    /**
     *债券转让原标的编号
     */
    public void setRefLoanNo(String refLoanNo) {
        this.refLoanNo = refLoanNo == null ? null : refLoanNo.trim();
    }

    /**
     *原投标第三方交易流水号
     */
    public String getOldReqSeq() {
        return oldReqSeq;
    }

    /**
     *原投标第三方交易流水号
     */
    public void setOldReqSeq(String oldReqSeq) {
        this.oldReqSeq = oldReqSeq == null ? null : oldReqSeq.trim();
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
     *借款人证件类型,身份证：1010
     */
    public Integer getBwIdType() {
        return bwIdType;
    }

    /**
     *借款人证件类型,身份证：1010
     */
    public void setBwIdType(Integer bwIdType) {
        this.bwIdType = bwIdType;
    }

    /**
     *借款人证件号码
     */
    public String getBwIdNo() {
        return bwIdNo;
    }

    /**
     *借款人证件号码
     */
    public void setBwIdNo(String bwIdNo) {
        this.bwIdNo = bwIdNo == null ? null : bwIdNo.trim();
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
     *借款人账号所属行号
     */
    public String getBwAcBankId() {
        return bwAcBankId;
    }

    /**
     *借款人账号所属行号
     */
    public void setBwAcBankId(String bwAcBankId) {
        this.bwAcBankId = bwAcBankId == null ? null : bwAcBankId.trim();
    }

    /**
     *借款人账号所属行名
     */
    public String getBwAcBankName() {
        return bwAcBankName;
    }

    /**
     *借款人账号所属行名
     */
    public void setBwAcBankName(String bwAcBankName) {
        this.bwAcBankName = bwAcBankName == null ? null : bwAcBankName.trim();
    }

    /**
     *借款人金额
     */
    public Long getBwAmt() {
        return bwAmt;
    }

    /**
     *借款人金额
     */
    public void setBwAmt(Long bwAmt) {
        this.bwAmt = bwAmt;
    }

    /**
     *借款人抵押品编号
     */
    public String getMortgageId() {
        return mortgageId;
    }

    /**
     *借款人抵押品编号
     */
    public void setMortgageId(String mortgageId) {
        this.mortgageId = mortgageId == null ? null : mortgageId.trim();
    }

    /**
     *借款人抵押品简单描述
     */
    public String getMortgageInfo() {
        return mortgageInfo;
    }

    /**
     *借款人抵押品简单描述
     */
    public void setMortgageInfo(String mortgageInfo) {
        this.mortgageInfo = mortgageInfo == null ? null : mortgageInfo.trim();
    }

    /**
     *借款人审批通过日期
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     *借款人审批通过日期
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
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
     *银行交易流水号
     */
    public String getResJnlNo() {
        return resJnlNo;
    }

    /**
     *银行交易流水号
     */
    public void setResJnlNo(String resJnlNo) {
        this.resJnlNo = resJnlNo == null ? null : resJnlNo.trim();
    }

    /**
     *交易日期 + 交易时间
     */
    public Date getTransDateTime() {
        return transDateTime;
    }

    /**
     *交易日期 + 交易时间
     */
    public void setTransDateTime(Date transDateTime) {
        this.transDateTime = transDateTime;
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