package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00060Req extends GHBankReq
{

    private static final long serialVersionUID = -6320285862401921254L;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 原投标流水号
     */
    private String oldReqSeqNo;
    /**
     * 投资人账号
     */
    private String acNo;
    /**
     * 投资人账号户名
     */
    private String acName;
    /**
     * 撤标原因
     */
    private String cancelReason;
    
    public OGW00060Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00060;
        this.initChannelFlow();
    }

    public Long getLoanNo()
    {
        return loanNo;
    }

    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
    }

    public String getAcName()
    {
        return acName;
    }

    public void setAcName(String acName)
    {
        this.acName = acName;
    }

    public String getCancelReason()
    {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
    }


    @Override
    public String toString()
    {
        return "OGW00060Req [loanNo=" + loanNo + ", oldReqSeqNo=" + oldReqSeqNo + ", acNo=" + acNo + ", acName="
                + acName + ", cancelReason=" + cancelReason + ", channelFlow=" + channelFlow + ", channelDateTime="
                + channelDateTime + ", transCode=" + transCode + "]";
    }
    
}
