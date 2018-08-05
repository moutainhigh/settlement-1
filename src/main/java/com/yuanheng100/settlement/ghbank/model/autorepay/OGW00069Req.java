package com.yuanheng100.settlement.ghbank.model.autorepay;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00069Req extends GHBankReq
{

    private static final long serialVersionUID = -13507509272130125L;
    /**
     * 交易类型
     */
    private Short tTrans;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 还款账号户名
     */
    private String bwAcName;
    /**
     * 还款账号
     */
    private String bwAcNo;
    /**
     * 还款金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 返回商户URL
     */
    private String returnUrl;
    
    public OGW00069Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00069;
        this.tTrans = TransType.AUTOMATIC_PAYMENT_AUTHORIZATION.getType();
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

    public Long getLoanNo()
    {
        return loanNo;
    }

    public void setLoanNo(Long loanNo)
    {
        this.loanNo = loanNo;
    }

    public String getBwAcName()
    {
        return bwAcName;
    }

    public void setBwAcName(String bwAcName)
    {
        this.bwAcName = bwAcName;
    }

    public String getBwAcNo()
    {
        return bwAcNo;
    }

    public void setBwAcNo(String bwAcNo)
    {
        this.bwAcNo = bwAcNo;
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
        return "OGW00069Req [tTrans=" + tTrans + ", loanNo=" + loanNo + ", bwAcName=" + bwAcName + ", bwAcNo=" + bwAcNo
                + ", amount=" + amount + ", remark=" + remark + ", returnUrl=" + returnUrl + ", channelCode="
                + channelCode + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime
                + ", transCode=" + transCode + "]";
    }
    
}
