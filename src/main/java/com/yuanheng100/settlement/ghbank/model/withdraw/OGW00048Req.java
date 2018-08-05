package com.yuanheng100.settlement.ghbank.model.withdraw;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00048Req extends GHBankReq
{

    private static final long serialVersionUID = 6714060680335257351L;
    /**
     * 原提现交易日期
     */
    private Date transDt;
    /**
     * 原提现交易流水号
     */
    private String oldReqSeqNo;
    

    public OGW00048Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00048;
        this.initChannelFlow();
    }

    public Date getTransDt()
    {
        return transDt;
    }

    public void setTransDt(Date transDt)
    {
        this.transDt = transDt;
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
