package com.yuanheng100.settlement.ghbank.model.invest;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jlqian on 2017/4/25.
 */
public class OGW00054Req extends GHBankReq
{

    private static final long serialVersionUID = -245796797837902138L;
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
     * 优惠总金额
     */
    private BigDecimal amount;
    /**
     * 优惠总笔数
     */
    private Integer totalNum;
    /**
     * 循环列表
     */
    private List<Feedback> feedbackList;
    
    public OGW00054Req(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW00054;
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        super.initChannelFlow();
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

    public List<Feedback> getFeedbackList()
    {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList)
    {
        this.feedbackList = feedbackList;
    }
    

    public static class Feedback
    {
        /**
         * 子流水号
         */
        private String subSeqNo;
        /**
         * 原投标流水号
         */
        private String oldReqSeqNo;
        /**
         * 投资人账号
         */
        private String acNo;
        /**
         * 投资人账号户名
         */
        private String acName;
        /**
         * 优惠金额
         */
        private BigDecimal amount;
        /**
         * 备注
         */
        private String remark;
        /**
         * 备用字段3
         */
        private String extFiled3;

        public Feedback(String subSeqNo, String oldReqSeqNo, String acNo, String acName, BigDecimal amount)
        {
            this(subSeqNo,oldReqSeqNo,acNo,acName,amount,null,null);
        }

        public Feedback(String subSeqNo, String oldReqSeqNo, String acNo, String acName, BigDecimal amount, String remark)
        {
            this(subSeqNo,oldReqSeqNo,acNo,acName,amount,remark,null);
        }

        public Feedback(String subSeqNo, String oldReqSeqNo, String acNo, String acName, BigDecimal amount, String remark, String extFiled3)
        {
            this.subSeqNo = subSeqNo;
            this.oldReqSeqNo = oldReqSeqNo;
            this.acNo = acNo;
            this.acName = acName;
            this.amount = amount;
            this.remark = remark;
            this.extFiled3 = extFiled3;
        }

        public String getSubSeqNo()
        {
            return subSeqNo;
        }

        public void setSubSeqNo(String subSeqNo)
        {
            this.subSeqNo = subSeqNo;
        }

        public String getOldReqSeqNo()
        {
            return oldReqSeqNo;
        }

        public void setOldReqSeqNo(String oldReqSeqNo)
        {
            this.oldReqSeqNo = oldReqSeqNo;
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
