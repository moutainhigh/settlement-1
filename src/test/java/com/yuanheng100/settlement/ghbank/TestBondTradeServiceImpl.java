package com.yuanheng100.settlement.ghbank;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00062Resp;
import com.yuanheng100.settlement.ghbank.service.IBondTradeService;

public class TestBondTradeServiceImpl extends BaseTest
{

    @Autowired
    private IBondTradeService bondTradeService;
    
    private static Logger logger = Logger.getLogger(TestBondTradeServiceImpl.class);
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testQueryTransferResult()
    {
        logger.info("\n\n----------testQueryTransferResult--------------");
        
        OGW00062Resp resp62 = bondTradeService.queryTransferResult(AppId.PC, "112233");
        
        logger.info("单元测试得到的response："+resp62);
    }

}
