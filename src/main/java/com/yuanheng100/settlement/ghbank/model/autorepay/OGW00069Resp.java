package com.yuanheng100.settlement.ghbank.model.autorepay;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00069Resp extends GHBankResp
{
    /**
     * 原交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public String getResJnlNo()
    {
        return resJnlNo;
    }

    public void setResJnlNo(String resJnlNo)
    {
        this.resJnlNo = resJnlNo;
    }
}
