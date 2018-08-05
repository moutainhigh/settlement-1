package com.yuanheng100.settlement.payease.callback;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;

/**
 * Created by jlqian on 2016/10/19.
 */
public interface IWithdrawCallbackListener
{
    void setWithdrawResult(TRS001006Resp trs001006Resp) throws IllegalPlatformAugumentException;
}
