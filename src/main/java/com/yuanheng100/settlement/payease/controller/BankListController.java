package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.settlement.common.conts.PageVo;
import com.yuanheng100.settlement.common.model.system.SysBank;
import com.yuanheng100.settlement.common.service.ISysBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 银行信息页面
 *
 * Created by wangguangshuo on 2017/1/6.
 */
@Controller
@RequestMapping("/payease")
public class BankListController
{

    @Autowired
    private ISysBankService sysBankService;

    /**
     * 查询银行信息（限额）
     * @param bankCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBankDesc")
    public SysBank getBankDesc(@RequestParam String bankCode)
    {
        return sysBankService.getBankByCode(Short.parseShort(bankCode));
    }

    /**
     * 银行信息列表
     * @param pageVo
     * @return
     */
    @RequestMapping("/bankList")
    public String bankList(PageVo<Map<String,Object>> pageVo)
    {
        List<SysBank> allBanks = sysBankService.getAllBanks();
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for (SysBank bank : allBanks)
        {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("code",bank.getCode());
            map.put("shortName",bank.getShortName());
            map.put("fullName",bank.getFullName());
            map.put("ps",getNum(String.valueOf(Double.parseDouble(String.valueOf(bank.getPayeaseDeductSingleLimit())) / 10000)));
            map.put("pd",getNum(String.valueOf(Double.parseDouble(String.valueOf(bank.getPayeaseDeductDailyLimit())) / 10000)));
            map.put("us",getNum(String.valueOf(Double.parseDouble(String.valueOf(bank.getUnspayDeductSingleLimit())) / 10000)));
            map.put("ud",getNum(String.valueOf(Double.parseDouble(String.valueOf(bank.getUnspayDeductDailyLimit())) / 10000)));
            list.add(map);
        }
        pageVo.setVoList(list);
        return "payease/bankList";
    }

    private static String getNum(String ps)
    {
        while (ps.endsWith("0")){
            ps = ps.substring(0,ps.length() - 1);
        }
        while (ps.endsWith(".")){
            ps = ps.substring(0,ps.length() - 1);
        }
        return ps;
    }
}
