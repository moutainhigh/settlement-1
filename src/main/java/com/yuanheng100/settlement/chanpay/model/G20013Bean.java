package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.Gw01MsgBase;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2016/9/12.
 */
public class G20013Bean extends Gw01MsgBase
{
    /**
     * 原交易请求号
     */
    private String trxReqSn;
    /**
     * 企业账号
     */
    private String corpAcctNo;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 协议号
     */
    private String protocolNo;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 手续费
     */
    private BigDecimal charge;
    /**
     * 外部企业流水号
     */
    private String corpFlowNo;
    /*
     * 备注
     */
    private String summary;
    /**
     * 用途
     */
    private String postscript;

    public String getTrxReqSn()
    {
        return trxReqSn;
    }

    public void setTrxReqSn(String trxReqSn)
    {
        this.trxReqSn = trxReqSn;
    }

    public String getCorpAcctNo()
    {
        return corpAcctNo;
    }

    public void setCorpAcctNo(String corpAcctNo)
    {
        this.corpAcctNo = corpAcctNo;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getProtocolNo()
    {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo)
    {
        this.protocolNo = protocolNo;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getCharge()
    {
        return charge;
    }

    public void setCharge(BigDecimal charge)
    {
        this.charge = charge;
    }

    public String getCorpFlowNo()
    {
        return corpFlowNo;
    }

    public void setCorpFlowNo(String corpFlowNo)
    {
        this.corpFlowNo = corpFlowNo;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getPostscript()
    {
        return postscript;
    }

    public void setPostscript(String postscript)
    {
        this.postscript = postscript;
    }
}
