package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.utils.BankUtil;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;

/**
 * TRS001008Adapter
 *
 * @author Bai Song
 */
public class TRS001008Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{


    @Override
    public String getModuleSubUrl()
    {
        return TransCode.TRS001008.getSubUrl();
    }


    @Override
    public MessageResponse send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        TRS001008Resp trs001008Resp = JSON.parseObject(responseString, TRS001008Resp.class);
        String accBank = JSON.parseObject(responseString).getString("accBank");
        try
        {
            trs001008Resp.setAccBankCode(BankUtil.getBankByFullName(accBank).getCode());
        } catch (Exception e)
        {
            trs001008Resp.setAccBankCode(null);
        }
        return trs001008Resp;
    }


}
