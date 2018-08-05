package com.yuanheng100.settlement.ghbank.model.loan;

import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00077Req extends GHBankReq
{

    private static final long serialVersionUID = 1395429040141137114L;
    /**
     * 对账类型
     */
    private Short operFlag;
    /**
     * 对账日期
     */
    private Date checkDate;
    
    public OGW00077Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00077;
        this.initChannelFlow();
    }

    public Short getOperFlag()
    {
        return operFlag;
    }

    public void setOperFlag(Short operFlag)
    {
        this.operFlag = operFlag;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }
    
}
