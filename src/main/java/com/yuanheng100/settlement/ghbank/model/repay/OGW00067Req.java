package com.yuanheng100.settlement.ghbank.model.repay;

import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.util.ConfigFile;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00067Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = -1630676383143094132L;
    /**
     * 交易类型
     */
    private Short tTrans;
    /**
     * 还款类型
     */
    private Short dfFlag;
    /**
     * 原垫付请求流水号
     */
    private String oldReqSeqNo;
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
    /**
     * 手续费
     */
    private BigDecimal feeAmt;
    
    public OGW00067Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = TransCode.OGW00067;
        this.tTrans = TransType.REPAYMENT.getType();
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

    public Short getDfFlag()
    {
        return dfFlag;
    }

    public void setDfFlag(Short dfFlag)
    {
        this.dfFlag = dfFlag;
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
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

    public BigDecimal getFeeAmt()
    {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt)
    {
        this.feeAmt = feeAmt;
    }

    @Override
    public String toString()
    {
        return "OGW00067Req [tTrans=" + tTrans + ", dfFlag=" + dfFlag + ", oldReqSeqNo=" + oldReqSeqNo + ", loanNo=" + loanNo + ", bwAcName=" + bwAcName
                + ", bwAcNo=" + bwAcNo + ", amount=" + amount + ", remark=" + remark + ", returnUrl=" + returnUrl + ", feeAmt=" + feeAmt + ", channelCode="
                + channelCode + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }
    
}
