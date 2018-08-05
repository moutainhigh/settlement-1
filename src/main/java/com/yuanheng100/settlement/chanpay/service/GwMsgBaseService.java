package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.settlement.chanpay.common.Gw01MsgBase;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by jlqian on 2016/9/9.
 */
public abstract class GwMsgBaseService<T extends Gw01MsgBase> {

    private static final Logger LOG = Logger.getLogger(GwMsgBaseService.class);
    /**
     * 组织Cj报文，并发送
     */
    protected void buildCjmsgAndSend(T bean) {

        try {
            String cjReqmsg = buildCjmsg(bean);
            // 签名
            CjSignHelper singHelper = new CjSignHelper();
            String signMsg = singHelper.signXml$Add(cjReqmsg);
            // 发送报文
            String cjRespmsg = CjMsgSendHelper.sendAndGetString(signMsg);
            // 验证签名
            CjSignHelper.VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
            if (!verifyResult.result) {
                throw new Exception("验证CJ返回数据的签名失败！" + verifyResult.errMsg);
            }
            parseCjMsgToDto(cjRespmsg, bean);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建畅捷通的发送报文
     * @param bean
     * @return
     */
    protected abstract String buildCjmsg(T bean);

    /**
     * 解析畅捷通的响应报文
     * @param cjRespmsg
     * @param bean
     */
    protected void parseCjMsgToDto(String cjRespmsg, T bean) throws DocumentException {
        Document reqDoc = DocumentHelper.parseText(cjRespmsg);

        Element msgEl = reqDoc.getRootElement();
        Element infoEl = msgEl.element("INFO");

        bean.setRetCode(infoEl.elementText("RET_CODE"));
        bean.setErrMsg(infoEl.elementText("ERR_MSG"));
        bean.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));
        LOG.debug("基本响应信息：retcode[" + bean.getRetCode() + "], errmsg[" + bean.getErrMsg() + "]");
    }
}
