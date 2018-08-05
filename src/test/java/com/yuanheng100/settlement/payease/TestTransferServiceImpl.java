package com.yuanheng100.settlement.payease;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.service.ITransferService;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试 转账服务 TRS001007
 *
 * @author Bai Song
 */
public class TestTransferServiceImpl extends BaseTest
{

    @Autowired
    ITransferService transferService;

    @Autowired
    ISequenceIdService payeaseSequenceIdService;

    private static Logger logger = Logger.getLogger(TestDeductServiceImpl.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testTransferBetweenInvestAccount() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testTransferBetweenInvestAccount()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001007Req transferReq = new TRS001007Req();
        transferReq.setSerlNum(serlNum);

        transferReq.setTransferOutUser(BAISONG_ID);
        transferReq.setTransferOutUserId(BAISONG_IDCARD);

        transferReq.setTransferInUser(QIANJIALIANG_ID);
        transferReq.setTransferInUserId(QIANJIALIANG_IDCARD);

        transferReq.setTransferAmount(QIANJIALIANG_INVEST_AMOUNT.toString());

        transferReq.setTransferOutUserFee("0.00");
        transferReq.setTransferInUserFee("0.00");

        transferReq.setMerdata1("测试提现预留字段1");
        transferReq.setMerdata2("测试提现预留字段2");

        TRS001007Resp response = transferService.transferBetweenInvestAccount(transferReq);
        logger.debug("代扣测试代码收到: " + response);
    }

    @Test
    public void testTransferInvestToLiability() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testTransferInvestToLiability()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001007Req transferReq = new TRS001007Req();
        transferReq.setSerlNum(serlNum);

        transferReq.setTransferOutUser(BAISONG_ID);
        transferReq.setTransferOutUserId(BAISONG_IDCARD);

        transferReq.setTransferInUser(QIANJIALIANG_ID);
        transferReq.setTransferInUserId(QIANJIALIANG_IDCARD);

        transferReq.setTransferAmount(QIANJIALIANG_INVEST_AMOUNT.toString());

        transferReq.setTransferOutUserFee("0.00");
        transferReq.setTransferInUserFee("0.00");

        TRS001007Resp response = transferService.transferInvestToLiability(transferReq);
        logger.debug("代扣测试代码收到: " + response);
    }

    @Test
    public void testTransferLiabilityToInvest() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testTransferLiabilityToInvest()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001007Req transferReq = new TRS001007Req();
        transferReq.setSerlNum(serlNum);

        transferReq.setTransferOutUser(BAISONG_ID);
        transferReq.setTransferOutUserId(BAISONG_IDCARD);

        transferReq.setTransferInUser(QIANJIALIANG_ID);
        transferReq.setTransferInUserId(QIANJIALIANG_IDCARD);

        transferReq.setTransferAmount(QIANJIALIANG_INVEST_AMOUNT.toString());

        transferReq.setTransferOutUserFee("0.00");
        transferReq.setTransferInUserFee("0.00");

        TRS001007Resp response = transferService.transferLiabilityToInvest(transferReq);
        logger.debug("代扣测试代码收到: " + response);
    }

    public void testQueryTransferResult() throws IllegalPlatformAugumentException
    {
        List<TRS001007Req> trs001007reqs = transferService.queryLocalWithReturnCode("0002");
        for (TRS001007Req trs001007req : trs001007reqs)
        {
            TRS001007Resp response = transferService.queryTransferResult(trs001007req);
            logger.debug("代扣测试代码收到: " + response);
        }
    }


}
