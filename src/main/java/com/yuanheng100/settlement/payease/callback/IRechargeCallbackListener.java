package com.yuanheng100.settlement.payease.callback;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;

/**
 * Created by jlqian on 2016/10/19.
 */
public interface IRechargeCallbackListener
{
    void setRechargeResult(RechargeBackResp rechargeBackResp) throws IllegalPlatformAugumentException;
}
