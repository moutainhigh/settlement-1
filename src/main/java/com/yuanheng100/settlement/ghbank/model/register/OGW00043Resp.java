package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.consts.IDType;
import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00043Resp extends GHBankResp
{

    private static final long serialVersionUID = 8862252173084114947L;
    /**
     * 原开户交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 银行交易流水号
     */
    private String resJnlNo;
    /**
     * 交易日期 交易时间
     */
    private Date transDateTime;
    /**
     * 银行账号
     */
    private String acNo;
    /**
     * 姓名
     */
    private String acName;
    /**
     * 证件类型
     */
    private IDType idType;
    /**
     * 证件号码 只支持身份证
     */
    private String idNo;
    /**
     * 手机号码
     */
    private String mobile;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
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

    public IDType getIdType()
    {
        return idType;
    }

    public void setIdType(IDType idType)
    {
        this.idType = idType;
    }

    public String getIdNo()
    {
        return idNo;
    }

    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    /**
     * @return the mobile
     */
    public String getMobile()
    {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "OGW00043Resp [oldReqSeqNo=" + oldReqSeqNo + ", returnStatus=" + returnStatus + ", returnErrorMsg="
                + returnErrorMsg + ", resJnlNo=" + resJnlNo + ", transDateTime=" + transDateTime + ", acNo=" + acNo
                + ", acName=" + acName + ", idType=" + idType + ", idNo=" + idNo + ", mobile=" + mobile + "]";
    }
    
    

}
