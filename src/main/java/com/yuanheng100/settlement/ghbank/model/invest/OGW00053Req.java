package com.yuanheng100.settlement.ghbank.model.invest;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/25.
 */
public class OGW00053Req extends GHBankReq
{

    private static final long serialVersionUID = -2256319179763925182L;
    /**
     * 原投标交易流水号
     */
    private String oldReqSeqNo;
    
    public OGW00053Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00053;
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        super.initChannelFlow();
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

}
