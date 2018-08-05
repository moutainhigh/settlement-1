package com.yuanheng100.settlement.ghbank.model.querybalance;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00049Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = -5294996522854807216L;
    /**
     * 银行账号
     */
    private String acNo;
    /**
     * 账号户名
     */
    private String acName;
    /**
     * 业务类型
     * 意为充值到哪类账户，是E账号活期户还是专户可不送此字段或传空串
     */
    private String busType;
    
    
    public OGW00049Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00049;
        this.initChannelFlow();
    }

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
    }

    public String getAcName()
    {
        return acName;
    }

    public void setAcName(String acName)
    {
        this.acName = acName;
    }

    public String getBusType()
    {
        return busType;
    }

    public void setBusType(String busType)
    {
        this.busType = busType;
    }

    @Override
    public String getTransCode()
    {
        return "OGW00049";
    }
    
}
