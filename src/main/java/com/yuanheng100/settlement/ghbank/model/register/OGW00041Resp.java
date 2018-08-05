package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGW00041Resp extends GHBankResp
{

    private static final long serialVersionUID = 4029649065217530697L;
    /**
     * 短信唯一标识 与客户收到的短信动态密码建立一一对应关系
     */
    private String otpSeqNo;
    /**
     * 短信序号 只用于页面展示
     */
    private String otpIndex;

    public String getOtpSeqNo()
    {
        return otpSeqNo;
    }

    public void setOtpSeqNo(String otpSeqNo)
    {
        this.otpSeqNo = otpSeqNo;
    }

    public String getOtpIndex()
    {
        return otpIndex;
    }

    public void setOtpIndex(String otpIndex)
    {
        this.otpIndex = otpIndex;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "OGW00041Resp [otpSeqNo=" + otpSeqNo + ", otpIndex=" + otpIndex + ", channelCode=" + channelCode
                + ", transCode=" + transCode + ", channelFlow=" + channelFlow + ", serverFlow=" + serverFlow
                + ", serverDateTime=" + serverDateTime + ", encryptData=" + encryptData + ", status=" + status
                + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
    }

    
}
