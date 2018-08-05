package com.yuanheng100.settlement.ghbank.model.autorepay;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00072Resp extends GHBankResp
{
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
}
