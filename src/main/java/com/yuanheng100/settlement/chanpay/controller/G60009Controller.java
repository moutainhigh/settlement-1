package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.chanpay.service.ISyncAuthenticationService;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/10.
 */
@Controller
@RequestMapping("/chanpay/g60009")
public class G60009Controller {

    private static final Logger LOG = Logger.getLogger(G60009Controller.class);
    @Autowired
    private ISyncAuthenticationService syncAuthenticationService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String authenticateList(@RequestParam(value = "reqSn", required = false) String reqSn,
                               @RequestParam(value = "bankGeneralName", required = false) String bankGeneralName,
                               @RequestParam(value = "bankCode", required = false) Integer bankCode,
                               @RequestParam(value = "accountNo", required = false) String accountNo,
                               @RequestParam(value = "accountName", required = false) String accountName,
                               @RequestParam(value = "retCode", required = false) String retCode,
                               @RequestParam(value = "timestampStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date timestampStartDate,
                               @RequestParam(value = "timestampEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date timestampEndDate,
                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                               Map<String, Object> map) {

        Page<G60009Bean> page = new Page<G60009Bean>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("reqSn", reqSn);
        searchConditions.put("bankGeneralName", bankGeneralName);
        searchConditions.put("bankCode", bankCode);
        searchConditions.put("accountNo", accountNo);
        searchConditions.put("accountName", accountName);
        searchConditions.put("retCode", retCode);
        searchConditions.put("timestampStartDate", timestampStartDate);
        searchConditions.put("timestampEndDate", timestampEndDate);
        searchConditions.put("pageNo", pageNo);

        syncAuthenticationService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);

        return "chanpay/authenticate";
    }
}
