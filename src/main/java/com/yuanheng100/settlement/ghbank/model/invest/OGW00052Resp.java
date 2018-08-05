package com.yuanheng100.settlement.ghbank.model.invest;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00052Resp extends GHBankResp
{

    private static final long serialVersionUID = 3391787141761080837L;
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

    @Override
    public String toString()
    {
        return "OGW00052Resp [oldReqSeqNo=" + oldReqSeqNo + ", channelFlow=" + channelFlow + ", serverFlow="
                + serverFlow + ", status=" + status + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }

    
}
