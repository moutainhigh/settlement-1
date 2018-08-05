package com.yuanheng100.settlement.ghbank.model.repay;

import java.math.BigDecimal;
import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00068Resp extends GHBankResp
{

    private static final long serialVersionUID = 4075935027930049074L;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;
    /**
     * 交易日期 交易时间
     */
    private Date transDateTime;
    /**
     * 还款交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
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
     * 银行止付日期
     */
    private Date hostDt;
    /**
     * 银行止付流水号
     */
    private String hostJnlNo;

    public String getResJnlNo()
    {
        return resJnlNo;
    }

    public void setResJnlNo(String resJnlNo)
    {
        this.resJnlNo = resJnlNo;
    }

    public Date getTransDateTime()
    {
        return transDateTime;
    }

    public void setTransDateTime(Date transDateTime)
    {
        this.transDateTime = transDateTime;
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public ReturnStatus getReturnStatus()
    {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus)
    {
        this.returnStatus = returnStatus;
    }

    public String getReturnErrorMsg()
    {
        return returnErrorMsg;
    }

    public void setReturnErrorMsg(String returnErrorMsg)
    {
        this.returnErrorMsg = returnErrorMsg;
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

    public Date getHostDt()
    {
        return hostDt;
    }

    public void setHostDt(Date hostDt)
    {
        this.hostDt = hostDt;
    }

    public String getHostJnlNo()
    {
        return hostJnlNo;
    }

    public void setHostJnlNo(String hostJnlNo)
    {
        this.hostJnlNo = hostJnlNo;
    }
}
