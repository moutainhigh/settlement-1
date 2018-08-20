package com.zcguodian.settlement.unspay;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcguodian.settlement.BaseTest;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

public class TestQueryOrderStatus extends BaseTest
{
	private static Logger logger = Logger.getLogger(TestQueryOrderStatus.class);

	@Autowired
	private IFourElementsPayService fourElementsPayService;
	
	@Test
	public void queryOrderStatus()
	{
//		fourElementsPayService.queryOrderStatus(1532334760311L);
	}
}
