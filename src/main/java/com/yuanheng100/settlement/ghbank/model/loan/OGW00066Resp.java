package com.yuanheng100.settlement.ghbank.model.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00066Resp extends GHBankResp
{
    /**
     * 
     */
    private static final long serialVersionUID = 6578487622603953187L;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 原流标交易流水号
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
     * 循环列表
     */
    private List<OGW00066Resp.RS> rsList;

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

    public List<OGW00066Resp.RS> getRsList()
    {
        return rsList;
    }

    public void setRsList(List<OGW00066Resp.RS> rsList)
    {
        this.rsList = rsList;
    }

    public static class RS
    {
        /**
         * 投标交易流水号
         */
        private String reqSeqNo;
        /**
         * 借款编号
         */
        private Long loanNo;
        /**
         * 投资人账号
         */
        private String acNo;
        /**
         * 投资人账号户名
         */
        private String acName;
        /**
         * 投标金额
         */
        private BigDecimal amount;
        /**
         * 状态
         */
        private ReturnStatus status;
        /**
         * 错误原因
         */
        private String errorMsg;
        /**
         * 银行止付日期
         */
        private Date hostDt;
        /**
         * 银行止付流水号
         */
        private String hostJnlNo;
        /**
         * 备用字段3
         */
        private String extFiled3;

        public String getReqSeqNo()
        {
            return reqSeqNo;
        }

        public void setReqSeqNo(String reqSeqNo)
        {
            this.reqSeqNo = reqSeqNo;
        }

        public Long getLoanNo()
        {
            return loanNo;
        }

        public void setLoanNo(Long loanNo)
        {
            this.loanNo = loanNo;
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

        public ReturnStatus getStatus()
        {
            return status;
        }

        public void setStatus(ReturnStatus status)
        {
            this.status = status;
        }

        public String getErrorMsg()
        {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg)
        {
            this.errorMsg = errorMsg;
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

        public String getExtFiled3()
        {
            return extFiled3;
        }

        public void setExtFiled3(String extFiled3)
        {
            this.extFiled3 = extFiled3;
        }
    }
}
