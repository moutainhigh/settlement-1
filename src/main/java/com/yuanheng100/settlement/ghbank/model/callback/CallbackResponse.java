package com.yuanheng100.settlement.ghbank.model.callback;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * 接收到银行方的回调后，再向银行发送的acknowledge Response
 * @author Baisong
 *
 */
public class CallbackResponse extends GHBankReq
{

    private static final long serialVersionUID = -2567405196899032901L;

    /**
     * 原开户交易流水号
     */
    private String oldReqSeqNo;
    
    private String returnCode;
    
    private String returnMsg;
    
    private CallbackResponse(int sequenceId)
    {
        super(sequenceId);
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
    }
    
    /**
     * 
     * @param sequenceId
     * @param transCode 交易码 
     * @param returnCode 响应码 000000标识成功
     * @param returnMsg  响应信息
     * @param oldReqSeqNo  原开户交易流水号
     */
    public CallbackResponse(int sequenceId, String transCode, String returnCode, String returnMsg, String oldReqSeqNo)
    {
        this(sequenceId);
        this.transCode = transCode;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.oldReqSeqNo = oldReqSeqNo;
        this.appId = AppId.PC;
        this.initChannelFlow();
    }

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public String getReturnCode()
    {
        return returnCode;
    }

    public String getReturnMsg()
    {
        return returnMsg;
    }

    @Override
    public String toString()
    {
        return "CallbackResponse [oldReqSeqNo=" + oldReqSeqNo + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", channelCode=" + channelCode
                + ", channelFlow=" + channelFlow + ", channelDateTime=" + channelDateTime + ", transCode=" + transCode + "]";
    }

}
