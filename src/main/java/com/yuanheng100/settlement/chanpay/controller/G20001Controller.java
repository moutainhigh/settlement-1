package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.model.G20001Bean;
import com.yuanheng100.settlement.chanpay.util.U;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/G20001")
public class G20001Controller {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(G20001Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public G20001Bean sendMessage(G20001Bean data)
	{
		data.setReqSn(U.createUUID());
		return data;
	}

}
