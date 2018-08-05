package com.yuanheng100.settlement.ghbank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IWithdrawService;
import com.yuanheng100.settlement.ghbank.service.impl.WithdrawServiceImpl;
import com.yuanheng100.util.ConfigFile;


/**
 * 提现相关controller
 * @author Baisong
 */
@Controller
@RequestMapping("/ghbank/withdraw")
public class WithdrawController
{

    private static Logger logger = Logger.getLogger(WithdrawController.class);
    
    @Autowired
    private IWithdrawService withdrawService;
    
    @RequestMapping("/ogw00047")
    private String getWithdrawParam(String appIdCode, String acNo, String acName, BigDecimal amount, String remark, Map<String, Object> map)
    {
        logger.info("controller的getWithdrawParam方法被唤起");
        String xmlparam = withdrawService.getWithdrawXmlparam(AppId.parseString(appIdCode), acNo, acName, amount, remark);
        
        map.put("transCode", TransCode.OGW00047);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
