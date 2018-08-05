package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.exception.UnauthorizedException;
import com.yuanheng100.settlement.chanpay.model.G20013Bean;
import org.dom4j.DocumentException;

/**
 * Created by jlqian on 2016/9/12.
 */
public interface IPayCallbackService
{
    G20013Bean callback(String requestBody) throws UnauthorizedException, DocumentException, IllegalPlatformAugumentException;

    String getCjRespMsg(G20013Bean callback);
}
