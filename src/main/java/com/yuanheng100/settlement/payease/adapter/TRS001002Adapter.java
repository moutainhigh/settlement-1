package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;

/**
 * TRS001002Adapter
 * 
 * @author Bai Song
 * 
 */
public class TRS001002Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{

    
    @Override
    public String getModuleSubUrl()
    {
        return TransCode.TRS001002.getSubUrl();
    }


    @Override
    public TRS001002Resp send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        return JSON.parseObject(responseString, TRS001002Resp.class);
    }

    
}
