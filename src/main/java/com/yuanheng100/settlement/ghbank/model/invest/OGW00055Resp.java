package com.yuanheng100.settlement.ghbank.model.invest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00055Resp extends GHBankResp
{

    private static final long serialVersionUID = -1117369275752682630L;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;
    /**
     * 交易日期 交易时间
     */
    private Date transDateTime;
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
     * 优惠总金额
     */
    private BigDecimal amount;
    /**
     * 优惠总笔数
     */
    private Integer totalNum;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 循环列表
     */
    private List<RS> rsList;

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

    public Integer getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
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

    public List<RS> getRsList()
    {
        return rsList;
    }

    public void setRsList(List<RS> rsList)
    {
        this.rsList = rsList;
    }

    public static class RS extends OGW00054Req.Feedback
    {
        /**
         * 状态
         */
        private ReturnStatus status;
        /**
         * 错误原因
         */
        private String errorMsg;

        public RS(String subSeqNo, String oldReqSeqNo, String acNo, String acName, BigDecimal amount)
        {
            super(subSeqNo, oldReqSeqNo, acNo, acName, amount);
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
    }


}
