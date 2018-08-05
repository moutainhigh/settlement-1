package com.zcguodian.settlement.unspay;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcguodian.settlement.BaseTest;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

public class TestResponseResult extends BaseTest
{
	private static Logger logger = Logger.getLogger(TestResponseResult.class);

//	@Autowired
//	private IFourElementsPayService fourElementsPayService;
	
	@Test
	public void responseResult(HttpServletRequest request)
	{
		logger.info(request.getParameter("result_code"));
		logger.info(request.getParameter("result_msg"));
		logger.info(request.getParameter("amount"));
		logger.info(request.getParameter("orderId"));
		logger.info(request.getParameter("mac"));
		// fourElementsPayService.fourElementsPay(str);
	}
}
