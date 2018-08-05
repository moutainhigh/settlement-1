package com.yuanheng100.settlement.ghbank;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00046Resp;
import com.yuanheng100.settlement.ghbank.service.IRechargeService;

public class TestRechargeServiceImpl extends BaseTest
{

    @Autowired
    private IRechargeService ghbankRechargeService;

    private static Logger logger = Logger.getLogger(TestRechargeServiceImpl.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testQueryRechargeResult()
    {
        logger.info("\n\n----------testQueryRechargeResult--------------");

        OGW00046Resp resp46 = ghbankRechargeService.queryRechargeResult(AppId.PC, "P2P1792017071704500000000206");

        System.out.println(resp46);
    }

}
