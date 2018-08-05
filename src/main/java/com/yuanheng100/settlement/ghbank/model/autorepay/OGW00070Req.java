package com.yuanheng100.settlement.ghbank.model.autorepay;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00070Req extends GHBankReq
{

    private static final long serialVersionUID = -7960430810524096233L;
    /**
     * 原放款交易流水号
     */
    private String oldReqSeqNo;
    
    public OGW00070Req(int sequenceId)
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

    @Override
    public String getTransCode()
    {
        return TransCode.OGW00070;
    }
}
