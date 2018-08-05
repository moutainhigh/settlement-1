package com.yuanheng100.settlement.ghbank;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00048Resp;
import com.yuanheng100.settlement.ghbank.service.IWithdrawService;

public class TestWithdrawServiceImpl extends BaseTest
{
    
    @Autowired
    private IWithdrawService withdrawService;
    
    private static Logger logger = Logger.getLogger(TestWithdrawServiceImpl.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testQueryWithdrawResult()
    {
        logger.info("\n\n----------testQueryWithdrawResult--------------");
        
        OGW00048Resp resp48 = withdrawService.queryWithdrawResult(AppId.PC, new Date(), "P2P1792017072004700000000316");
        
        logger.info("单元测试得到的response："+resp48);
    }

}
