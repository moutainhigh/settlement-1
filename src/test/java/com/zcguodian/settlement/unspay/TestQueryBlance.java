package com.zcguodian.settlement.unspay;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcguodian.settlement.BaseTest;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

/**
 * 中城国典银生宝商户余额查询
 * @author Administrator
 *
 */
public class TestQueryBlance extends BaseTest
{	
	private static Logger logger = Logger.getLogger(TestQueryBlance.class);
	
	@Autowired
	private IFourElementsPayService fourElementsPayService;
	
	@Test
	public void queryBlance()
	{
		String result = fourElementsPayService.queryBlance();
		logger.info("查询的余额信息：" + result);
	}
}
