package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * 获取短信验证码
 * Created by jlqian on 2017/4/17.
 */
public class OGW00041Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = 6372882527136611271L;
    /**
     * 操作类型
     * 1：自动投标撤销
     * 2：自动还款授权撤销
     * 0：默认
     */
    private Short trsType;
    /**
     * 银行账号
     * E账户账号，即关联账号.P2p商户必填
     */
    private String acNo;
    /**
     * 手机号 注册的时候必填，11位手机号
     */
    private Long mobileNo;
    
    private OGW00041Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00041;
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        super.initChannelFlow();
    }
    
    public OGW00041Req(int sequenceId, Short trsType)
    {
        this(sequenceId);
        this.trsType = trsType;
    }

    public Short getTrsType()
    {
        return trsType;
    }

    public void setTrsType(Short trsType)
    {
        this.trsType = trsType;
    }

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
    }

    public Long getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString()
    {
        return "OGW00041Req [trsType=" + trsType + ", acNo=" + acNo + ", mobileNo=" + mobileNo + ", channelFlow="
                + channelFlow + ", transCode=" + transCode + "]";
    }

    
}
