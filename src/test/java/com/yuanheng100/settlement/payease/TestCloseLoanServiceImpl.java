package com.yuanheng100.settlement.payease;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Req;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Req;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.ICloseLoanService;
import com.yuanheng100.settlement.payease.service.IInvestService;
import com.yuanheng100.settlement.payease.service.IQueryService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;

/**
 * TRS001003 - 标的成功通知
 * @author Bai Song
 *
 */
public class TestCloseLoanServiceImpl extends BaseTest
{

    private static Logger logger = Logger.getLogger(TestCloseLoanServiceImpl.class);
    
    @Autowired
    ICloseLoanService closeLoanServiceImpl;
    
    @Autowired
    IQueryService queryService;
    
    @Autowired
    ISequenceIdService sequenceService;
    
    @Autowired
    IInvestService investService;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testWholeCloseLoan()
    {
        String sequenceId = String.valueOf(sequenceService.getSequenceId());
        try
        {
            testQueryBorrowerAccount();
            test_invest_1();
            test_invest_2();
            testCloseLoan(sequenceId);
            testQueryCloseLoan(sequenceId);
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
                logger.error(e);
            }
            testQueryBorrowerAccount();
        }
        catch (IllegalPlatformAugumentException ipae)
        {
            logger.error("单元测试时出现异常", ipae);
        }

    }
    

    public void test_invest_1() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始test_invest_1()--------------");
        
        TRS001002Req investFreezeReq = new TRS001002Req();
        String sequenceId = String.valueOf(sequenceService.getSequenceId());
        investFreezeReq.setSerlNum(sequenceId);
        investFreezeReq.setAuthId(sequenceId);
        
        investFreezeReq.setBorrower(BORROWER_ID);
        investFreezeReq.setBorrowerId(BORROWER_ID_CARD);
        investFreezeReq.setBorrowerName(BORROWER_NAME);
        
        investFreezeReq.setContractNum(LOAN_ID);
        
        investFreezeReq.setCertNum(sequenceId);
        investFreezeReq.setLoanAmount(BAISONG_INVEST_AMOUNT.toString());
        
        investFreezeReq.setLender(BAISONG_ID);
        
        TRS001002Resp response = investService.investFreeze(investFreezeReq);
        logger.debug("测试代码收到: "+response);

    }
    
    public void test_invest_2() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始test_invest_2()--------------");
        
        TRS001002Req investFreezeReq = new TRS001002Req();
        String sequenceId = String.valueOf(sequenceService.getSequenceId());
        investFreezeReq.setSerlNum(sequenceId);
        investFreezeReq.setAuthId(sequenceId);
        
        investFreezeReq.setBorrower(BORROWER_ID);
        investFreezeReq.setBorrowerId(BORROWER_ID_CARD);
        investFreezeReq.setBorrowerName(BORROWER_NAME);
        
        investFreezeReq.setContractNum(LOAN_ID);
        
        investFreezeReq.setCertNum(sequenceId);
        investFreezeReq.setLoanAmount(QIANJIALIANG_INVEST_AMOUNT.toString());
        
        investFreezeReq.setLender(QIANJIALIANG_ID);
        
        TRS001002Resp response = investService.investFreeze(investFreezeReq);
        logger.debug("测试代码收到: "+response);

    }
    
    public void testCloseLoan(String sequenceId) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testCloseLoan()--------------");
        
        TRS001003Req closeLoanReq = new TRS001003Req();
        
        closeLoanReq.setSerlNum(sequenceId);
        closeLoanReq.setBorrower(BORROWER_ID);
        closeLoanReq.setContractNum(LOAN_ID);
        
        closeLoanReq.setTotalAmount(BAISONG_INVEST_AMOUNT.add(QIANJIALIANG_INVEST_AMOUNT).toString());
        closeLoanReq.setTotalNum(String.valueOf(2));
        
        TRS001003Resp response = closeLoanServiceImpl.closeLoan(closeLoanReq);
        logger.debug(response);
    }

    public void testQueryCloseLoan(String sequenceId) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryCloseLoan()--------------");
        
        TRS001003Req queryCloseLoanReq = new TRS001003Req();
        queryCloseLoanReq.setBorrower(BORROWER_ID);
        queryCloseLoanReq.setContractNum(LOAN_ID);
        
        TRS001003Resp response = closeLoanServiceImpl.queryCloseLoan(queryCloseLoanReq);
        
        logger.debug(response);
    }

    
    public void testQueryBorrowerAccount()
    {
        logger.info("\n\n----------开始查询借款人账户余额--------------");
        
        TRS001010Req queryReq = new TRS001010Req();
        queryReq.setUser(BORROWER_ID);
        TRS001010Resp queryResp = queryService.queryInvestAccount(queryReq);
        System.out.println("投资类账户查询结果"+queryResp);
        
        queryResp = queryService.queryLiabilityAccount(queryReq);
        System.out.println("负债类账户查询结果"+queryResp);
    }
    
}
