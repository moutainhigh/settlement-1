package com.yuanheng100.settlement.payease;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Req;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IInvestService;
import com.yuanheng100.settlement.payease.service.IQueryService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 投标冻结/解冻服务接口 TRS001002
 *
 * @author Bai Song
 */
public class TestInvestServiceImpl extends BaseTest
{

    private static Logger logger = Logger.getLogger(TestInvestServiceImpl.class);

    @Autowired
    IInvestService investService;

    @Autowired
    IQueryService queryService;

    @Autowired
    ISequenceIdService sequenceService;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testWholeProcess()
    {
        try
        {
            test_invest_1();
            test_invest_2();
            testQueryService();
        } catch (IllegalPlatformAugumentException ipae)
        {
            logger.error("单元测试时出现异常", ipae);
        }

    }

    @Test
    public void testUnfreeze()
    {
        unfreeze("4","30001","4","4",new BigDecimal("100.00"),"30011");
        unfreeze("5","30001","4","5",new BigDecimal("100.00"),"30012");
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
        logger.debug("测试代码收到: " + response);

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
        logger.debug("测试代码收到: " + response);

    }


    public void testQueryService() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryService()--------------");

        TRS001010Req queryReq = new TRS001010Req();
        queryReq.setUser(BAISONG_ID);
        TRS001010Resp queryResp = queryService.queryInvestAccount(queryReq);
        logger.debug("投资类账户查询结果" + queryResp);

        queryResp = queryService.queryLiabilityAccount(queryReq);
        logger.debug("负债类账户查询结果" + queryResp);

        queryReq.setUser(QIANJIALIANG_ID);
        queryResp = queryService.queryInvestAccount(queryReq);
        logger.debug("投资类账户查询结果" + queryResp);

        queryResp = queryService.queryLiabilityAccount(queryReq);
        logger.debug("负债类账户查询结果" + queryResp);
    }

    public void unfreeze(String authId, String borrower, String contractNum, String certNum, BigDecimal loanAmount, String lender)
    {

        TRS001002Req trs001002Req = new TRS001002Req();

        String sequenceId = String.valueOf(sequenceService.getSequenceId());
        trs001002Req.setSerlNum(sequenceId);
        trs001002Req.setAuthId(authId);

        trs001002Req.setBorrower(borrower);

        trs001002Req.setContractNum(contractNum);

        trs001002Req.setCertNum(certNum);
        trs001002Req.setLoanAmount(loanAmount.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString());

        trs001002Req.setLender(lender);
        try
        {
            TRS001002Resp unfreeze = investService.unfreeze(trs001002Req);
            logger.info("投标解冻结果" + unfreeze);
        } catch (IllegalPlatformAugumentException e)
        {
            e.printStackTrace();
        }
    }


}
