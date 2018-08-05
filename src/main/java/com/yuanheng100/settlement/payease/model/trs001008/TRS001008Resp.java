package com.yuanheng100.settlement.payease.model.trs001008;

import com.yuanheng100.settlement.payease.model.PayeaseResp;

/**
 * 代扣交易返回对象
 *
 * @author Administrator
 */
public class TRS001008Resp extends PayeaseResp
{

    private static final long serialVersionUID = -1711824247760963801L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 请求时间
     */
    private String reqTime;

    /**
     * 操作码
     */
    private String operationCode;

    /**
     * 流水号
     */
    private String serlNum;

    /**
     * 授权码
     */
    private String authId;

    /**
     * P2P网站用户注册名
     */
    private String user;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 开户证件类型
     */
    private String idType;

    /**
     * 开户证件号码
     */
    private String id;

    /**
     * 开户名
     */
    private String accName;

    /**
     * 省
     */
    private String accProvince;

    /**
     * 市
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
     * 开户银行账号
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
     * 金额
     */
    private String amount;

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回结果描述
     */
    private String returnMsg;

    /**
     * P2P商户编号
     */
    private String groupId;

    /**
     * P2P子公司编号
     */
    private String branchId;

    /**
     * 预留字段1
     * 暂时用于填写客户协议编号
     */
    private String merdata1;

    /**
     * 预留字段2
     */
    private String merdata2;


    public TRS001008Resp()
    {
    }

    public String getTransCode()
    {
        return transCode;
    }

    public void setTransCode(String transCode)
    {
        this.transCode = transCode;
    }

    public String getReqTime()
    {
        return reqTime;
    }

    public void setReqTime(String reqTime)
    {
        this.reqTime = reqTime;
    }

    public String getOperationCode()
    {
        return operationCode;
    }

    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
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

    public String getReturnCode()
    {
        return returnCode;
    }

    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }

    public String getReturnMsg()
    {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg)
    {
        this.returnMsg = returnMsg;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public void setBranchId(String branchId)
    {
        this.branchId = branchId;
    }

    public String getMerdata1()
    {
        return merdata1;
    }

    public void setMerdata1(String merdata1)
    {
        this.merdata1 = merdata1;
    }

    public String getMerdata2()
    {
        return merdata2;
    }

    public void setMerdata2(String merdata2)
    {
        this.merdata2 = merdata2;
    }


    @Override
    public String toString()
    {
        return "TRS001008Resp [transCode=" + transCode + ", reqTime=" + reqTime + ", operationCode=" + operationCode
                + ", serlNum=" + serlNum + ", authId=" + authId + ", user=" + user + ", mobile=" + mobile + ", idType="
                + idType + ", id=" + id + ", accName=" + accName + ", accProvince=" + accProvince + ", accCity="
                + accCity + ", accBankCode=" + accBankCode + ", accNum=" + accNum + ", accType=" + accType + ", accProp="
                + accProp + ", amount=" + amount + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
                + ", groupId=" + groupId + ", branchId=" + branchId + ", merdata1=" + merdata1 + ", merdata2="
                + merdata2 + "]";
    }

}
