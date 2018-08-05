package com.yuanheng100.settlement.ghbank;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.OperFlag;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00077Resp;
import com.yuanheng100.settlement.ghbank.model.querybalance.OGW00049Resp;
import com.yuanheng100.settlement.ghbank.service.IQueryBalanceService;

public class TestQueryBalanceServiceImpl extends BaseTest
{

    @Autowired
    private IQueryBalanceService ghBankQueryBalanceService;
    
    private static Logger logger = Logger.getLogger(TestQueryBalanceServiceImpl.class);
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testQueryBalance()
    {
        logger.info("\n\n----------testQueryBalance--------------");
        
        //OGW00049Resp resp49 = ghBankQueryBalanceService.queryBalance(AppId.PC, "8970660100000028436", "郭三");
        OGW00049Resp resp49 = ghBankQueryBalanceService.queryBalance(AppId.PC, "8970660100000028527", "郭四");
        //OGW00049Resp resp49 = ghBankQueryBalanceService.queryBalance(AppId.PC, "8970660100000028550", "郭五");
        
        logger.info("单元测试得到的response："+resp49);
    }
    
//    @Test
//    public void testDayEndCheck()
//    {
//        logger.info("\n\n----------testDayEndCheck--------------");
//        
//        OGW00077Resp resp77 = ghBankQueryBalanceService.dayEndCheck(AppId.PC, OperFlag.AMOUNT.getFlag(), new Date());
//        
//        logger.info("单元测试得到的response："+resp77);
//    }
    

}
