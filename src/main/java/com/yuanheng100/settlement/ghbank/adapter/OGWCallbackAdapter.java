package com.yuanheng100.settlement.ghbank.adapter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.callback.CallbackResponse;
import com.yuanheng100.settlement.ghbank.utils.HttpUtils;

/**
 * Created by jlqian on 2017/4/17.
 */
public class OGWCallbackAdapter extends AbstractGHBankAdapter<CallbackResponse>
{
    
    private static Logger logger = Logger.getLogger(OGWCallbackAdapter.class);
    
    
    @Override
    protected Document createXml(CallbackResponse callbackResponse)
    {
        Document document = super.createXml(callbackResponse);
        Element rootElement = document.getRootElement();
        Element body = rootElement.element("body");
        Element xmlpara = body.element("XMLPARA");
        //将当前业务的字段添加到xmlpara，在父类中加密并替换成加密字符串

        xmlpara.addElement("RETURNCODE").addText(callbackResponse.getReturnCode());
        xmlpara.addElement("RETURNMSG").addText(callbackResponse.getReturnMsg());
        xmlpara.addElement("OLDREQSEQNO").addText(callbackResponse.getOldReqSeqNo());

        return document;
    }

    @Override
    protected GHBankResp parseXmlToGHBankResp(Document document)
    {
        logger.warn("这里应该不会再接收到response了: "+document.getRootElement().asXML());
        return null;
    }
    
    public void postCallbackResponse(CallbackResponse callbackResponse)
    {
        String postString = this.getPostString(callbackResponse);
        //发送请求，获取返回值
        logger.info("acknowledge postString="+postString);
        String acknowledgeResponse = HttpUtils.post(GHBANK_URL, postString);
        logger.info("acknowledge Response="+acknowledgeResponse);
    }

}
