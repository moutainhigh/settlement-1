package com.yuanheng100.settlement.ghbank.model.repay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00074Req extends GHBankReq
{

    private static final long serialVersionUID = 4109260067267019812L;
    /**
     * 原还款交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 还款类型
     */
    private Short dfFlag;
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
     * 总笔数
     */
    private Integer totalNum;
    /**
     * 循环列表<REPAYLIST>有多少个需发收益的投资人就有多少个REPAYLIST标签
     */
    private List<RepayItem> repayList;

    
    public OGW00074Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00074;
        this.initChannelFlow();
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public Short getDfFlag()
    {
        return dfFlag;
    }

    public void setDfFlag(Short dfFlag)
    {
        this.dfFlag = dfFlag;
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

    public Integer getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }

    public List<RepayItem> getRepayList()
    {
        return repayList;
    }

    public void setRepayList(List<RepayItem> repayList)
    {
        this.repayList = repayList;
    }

    public static class RepayItem
    {
        /**
         * 原还款交易流水号
         */
        private String oldReqSeqNo;
        
        /**
         * 子流水号
         */
        private String subSeqNo;
        /**
         * 投资人账号
         */
        private String acNo;
        /**
         * 投资人账号户名
         */
        private String acName;
        /**
         * 该收益所属截止日期
         */
        private Date incomeDate;
        /**
         * 还款总金额
         */
        private BigDecimal amount;
        /**
         * 本次还款本金
         */
        private BigDecimal principalAmt;
        /**
         * 本次还款收益
         */
        private BigDecimal incomeAmt;
        /**
         * 本次还款费用
         */
        private BigDecimal feeAmt;
        /**
         * 备用字段3
         */
        private String extFiled3;
        
        public String getOldReqSeqNo()
        {
            return oldReqSeqNo;
        }

        public void setOldReqSeqNo(String oldReqSeqNo)
        {
            this.oldReqSeqNo = oldReqSeqNo;
        }

        public String getSubSeqNo()
        {
            return subSeqNo;
        }

        public void setSubSeqNo(String subSeqNo)
        {
            this.subSeqNo = subSeqNo;
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

        public Date getIncomeDate()
        {
            return incomeDate;
        }

        public void setIncomeDate(Date incomeDate)
        {
            this.incomeDate = incomeDate;
        }

        public BigDecimal getAmount()
        {
            return amount;
        }

        public void setAmount(BigDecimal amount)
        {
            this.amount = amount;
        }

        public BigDecimal getPrincipalAmt()
        {
            return principalAmt;
        }

        public void setPrincipalAmt(BigDecimal principalAmt)
        {
            this.principalAmt = principalAmt;
        }

        public BigDecimal getIncomeAmt()
        {
            return incomeAmt;
        }

        public void setIncomeAmt(BigDecimal incomeAmt)
        {
            this.incomeAmt = incomeAmt;
        }

        public BigDecimal getFeeAmt()
        {
            return feeAmt;
        }

        public void setFeeAmt(BigDecimal feeAmt)
        {
            this.feeAmt = feeAmt;
        }

        public String getExtFiled3()
        {
            return extFiled3;
        }

        public void setExtFiled3(String extFiled3)
        {
            this.extFiled3 = extFiled3;
        }

        @Override
        public String toString()
        {
            return "RepayItem [oldReqSeqNo=" + oldReqSeqNo + ", subSeqNo=" + subSeqNo + ", acNo=" + acNo + ", acName="
                    + acName + ", incomeDate=" + incomeDate + ", amount=" + amount + ", principalAmt=" + principalAmt
                    + ", incomeAmt=" + incomeAmt + ", feeAmt=" + feeAmt + ", extFiled3=" + extFiled3 + "]";
        }
        
    }

    @Override
    public String toString()
    {
        return "OGW00074Req [oldReqSeqNo=" + oldReqSeqNo + ", dfFlag=" + dfFlag + ", loanNo=" + loanNo + ", bwAcName=" + bwAcName + ", bwAcNo=" + bwAcNo
                + ", totalNum=" + totalNum + ", repayList=" + repayList + ", channelCode=" + channelCode + ", channelFlow=" + channelFlow
                + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }
    
}
