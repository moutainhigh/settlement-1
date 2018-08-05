package com.yuanheng100.settlement.payease.model.syn001001;

import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 同步帐户请求 SYN001001
 *
 * @author Administrator
 */
public class SYN001001Req extends PayeaseReq
{

    /**
     * P2P网站用户注册名
     */
    private String user;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String id;
    /**
     * 经过认证的（身份证或其他证件）用户姓名
     */
    private String userName;
    /**
     * 注册用户手机号
     */
    private String mobile;
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
     * 在构造函数里设置好交易码
     */
    public SYN001001Req(String user)
    {
        super();
        this.setTransCode(TransCode.SYN001001.getCode());
        this.user = user;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
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

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getAccName()
    {
        return accName;
    }

    public void setAccName(String accName)
    {
        this.accName = accName;
    }

    /**
     * @return the accProvince
     */
    public String getAccProvince()
    {
        return accProvince;
    }

    /**
     * @param accProvince the accProvince to set
     */
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


    @Override
    public String toString()
    {
        return "SYN001001Req [user=" + user + ", idType=" + idType + ", id=" + id + ", userName=" + userName
                + ", mobile=" + mobile + ", accName=" + accName + ", accProvince=" + accProvince + ", accCity="
                + accCity + ", accBankCode=" + accBankCode + ", accBranchName=" + accBranchName + ", accNum=" + accNum
                + ", accType=" + accType + ", accProp=" + accProp + "]";
    }

}
