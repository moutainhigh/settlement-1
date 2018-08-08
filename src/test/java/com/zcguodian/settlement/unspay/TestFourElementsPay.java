package com.zcguodian.settlement.unspay;

import java.math.BigDecimal;
import java.util.Date;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

/**
 * 中城国典银生宝四要素实时代付测试
 * 
 * @author Administrator
 *
 */
public class TestFourElementsPay extends BaseTest
{
	private static Logger logger = Logger.getLogger(TestFourElementsPay.class);

	@Autowired
	private IFourElementsPayService fourElementsPayService;

	@Test
	public void testFourElementsPay()
	{
		UnspayFourElementsPay unspayFourElementsPay = new UnspayFourElementsPay();
//		unspayFourElementsPay.setAccountId(1120180709153050001L);
		unspayFourElementsPay.setName("柳健青");
		unspayFourElementsPay.setCardNo("6212260302018048181");
		unspayFourElementsPay.setOrderId(Integer.valueOf((int) new Date().getTime()));
		unspayFourElementsPay.setPurpose("测试");
		unspayFourElementsPay.setAmount(new BigDecimal(4));
		unspayFourElementsPay.setIdCardNo("370687198908081576");
		unspayFourElementsPay.setSummary("测试");
		unspayFourElementsPay.setPhoneNo(15154258821L);
//		unspayFourElementsPay.setResponseUrl("http://w4trrm.natappfree.cc/fund-settlement-impl/responseResult");
		logger.info(unspayFourElementsPay);

//		fourElementsPayService.fourElementsPay(unspayFourElementsPay);
	}

}