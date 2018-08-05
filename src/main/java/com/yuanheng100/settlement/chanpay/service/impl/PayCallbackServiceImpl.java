package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.exception.UnauthorizedException;
import com.yuanheng100.settlement.chanpay.callback.IPayCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.mapper.G10002BeanMapper;
import com.yuanheng100.settlement.chanpay.mapper.G20014BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.model.G20013Bean;
import com.yuanheng100.settlement.chanpay.service.IPayCallbackService;
import com.yuanheng100.settlement.chanpay.util.CallbackListenerContainer;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jlqian on 2016/9/12.
 */
public class PayCallbackServiceImpl implements IPayCallbackService
{
    private static final Logger LOG = Logger.getLogger(PayCallbackServiceImpl.class);

    @Autowired
    private G10002BeanMapper g10002BeanMapper;
    @Autowired
    private G20014BeanMapper g20014BeanMapper;

    @Transactional
    @Override
    public G20013Bean callback(String cjRequmsg) throws UnauthorizedException, DocumentException, IllegalPlatformAugumentException
    {
        //验证签名
        CjSignHelper singHelper = new CjSignHelper();
        CjSignHelper.VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRequmsg);
        if (!verifyResult.result)
        {
            throw new UnauthorizedException("验证CJ回调数据的签名失败！" + verifyResult.errMsg);
        }
        //构建回调对象
        G20013Bean g20013Bean = new G20013Bean();
        parseCjMsgToDto(cjRequmsg, g20013Bean);

        //检查是否存在当前交易
        G10001Bean g10001Bean = g10002BeanMapper.selectByReqSn(g20013Bean.getTrxReqSn());
        if(g10001Bean==null){
            throw new IllegalPlatformAugumentException("订单编号"+g20013Bean.getTrxReqSn()+"不存在");
        }
        //检验结果是否已经存在
        if(CjDetailRetcode.$0000_处理成功.getCode().equals(g10001Bean.getTradeCode())
                || CjDetailRetcode.$2013_收款行未开通业务.getCode().equals(g10001Bean.getTradeCode())
                || CjDetailRetcode.$3999_其他错误.getCode().equals(g10001Bean.getTradeCode())){
            LOG.warn("订单编号"+g10001Bean.getReqSn()+"结果已存在，不再更新数据库"+JSON.toJSONString(g20013Bean));
            return g20013Bean;
        }
        //存入数据库
        LOG.info("畅捷通代付回调插入到数据库："+JSON.toJSONString(g20013Bean));
        g20014BeanMapper.insertSelective(g20013Bean);
        //更新代付表G100002
        g10001Bean.setTradeCode(g20013Bean.getRetCode());
        g10001Bean.setTradeMsg(g20013Bean.getErrMsg());
        LOG.info("畅捷通代付回调更新数据库代付表："+JSON.toJSONString(g10001Bean));
        g10002BeanMapper.updateTradeResult(g10001Bean);
        IPayCallbackListener payCallbackListener = CallbackListenerContainer.getPayCallbackListener(g20013Bean.getTrxReqSn());
        if(payCallbackListener!=null){
            LOG.info("畅捷通代付回调成功，对P2P进回调");
            try{
                payCallbackListener.setPayResult(g20013Bean);
                CallbackListenerContainer.removePayCallbackListener(g20013Bean.getTrxReqSn());
            }catch (IllegalPlatformAugumentException e){
                LOG.info("P2P回调失败",e);
            }
        }
        return g20013Bean;
    }

    protected void parseCjMsgToDto(String cjRequmsg, G20013Bean bean) throws DocumentException
    {
        Document reqDoc = DocumentHelper.parseText(cjRequmsg);

        Element msgEl = reqDoc.getRootElement();
        Element infoEl = msgEl.element("INFO");

        bean.setTrxCode(infoEl.elementText("TRX_CODE"));
        bean.setMertid(infoEl.elementText("MERCHANT_ID"));
        bean.setReqSn(infoEl.elementText("REQ_SN"));
        bean.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));

        Element bodyEl = msgEl.element("BODY");
        bean.setTrxReqSn(bodyEl.elementText("TRX_REQ_SN"));
        bean.setRetCode(bodyEl.elementText("RET_CODE"));
        bean.setErrMsg(bodyEl.elementText("ERR_MSG"));
        bean.setCorpAcctNo(bodyEl.elementText("CORP_ACCT_NO"));
        bean.setAccountNo(bodyEl.elementText("ACCOUNT_NO"));
        bean.setAccountName(bodyEl.elementText("ACCOUNT_NAME"));
        bean.setProtocolNo(bodyEl.elementText("PROTOCOL_NO"));
        bean.setAmount(U.money_cent2yuan(bodyEl.elementText("AMOUNT")));
        bean.setCharge(U.money_cent2yuan(bodyEl.elementText("CHARGE")));
        bean.setCorpFlowNo(bodyEl.elementText("CORP_FLOW_NO"));
        bean.setSummary(bodyEl.elementText("SUMMARY"));
        bean.setPostscript(bodyEl.elementText("POSTSCRIPT"));

        LOG.debug("支付回调信息信息：" + JSON.toJSONString(bean));
    }

    @Override
    public String getCjRespMsg(G20013Bean callback)
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_单笔实时付款交易结果推送);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(callback.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(callback.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");
        String xml = Cj.formatXml_UTF8(doc);
        return xml;
    }

}