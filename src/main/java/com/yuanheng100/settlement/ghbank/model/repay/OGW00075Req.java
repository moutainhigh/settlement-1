package com.yuanheng100.settlement.ghbank.model.repay;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00075Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = 8097250046993575654L;
    /**
     * 原流水号
     */
    private String oldReqSeqNo;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 子流水号
     */
    private String subSeqNo;
    
    public OGW00075Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00075;
        this.initChannelFlow();
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public Long getLoanNo()
    {
        return loanNo;
    }

    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }

    public String getSubSeqNo()
    {
        return subSeqNo;
    }

    public void setSubSeqNo(String subSeqNo)
    {
        this.subSeqNo = subSeqNo;
    }

    @Override
    public String toString()
    {
        return "OGW00075Req [oldReqSeqNo=" + oldReqSeqNo + ", loanNo=" + loanNo + ", subSeqNo=" + subSeqNo + ", channelFlow=" + channelFlow
                + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }
    
}
