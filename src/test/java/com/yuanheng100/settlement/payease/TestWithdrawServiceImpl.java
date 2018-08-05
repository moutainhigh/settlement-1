package com.yuanheng100.settlement.payease;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.service.IWithdrawService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试：提现服务 TRS001006
 */
public class TestWithdrawServiceImpl extends BaseTest
{
    private static Logger logger = Logger.getLogger(TestWithdrawServiceImpl.class);

    @Autowired
    private IWithdrawService payeaseWithdrawService;

    @Autowired
    ISequenceIdService payeaseSequenceIdService;

    @Test
    public void testWithdrawFromLendAccount() throws IllegalPlatformAugumentException
    {

        logger.info("\n\n----------开始testWithdrawFromLendAccount()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001006Req trs001006req = new TRS001006Req();
        trs001006req.setSerlNum(serlNum);

        trs001006req.setUser(BAISONG_ID);
        trs001006req.setId(BAISONG_IDCARD);

        trs001006req.setAccName(BAISONG_NAME);
        trs001006req.setAccBankCode(BAISONG_ACC_BANK_CODE);

        trs001006req.setAccNum(BAISONG_ACC_NUM);

        trs001006req.setAmount("1.00");

        trs001006req.setMerdata1("测试提现预留字段1");
        trs001006req.setMerdata2("测试提现预留字段2");

        TRS001006Resp response = payeaseWithdrawService.withdrawFromLendAccount(trs001006req);
        logger.debug("提现测试代码收到: " + response);

    }

    @Test
    public void testWithdrawFromBorrowerAccount() throws IllegalPlatformAugumentException
    {

        logger.info("\n\n----------开始testWithdrawFromBorrowerAccount()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001006Req trs001006req = new TRS001006Req();
        trs001006req.setSerlNum(serlNum);

        trs001006req.setUser(BAISONG_ID);
        trs001006req.setId(BAISONG_IDCARD);

        trs001006req.setAccName(BAISONG_NAME);
        trs001006req.setAccBankCode(BAISONG_ACC_BANK_CODE);

        trs001006req.setAccNum(BAISONG_ACC_NUM);

        trs001006req.setAmount("1.00");

        trs001006req.setMerdata1("测试提现预留字段1");
        trs001006req.setMerdata2("测试提现预留字段2");

        TRS001006Resp response = payeaseWithdrawService.withdrawFromBorrowerAccount(trs001006req);
        logger.debug("提现测试代码收到: " + response);

    }

    @Test
    public void testQueryWithdrawResult() throws IllegalPlatformAugumentException
    {

        logger.info("\n\n----------开始testQueryWithdrawResult()--------------");

        List<TRS001006Req> trs001006Reqs = payeaseWithdrawService.queryLocalWithReturnCode("0002");
        for (TRS001006Req trs001006req : trs001006Reqs)
        {
            TRS001006Resp response = payeaseWithdrawService.queryWithdrawResult(trs001006req);
            logger.debug("提现测试代码收到: " + response);
        }
    }

    @Test
    public void testWithdrawFromBorrowerAccountByPlatform() throws IllegalPlatformAugumentException
    {

        logger.info("\n\n----------开始testWithdrawFromBorrowerAccountByPlatform()--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001006Req trs001006req = new TRS001006Req();
        trs001006req.setSerlNum(serlNum);

        trs001006req.setUser(BAISONG_ID);
        trs001006req.setId(BAISONG_IDCARD);

        trs001006req.setAccName(BAISONG_NAME);
        trs001006req.setAccBankCode(BAISONG_ACC_BANK_CODE);

        trs001006req.setAccNum(BAISONG_ACC_NUM);

        trs001006req.setAmount("1.00");

        trs001006req.setMerdata1("测试提现预留字段1");
        trs001006req.setMerdata2("测试提现预留字段2");

        TRS001006Resp response = payeaseWithdrawService.withdrawFromBorrowerAccountByPlatform(trs001006req);
        logger.debug("提现测试代码收到: " + response);

    }

}
