package com.yuanheng100.settlement.common.model;

/**
 * 结算接口顶级类
 * @author tianwei
 *
 */
public abstract class Settlement {
    /**
     * 交易密钥
     */
    private String key;
    
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 交易类型
     */
    private String transType;
    
    /**
     * 商户编号/商户客户号，商户在资金结算平台的唯一标示，由结算合作方提供
     */
    private String groupId;
    
    /**
     * 商户子编号
     */
    private String branchId;
    
    /**
     * 请求日期
     */
    private String reqTime;

    /**
     * 操作码
     */
    private String operationCode;
    
    /**
     * 商户后台应答地址
     */
    private String bgRetUrl;
    
    /**
     * 签名，由参数列表拼接而成，汇付天下使用
     */
    private String chkValue;
    
    /**
     * 结算类型，结算合作商类型，首信易、汇付天下等
     */
    private String settlementType;
    
    /**
     * 交易结果返回码
     */
    private String returnCode;
    
    /**
     * 交易结果应答描述
     */
    private String returnMsg;
    
    /**
     * 交易流水号
     */
    private String serlNum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getBgRetUrl() {
        return bgRetUrl;
    }

    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSerlNum() {
        return serlNum;
    }

    public void setSerlNum(String serlNum) {
        this.serlNum = serlNum;
    }
    
}
