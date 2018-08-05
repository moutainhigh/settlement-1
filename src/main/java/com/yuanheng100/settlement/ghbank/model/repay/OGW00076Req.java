package com.yuanheng100.settlement.ghbank.model.repay;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.Priority;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/4/26.
 */
public class OGW00076Req extends GHBankReq
{

    private static final long serialVersionUID = 1303789237884941387L;
    /**
     * 收款账号
     */
    private String acNo;
    /**
     * 收款户名
     */
    private String acName;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
    
    private OGW00076Req(int sequenceId)
    {
        super(sequenceId);
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

    @Override
    public String getTransCode()
    {
        return TransCode.OGW00076;
    }
    
}
