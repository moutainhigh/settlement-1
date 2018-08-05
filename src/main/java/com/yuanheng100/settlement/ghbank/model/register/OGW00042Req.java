package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.util.ConfigFile;

/**
 * Created by jlqian on 2017/4/18.
 */
public class OGW00042Req extends GHBankReq
{

    private static final long serialVersionUID = 7873304948909151250L;
    /**
     * 交易类型
     */
    private Short tTrans;

    /**
     * 姓名
     */
    private String acName;
    /**
     * 证件类型
     */
    private Short idType;
    /**
     * 证件号码 只支持身份证
     */
    private String idNo;
    /**
     * 手机号码
     */
    private Long mobile;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 返回商户URL
     * 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。
     */
    private String returnUrl;
    /**
     * 客户经理编号
     */
    private String custMngrNo;
    
    public OGW00042Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = TransCode.OGW00042;
        this.tTrans = TransType.ACCOUNT_OPEN.getType();
        this.returnUrl = ConfigFile.getProperty("ghbank.callbackUrl");
        this.initChannelFlow();
    }
    
    /**
     * 本请求的transCode可能有两个，OGW00042或OGW00090。增加此构造函数以便能自定义transCode
     * @param sequenceId
     * @param transCode
     */
    public OGW00042Req(int sequenceId, String transCode)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = transCode;
    }

    public short gettTrans()
    {
        return tTrans;
    }

    public void settTrans(short tTrans)
    {
        this.tTrans = tTrans;
    }

    public String getAcName()
    {
        return acName;
    }

    public void setAcName(String acName)
    {
        this.acName = acName;
    }


    public Short getIdType()
    {
        return idType;
    }

    public void setIdType(Short idType)
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

    public Long getMobile()
    {
        return mobile;
    }

    public void setMobile(Long mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    public String getCustMngrNo()
    {
        return custMngrNo;
    }

    public void setCustMngrNo(String custMngrNo)
    {
        this.custMngrNo = custMngrNo;
    }
    
    @Override
    public String getTransCode()
    {
        return this.transCode;
    }

    @Override
    public String toString()
    {
        return "OGW00042Req [tTrans=" + tTrans + ", acName=" + acName + ", idType=" + idType + ", idNo=" + idNo
                + ", mobile=" + mobile + ", email=" + email + ", returnUrl=" + returnUrl + ", custMngrNo=" + custMngrNo
                + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode
                + ", appId=" + appId + "]";
    }
    
}
