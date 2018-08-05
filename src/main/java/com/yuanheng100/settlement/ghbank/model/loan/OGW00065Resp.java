package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00065Resp extends GHBankResp
{

    private static final long serialVersionUID = -8478912101669535845L;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;
    /**
     * 交易日期 交易时间
     */
    private Date transDateTime;

    public String getResJnlNo()
    {
        return resJnlNo;
    }

    public void setResJnlNo(String resJnlNo)
    {
        this.resJnlNo = resJnlNo;
    }

    public Date getTransDateTime()
    {
        return transDateTime;
    }

    public void setTransDateTime(Date transDateTime)
    {
        this.transDateTime = transDateTime;
    }

    @Override
    public String toString()
    {
        return "OGW00065Resp [resJnlNo=" + resJnlNo + ", transDateTime=" + transDateTime + ", transCode=" + transCode + ", channelFlow=" + channelFlow
                + ", serverFlow=" + serverFlow + ", status=" + status + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }
    
}
