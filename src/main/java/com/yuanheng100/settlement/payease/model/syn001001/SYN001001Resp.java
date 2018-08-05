package com.yuanheng100.settlement.payease.model.syn001001;

import com.yuanheng100.settlement.payease.model.PayeaseResp;


/**
 * 同步帐户应答
 *
 * @author Administrator
 */
public class SYN001001Resp extends PayeaseResp
{

    private static final long serialVersionUID = -2319830437661586943L;


    /*以下四个属性页面数据使用字段，不与数据库对应*/
    private String account1;//投资账户余额
    private String account2;//负债账户余额
    private String status;//账户状态
    private String accBank;

    public String getAccBank()
    {
        return accBank;
    }

    public void setAccBank(String accBank)
    {
        this.accBank = accBank;
    }
    public String getAccount1()
    {
        return account1;
    }

    public void setAccount1(String account1)
    {
        this.account1 = account1;
    }

    public String getAccount2()
    {
        return account2;
    }

    public void setAccount2(String account2)
    {
        this.account2 = account2;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * 交易码
     */
    private String transCode;
    /**
     * 请求日期
     */
    private String reqTime;
    /**
     * 操作码
     */
    private String operationCode;
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
     * P2P总公司编号
     */
    private String groupId;
    /**
     * P2P子公司编号
     */
    private String branchId;

    /**
     * 开户名
     */
    private String accName;
    /**
     * 开户省
     */
    private String accProvice;
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

    public String getAccName()
    {
        return accName;
    }

    public void setAccName(String accName)
    {
        this.accName = accName;
    }

    public String getAccProvice()
    {
        return accProvice;
    }

    public void setAccProvice(String accProvice)
    {
        this.accProvice = accProvice;
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
        return "SYN001001Resp [returnCode=" + super.getReturnCode() + ", returnMsg=" + super.getReturnMsg()
                + ", transCode=" + transCode + ", reqTime=" + reqTime + ", operationCode=" + operationCode + ", user="
                + user + ", idType=" + idType + ", id=" + id + ", userName=" + userName + ", mobile=" + mobile
                + ", groupId=" + groupId + ", branchId=" + branchId + ", accName=" + accName + ", accProvice="
                + accProvice + ", accCity=" + accCity + ", accBankCode=" + accBankCode + ", accBranchName=" + accBranchName
                + ", accNum=" + accNum + ", accType=" + accType + ", accProp=" + accProp + "]";
    }


}
