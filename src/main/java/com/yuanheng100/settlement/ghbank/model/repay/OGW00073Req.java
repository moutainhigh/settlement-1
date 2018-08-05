package com.yuanheng100.settlement.ghbank.model.repay;

import java.math.BigDecimal;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00073Req extends GHBankReq
{

    private static final long serialVersionUID = 5082236639395862853L;
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
     * 手续费
     */
    private BigDecimal feeAmt;
    
    public OGW00073Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00073;
        this.initChannelFlow();
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

    public BigDecimal getFeeAmt()
    {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt)
    {
        this.feeAmt = feeAmt;
    }

}
