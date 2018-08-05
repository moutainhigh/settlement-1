package com.yuanheng100.settlement.chanpay.service.impl;

import com.yuanheng100.settlement.chanpay.service.IGwNotifyService;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by jlqian on 2016/9/13.
 */
public class GwNotifyServiceImpl implements IGwNotifyService
{
    private static final Logger LOG = Logger.getLogger(GwNotifyServiceImpl.class);
    @Override
    public String parseTrxCode(String cjRequmsg) throws DocumentException
    {
        Document reqDoc = DocumentHelper.parseText(cjRequmsg);

        Element msgEl = reqDoc.getRootElement();
        Element infoEl = msgEl.element("INFO");

        String trxCode = infoEl.elementText("TRX_CODE");

        return trxCode;
    }
}
