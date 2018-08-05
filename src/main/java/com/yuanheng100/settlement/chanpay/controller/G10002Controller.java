package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.service.IPayService;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/chanpay/g10002")
public class G10002Controller {

	public static final Logger LOG = Logger.getLogger(G10002Controller.class);

	@Autowired
	private IPayService payService;

	@ResponseBody
	@RequestMapping("/pay")
	public String pay(){
		G10001Bean g10001Bean = new G10001Bean();
		g10001Bean.setBankGeneralName("光大银行");
		g10001Bean.setAccountNo("6226630202347414");
		g10001Bean.setAccountName("钱家亮");
		g10001Bean.setBankName("光大银行光华路支行");
		g10001Bean.setAmount(new BigDecimal(0.01));
		try {
			payService.pay(g10001Bean);
			return "支付请求为:"+g10001Bean.getRetCode();
		} catch (IllegalPlatformAugumentException e) {
			return "支付请求失败:"+e.getMessage();
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String payList(@RequestParam(value = "reqSn", required = false) String reqSn,
								   @RequestParam(value = "accountName", required = false) String accountName,
								   @RequestParam(value = "accountNo", required = false) String accountNo,
								   @RequestParam(value = "tradeStatus", required = false) String tradeStatus,
								   @RequestParam(value = "timestampStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date timestampStartDate,
								   @RequestParam(value = "timestampEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date timestampEndDate,
								   @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
								   Map<String, Object> map) {

		Page<Map<String,Object>> page = new Page<Map<String,Object>>(10);
		page.setPageNo(pageNo);
		HashMap<String, Object> searchConditions = new HashMap<String, Object>();
		searchConditions.put("reqSn", reqSn);
		searchConditions.put("tradeStatus", tradeStatus);
		searchConditions.put("accountNo", accountNo);
		searchConditions.put("accountName", accountName);
		searchConditions.put("timestampStartDate", timestampStartDate);
		searchConditions.put("timestampEndDate", timestampEndDate);
		searchConditions.put("pageNo", pageNo);

		payService.getListPage(searchConditions, page);
		map.put("page", page);
		map.putAll(searchConditions);

		return "chanpay/pay";
	}
}
