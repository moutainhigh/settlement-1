package com.yuanheng100.settlement.ghbank.model.repay;

import java.util.Date;
import java.util.List;

import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00075Resp extends GHBankResp
{

    private static final long serialVersionUID = -5692139332925037100L;
    /**
     * 还款交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 还款类型
     */
    private DFFlag dfFlag;
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
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 循环列表<REPAYLIST>有多少个需发收益的投资人就有多少个REPAYLIST标签
     */
    private List<RS> rsList;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public DFFlag getDfFlag()
    {
        return dfFlag;
    }

    public void setDfFlag(DFFlag dfFlag)
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

    public static class RS extends OGW00074Req.RepayItem
    {
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
    }
}
