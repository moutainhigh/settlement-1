package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper.SendAndGetBytes_Response;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/chanpay/g40001")
public class G40001Controller
{

    private static final Logger LOG = Logger.getLogger(G40001Controller.class);

    @ResponseBody
    @RequestMapping(value = "/download")
    public String sendMessage(HttpServletResponse response)
    {
        try
        {
            SendAndGetBytes_Response ret = buildCjmsgAndSend();
            response.setContentType(ret.contentType);
            response.addHeader("Content-Disposition", ret.contentDisposition);
            response.addHeader(Cj.PROP_NAME_RET_CODE, ret.retcode);
            response.addHeader(Cj.PROP_NAME_ERR_MSG, ret.errmsg);
            response.setCharacterEncoding("utf-8");
            ServletOutputStream out = response.getOutputStream();
            out.write(ret.data);
            out.flush();
            out.close();
            return "1";
        } catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 组织Cj报文，并发送
     */
    private SendAndGetBytes_Response buildCjmsgAndSend()
    {
        SendAndGetBytes_Response ret = null;
        try
        {
            String cjReqmsg = buildCjmsg();
            // 签名
            CjSignHelper singHelper = new CjSignHelper();
            String signMsg = singHelper.signXml$Add(cjReqmsg);
            // 发送报文
            ret = CjMsgSendHelper.sendAndGetBytes(signMsg);
        } catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return ret;
    }

    public String buildCjmsg()
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_行名行号下载);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(U.createUUID()));
        infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
        infoEl.addElement("SIGNED_MSG").setText("");


        String xml = Cj.formatXml_UTF8(doc);
        LOG.info("产生G40001行名行号下载：" + U.substringByByte(xml, 512));
        return xml;
    }

}
