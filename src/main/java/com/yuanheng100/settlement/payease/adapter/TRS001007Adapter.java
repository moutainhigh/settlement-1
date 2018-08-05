package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;

/**
 * TRS001007Adapter
 * 
 * @author Bai Song
 * 
 */
public class TRS001007Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{

    
    @Override
    public String getModuleSubUrl()
    {
        return TransCode.TRS001007.getSubUrl();
    }


    @Override
    public MessageResponse send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        return JSON.parseObject(responseString, TRS001007Resp.class);
    }
    
    

}
