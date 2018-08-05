package com.yuanheng100.settlement.ghbank.model.repay;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00067Resp extends GHBankResp
{
    /**
     * 原交易流水号
     */
    private String oldReqSeqNo;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }
}
