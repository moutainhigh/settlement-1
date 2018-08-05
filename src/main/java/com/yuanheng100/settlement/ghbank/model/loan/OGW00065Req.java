package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00065Req extends GHBankReq
{

    private static final long serialVersionUID = -5746529926225180613L;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 借款人姓名
     */
    private String bwAcName;
    /**
     * 借款人账号
     */
    private String bwAcNo;
    /**
     * 账户管理费
     */
    private BigDecimal acMngAmt;
    /**
     * 风险保证金
     */
    private BigDecimal guarantAmt;
    /**
     * 备注
     */
    private String remark;
    
    public OGW00065Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00065;
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

    public BigDecimal getAcMngAmt()
    {
        return acMngAmt;
    }

    public void setAcMngAmt(BigDecimal acMngAmt)
    {
        this.acMngAmt = acMngAmt;
    }

    public BigDecimal getGuarantAmt()
    {
        return guarantAmt;
    }

    public void setGuarantAmt(BigDecimal guarantAmt)
    {
        this.guarantAmt = guarantAmt;
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
    public String toString()
    {
        return "OGW00065Req [loanNo=" + loanNo + ", bwAcName=" + bwAcName + ", bwAcNo=" + bwAcNo + ", acMngAmt=" + acMngAmt + ", guarantAmt=" + guarantAmt
                + ", remark=" + remark + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }
    
}
