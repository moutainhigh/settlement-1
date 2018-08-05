package com.yuanheng100.settlement.chanpay.callback;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.Q20008Bean;

/**
 * Created by jlqian on 2016/10/19.
 */
public interface IRechargeCallbackListener
{
    void setRechargeResult(Q20008Bean q20008Bean) throws IllegalPlatformAugumentException;
}
