package com.yuanheng100.settlement.payease.model.trs001007;

import com.yuanheng100.settlement.payease.model.PayeaseResp;

/**
 * 转帐响应
 * 
 * @author Administrator
 * 
 */
public class TRS001007Resp extends PayeaseResp
{

    private static final long serialVersionUID = -3087388157838709685L;

    /**
     * 商户流水号
     */
    private String serlNum;
    /**
     * 授权号
     */
    private String authId;
    /**
     * 证件类型
     */
    private String transferInIdType;
    /**
     * 证件号
     */
    private String transferInUserId;
    /**
     * 注册用户手机号
     */
    private String transferInMobile;
    /**
     * 证件类型
     */
    private String transferOutIdType;
    /**
     * 证件号
     */
    private String transferOutUserId;
    /**
     * 注册用户手机号
     */
    private String transferOutIMobile;
    /**
     * 用户密码
     */
    private String pin;
    /**
     * 转出方P2P网站用户注册名
     */
    private String TransferOutUser;
    /**
     * 转入方P2P网站用户注册名
     */
    private String TransferInUser;
    /**
     * 转账金额
     */
    private String TransferAmount;
    /**
     * 转出方手续费
     */
    private String TransferOutUserFee;
    /**
     * 转入方手续费
     */
    private String TransferInUserFee;
    
    private int moneyRecordId;

    public String getSerlNum()
    {
        return serlNum;
    }

    public void setSerlNum(String serlNum)
    {
        this.serlNum = serlNum;
    }

    public String getAuthId()
    {
        return authId;
    }

    public void setAuthId(String authId)
    {
        this.authId = authId;
    }

    public String getTransferInIdType()
    {
        return transferInIdType;
    }

    public void setTransferInIdType(String transferInIdType)
    {
        this.transferInIdType = transferInIdType;
    }


    public String getTransferInMobile()
    {
        return transferInMobile;
    }

    public void setTransferInMobile(String transferInMobile)
    {
        this.transferInMobile = transferInMobile;
    }

    public String getTransferOutIdType()
    {
        return transferOutIdType;
    }

    public void setTransferOutIdType(String transferOutIdType)
    {
        this.transferOutIdType = transferOutIdType;
    }

    public String getTransferOutIMobile()
    {
        return transferOutIMobile;
    }

    public void setTransferOutIMobile(String transferOutIMobile)
    {
        this.transferOutIMobile = transferOutIMobile;
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public String getTransferOutUser()
    {
        return TransferOutUser;
    }

    public void setTransferOutUser(String transferOutUser)
    {
        TransferOutUser = transferOutUser;
    }

    public String getTransferInUser()
    {
        return TransferInUser;
    }

    public void setTransferInUser(String transferInUser)
    {
        TransferInUser = transferInUser;
    }

    public String getTransferAmount()
    {
        return TransferAmount;
    }

    public void setTransferAmount(String transferAmount)
    {
        TransferAmount = transferAmount;
    }

    public String getTransferOutUserFee()
    {
        return TransferOutUserFee;
    }

    public void setTransferOutUserFee(String transferOutUserFee)
    {
        TransferOutUserFee = transferOutUserFee;
    }

    public String getTransferInUserFee()
    {
        return TransferInUserFee;
    }

    public void setTransferInUserFee(String transferInUserFee)
    {
        TransferInUserFee = transferInUserFee;
    }

    /**
     * @return the transferInUserId
     */
    public String getTransferInUserId()
    {
        return transferInUserId;
    }

    /**
     * @param transferInUserId the transferInUserId to set
     */
    public void setTransferInUserId(String transferInUserId)
    {
        this.transferInUserId = transferInUserId;
    }

    /**
     * @return the transferOutUserId
     */
    public String getTransferOutUserId()
    {
        return transferOutUserId;
    }

    /**
     * @param transferOutUserId the transferOutUserId to set
     */
    public void setTransferOutUserId(String transferOutUserId)
    {
        this.transferOutUserId = transferOutUserId;
    }

    public int getMoneyRecordId()
    {
        return moneyRecordId;
    }

    public void setMoneyRecordId(int moneyRecordId)
    {
        this.moneyRecordId = moneyRecordId;
    }

    @Override
    public String toString()
    {
        return "TRS001007Resp [transCode=" + getTransCode() + ", reqTime=" + getReqTime() + ", operationCode=" + getOperationCode()
                + ", serlNum=" + serlNum + ", authId=" + authId + ", transferInIdType=" + transferInIdType
                + ", transferInUserId=" + transferInUserId + ", transferInMobile=" + transferInMobile
                + ", transferOutIdType=" + transferOutIdType + ", transferOutUserId=" + transferOutUserId
                + ", transferOutIMobile=" + transferOutIMobile + ", pin=" + pin + ", TransferOutUser="
                + TransferOutUser + ", TransferInUser=" + TransferInUser + ", TransferAmount=" + TransferAmount
                + ", TransferOutUserFee=" + TransferOutUserFee + ", TransferInUserFee=" + TransferInUserFee
                + ", groupId=" + getGroupId() + ", branchId=" + getBranchId() + ", merData1=" + getMerdata1() + ", merData2="
                + getMerdata2() + ", returnCode=" + getReturnCode() + ", returnMsg=" + getReturnMsg() + ", moneyRecordId="
                + moneyRecordId + "]";
    }

    
}
