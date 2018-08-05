package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/18.
 */
public class OGW00042Resp extends GHBankResp
{
  
    private static final long serialVersionUID = -8246249096911856462L;
    /**
     * 原开户交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 姓名
     */
    private String respAcName;
    /**
     * 证件类型
     */
    private Short respIdType;
    /**
     * 证件号码 只支持身份证
     */
    private String respIdNo;
    /**
     * 手机号码
     */
    private Long respMobile;
    
    /**
     * 华兴银行e账户账号
     */
    private String acNo;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public String getRespAcName()
    {
        return respAcName;
    }

    public void setRespAcName(String respAcName)
    {
        this.respAcName = respAcName;
    }

    public Short getRespIdType()
    {
        return respIdType;
    }

    public void setRespIdType(Short respIdType)
    {
        this.respIdType = respIdType;
    }

    public String getRespIdNo()
    {
        return respIdNo;
    }

    public void setRespIdNo(String respIdNo)
    {
        this.respIdNo = respIdNo;
    }

    public Long getRespMobile()
    {
        return respMobile;
    }

    public void setRespMobile(Long respMobile)
    {
        this.respMobile = respMobile;
    }

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
    }

    @Override
    public String toString()
    {
        return "OGW00042Resp [oldReqSeqNo=" + oldReqSeqNo + ", respAcName=" + respAcName + ", respIdType=" + respIdType + ", respIdNo=" + respIdNo
                + ", respMobile=" + respMobile + ", acNo=" + acNo + ", transCode=" + transCode + ", channelFlow=" + channelFlow + ", serverFlow="
                + serverFlow + ", serverDateTime=" + serverDateTime + ", status=" + status + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }
    
}
