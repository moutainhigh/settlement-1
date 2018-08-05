package com.yuanheng100.settlement.ghbank.model.querybalance;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00049Resp extends GHBankResp
{
    /**
     * 
     */
    private static final long serialVersionUID = -980205177721679330L;
    /**
     * 银行账号
     */
    private String acNo;
    /**
     * 账号户名
     */
    private String acName;
    /**
     * 账户余额
     */
    private BigDecimal acctBal;
    /**
     * 可用余额
     */
    private BigDecimal availableBal;
    /**
     * 冻结金额
     */
    private BigDecimal frozBl;

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
    }

    public String getAcName()
    {
        return acName;
    }

    public void setAcName(String acName)
    {
        this.acName = acName;
    }

    public BigDecimal getAcctBal()
    {
        return acctBal;
    }

    public void setAcctBal(BigDecimal acctBal)
    {
        this.acctBal = acctBal;
    }

    public BigDecimal getAvailableBal()
    {
        return availableBal;
    }

    public void setAvailableBal(BigDecimal availableBal)
    {
        this.availableBal = availableBal;
    }

    public BigDecimal getFrozBl()
    {
        return frozBl;
    }

    public void setFrozBl(BigDecimal frozBl)
    {
        this.frozBl = frozBl;
    }

    @Override
    public String toString()
    {
        return "OGW00049Resp 余额查询  [acNo=" + acNo + ", acName=" + acName + ", acctBal=" + acctBal + ", availableBal="
                + availableBal + ", frozBl=" + frozBl + ", transCode=" + transCode + ", channelFlow=" + channelFlow
                + ", serverFlow=" + serverFlow + ", serverDateTime=" + serverDateTime + ", status=" + status
                + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }
    
    
}
