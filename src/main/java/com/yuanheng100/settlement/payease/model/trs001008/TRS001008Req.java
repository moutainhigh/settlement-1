package com.yuanheng100.settlement.payease.model.trs001008;

import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 代扣交易请求对象
 *
 * @author Administrator
 */
public class TRS001008Req extends PayeaseReq
{

    private static final long serialVersionUID = -8620920195800327933L;

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
     * 账户密码
     */
    private String pin;

    /**
     * 在资金流水表中对应的id
     */
    private int moneyRecordId;


    public TRS001008Req()
    {
        super();
        this.setTransCode(TransCode.TRS001008.getCode());
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

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
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
        return "TRS001008Req [serlNum=" + serlNum + ", authId=" + authId + ", user=" + user + ", mobile=" + mobile
                + ", idType=" + idType + ", id=" + id + ", accName=" + accName + ", accProvince=" + accProvince
                + ", accCity=" + accCity + ", accBankCode=" + accBankCode + ", accNum=" + accNum + ", accType=" + accType
                + ", accProp=" + accProp + ", amount=" + amount + ", pin=" + pin + ", moneyRecordId=" + moneyRecordId
                + "]";
    }


}
