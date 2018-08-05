package com.yuanheng100.settlement.ghbank.model.bond;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00062Req extends GHBankReq
{

    private static final long serialVersionUID = 7304308711712205139L;
    
    /**
     * 原债券转让申请流水
     */
    private String oldReqSeqNo;
    
    public OGW00062Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00062;
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

    @Override
    public String toString()
    {
        return "OGW00062Req [oldReqSeqNo=" + oldReqSeqNo + ", channelFlow=" + channelFlow + ", transCode=" + transCode
                + "]";
    }
    
}
