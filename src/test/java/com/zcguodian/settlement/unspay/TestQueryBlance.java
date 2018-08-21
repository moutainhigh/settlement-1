package com.zcguodian.settlement.unspay;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.zcguodian.settlement.BaseTest;
import com.zcguodian.settlement.unspay.utils.UnspayZCGDUtil;

/**
 * 中城国典银生宝商户余额查询
 * @author Administrator
 *
 */
public class TestQueryBlance extends BaseTest
{	
	private static Logger logger = Logger.getLogger(TestQueryBlance.class);
	
	@Test
	public void queryBlance()
	{
		Map<String, String> map = UnspayZCGDUtil.queryBalance();
		logger.info("查询的余额信息：" + map.get("balance"));
	}
}
