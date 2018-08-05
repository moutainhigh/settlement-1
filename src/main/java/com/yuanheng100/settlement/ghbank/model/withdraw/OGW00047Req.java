package com.yuanheng100.settlement.ghbank.model.withdraw;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.util.ConfigFile;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00047Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = -5436020255329425781L;

    /**
     * 交易类型
     */
    private Short tTrans;
    /**
     * 银行账号
     */
    private String acNo;
    /**
     * 账号户名
     */
    private String acName;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 返回商户URL
     * 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    private String returnUrl;
    
    
    public OGW00047Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = TransCode.OGW00047;
        this.tTrans = TransType.WITHDRAW.getType();
        this.returnUrl = ConfigFile.getProperty("ghbank.callbackUrl");
        this.initChannelFlow();
    }

    public Short gettTrans()
    {
        return tTrans;
    }

    public void settTrans(Short tTrans)
    {
        this.tTrans = tTrans;
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

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString()
    {
        return "OGW00047Req [tTrans=" + tTrans + ", acNo=" + acNo + ", acName=" + acName + ", amount=" + amount
                + ", remark=" + remark + ", returnUrl=" + returnUrl + ", channelFlow=" + channelFlow
                + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }
    
    
}
