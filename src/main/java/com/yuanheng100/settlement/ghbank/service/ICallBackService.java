package com.yuanheng100.settlement.ghbank.service;

import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TResp;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TResp;

/**
 * 处理所有回调请求/银行方主动发送请求的服务类
 * 
 * @author Baisong
 *
 */
public interface ICallBackService
{
    /**
     * 银行主动单笔撤标（必须）(OGW0014T)
     * @param ogwa0014TReq
     * @return
     */
    OGW0014TResp bankCancelLoan(OGW0014TReq ogw0014TReq);
    
    
    /**
     * 银行主动流标（必须）(OGW0015T)
     * @param ogwa0015TReq
     * @return
     */
    OGW0015TResp bankRepealLoan(OGW0015TReq ogw0015TReq);
}
