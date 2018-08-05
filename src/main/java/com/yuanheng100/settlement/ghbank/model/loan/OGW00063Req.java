package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00063Req extends GHBankReq
{

    private static final long serialVersionUID = -8115210817744704849L;
    
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 流标原因
     */
    private String cancelReason;

    public OGW00063Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00063;
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        super.initChannelFlow();
    }

    public Long getLoanNo()
    {
        return loanNo;
    }


    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }


    public String getCancelReason()
    {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
    }
}
