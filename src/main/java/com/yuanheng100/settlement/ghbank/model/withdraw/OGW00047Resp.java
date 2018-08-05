package com.yuanheng100.settlement.ghbank.model.withdraw;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00047Resp extends GHBankResp
{
    /**
     * 
     */
    private static final long serialVersionUID = -1747180824231762718L;
    /**
     * 原交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 订单处理状态
     */
    private Short withdrawStatus;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }


    public Short getWithdrawStatus()
    {
        return withdrawStatus;
    }

    public void setWithdrawStatus(Short withdrawStatus)
    {
        this.withdrawStatus = withdrawStatus;
    }

    @Override
    public String toString()
    {
        return "OGW00047Resp [oldReqSeqNo=" + oldReqSeqNo + ", withdrawStatus=" + withdrawStatus + ", transCode=" + transCode + ", channelFlow=" + channelFlow
                + ", serverFlow=" + serverFlow + ", serverDateTime=" + serverDateTime + ", status=" + status + ", errorCode=" + errorCode + ", errorMsg="
                + errorMsg + "]";
    }

}
