package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.service.INotifyTradeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/7.
 * 用于畅捷交易状态通知
 */
@RequestMapping("/chanpay/q20008")
@RestController
public class Q20008Controller {

    private static final Logger LOG = Logger.getLogger(Q20008Controller.class);
    @Autowired
    private INotifyTradeService notifyTradeService;

    @RequestMapping("/notifyTrade")
    public String notifyTrade(HttpServletRequest request){
        //获取所有参数，并进行格式转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        for (Map.Entry<String,String[]> entry: parameterMap.entrySet()) {
            stringStringHashMap.put(entry.getKey(),entry.getValue()[0]);
        }
        boolean b = notifyTradeService.notifyTrade(stringStringHashMap);
        return "success";
    }
}
