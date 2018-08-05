package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.G20001Bean;

/**
 * Created by jlqian on 2016/9/12.
 */
public interface IPayRemoteQueryService
{
    boolean query(G20001Bean bean) throws IllegalPlatformAugumentException;
}
