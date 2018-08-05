package com.yuanheng100.settlement.ghbank;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanheng100.settlement.ghbank.adapter.OGW0014TTestAdapter;
import com.yuanheng100.settlement.ghbank.model.callback.OGW0014TTestReq;

public class TestCallBackServiceImpl
{

    private static Logger logger = LoggerFactory.getLogger(TestCallBackServiceImpl.class);
    

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testBankCancelLoan()
    {
        logger.info("\n\n----------testBankCancelLoan--------------");
        
        OGW0014TTestReq req14t_test = new OGW0014TTestReq(1);
        
        req14t_test.setLoanNo((long)112233);
        req14t_test.setOldReqSeqNo("223344");
        req14t_test.setAcNo("3344556");
        req14t_test.setAcName("柏松");
        req14t_test.setCancelReason("就是测试一下");
        
        
        
        OGW0014TTestAdapter adpater14t_test = new OGW0014TTestAdapter();
        adpater14t_test.postAndReceive(req14t_test);
    }

}
