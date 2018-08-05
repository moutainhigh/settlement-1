package com.yuanheng100.settlement.ghbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jlqian on 2017/4/17.
 */
public class GHBankResp implements Serializable
{

    private static final long serialVersionUID = -1815209297305099099L;
    /**
     * 接入渠道 渠道标识，商户回复的请求，具体值由华兴银行统一分配,每个商户不一样)；
     * 华兴行回复的请求值为“GHB”
     */
    protected String channelCode;
    /**
     * 交易码 具体值以各自交易定义的为准
     */
    protected String transCode;
    /**
     * 原渠道流水号 原请求的渠道流水号
     */
    protected String channelFlow;
    /**
     * 服务流水号
     */
    protected String serverFlow;
    /**
     * 服务日期 yyyyyMMdd
     * 服务时间 HHmmss
     * 合二为一
     */
    protected Date serverDateTime;
    /**
     * 加密域 暂时为空
     */
    protected String encryptData;
    /**
     * 业务状态
     * 0：受理成功
     * 1：受理失败
     * 2：受理中
     * 3：受理超时，不确定
     * 如查询交易返回状态为1时交易可置为失败。
     */
    protected Short status;
    /**
     * 错误代码
     * 单个0：受理成功
     * 其它：错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     * 错误代码非0时，返回具体的错误原因
     */
    protected String errorMsg;
    /**
     * 商户唯一标识 银行统一提供
     */
    private String merchantId;
    /**
     * 银行标识 固定值：GHB
     */
    private String bankId;
    /**
     * 备用字段1
     */
    private String extFiled1;
    /**
     * 备用字段2
     */
    private String extFiled2;
    /**
     * 备用字段3
     */
    private String extFiled3;

    public String getChannelCode()
    {
        return channelCode;
    }

    public void setChannelCode(String channelCode)
    {
        this.channelCode = channelCode;
    }

    public String getTransCode()
    {
        return transCode;
    }

    public void setTransCode(String transCode)
    {
        this.transCode = transCode;
    }

    public String getChannelFlow()
    {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow)
    {
        this.channelFlow = channelFlow;
    }

    public String getServerFlow()
    {
        return serverFlow;
    }

    public void setServerFlow(String serverFlow)
    {
        this.serverFlow = serverFlow;
    }

    public Date getServerDateTime()
    {
        return serverDateTime;
    }

    public void setServerDateTime(Date serverDateTime)
    {
        this.serverDateTime = serverDateTime;
    }

    public String getEncryptData()
    {
        return encryptData;
    }

    public void setEncryptData(String encryptData)
    {
        this.encryptData = encryptData;
    }

    public Short getStatus()
    {
        return status;
    }

    public void setStatus(Short status)
    {
        this.status = status;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getExtFiled1()
    {
        return extFiled1;
    }

    public void setExtFiled1(String extFiled1)
    {
        this.extFiled1 = extFiled1;
    }

    public String getExtFiled2()
    {
        return extFiled2;
    }

    public void setExtFiled2(String extFiled2)
    {
        this.extFiled2 = extFiled2;
    }

    public String getExtFiled3()
    {
        return extFiled3;
    }

    public void setExtFiled3(String extFiled3)
    {
        this.extFiled3 = extFiled3;
    }

    @Override
    public String toString()
    {
        return "GHBankResp [channelCode=" + channelCode + ", transCode=" + transCode + ", channelFlow=" + channelFlow
                + ", serverFlow=" + serverFlow + ", serverDateTime=" + serverDateTime + ", encryptData=" + encryptData
                + ", status=" + status + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }
    
    
}
