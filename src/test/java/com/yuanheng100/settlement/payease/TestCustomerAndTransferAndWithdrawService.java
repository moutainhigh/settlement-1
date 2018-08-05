package com.yuanheng100.settlement.payease;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.*;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jlqian on 2016/12/14.
 */
public class TestCustomerAndTransferAndWithdrawService extends BaseTest
{
    private static Logger logger = Logger.getLogger(TestCustomerAndTransferAndWithdrawService.class);

    @Autowired
    ICustomerService customerService;

    @Autowired
    IQueryService queryService;

    @Autowired
    ITransferService transferService;

    @Autowired
    private IWithdrawService payeaseWithdrawService;

    @Autowired
    ISequenceIdService payeaseSequenceIdService;


    //居间人账户
    private static final String MEDIATOR_USER = QIANJIALIANG_ID;

    //居间人身份证号
    private static final String MEDIATOR_USER_ID = QIANJIALIANG_IDCARD;

    @Test
    public void test() throws IllegalPlatformAugumentException
    {
        testDeduct(BAISONG_ID, BAISONG_NAME, "370102198004222910", (short) 102, "9558800200141836664", "1000.00");
    }

    /**
     * 开户 + 转账 + 提现
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

        //等待实名认证
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

        logger.info("\n\n----------首信易账户转账--------------");
        testTransfer(MEDIATOR_USER, MEDIATOR_USER_ID, user, id, amount);

        logger.info("\n\n----------首信易账户提现--------------");
        testWithdrawFromLendAccount(user, id, userName, accBankCode, accNum, amount);


    }

    /*****************************************************
     * 账户相关
     ******************************************************/

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

    /*****************************************************
     * 转账相关
     ******************************************************/

    public void testTransfer(String transferOutUser, String transferOutUserId, String transferInUser, String transferInUserId, String transferAmount) throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始由公司投资账户转账至借款人投资账户--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001007Req transferReq = new TRS001007Req();
        transferReq.setSerlNum(serlNum);

        transferReq.setTransferOutUser(transferOutUser);
        transferReq.setTransferOutUserId(transferOutUserId);

        transferReq.setTransferInUser(transferInUser);
        transferReq.setTransferInUserId(transferInUserId);

        transferReq.setTransferAmount(transferAmount);

        transferReq.setTransferOutUserFee("0.00");
        transferReq.setTransferInUserFee("0.00");

        TRS001007Resp response = transferService.transferBetweenInvestAccount(transferReq);
        logger.debug("代扣测试代码收到: " + response);

        if (!response.getReturnCode().equals("0000"))
        {
            throw new IllegalPlatformAugumentException("转账未成功，不能够提现！");
        }

    }

    /*****************************************************
     * 提现相关
     ******************************************************/

    public void testWithdrawFromLendAccount(String user, String id, String accName, Short accBankCode, String accNum, String amount) throws IllegalPlatformAugumentException
    {

        logger.info("\n\n----------开始对借款人账户提现到银行卡--------------");
        String serlNum = String.valueOf(payeaseSequenceIdService.getSequenceId());

        TRS001006Req trs001006req = new TRS001006Req();
        trs001006req.setSerlNum(serlNum);

        trs001006req.setUser(user);
        trs001006req.setId(id);

        trs001006req.setAccName(accName);
        trs001006req.setAccBankCode(accBankCode);

        trs001006req.setAccNum(accNum);

        trs001006req.setAmount(amount);

        TRS001006Resp response = payeaseWithdrawService.withdrawFromLendAccount(trs001006req);
        logger.debug("提现测试代码收到: " + response);

    }


}
