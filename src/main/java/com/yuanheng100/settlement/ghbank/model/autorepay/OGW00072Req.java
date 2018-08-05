package com.yuanheng100.settlement.ghbank.model.autorepay;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00072Req extends GHBankReq
{
    /**
     * 
     */
    private static final long serialVersionUID = 6685852622117965181L;
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
     * 手续费
     */
    private BigDecimal feeAmt;
    /**
     * 还款金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
    
    public OGW00072Req(int sequenceId)
    {
        super(sequenceId);
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

    public BigDecimal getFeeAmt()
    {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt)
    {
        this.feeAmt = feeAmt;
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
    
    @Override
    public String getTransCode()
    {
        return TransCode.OGW00072;
    }
}
