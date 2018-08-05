package com.yuanheng100.settlement.payease.callback;

import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;

/**
 * Created by jlqian on 2017/2/6.
 */
public interface IBindBankCardCallbackListener
{
    void bindCallback(SYN001001Resp syn001001Resp);
}
