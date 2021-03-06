package com.yuanheng100.settlement.payease.model.trs001006;

import com.yuanheng100.settlement.payease.model.PayeaseResp;

/**
 * 提现响应
 *
 * @author Administrator
 */
public class TRS001006Resp extends PayeaseResp
{
    /**
     * 商户流水号
     */
    private String serlNum;
    /**
     * 授权号
     */
    private String authId;
    /**
     * P2P网站用户注册名
     */
    private String user;
    /**
     * 注册用户手机号
     */
    private String mobile;
    /**
     * 开户证件类型
     */
    private String idType;
    /**
     * 开户证件号
     */
    private String id;
    /**
     * 开户名
     */
    private String accName;
    /**
     * 开户省
     */
    private String accProvince;
    /**
     * 开户市
     */
    private String accCity;
    /**
     * 开户银行名称
     */
    private Short accBankCode;

    private String accBank;

    public String getAccBank()
    {
        return accBank;
    }

    public void setAccBank(String accBank)
    {
        this.accBank = accBank;
    }

    /**
     * 开户分行支行名称
     */
    private String accBranchName;
    /**
     * 银行账号
     */
    private String accNum;
    /**
     * 账号类型
     */
    private String accType;
    /**
     * 账号类别
     */
    private String accProp;
    /**
     * 提现金额
     */
    private String amount;
    /**
     * 联行号
     */
    private String accBranchNum;
    /**
     * 扣取虚拟账户金额
     */
    private String totalAmount;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 是否是实时通道
     */
    private String urgency;
    /**
     * 拆分标识
     */
    private String amountSplit;
    /**
     * 用户密码
     */
    private String pin;

    private int moneyRecordId;

    public String getAccBranchNum()
    {
        return accBranchNum;
    }

    public void setAccBranchNum(String accBranchNum)
    {
        this.accBranchNum = accBranchNum;
    }

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public void setUrgency(String urgency)
    {
        this.urgency = urgency;
    }

    public String getAmountSplit()
    {
        return amountSplit;
    }

    public void setAmountSplit(String amountSplit)
    {
        this.amountSplit = amountSplit;
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
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

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getIdType()
    {
        return idType;
    }

    public void setIdType(String idType)
    {
        this.idType = idType;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAccName()
    {
        return accName;
    }

    public void setAccName(String accName)
    {
        this.accName = accName;
    }

    public String getAccProvince()
    {
        return accProvince;
    }

    public void setAccProvince(String accProvince)
    {
        this.accProvince = accProvince;
    }

    public String getAccCity()
    {
        return accCity;
    }

    public void setAccCity(String accCity)
    {
        this.accCity = accCity;
    }

    public Short getAccBankCode()
    {
        return accBankCode;
    }

    public void setAccBankCode(Short accBankCode)
    {
        this.accBankCode = accBankCode;
    }

    public String getAccBranchName()
    {
        return accBranchName;
    }

    public void setAccBranchName(String accBranchName)
    {
        this.accBranchName = accBranchName;
    }

    public String getAccNum()
    {
        return accNum;
    }

    public void setAccNum(String accNum)
    {
        this.accNum = accNum;
    }

    public String getAccType()
    {
        return accType;
    }

    public void setAccType(String accType)
    {
        this.accType = accType;
    }

    public String getAccProp()
    {
        return accProp;
    }

    public void setAccProp(String accProp)
    {
        this.accProp = accProp;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
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
        return "TRS001006Resp [transCode=" + getTransCode() + ", reqTime=" + getReqTime() + ", operationCode=" + getOperationCode()
                + ", serlNum=" + serlNum + ", authId=" + authId + ", user=" + user + ", mobile=" + mobile
                + ", idType=" + idType + ", id=" + id + ", accName=" + accName + ", accProvince=" + accProvince
                + ", accCity=" + accCity + ", accBankCode=" + accBankCode + ", accBranchName=" + accBranchName + ", accBranchNum=" + accBranchNum
                + ", accNum=" + accNum + ", accType=" + accType + ", accProp=" + accProp + ", totalAmount=" + totalAmount
                + ", amount=" + amount + ", fee=" + fee + ", urgency=" + urgency + ", amountSplit=" + amountSplit
                + ", groupId=" + getGroupId() + ", branchId=" + getBranchId() + ", merData1=" + getMerdata1() + ", merData2="
                + getMerdata2() + ", returnCode=" + getReturnCode() + ", returnMsg=" + getReturnMsg() + ", moneyRecordId="
                + moneyRecordId + "]";
    }
}
