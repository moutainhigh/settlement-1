package com.yuanheng100.settlement.payease.callback;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;

/**
 * Created by jlqian on 2017/2/15.
 */
public interface ICloseLoanCallbackListener
{
    void closeLoan(TRS001003Resp trs001003Resp) throws IllegalPlatformAugumentException;
}
