package com.yuanheng100.settlement.payease.model.trs001007;

import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 转帐请求
 *
 * @author Administrator
 */
public class TRS001007Req extends PayeaseReq
{

    private static final long serialVersionUID = 2747524652484374085L;
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
    private String transferOutUser;
    /**
     * 转入方P2P网站用户注册名
     */
    private String transferInUser;
    /**
     * 转账金额
     */
    private String transferAmount;
    /**
     * 转出方手续费
     */
    private String transferOutUserFee;
    /**
     * 转入方手续费
     */
    private String transferInUserFee;


    private int moneyRecordId;

    public TRS001007Req()
    {
        super();
        this.setTransCode(TransCode.TRS001007.getCode());
    }

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
        return transferOutUser;
    }

    public void setTransferOutUser(String transferOutUser)
    {
        this.transferOutUser = transferOutUser;
    }

    public String getTransferInUser()
    {
        return transferInUser;
    }

    public void setTransferInUser(String transferInUser)
    {
        this.transferInUser = transferInUser;
    }

    public String getTransferAmount()
    {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount)
    {
        this.transferAmount = transferAmount;
    }

    public String getTransferOutUserFee()
    {
        return transferOutUserFee;
    }

    public void setTransferOutUserFee(String transferOutUserFee)
    {
        this.transferOutUserFee = transferOutUserFee;
    }

    public String getTransferInUserFee()
    {
        return transferInUserFee;
    }

    public void setTransferInUserFee(String transferInUserFee)
    {
        this.transferInUserFee = transferInUserFee;
    }

    /**
     * @return the moneyRecordId
     */
    public int getMoneyRecordId()
    {
        return moneyRecordId;
    }

    /**
     * @param moneyRecordId the moneyRecordId to set
     */
    public void setMoneyRecordId(int moneyRecordId)
    {
        this.moneyRecordId = moneyRecordId;
    }

    @Override
    public String toString()
    {
        return "TRS001007Req [serlNum=" + serlNum + ", authId=" + authId + ", transferInIdType=" + transferInIdType
                + ", transferInId=" + transferInUserId + ", transferInMobile=" + transferInMobile + ", transferOutIdType="
                + transferOutIdType + ", transferOutId=" + transferOutUserId + ", transferOutIMobile=" + transferOutIMobile
                + ", pin=" + pin + ", TransferOutUser=" + transferOutUser + ", TransferInUser=" + transferInUser
                + ", TransferAmount=" + transferAmount + ", TransferOutUserFee=" + transferOutUserFee
                + ", TransferInUserFee=" + transferInUserFee + ", merData1=" + getMerdata1() + ", merData2=" + getMerdata2()
                + "]";
    }

}
