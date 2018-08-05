package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00064Req extends GHBankReq
{
    /**
     * 原流标交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 原投标流水号
     */
    private String oldTTJnl;
    
    public OGW00064Req(int sequenceId)
    {
        super(sequenceId);
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
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
    public String getTransCode()
    {
        return TransCode.OGW00064;
    }
}
