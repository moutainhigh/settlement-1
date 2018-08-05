package com.yuanheng100.settlement.ghbank.model.autorepay;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00071Req extends GHBankReq
{

    private static final long serialVersionUID = -1406285814550560163L;
    /**
     * 动态密码唯一标识
     */
    private String otpSeqNo;
    /**
     * 动态密码
     */
    private String otpNo;
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
     * 备注
     */
    private String remark;
    
    public OGW00071Req(int sequenceId)
    {
        super(sequenceId);
    }

    public String getOtpSeqNo()
    {
        return otpSeqNo;
    }

    public void setOtpSeqNo(String otpSeqNo)
    {
        this.otpSeqNo = otpSeqNo;
    }

    public String getOtpNo()
    {
        return otpNo;
    }

    public void setOtpNo(String otpNo)
    {
        this.otpNo = otpNo;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Override
    public String getTransCode()
    {
        return TransCode.OGW00071;
    }
}
