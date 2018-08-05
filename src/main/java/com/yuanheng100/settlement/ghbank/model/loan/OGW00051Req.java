package com.yuanheng100.settlement.ghbank.model.loan;

import java.math.BigDecimal;
import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00051Req extends GHBankReq
{

    private static final long serialVersionUID = -898005693829458161L;
    /**
     * 借款编号
     */
    private Long loanNo;
    /**
     * 标的编号
     * 目前与借款编号一致
     */
    private Long investId;
    /**
     * 标的名称
     */
    private String investObjName;
    /**
     * 标的简介
     */
    private String investObjInfo;
    /**
     * 最低投标金额
     */
    private BigDecimal minInvestAmt;
    /**
     * 最高投标金额
     */
    private BigDecimal maxInvestAmt;
    /**
     * 总标的金额
     */
    private BigDecimal investObjAmt;
    /**
     * 招标开始日期
     */
    private Date investBeginDate;
    /**
     * 招标到期日期
     */
    private Date investEndDate;
    /**
     * 还款日期
     */
    private Date repayDate;
    /**
     * 年利率
     * 最大值为：999.999999
     */
    private BigDecimal yearRate;
    /**
     * 期限
     * 整型，天数，单位为天
     */
    private Integer investRange;
    /**
     * 计息方式
     */
    private String ratesType;
    /**
     * 还款方式
     */
    private String repaysType;
    /**
     * 标的状态
     */
    private Short investObjState;
    /**
     * 借款人总数
     */
    private Integer bwTotalNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为债券转让标的
     */
    private Boolean zrFlag;
    /**
     * 债券转让原标的编号
     * 当ZRFLAG=1时必填
     */
    private String refLoanNo;
    /**
     * 原投标第三方交易流水号
     * 当ZRFLAG=1时必填
     */
    private String oldReqSeq;
    /**
     * 借款人姓名
     */
    private String bwAcName;
    /**
     * 借款人证件类型
     */
    private Short bwIdType;
    /**
     * 借款人证件号码
     */
    private String bwIdNo;
    /**
     * 借款人账号
     */
    private String bwAcNo;
    /**
     * 借款人账号所属行号
     */
    private String bwAcBankId;
    /**
     * 借款人账号所属行名
     */
    private String bwAcBankName;
    /**
     * 借款人金额
     */
    private BigDecimal bwAmt;
    /**
     * 借款人抵押品编号
     */
    private String mortgageId;
    /**
     * 借款人抵押品简单描述
     */
    private String mortgageInfo;
    /**
     * 借款人审批通过日期
     */
    private Date checkDate;
    /**
     * 备注（其它未尽事宜）
     */
    private String bwRemark;
    
    public OGW00051Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        this.transCode = TransCode.OGW00051;
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

    public Long getInvestId()
    {
        return investId;
    }

    public void setInvestId(Long investId)
    {
        this.investId = investId;
    }

    public String getInvestObjName()
    {
        return investObjName;
    }

    public void setInvestObjName(String investObjName)
    {
        this.investObjName = investObjName;
    }

    public String getInvestObjInfo()
    {
        return investObjInfo;
    }

    public void setInvestObjInfo(String investObjInfo)
    {
        this.investObjInfo = investObjInfo;
    }

    public BigDecimal getMinInvestAmt()
    {
        return minInvestAmt;
    }

    public void setMinInvestAmt(BigDecimal minInvestAmt)
    {
        this.minInvestAmt = minInvestAmt;
    }

    public BigDecimal getMaxInvestAmt()
    {
        return maxInvestAmt;
    }

    public void setMaxInvestAmt(BigDecimal maxInvestAmt)
    {
        this.maxInvestAmt = maxInvestAmt;
    }

    public BigDecimal getInvestObjAmt()
    {
        return investObjAmt;
    }

    public void setInvestObjAmt(BigDecimal investObjAmt)
    {
        this.investObjAmt = investObjAmt;
    }

    public Date getInvestBeginDate()
    {
        return investBeginDate;
    }

    public void setInvestBeginDate(Date investBeginDate)
    {
        this.investBeginDate = investBeginDate;
    }

    public Date getInvestEndDate()
    {
        return investEndDate;
    }

    public void setInvestEndDate(Date investEndDate)
    {
        this.investEndDate = investEndDate;
    }

    public Date getRepayDate()
    {
        return repayDate;
    }

    public void setRepayDate(Date repayDate)
    {
        this.repayDate = repayDate;
    }

    public BigDecimal getYearRate()
    {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate)
    {
        this.yearRate = yearRate;
    }

    public Integer getInvestRange()
    {
        return investRange;
    }

    public void setInvestRange(Integer investRange)
    {
        this.investRange = investRange;
    }

    public String getRatesType()
    {
        return ratesType;
    }

    public void setRatesType(String ratesType)
    {
        this.ratesType = ratesType;
    }

    public String getRepaysType()
    {
        return repaysType;
    }

    public void setRepaysType(String repaysType)
    {
        this.repaysType = repaysType;
    }

    public Short getInvestObjState()
    {
        return investObjState;
    }

    public void setInvestObjState(Short investObjState)
    {
        this.investObjState = investObjState;
    }

    public Integer getBwTotalNum()
    {
        return bwTotalNum;
    }

    public void setBwTotalNum(Integer bwTotalNum)
    {
        this.bwTotalNum = bwTotalNum;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Boolean getZrFlag()
    {
        return zrFlag;
    }

    public void setZrFlag(Boolean zrFlag)
    {
        this.zrFlag = zrFlag;
    }

    public String getRefLoanNo()
    {
        return refLoanNo;
    }

    public void setRefLoanNo(String refLoanNo)
    {
        this.refLoanNo = refLoanNo;
    }

    public String getOldReqSeq()
    {
        return oldReqSeq;
    }

    public void setOldReqSeq(String oldReqSeq)
    {
        this.oldReqSeq = oldReqSeq;
    }

    public String getBwAcName()
    {
        return bwAcName;
    }

    public void setBwAcName(String bwAcName)
    {
        this.bwAcName = bwAcName;
    }

    public Short getBwIdType()
    {
        return bwIdType;
    }

    public void setBwIdType(Short bwIdType)
    {
        this.bwIdType = bwIdType;
    }

    public String getBwIdNo()
    {
        return bwIdNo;
    }

    public void setBwIdNo(String bwIdNo)
    {
        this.bwIdNo = bwIdNo;
    }

    public String getBwAcNo()
    {
        return bwAcNo;
    }

    public void setBwAcNo(String bwAcNo)
    {
        this.bwAcNo = bwAcNo;
    }

    public String getBwAcBankId()
    {
        return bwAcBankId;
    }

    public void setBwAcBankId(String bwAcBankId)
    {
        this.bwAcBankId = bwAcBankId;
    }

    public String getBwAcBankName()
    {
        return bwAcBankName;
    }

    public void setBwAcBankName(String bwAcBankName)
    {
        this.bwAcBankName = bwAcBankName;
    }

    public BigDecimal getBwAmt()
    {
        return bwAmt;
    }

    public void setBwAmt(BigDecimal bwAmt)
    {
        this.bwAmt = bwAmt;
    }

    public String getMortgageId()
    {
        return mortgageId;
    }

    public void setMortgageId(String mortgageId)
    {
        this.mortgageId = mortgageId;
    }

    public String getMortgageInfo()
    {
        return mortgageInfo;
    }

    public void setMortgageInfo(String mortgageInfo)
    {
        this.mortgageInfo = mortgageInfo;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public String getBwRemark()
    {
        return bwRemark;
    }

    public void setBwRemark(String bwRemark)
    {
        this.bwRemark = bwRemark;
    }

    @Override
    public String toString()
    {
        return "OGW00051Req [loanNo=" + loanNo + ", investId=" + investId + ", investObjName=" + investObjName
                + ", investObjInfo=" + investObjInfo + ", minInvestAmt=" + minInvestAmt + ", maxInvestAmt="
                + maxInvestAmt + ", investObjAmt=" + investObjAmt + ", investBeginDate=" + investBeginDate
                + ", investEndDate=" + investEndDate + ", repayDate=" + repayDate + ", yearRate=" + yearRate
                + ", investRange=" + investRange + ", ratesType=" + ratesType + ", repaysType=" + repaysType
                + ", investObjState=" + investObjState + ", bwTotalNum=" + bwTotalNum + ", remark=" + remark
                + ", zrFlag=" + zrFlag + ", refLoanNo=" + refLoanNo + ", oldReqSeq=" + oldReqSeq + ", bwAcName="
                + bwAcName + ", bwIdType=" + bwIdType + ", bwIdNo=" + bwIdNo + ", bwAcNo=" + bwAcNo + ", bwAcBankId="
                + bwAcBankId + ", bwAcBankName=" + bwAcBankName + ", bwAmt=" + bwAmt + ", mortgageId=" + mortgageId
                + ", mortgageInfo=" + mortgageInfo + ", checkDate=" + checkDate + ", bwRemark=" + bwRemark
                + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode
                + "]";
    }
    
}
