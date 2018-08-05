package com.yuanheng100.settlement.ghbank;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TrsType;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00043Resp;
import com.yuanheng100.settlement.ghbank.service.IRegisterService;

/**
 * Created by jlqian on 2017/4/17.
 */
public class TestRegisterServiceImpl extends BaseTest
{
    @Autowired
    private IRegisterService registerService;
    
    private static Logger logger = Logger.getLogger(TestRegisterServiceImpl.class);

//    @Test
//    public void testGetVerificationCode()
//    {
//        logger.info("\n\n----------testGetVerificationCode--------------");
//
//        OGW00041Resp ogw00041Resp = registerService.getVerificationCode(AppId.PC, TrsType.DEFAULT, null, 13521182597L);
//
//        System.out.println("ogw00041Resp="+ogw00041Resp);
//    }
//    
    @Test
    public void testQueryOpenAccountResult()
    {
        logger.info("\n\n----------testQueryOpenAccountResult--------------");
        
        OGW00043Resp resp43 = registerService.queryOpenAccountResult(AppId.PC, "P2P1792017072004200000000305");
        
        logger.info(resp43);
        
    }

}
