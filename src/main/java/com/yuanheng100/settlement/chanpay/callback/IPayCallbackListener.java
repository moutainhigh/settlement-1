package com.yuanheng100.settlement.chanpay.callback;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.G20013Bean;

/**
 * Created by jlqian on 2016/10/20.
 */
public interface IPayCallbackListener
{
    void setPayResult(G20013Bean g20013Bean) throws IllegalPlatformAugumentException;
}
