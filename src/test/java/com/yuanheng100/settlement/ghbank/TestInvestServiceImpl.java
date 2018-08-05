package com.yuanheng100.settlement.ghbank;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00053Resp;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00055Resp;
import com.yuanheng100.settlement.ghbank.service.IInvestService;

public class TestInvestServiceImpl extends BaseTest
{

    @Autowired
    private IInvestService investService;

    private static Logger logger = Logger.getLogger(TestLoanServiceImpl.class);
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testQueryInvestResult()
    {
        logger.info("\n\n----------testQueryInvestResult--------------");
        
        OGW00053Resp resp53 = investService.queryInvestResult(AppId.PC, "P2P1792017071805200000001256");
        
        logger.info("单元测试得到的response："+resp53);
                
    }
//
//    @Test
//    public void testInvestBonus()
//    {
//    }
//
//    @Test
//    public void testQueryInvestBonus()
//    {
//        logger.info("\n\n----------testQueryInvestResult--------------");
//        
//        OGW00055Resp resp55 = investService.queryInvestBonus(AppId.PC, "1111");
//        
//        logger.info("单元测试得到的response："+resp55);
//    }

}
