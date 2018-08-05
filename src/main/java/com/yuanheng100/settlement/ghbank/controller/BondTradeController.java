package com.yuanheng100.settlement.ghbank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IBondTradeService;
import com.yuanheng100.util.ConfigFile;

/**
 * 债权转让controller
 * @author Baisong
 *
 */
@Controller
@RequestMapping("/ghbank/bond")
public class BondTradeController
{

    @Autowired
    private IBondTradeService bondTradeService;
    
    @RequestMapping("/ogw00061")
    private String getSellBondParam(String appIdCode, String oldReqSeqNo, String oldReqNumber, String oldReqName, String accNo, String custName, BigDecimal amount, BigDecimal preIncome, Map<String, Object> map)
    {

        String xmlparam =bondTradeService.getTradeReqXmlParam(AppId.parseString(appIdCode), oldReqSeqNo, Integer.parseInt(oldReqNumber), oldReqName, accNo, custName, amount, preIncome);
        
        map.put("transCode", TransCode.OGW00061);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
