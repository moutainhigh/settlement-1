package com.yuanheng100.settlement.chanpay.common;

import java.io.Serializable;

/**
 * Created by jlqian on 2016/9/7.
 */
public abstract class QpayMsgBase implements Serializable
{
    /*******************************************************请求参数***********************************************/
    /**
     * 接口名称。不可空
     */
    private String service;
    /**
     * 接口版本，目前只有固定值1.0 不可空
     */
    private Double version;
    /**
     * 签约合作方的唯一用户号 不可空
     */
    private String partnerId;
    /**
     * 商户网站使用的编码格式，如utf-8、gbk、gb2312等 不可空
     */
    private String inputCharset;
    /**
     * 参见“签名机制”	不可空	e8qdwl9caset5zugii2r7q0k8ikopxor
     */
    private String sign;
    /**
     * 签名方式只支持RSA、MD5 不可空
     */
    private String signType;
    /**
     * 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。 可空
     * 空则不会进行该操作。
     */
    private String returnUrl;
    /**
     * 说明信息 可空
     */
    private String memo;

    /*******************************************************返回参数*******************************************/
    /**
     * 表示接口调用是否成功，并不表明业务处理结果。 不可空
     */
    private String isSuccess;
    /**
     * 返回错误码
     */
    private String errorCode;
    /**
     * 返回错误原因
     */
    private String errorMessage;


    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public Double getVersion()
    {
        return version;
    }

    public void setVersion(Double version)
    {
        this.version = version;
    }

    public String getPartnerId()
    {
        return partnerId;
    }

    public void setPartnerId(String partnerId)
    {
        this.partnerId = partnerId;
    }

    public String getInputCharset()
    {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset)
    {
        this.inputCharset = inputCharset;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getSignType()
    {
        return signType;
    }

    public void setSignType(String signType)
    {
        this.signType = signType;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public String getIsSuccess()
    {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
