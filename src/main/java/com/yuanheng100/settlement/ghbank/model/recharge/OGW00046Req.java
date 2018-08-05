package com.yuanheng100.settlement.ghbank.model.recharge;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00046Req extends GHBankReq
{

    private static final long serialVersionUID = 6176736057640378670L;
    /**
     * 原充值交易流水号 原充值交易报文头的“渠道流水号
     */
    private String oldReqSeqNo;
    
    public OGW00046Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00046;
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
}
