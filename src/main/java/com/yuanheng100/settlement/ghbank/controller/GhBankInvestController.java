package com.yuanheng100.settlement.ghbank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IInvestService;
import com.yuanheng100.util.ConfigFile;

/**
 * 投资相关Controller
 * @author Baisong
 *
 */
@Controller
@RequestMapping("/ghbank/invest")
public class GhBankInvestController
{

    @Autowired
    private IInvestService investService;
    
    @RequestMapping("/ogw00052")
    private String getInvestParam(String appIdCode, String loanNo, String acNo, String acName, BigDecimal amount, String remark, Map<String, Object> map)
    {
              
        String xmlparam = investService.getInvestXmlParam(AppId.parseString(appIdCode), Integer.parseInt(loanNo), acNo, acName, amount, remark);
        
        map.put("transCode", TransCode.OGW00052);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
