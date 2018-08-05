package com.yuanheng100.settlement.payease;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.ICustomerService;
import com.yuanheng100.settlement.payease.service.IDeductService;
import com.yuanheng100.settlement.payease.service.IQueryService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jlqian on 2016/12/14.
 */
public class TestCustomerServiceAndDeductService extends BaseTest
{

    private static Logger logger = Logger.getLogger(TestCustomerServiceAndDeductService.class);

    @Autowired
    ICustomerService customerService;

    @Autowired
    IQueryService queryService;

    @Autowired
    IDeductService deductService;

    @Autowired
    ISequenceIdService payeaseSequenceIdService;


    @Test
    public void test() throws IllegalPlatformAugumentException
    {
        testDeduct(BAISONG_ID, BAISONG_NAME, "370102198004222910", BAISONG_ACC_BANK_CODE, "9558800200141836664", "1000.00");
    }

    /**
     * 开户加扣款
     *
     * @param user
     * @param userName
     * @param id
     * @param accBankCode
     * @param accNum
     * @param amount
     * @throws IllegalPlatformAugumentException
     */
    public void testDeduct(String user, String userName, String id, Short accBankCode, String accNum, String amount) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开通首信易账户--------------");
        testRegister(user);
        testSyncIdentity(user, userName, id);
        testBindCard(user, userName, id, accBankCode, accNum);

        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e)
        {
            logger.error(e);
        }

        testQueryAccount(user);
        testQueryBankCard(user, userName, id, accBankCode, accNum);
        testQueryService(user);

        logger.info("\n\n----------首信易账户扣款--------------");
        testDeductToInvest(user, userName, id, accBankCode, accNum, amount);
    }

    public void testRegister(String user) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始账户同步开通--------------");

        SYN001001Req userRegister = new SYN001001Req(user);

        SYN001001Resp response = customerService.register(userRegister);
        logger.info(response);

    }

    public void testSyncIdentity(String user, String userName, String id) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始同步用户身份信息--------------");

        SYN001001Req syncIdentityReq = new SYN001001Req(user);
        syncIdentityReq.setUserName(userName);
        syncIdentityReq.setIdType(IdType.IDENTITY_CARD.getCode());
        syncIdentityReq.setId(id);

        SYN001001Resp response = customerService.syncIdentity(syncIdentityReq);
        logger.info(response);
    }

    public void testBindCard(String user, String userName, String id, Short accBankCode, String accNum) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始同步用户银行卡信息--------------");

        SYN001001Req bindCardReq = new SYN001001Req(user);
        bindCardReq.setUserName(userName);
        bindCardReq.setId(id);
        bindCardReq.setAccName(userName);
        bindCardReq.setAccBankCode(accBankCode);
        bindCardReq.setAccNum(accNum);
        bindCardReq.setIdType(IdType.IDENTITY_CARD.getCode());
        bindCardReq.setAccType(ConfigFile.getProperty("payease.bank.corde.type"));
        bindCardReq.setAccProp(ConfigFile.getProperty("payease.acc.prop"));
        SYN001001Resp response = customerService.bindBankCard(bindCardReq);
        logger.info(response);
    }

    public void testQueryAccount(String user) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始查询账户信息--------------");
        SYN001001Req queryAccountReq = new SYN001001Req(user);

        SYN001001Resp response = customerService.queryAccount(queryAccountReq);
        logger.info(response);
    }

    public void testQueryBankCard(String user, String userName, String id, Short accBankCode, String accNum) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始查询验卡是否通过--------------");
        SYN001001Req bindCardQueryReq = new SYN001001Req(user);
        bindCardQueryReq.setUserName(userName);
        bindCardQueryReq.setId(id);
        bindCardQueryReq.setIdType(IdType.IDENTITY_CARD.getCode());
        bindCardQueryReq.setAccName(userName); //此字段必填，但文档上没注明
        bindCardQueryReq.setAccNum(accNum);
        bindCardQueryReq.setAccBankCode(accBankCode);
        SYN001001Resp response = customerService.queryBankCard(bindCardQueryReq);
        logger.info(response);
        if (!response.getReturnCode().equals("0010"))
        {
            throw new IllegalPlatformAugumentException("银行卡验证未通过，不能扣款！");
        }
    }


    public void testQueryService(String user) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始查询账户余额--------------");

        TRS001010Req queryReq = new TRS001010Req();
        queryReq.setUser(user);
        TRS001010Resp queryResp = queryService.queryInvestAccount(queryReq);
        System.out.println("投资类账户查询结果" + queryResp);

        queryResp = queryService.queryLiabilityAccount(queryReq);
        System.out.println("负债类账户查询结果" + queryResp);
    }

    /**
     * 测试代扣
     *
     * @param user
     * @param userName
     * @param id
     * @param accBankCode
     * @param accNum
     * @param amount
     * @throws IllegalPlatformAugumentException
     */
    public void testDeductToInvest(String user, String userName, String id, Short accBankCode, String accNum, String amount) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始执行代扣操作--------------");

        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());
        TRS001008Req deductReq = new TRS001008Req();
        deductReq.setSerlNum(serlNum);
        deductReq.setUser(user);

        deductReq.setIdType(IdType.IDENTITY_CARD.getCode());
        deductReq.setId(id);

        deductReq.setAccBankCode(accBankCode);
        deductReq.setAccName(userName);
        deductReq.setAccNum(accNum);
        deductReq.setAccType("00");
        deductReq.setAccProp("PR");

        deductReq.setAmount(amount);
        deductReq.setMerdata1("");

        TRS001008Resp response = deductService.deductToInvest(deductReq);

        logger.debug("代扣测试代码收到: " + response);
    }


    /**
     * 测试代扣结果
     *
     * @throws IllegalPlatformAugumentException
     */
    public void testQueryDeduct() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始查询代扣结果--------------");

        List<TRS001008Req> trs001008reqs = deductService.queryLocalWithReturnCode("0002");
        for (TRS001008Req trs001008req : trs001008reqs)
        {
            TRS001008Resp response = deductService.queryDeduct(trs001008req);
            logger.debug("提现测试代码收到: " + response);
        }
    }

}
