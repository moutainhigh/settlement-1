package com.zcguodian.settlement.unspay;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.zcguodian.settlement.BaseTest;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

public class TestQueryOrderStatus extends BaseTest
{
	private static Logger logger = Logger.getLogger(TestQueryOrderStatus.class);

	@Autowired
	private IFourElementsPayService fourElementsPayService;
	
	@Test
	public void queryOrderStatus()
	{
		int orderId = 1;
		UnspayFourElementsPay payByOrderId = fourElementsPayService.getPayByOrderId(orderId);
		logger.info("实时代付对象:" + JSON.toJSONString(payByOrderId));
		fourElementsPayService.queryOrderStatusRemote(payByOrderId);
	}
}
