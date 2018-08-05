package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.utils.BankUtil;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;

/**
 * 用来向首信易服务器发送http请求的适配器
 *
 * @author Bai Song
 */
public class SYN001001Adapter extends AbstractPayeaseAdapter<PayeaseReq>
{

    @Override
    public String getModuleSubUrl()
    {
        return TransCode.SYN001001.getSubUrl();
    }


    @Override
    public MessageResponse send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        String responseString = postAndReceive(payeaseReq, arg1);
        SYN001001Resp syn001001Resp = JSON.parseObject(responseString, SYN001001Resp.class);
        String accBank = JSON.parseObject(responseString).getString("accBank");
        try
        {
            syn001001Resp.setAccBankCode(BankUtil.getBankByFullName(accBank).getCode());
        } catch (Exception e)
        {
            syn001001Resp.setAccBankCode(null);
        }
        return syn001001Resp;
    }


}
