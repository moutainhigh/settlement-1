package com.yuanheng100.settlement.payease.controller;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.callback.ICloseLoanCallbackListener;
import com.yuanheng100.settlement.payease.callback.IWithdrawCallbackListener;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;
import com.yuanheng100.settlement.payease.service.ICloseLoanService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Controller
@RequestMapping("/payease/trs001003")
public class TRS001003Controller
{
    private static Logger logger = Logger.getLogger(TRS001003Controller.class);

    @Autowired
    private ICloseLoanService closeLoanService;

    @ResponseBody
    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    public String closeLoanNotice(HttpServletResponse response, HttpServletRequest request)
    {
        logger.info("开始接收标的成功通知回调数据");
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String result = in.readLine();
            String showString = EncDecUtil.dec(result);
            TRS001003Resp trs001003Resp = JSON.parseObject(showString, TRS001003Resp.class);
            trs001003Resp.setReturnTime(new Date());
            logger.info("更新标的成功通知结果: " + showString);
            closeLoanService.updateResult(trs001003Resp);
            //对P2P回调
            ICloseLoanCallbackListener iCloseLoanCallbackListener = CallbackListenerContainer.removeCloseLoanCallbackListener(trs001003Resp.getSerlNum());
            if (iCloseLoanCallbackListener != null)
            {
                iCloseLoanCallbackListener.closeLoan(trs001003Resp);
            }
            return "成功";
        } catch (IOException ioe)
        {
            logger.error("接收标的成功通知时出现异常", ioe);
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error("对P2P回调时出现异常", e);
        }

        return "成功";
    }
}
