package com.yuanheng100.settlement.chanpay.service;

import org.dom4j.DocumentException;

/**
 * Created by jlqian on 2016/9/13.
 */
public interface IGwNotifyService
{
    String parseTrxCode(String cjRequmsg) throws DocumentException;
}
