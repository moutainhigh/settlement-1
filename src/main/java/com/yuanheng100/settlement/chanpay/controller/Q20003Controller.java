package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import com.yuanheng100.settlement.chanpay.service.IRechargeService;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jlqian on 2016/9/8.
 */
@RequestMapping("/chanpay/q20003")
@Controller
public class Q20003Controller {

    private static final Logger LOG = Logger.getLogger(Q20003Controller.class);

    @Autowired
    private IRechargeService rechargeService;

    /**
     * 获取支付链接
     * @return
     */
    @ResponseBody
    @RequestMapping("/rechargeUrl")
    public String getPayUrl(){
        Q20003Bean q20003Bean = new Q20003Bean();
        q20003Bean.setTradeAmount(new BigDecimal(1024.00));
        q20003Bean.setOutTradeNo((UUID.randomUUID().toString()).replace("-", ""));
        q20003Bean.setReturnUrl("http://117.114.139.34:2084/settlement/");
        q20003Bean.setNotifyUrl("http://117.114.139.34:2084/settlement/chanpay/q20008/notifyTrade");
        try {
            return "<a href="+rechargeService.getRechargeUrl(q20003Bean)+">支付</a>";
        } catch (IllegalPlatformAugumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 充值记录页面
     * @param outTradeNo
     * @param tradeStatus
     * @param orderTimeStartDate
     * @param orderTimeEndDate
     * @param pageNo
     * @param map
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String rechargeList(@RequestParam(value = "outTradeNo", required = false) String outTradeNo,
                                  @RequestParam(value = "tradeStatus", required = false) String tradeStatus,
                                  @RequestParam(value = "orderTimeStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderTimeStartDate,
                                  @RequestParam(value = "orderTimeEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderTimeEndDate,
                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  Map<String, Object> map) {

        Page<Map<String,Object>> page = new Page<Map<String,Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("outTradeNo", outTradeNo);
        searchConditions.put("tradeStatus", tradeStatus);
        searchConditions.put("orderTimeStartDate", orderTimeStartDate);
        searchConditions.put("orderTimeEndDate", orderTimeEndDate);
        searchConditions.put("pageNo", pageNo);

        rechargeService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);

        return "chanpay/recharge";
    }

}
