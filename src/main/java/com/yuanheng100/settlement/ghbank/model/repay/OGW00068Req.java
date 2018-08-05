package com.yuanheng100.settlement.ghbank.model.repay;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00068Req extends GHBankReq
{

    private static final long serialVersionUID = 2734153739733028370L;
    /**
     * 原放款交易流水号
     */
    private String oldReqSeqNo;
    
    public OGW00068Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00068;
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

    @Override
    public String toString()
    {
        return "OGW00068Req [oldReqSeqNo=" + oldReqSeqNo + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode
                + "]";
    }

}
