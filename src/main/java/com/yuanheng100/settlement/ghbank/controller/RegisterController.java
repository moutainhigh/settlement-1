package com.yuanheng100.settlement.ghbank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TransCode;
import com.yuanheng100.settlement.ghbank.service.IRegisterService;
import com.yuanheng100.util.ConfigFile;

@Controller
@RequestMapping("/ghbank/register")
public class RegisterController
{
    
    @Autowired
    private IRegisterService registerService;

    @RequestMapping("/ogw00042")
    public String register(String acName, String idNo, Long mobile, Map<String, Object> map)
    {
        String xmlparam = registerService.getRegisterParam(AppId.PC, acName, idNo, mobile);
        
        map.put("transCode", TransCode.OGW00042);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
    
    @RequestMapping("/ogw00044")
    public String register(String appIdCode, String acNo,Map<String, Object> map)
    {
        String xmlparam = registerService.getBindBankCardXML(AppId.parseString(appIdCode), acNo);
        
        map.put("transCode", TransCode.OGW00044);
        map.put("xmlparam", xmlparam);
        map.put("url", ConfigFile.getProperty("ghbank.url"));
        
        return "ghbank/test1/testForm";
    }
}
