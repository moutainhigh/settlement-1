package com.yuanheng100.settlement.ghbank.model.invest;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00055Req extends GHBankReq
{

    private static final long serialVersionUID = -3582272422071293785L;
    /**
     * 原投标优惠返回交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 子流水号
     */
    private String subSeqNo;
    
    public OGW00055Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00055;
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

    public String getSubSeqNo()
    {
        return subSeqNo;
    }

    public void setSubSeqNo(String subSeqNo)
    {
        this.subSeqNo = subSeqNo;
    }
    
}
