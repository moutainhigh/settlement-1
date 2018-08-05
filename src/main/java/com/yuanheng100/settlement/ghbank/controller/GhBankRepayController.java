package com.yuanheng100.settlement.ghbank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IRepayService;
import com.yuanheng100.util.ConfigFile;

/**
 * 还款相关Controller
 * 
 * @author Baisong
 *
 */
@Controller
@RequestMapping("/ghbank/repay")
public class GhBankRepayController
{

    @Autowired
    private IRepayService repayService;
    
    @RequestMapping("/ogw00067")
    private String repay(String appIdCode, String oldReqSeqNo, long loanNo, String bwAcName, String bwAcNo, BigDecimal amount, String remark, BigDecimal feeAmt, Map<String, Object> map)
    {
        
        DFFlag dfFlag = DFFlag.NORMAL_PREPAYMENT;
                
        String xmlparam = repayService.getRepayXmlparam(AppId.parseString(appIdCode), dfFlag, oldReqSeqNo, loanNo, bwAcName, bwAcNo, amount, remark, feeAmt);
        
        map.put("transCode", TransCode.OGW00067);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
