package com.yuanheng100.settlement.payease.model.trs001010;

import com.yuanheng100.settlement.payease.model.PayeaseResp;

/**
 * 账户查询类的返回结果类
 *
 * @author Bai Song
 */
public class TRS001010Resp extends PayeaseResp
{

    /**
     * 客户ID
     */
    private String user;
    /**
     * 当前总金额
     */
    private String currentBalance;
    /**
     * 可用金额
     */
    private String availableBalance;
    /**
     * 冻结金额
     */
    private String blockedBalance;
    /**
     * 帐户状态
     */
    private String accountStatus;

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public String getAvailableBalance()
    {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance)
    {
        this.availableBalance = availableBalance;
    }

    public String getBlockedBalance()
    {
        return blockedBalance;
    }

    public void setBlockedBalance(String blockedBalance)
    {
        this.blockedBalance = blockedBalance;
    }

    public String getAccountStatus()
    {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus)
    {
        this.accountStatus = accountStatus;
    }


    @Override
    public String toString()
    {
        return "TRS001010Resp [returnCode=" + super.getReturnCode() + ", returnMsg=" + super.getReturnMsg()
                + ", reqTime=" + getReqTime() + ", operationCode=" + getOperationCode() + ", user=" + user + ", groupId="
                + getGroupId() + ", branchId=" + getBranchId() + ", merdata1=" + getMerdata1() + ", merdata2=" + getMerdata2()
                + ", currentBalance=" + currentBalance + ", availableBalance=" + availableBalance + ", blockedBalance="
                + blockedBalance + ", accountStatus=" + accountStatus + ", returnCode=" + getReturnCode() + ", returnMsg="
                + getReturnMsg() + "]";
    }


}
