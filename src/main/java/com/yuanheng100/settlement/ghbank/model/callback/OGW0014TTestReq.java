package com.yuanheng100.settlement.ghbank.model.callback;

import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * 银行主动单笔撤标（OGW0014T）请求<br>
 * 这个request是模拟银行方发过来的测试用例的一部分
 * @author Baisong
 *
 */
public class OGW0014TTestReq extends GHBankReq
{

    private static final long serialVersionUID = 7073242111268540941L;
    /**
     * 借款编号
     */
    private Long loanNo;
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
     * 撤标原因
     */
    private String cancelReason;
    /**
     * 银行止付日期
     */
    private Date hostDt;
    /**
     * 银行止付流水号
     */
    private String hostJnlNo;
    
    
    public OGW0014TTestReq(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW0014T;
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

    public String getCancelReason()
    {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
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
