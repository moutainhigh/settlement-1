package com.yuanheng100.settlement.ghbank.model.bond;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.util.ConfigFile;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00061Req extends GHBankReq
{

    private static final long serialVersionUID = -9194541055472124763L;
    /**
     * 交易类型
     */
    private Short tTrans;
    /**
     * 原投标流水
     */
    private String oldReqSeqNo;
    /**
     * 原标的编号（即loanNo）
     */
    private Integer oldReqNumber;
    /**
     * 原标的名称
     */
    private String oldReqName;
    /**
     * 转让人银行账号
     */
    private String accNo;
    /**
     * 转让人名称
     */
    private String custName;
    /**
     * 剩余金额
     */
    private BigDecimal amount;
    /**
     * 预计剩余收益
     */
    private BigDecimal preIncome;
    /**
     * 备注
     */
    private String remark;
    /**
     * 返回商户URL
     * 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    private String returnUrl;
    
    public OGW00061Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = TransCode.OGW00061;
        this.tTrans = TransType.BOND_TRADE.getType();
        this.returnUrl = ConfigFile.getProperty("ghbank.callbackUrl");
        this.initChannelFlow();
    }

    public Short gettTrans()
    {
        return tTrans;
    }

    public void settTrans(Short tTrans)
    {
        this.tTrans = tTrans;
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public Integer getOldReqNumber()
    {
        return oldReqNumber;
    }

    public void setOldReqNumber(Integer oldReqNumber)
    {
        this.oldReqNumber = oldReqNumber;
    }

    public String getOldReqName()
    {
        return oldReqName;
    }

    public void setOldReqName(String oldReqName)
    {
        this.oldReqName = oldReqName;
    }

    public String getAccNo()
    {
        return accNo;
    }

    public void setAccNo(String accNo)
    {
        this.accNo = accNo;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getPreIncome()
    {
        return preIncome;
    }

    public void setPreIncome(BigDecimal preIncome)
    {
        this.preIncome = preIncome;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString()
    {
        return "OGW00061Req [tTrans=" + tTrans + ", oldReqSeqNo=" + oldReqSeqNo + ", oldReqNumber=" + oldReqNumber
                + ", oldReqName=" + oldReqName + ", accNo=" + accNo + ", custName=" + custName + ", amount=" + amount
                + ", preIncome=" + preIncome + ", remark=" + remark + ", returnUrl=" + returnUrl + ", channelFlow="
                + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + ", appId="
                + appId + "]";
    }
    
}
