package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.service.IInvestService;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 投标冻结页面相关
 */
@Controller
@RequestMapping("/payease/trs001002")
public class TRS001002Controller
{
    private static Logger logger = Logger.getLogger(TRS001002Controller.class);
    @Autowired
    private IInvestService investService;

    @RequestMapping("/list")
    public String deductList(@RequestParam(value = "lender", required = false) String lender, @RequestParam(value = "lenderId", required = false) String lenderId,
                             @RequestParam(value = "lenderName", required = false) String lenderName,@RequestParam(value = "contractNum", required = false) String contractNum,
                             @RequestParam(value = "returnCode", required = false) String returnCode,
                             @RequestParam(value = "returnStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnStartTime,
                             @RequestParam(value = "returnEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnEndTime,
                             @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                             Map<String, Object> map)
    {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("lender", lender);
        searchConditions.put("lenderId", lenderId);
        searchConditions.put("lenderName", lenderName);
        searchConditions.put("contractNum", contractNum);
        searchConditions.put("returnStartTime", returnStartTime);
        searchConditions.put("returnEndTime", returnEndTime);
        searchConditions.put("returnCode", returnCode);

        investService.getListPage(searchConditions, page);

        map.put("page", page);
        map.putAll(searchConditions);

        return "payease/invest";
    }
}
