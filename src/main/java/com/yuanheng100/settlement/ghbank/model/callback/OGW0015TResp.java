package com.yuanheng100.settlement.ghbank.model.callback;

import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankReq;

/**
 * 银行主动流标(OGW0015T)
 * <br>
 * 这个response是我方向银行方回复过去的，所以继承了GHBankReq
 * @author Baisong
 *
 */
public class OGW0015TResp extends GHBankReq
{

    private static final long serialVersionUID = 4307577123757332000L;

    public OGW0015TResp(int sequenceId)
    {
        super(sequenceId);
        this.transCode = TransCode.OGW0015T;
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
        super.initChannelFlow();
    }
    
    /**
     * 响应码
     */
    private String returnCode;
    
    /**
     * 响应信息
     */
    private String returnMsg;
    
    /**
     * 原投标流水号
     */
    private String oldReqSeqNo;

    /**
     * @return the returnCode
     */
    public String getReturnCode()
    {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }

    /**
     * @return the returnMsg
     */
    public String getReturnMsg()
    {
        return returnMsg;
    }

    /**
     * @param returnMsg the returnMsg to set
     */
    public void setReturnMsg(String returnMsg)
    {
        this.returnMsg = returnMsg;
    }

    /**
     * @return the oldReqSeqNo
     */
    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    /**
     * @param oldReqSeqNo the oldReqSeqNo to set
     */
    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }
    

}
