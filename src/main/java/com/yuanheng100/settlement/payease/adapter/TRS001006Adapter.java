package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.utils.BankUtil;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;

/**
 * TRS001006Adapter
 * 
 * @author Bai Song
 * 
 */
public class TRS001006Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{

    
    @Override
    public String getModuleSubUrl()
    {
        return TransCode.TRS001006.getSubUrl();
    }


    @Override
    public MessageResponse send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        TRS001006Resp trs001006Resp = JSON.parseObject(responseString, TRS001006Resp.class);
        String accBank = JSON.parseObject(responseString).getString("accBank");
        try
        {
            trs001006Resp.setAccBankCode(BankUtil.getBankByFullName(accBank).getCode());
        } catch (Exception e)
        {
            trs001006Resp.setAccBankCode(null);
        }
        return trs001006Resp;
    }

    
}
