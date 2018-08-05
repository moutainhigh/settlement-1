package com.yuanheng100.settlement.common.controller;

import com.yuanheng100.settlement.common.model.system.SysBank;
import com.yuanheng100.settlement.common.service.ISysBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jlqian on 2016/8/18.
 */
@RequestMapping("/resource")
@RestController
public class ResourceController
{
    @Autowired
    private ISysBankService sysBankService;

    @RequestMapping("/bank")
    public HashMap<String, HashMap<String, Object>> bankInfo()
    {
        //存放Bank信息
        List<SysBank> bankList = sysBankService.getAllBanks();
        HashMap<String, HashMap<String, Object>> banks = new HashMap<String, HashMap<String, Object>>();
        for (SysBank bankModel : bankList)
        {
            HashMap<String, Object> bank = new HashMap<String, Object>();
            Short code = bankModel.getCode();
            String bankName = bankModel.getFullName();
            String shortBankName = bankModel.getShortName();
            bank.put("code", code);
            bank.put("bankName", bankName);
            bank.put("shortBankName", shortBankName);
            banks.put(code.toString(), bank);
        }
        return banks;
    }
}
