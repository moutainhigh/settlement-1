package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00060Resp extends GHBankResp
{

    private static final long serialVersionUID = 1486672114133033123L;
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
