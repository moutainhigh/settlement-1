package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.consts.TransType;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;
import com.yuanheng100.util.ConfigFile;

/**
 * 绑卡 
 * 
 * Created by jlqian on 2017/4/19.
 */
public class OGW00044Req extends GHBankReq
{

    private static final long serialVersionUID = -736595932085311574L;
    
    /**
     * 交易类型
     */
    private Short tTrans;
    /**
     * 银行账号
     */
    private String acNo;
    /**
     * 返回商户URL
     */
    private String returnUrl;
    
    public OGW00044Req(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.ASYNC.getMethod());
        this.transCode = TransCode.OGW00044;
        this.tTrans = TransType.BIND_BANK_CARD.getType();
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

    public String getAcNo()
    {
        return acNo;
    }

    public void setAcNo(String acNo)
    {
        this.acNo = acNo;
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
        return "OGW00044Req [tTrans=" + tTrans + ", acNo=" + acNo + ", returnUrl=" + returnUrl + ", channelFlow="
                + channelFlow + ", transCode=" + transCode + "]";
    }

    
}
