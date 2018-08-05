package com.yuanheng100.settlement.ghbank.model.bond;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00061Resp extends GHBankResp
{

    private static final long serialVersionUID = 8456391280316257850L;
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
        return "OGW00061Resp [oldReqSeqNo=" + oldReqSeqNo + ", channelFlow=" + channelFlow + ", serverFlow="
                + serverFlow + ", serverDateTime=" + serverDateTime + ", status=" + status + ", errorCode=" + errorCode
                + ", errorMsg=" + errorMsg + "]";
    }
    
}
