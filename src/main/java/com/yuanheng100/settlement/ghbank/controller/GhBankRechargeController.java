package com.yuanheng100.settlement.ghbank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IRechargeService;
import com.yuanheng100.util.ConfigFile;

/**
 * 充值相关的controller
 * @author Baisong
 */
@Controller
@RequestMapping("/ghbank/recharge")
public class GhBankRechargeController
{

    @Autowired
    private IRechargeService rechargeService;
    
    @RequestMapping("/ogw00045")
    private String getRechargeParam(String appIdCode, String acNo, String acName, BigDecimal amount, String remark, Map<String, Object> map)
    {
        String xmlparam = rechargeService.getRechargeXmlParam(AppId.parseString(appIdCode), acNo, acName, amount, remark);
        
        map.put("transCode", TransCode.OGW00045);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
