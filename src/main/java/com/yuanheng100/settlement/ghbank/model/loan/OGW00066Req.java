package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00066Req extends GHBankReq
{

    private static final long serialVersionUID = 5903601607984061467L;
    /**
     * 原放款交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 原投标流水号
     */
    private String oldTTJnl;
    
    public OGW00066Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00066;
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

    public String getOldTTJnl()
    {
        return oldTTJnl;
    }

    public void setOldTTJnl(String oldTTJnl)
    {
        this.oldTTJnl = oldTTJnl;
    }

    @Override
    public String toString()
    {
        return "OGW00066Req [oldReqSeqNo=" + oldReqSeqNo + ", loanNo=" + loanNo + ", oldTTJnl=" + oldTTJnl + ", channelFlow=" + channelFlow
                + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }


}
