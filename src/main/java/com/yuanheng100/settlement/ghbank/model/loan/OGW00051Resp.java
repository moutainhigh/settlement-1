package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00051Resp extends GHBankResp
{

    private static final long serialVersionUID = -3270582926882233470L;
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
        return "OGW00051Resp [resJnlNo=" + resJnlNo + ", transDateTime=" + transDateTime + ", channelFlow="
                + channelFlow + ", serverFlow=" + serverFlow + ", serverDateTime=" + serverDateTime + ", status="
                + status + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }
    
    
}
