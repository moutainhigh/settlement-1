package com.yuanheng100.settlement.ghbank.model.invest;

import com.yuanheng100.settlement.ghbank.consts.IDType;
import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00053Resp extends GHBankResp
{
    /**
     * 投标交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;
    /**
     * 交易日期 交易时间
     */
    private Date transDateTime;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 银行止付日期
     */
    private String hostDt;
    /**
     * 银行止付流水号
     */
    private String hostJnlNo;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

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

    public String getHostDt()
    {
        return hostDt;
    }

    public void setHostDt(String hostDt)
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
