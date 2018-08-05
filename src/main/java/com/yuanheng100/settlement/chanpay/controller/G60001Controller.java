package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationRequestService;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/chanpay/g60001")
public class G60001Controller {

    public static final Logger LOG = Logger.getLogger(G60001Controller.class);

    @Autowired
    private IAsynAuthenticationRequestService asynAuthenticationRequestService;

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

        Page<Map<String,Object>> page = new Page<Map<String,Object>>(10);
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

        asynAuthenticationRequestService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);

        return "chanpay/authenticate";
    }

}
