package com.yuanheng100.settlement.ghbank;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.IDType;
import com.yuanheng100.settlement.ghbank.consts.LoanStatus;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Resp;
import com.yuanheng100.settlement.ghbank.service.ILoanService;

public class TestLoanServiceImpl extends BaseTest
{

    @Autowired
    private ILoanService loanService;

    private static Logger logger = Logger.getLogger(TestLoanServiceImpl.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

//    @Test
//    public void testCreateLoan()
//    {
//        logger.info("\n\n----------testCreateLoan--------------");
//
//        int loanNo = 1114;
//        BigDecimal borrowAmount = new BigDecimal("1500.00");
//        
//        OGW00051Req ogw00051Req = new OGW00051Req(loanService.getSequenceId());
//        
//        ogw00051Req.setAppId(AppId.PC);
//        ogw00051Req.setLoanNo(loanNo);
//        ogw00051Req.setInvestId(loanNo);
//        ogw00051Req.setInvestObjName("标的名称");
//        ogw00051Req.setInvestObjInfo("标的简介");
//        ogw00051Req.setMinInvestAmt(new BigDecimal("100.00"));
//        ogw00051Req.setMaxInvestAmt(new BigDecimal("10000.00"));
//        ogw00051Req.setInvestObjAmt(borrowAmount); //总标的金额
//        ogw00051Req.setInvestBeginDate(new Date());
//        ogw00051Req.setInvestEndDate(new Date());
//        ogw00051Req.setRepayDate(new Date());
//        ogw00051Req.setYearRate(new BigDecimal("0.12"));
//        ogw00051Req.setInvestRange(365);
//        ogw00051Req.setRatesType("计息方式");
//        ogw00051Req.setRepaysType("还款方式");
//        ogw00051Req.setInvestObjState(LoanStatus.NORMAL.getStatus());
//        ogw00051Req.setBwTotalNum(100);
//        ogw00051Req.setRemark("备注");
//        ogw00051Req.setZrFlag(false);
//        
//        ogw00051Req.setBwAcName("郭三");
//        ogw00051Req.setBwIdType(IDType.ID_CARD.getType());
//        ogw00051Req.setBwIdNo("31010319820101469x");
//        ogw00051Req.setBwAcNo("8970660100000028436");
//        //ogw00051Req.setBwAcBankId("112233");
//        //ogw00051Req.setBwAcBankName("工商银行");
//        ogw00051Req.setBwAmt(borrowAmount);
//        ogw00051Req.setMortgageId("1234");
//        ogw00051Req.setMortgageInfo("抵押品信息");
//        ogw00051Req.setCheckDate(new Date());
//        
//        OGW00051Resp resp51 = loanService.createLoan(ogw00051Req);
//
//        logger.info("单元测试得到的response：" + resp51);
//
//    }

  @Test
  public void testCreateLoan()
  {
      logger.info("\n\n----------testCreateLoan--------------");

      long loanNo = 1115;
      BigDecimal borrowAmount = new BigDecimal("1500.00");
      
      OGW00051Req ogw00051Req = new OGW00051Req(loanService.getSequenceId());
      
      ogw00051Req.setAppId(AppId.PC);
      ogw00051Req.setLoanNo(loanNo);
      ogw00051Req.setInvestId(loanNo);
      ogw00051Req.setInvestObjName("债权转让1");
      ogw00051Req.setInvestObjInfo("标的简介");
      ogw00051Req.setMinInvestAmt(new BigDecimal("100.00"));
      ogw00051Req.setMaxInvestAmt(new BigDecimal("10000.00"));
      ogw00051Req.setInvestObjAmt(borrowAmount); //总标的金额
      ogw00051Req.setInvestBeginDate(new Date());
      ogw00051Req.setInvestEndDate(new Date());
      ogw00051Req.setRepayDate(new Date());
      ogw00051Req.setYearRate(new BigDecimal("0.12"));                                              
      ogw00051Req.setInvestRange(365);
      ogw00051Req.setRatesType("计息方式");
      ogw00051Req.setRepaysType("还款方式");
      ogw00051Req.setInvestObjState(LoanStatus.NORMAL.getStatus());
      ogw00051Req.setBwTotalNum(100);
      ogw00051Req.setRemark("备注");
      ogw00051Req.setZrFlag(true);
      ogw00051Req.setRefLoanNo("1114");
      ogw00051Req.setOldReqSeq("P2P1792017071905200000000291");
      
      ogw00051Req.setBwAcName("郭五");
      ogw00051Req.setBwIdType(IDType.ID_CARD.getType());
      ogw00051Req.setBwIdNo("310103198201014091");
      ogw00051Req.setBwAcNo("8970660100000028550");
      //ogw00051Req.setBwAcBankId("112233");
      //ogw00051Req.setBwAcBankName("工商银行");
      ogw00051Req.setBwAmt(borrowAmount);
      ogw00051Req.setMortgageId("1234");
      ogw00051Req.setMortgageInfo("抵押品信息");
      ogw00051Req.setCheckDate(new Date());
      
      OGW00051Resp resp51 = loanService.createLoan(ogw00051Req);

      logger.info("单元测试得到的response：" + resp51);

  }
    

//    @Test
//    public void testCancelLoan()
//    {
//        logger.info("\n\n----------testCancelLoan--------------");
//
//        OGW00063Resp resp63 = loanService.cancelLoan(AppId.PC, "loanNo111", "测试流标原因");
//
//        logger.info("单元测试得到的response：" + resp63);
//    }
//
//    @Test
//    public void testQueryCancelResult()
//    {
//    }
//

//    @Test
//    public void testReleaseLoan()
//    {
//        
//        logger.info("\n\n----------testReleaseLoan--------------");
//        
//        int loanNo = 1114;
//        String bwAcNo = "8970660100000028436";
//        String bwAcName = "郭三";
//        
//        loanService.releaseLoan(AppId.PC, loanNo, bwAcName, bwAcNo, BigDecimal.ZERO, BigDecimal.ZERO);
//        
//    }

    
//        @Test
//        public void testQueryReleaseLoanResult()
//        {
//            logger.info("\n\n----------testQueryReleaseLoanResult--------------");
//            
//            String oldReqSeqNo = "P2P1792017071806500000000045";
//            int loanNo = 1112;
//            String oldTTJnl = "";
//            
//            loanService.queryReleaseLoanResult(AppId.PC, oldReqSeqNo, loanNo, oldTTJnl);
//        }

//    @Test
//    public void testDailyTradeCheck()
//    {
//    }

}
