package com.yuanheng100.settlement.ghbank.model.callback;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW0015TReq extends GHBankResp
{

    private static final long serialVersionUID = 2398268285943698908L;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 流标原因
     */
    private String cancelReason;
    

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
