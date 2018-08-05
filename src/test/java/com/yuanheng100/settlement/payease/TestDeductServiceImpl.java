package com.yuanheng100.settlement.payease;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.service.IDeductService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;

import java.util.List;

/**
 * 代扣服务接口，TRS001008
 * @author Bai Song
 *
 */
public class TestDeductServiceImpl extends BaseTest
{
    
    @Autowired
    IDeductService deductService;
    
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
    public void testDeductToInvest() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testDeductToInvest()--------------");
        
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());
        TRS001008Req deductReq = new TRS001008Req();
        deductReq.setSerlNum(serlNum);
        deductReq.setUser(BAISONG_ID);
        
        deductReq.setIdType(IdType.IDENTITY_CARD.getCode());
        deductReq.setId(BAISONG_IDCARD);
        
        deductReq.setAccBankCode(BAISONG_ACC_BANK_CODE);
        deductReq.setAccName(BAISONG_NAME);
        deductReq.setAccNum(BAISONG_ACC_NUM);
        deductReq.setAccType("00");
        deductReq.setAccProp("PR");
        
        deductReq.setAmount(BAISONG_INVEST_AMOUNT.toPlainString());
        deductReq.setMerdata1("");
        
        TRS001008Resp response = deductService.deductToInvest(deductReq);
        
        logger.debug("代扣测试代码收到: "+response);
    }

    @Test
    public void testDeductToRepay() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testDeductToInvest()--------------");
        
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());
        TRS001008Req deductReq = new TRS001008Req();
        deductReq.setSerlNum(serlNum);
        deductReq.setUser(BAISONG_ID);
        
        deductReq.setIdType(IdType.IDENTITY_CARD.getCode());
        deductReq.setId(BAISONG_IDCARD);
        
        deductReq.setAccBankCode(BAISONG_ACC_BANK_CODE);
        deductReq.setAccName(BAISONG_NAME);
        deductReq.setAccNum(BAISONG_ACC_NUM);
        deductReq.setAccType("00");
        deductReq.setAccProp("PR");
        
        deductReq.setAmount(BAISONG_INVEST_AMOUNT.toPlainString());
        deductReq.setMerdata1("");
        
        TRS001008Resp response = deductService.deductToRepay(deductReq);
        
        logger.debug("代扣测试代码收到: "+response);
    }

    @Test
    public void testQueryDeduct() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryDeductResult()--------------");

        List<TRS001008Req> trs001008reqs = deductService.queryLocalWithReturnCode("0002");
        for (TRS001008Req trs001008req : trs001008reqs)
        {
            TRS001008Resp response = deductService.queryDeduct(trs001008req);
            logger.debug("提现测试代码收到: " + response);
        }
    }
}
