package com.yuanheng100.settlement.ghbank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req.RepayItem;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Resp;
import com.yuanheng100.settlement.ghbank.service.ILoanService;
import com.yuanheng100.settlement.ghbank.service.IRepayService;

public class TestRepayServiceImpl extends BaseTest
{

    @Autowired
    private IRepayService ghbankRepayService;
    
    @Autowired
    private ILoanService loanService;

    private static Logger logger = Logger.getLogger(TestRepayServiceImpl.class);
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

//    @Test
//    public void testGetRepayXmlparam()
//    {
//        
//    }
    
//        @Test
//        public void testQueryRepayResult()
//        {
//            logger.info("\n\n----------testQueryRepayResult--------------");
//            
//            ghbankRepayService.queryRepayResult(AppId.PC, "P2P1792017071906700000000283");
//        }

//    @Test
//    public void testPayForBorrower()
//    {
//        logger.info("\n\n----------testPayForBorrower--------------");
//        
//        OGW00073Resp resp73 = ghbankRepayService.payForBorrower(AppId.PC, 111, "bwAcName", "222", new BigDecimal("12.34"), new BigDecimal("23.45"));
//        logger.info(resp73);
//    }

    @Test
    public void testRepayInterestDetail()
    {
        logger.info("\n\n----------testRepayInterestDetail()--------------");
        
        String oldReqSeqNo = "P2P1792017071906700000000283";
        
        short dfFlag = DFFlag.NORMAL_PREPAYMENT.getFlag();
        int loanNo = 1112;
        String bwAcName = "郭三";
        String bwAcNo = "8970660100000028436";
        
        
        List<OGW00074Req.RepayItem> repayList = new ArrayList<OGW00074Req.RepayItem>();
        
        OGW00074Req.RepayItem item1 = new RepayItem();
        item1.setOldReqSeqNo(oldReqSeqNo);
        item1.setSubSeqNo(String.valueOf(loanService.getSequenceId()));
        item1.setAcNo("8970660100000028527");
        item1.setAcName("郭四");
        item1.setAmount(new BigDecimal("1050.00"));
        
        repayList.add(item1);
        
        OGW00074Resp resp74 = ghbankRepayService.repayInterestDetail(AppId.PC, oldReqSeqNo, dfFlag, loanNo, bwAcName, bwAcNo,  repayList);
        
        logger.info("resp74="+resp74);
    }

//    @Test
//    public void testQueryRepayInterestResult()
//    {
//        logger.info("\n\n----------testQueryRepayInterestResult()--------------");
//        ghbankRepayService.queryRepayInterestResult(AppId.PC, "1111", 1133, "3344");
//    }

//    @Test
//    public void testInvestorBonus()
//    {
//    }

}
