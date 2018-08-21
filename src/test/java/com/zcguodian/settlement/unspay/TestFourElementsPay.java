package com.zcguodian.settlement.unspay;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

/**
 * 中城国典银生宝四要素实时代付测试
 * 
 * @author Administrator
 *
 */
public class TestFourElementsPay extends BaseTest
{
//	private static Logger logger = Logger.getLogger(TestFourElementsPay.class);

	@Autowired
	private IFourElementsPayService fourElementsPayService;

	@Test
	public void testFourElementsPay()
	{
		String orderIds = "1,2,3,4";
		Short verifyStatus = new Short("2");
		
		fourElementsPayService.agreePay(orderIds, verifyStatus, 111002);
	}

}