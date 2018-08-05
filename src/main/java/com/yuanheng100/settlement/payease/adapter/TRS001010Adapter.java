package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;

/**
 * TRS001010Adapter
 * 
 * @author Bai Song
 * 
 */
public class TRS001010Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{

    
    @Override
    public String getModuleSubUrl()
    {
        return TransCode.TRS001010.getSubUrl();
    }


    @Override
    public TRS001010Resp send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        return JSON.parseObject(responseString, TRS001010Resp.class);
    }
    
    

}
