package com.yuanheng100.settlement.ghbank.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuanheng100.settlement.ghbank.adapter.AbstractGHBankAdapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00042Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00044Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00045Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00047Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00052Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00061Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW00067Adapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW0014TAdapter;
import com.yuanheng100.settlement.ghbank.adapter.OGW0015TAdapter;
import com.yuanheng100.settlement.ghbank.adapter.OGWCallbackAdapter;
import com.yuanheng100.settlement.ghbank.consts.AcknowledgeCallBackCode;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.callback.CallbackResponse;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TResp;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TReq;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0015TResp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;
import com.yuanheng100.settlement.ghbank.service.IBondTradeService;
import com.yuanheng100.settlement.ghbank.service.ICallBackService;
import com.yuanheng100.settlement.ghbank.service.IInvestService;
import com.yuanheng100.settlement.ghbank.service.IListenerService;
import com.yuanheng100.settlement.ghbank.service.ILoanService;
import com.yuanheng100.settlement.ghbank.service.IRechargeService;
import com.yuanheng100.settlement.ghbank.service.IRegisterService;
import com.yuanheng100.settlement.ghbank.service.IRepayService;
import com.yuanheng100.settlement.ghbank.service.IWithdrawService;

/**
 * Created by jlqian on 2017/4/19.
 */
@RestController
@RequestMapping("/ghbank/callback")
public class GHBankCallBackController
{
    private static Logger logger = Logger.getLogger(GHBankCallBackController.class);
    
    @Autowired
    private ICallBackService callBackService;
    
    @Autowired
    private IRegisterService registerService;
    
    @Autowired
    private ILoanService loanService;
    
    @Autowired
    private IRechargeService rechargeService;
    
    @Autowired
    private IWithdrawService withdrawService;
    
    @Autowired
    private IInvestService investService;
    
    @Autowired
    private IBondTradeService bondTradeService;
    
    @Autowired
    private IListenerService listenerService;
    
    @Autowired
    private IRepayService repayService;
    
    private static Map<String, String> callbackReqCache = new HashMap<String, String>(10);
    
    
    
    @RequestMapping("/return")
    public String callback(HttpServletRequest request, HttpServletResponse response)
    {
        String callbackString = receiveRequest(request);
        String callbackResponseString = null;
        
        if (callbackString != null && callbackString.length() > 0)
        {
            callbackResponseString = handleCallBackResponse(callbackString);
        }
        else
        {
            logger.info("回调数据为空");
            
        }

        return callbackResponseString;
    }
    
    
    /**
     * 处理回调的请求
     * @param callbackString
     */
    private String handleCallBackResponse(String callbackString)
    {
        Document responseXML = AbstractGHBankAdapter.parseResponseStringToXML(callbackString);
        logger.info("回调内容: \t"+responseXML.asXML().replaceAll("\r|\n", ""));
        GHBankResp ghBankResp = new GHBankResp();
        ghBankResp = AbstractGHBankAdapter.parseXmlToGHBankResp(responseXML, ghBankResp);
        String callbackResponseString = null;
        OGWCallbackAdapter callbackAdapter = new OGWCallbackAdapter();
        
        //检查是否收到了重复的callback
        if(hitCallbackCache(ghBankResp))
        {
            logger.info("该callback命中缓存，跳过后续操作");
            return null;
        }
        
        CallbackResponse callbackResponse = null;
        
        if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0001))
        {
            OGW00042Adapter adapter42  = new OGW00042Adapter();
            OGW00042Resp resp42 = (OGW00042Resp)adapter42.parseXmlToGHBankResp(responseXML);
            registerService.updateRegisterResponse(resp42);
            
            listenerService.add42RegisterListener(resp42);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0001, AcknowledgeCallBackCode.SUCCESS, "开户交易成功", resp42.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0002))
        {
            OGW00044Adapter adapter44  = new OGW00044Adapter();
            OGW00044Resp resp44 = (OGW00044Resp)adapter44.parseXmlToGHBankResp(responseXML);
            registerService.updateBindCardResponse(resp44);
            
            listenerService.add44BindCardListener(resp44);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0002, AcknowledgeCallBackCode.SUCCESS, "绑卡交易成功", resp44.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0003))
        {
            OGW00045Adapter adapter45  = new OGW00045Adapter();
            OGW00045Resp resp45 = (OGW00045Resp)adapter45.parseXmlToGHBankResp(responseXML);
            rechargeService.updateRechargeResponse(resp45);
            
            listenerService.add45RechargeListener(resp45);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0003, AcknowledgeCallBackCode.SUCCESS, "充值交易成功", resp45.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0004))
        {
            OGW00047Adapter adapter47  = new OGW00047Adapter();
            OGW00047Resp resp47 = (OGW00047Resp)adapter47.parseXmlToGHBankResp(responseXML);
            withdrawService.updateWithdrawResponse(resp47);
            
            listenerService.add47WithdrawListener(resp47);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0004, AcknowledgeCallBackCode.SUCCESS, "提现交易成功", resp47.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0005))
        {
            OGW00052Adapter adapter52  = new OGW00052Adapter();
            OGW00052Resp resp52 = (OGW00052Resp)adapter52.parseXmlToGHBankResp(responseXML);
            investService.updateInvestResponse(resp52);
            
            listenerService.add52InvestListener(resp52);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0005, AcknowledgeCallBackCode.SUCCESS, "投标交易成功", resp52.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0006))
        {
            logger.warn("自动投标授权回调，暂未实现");
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0007))
        {
            OGW00061Adapter adapter61  = new OGW00061Adapter();
            OGW00061Resp resp61 = (OGW00061Resp)adapter61.parseXmlToGHBankResp(responseXML);
            bondTradeService.updateBondTradeRespone(resp61);
            
            listenerService.add61BondTradeListener(resp61);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0007, AcknowledgeCallBackCode.SUCCESS, "债权转让交易成功", resp61.getOldReqSeqNo());
        }
        else if(ghBankResp.getTransCode().equalsIgnoreCase(TransCode.OGWR0008))
        {
            OGW00067Adapter adapter67  = new OGW00067Adapter();
            OGW00067Resp resp67 = (OGW00067Resp)adapter67.parseXmlToGHBankResp(responseXML);
            repayService.updateRepayResponse(resp67);
            
            listenerService.add67RepayListener(resp67);
            
            callbackResponse = new CallbackResponse(loanService.getSequenceId(), TransCode.OGWR0008, AcknowledgeCallBackCode.SUCCESS, "还款交易成功", resp67.getOldReqSeqNo());
        }
        else
        {
            logger.warn("未识别的TransCode："+ghBankResp);
        }
        
        callbackResponseString = callbackAdapter.getPostString(callbackResponse);
        logger.info("响应"+ghBankResp.getTransCode()+"回调，发送返回："+callbackResponseString);
        return callbackResponseString;
    }
    
    /**
     * 判断当前收到的callback是否是重复的
     * @param ghBankResp
     * @return true意味着当前回调是重复发送的，应当跳过后续处理
     *      false 意味着当前回调是新的，应做后续处理
     */
    private boolean hitCallbackCache(GHBankResp ghBankResp)
    {
        logger.debug("收到的Callback transCode="+ghBankResp.getTransCode()+", channelFlow="+ghBankResp.getChannelFlow()+".  callbackReq缓存: "+callbackReqCache);
        if (callbackReqCache.get(ghBankResp.getTransCode()) == null)
        {
            callbackReqCache.put(ghBankResp.getTransCode(), ghBankResp.getChannelFlow());
            return false;
        }
        else
        {
            if(callbackReqCache.get(ghBankResp.getTransCode()).equals(ghBankResp.getChannelFlow()))
            {
                return true;
            }
            else
            {
                callbackReqCache.put(ghBankResp.getTransCode(), ghBankResp.getChannelFlow());
                return false;
            }
        }
    }
    
    /**
     * 打印Request
     */
    private static String receiveRequest(HttpServletRequest request)
    {
        String requestBody = null;
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            requestBody = in.readLine();
        }
        catch(IOException ioe)
        {
            logger.error("接收回调请求时发生异常",ioe);
        }
        
        logger.info("");
        logger.info("--------------- 外部Request 开始 ---------------");
        logger.info("Request URL:\t"+request.getRequestURL());
        logger.info("Content Length:\t"+request.getContentLength());
        
        Enumeration<String> reqHeads = request.getHeaderNames();
        StringBuffer headerString = new StringBuffer();
        while (reqHeads.hasMoreElements())
        {
            String nextHeader = reqHeads.nextElement();
            headerString.append(nextHeader);
            headerString.append(":");
            headerString.append(request.getHeader(nextHeader));
            headerString.append(", ");
        }
        logger.info("Headers:\t\t"+headerString);
        logger.debug("Body:\t\t"+request.getQueryString());
        logger.debug("Remote Addr:\t"+request.getRemoteAddr());
        logger.info("--------------- 外部Request 结束 ---------------");
        
        return requestBody;
    }
    
    /**
     * 银行主动单笔撤标 (OGW0014T)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ogw0014t")
    public String bankCancelLoan(HttpServletRequest request, HttpServletResponse response)
    {
        String postString = null;
        try
        {
            postString = IOUtils.toString(request.getInputStream(), "utf-8");
            logger.info("收到银行主动单笔撤标 (OGW0014T)回调数据：" + postString);
        }
        catch (IOException ioe)
        {
            logger.error("收到银行主动单笔撤标 (OGW0014T)时发生异常", ioe);
        }

        OGW0014TAdapter adapter0014t = new OGW0014TAdapter();
        OGW0014TReq ogwa0014TReq = (OGW0014TReq) adapter0014t.parseXml(postString);

        OGW0014TResp ogw0014TResp = callBackService.bankCancelLoan(ogwa0014TReq);

        return adapter0014t.getPostString(ogw0014TResp);
    }
    
    /**
     * 银行主动流标（必须）(OGW0015T)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ogw0015t")
    public String bankRepealLoan(HttpServletRequest request, HttpServletResponse response)
    {
        String postString = null;
        try
        {
            postString = IOUtils.toString(request.getInputStream(), "utf-8");
            logger.info("收到银行主动单笔撤标 (OGW0015T)回调数据：" + postString);
        }
        catch (IOException ioe)
        {
            logger.error("收到银行主动单笔撤标 (OGW0015T)时发生异常", ioe);
        }

        OGW0015TAdapter adapter0015t = new OGW0015TAdapter();
        OGW0015TReq ogwa0015TReq = (OGW0015TReq) adapter0015t.parseXml(postString);

        OGW0015TResp ogw0015TResp = callBackService.bankRepealLoan(ogwa0015TReq);

        return adapter0015t.getPostString(ogw0015TResp);
    }
}
