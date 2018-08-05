package com.yuanheng100.settlement.payease;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.ICustomerService;
import com.yuanheng100.settlement.payease.service.IQueryService;

/**
 * 资金清算中，关于客户开户等相关的服务接口 SYN001001
 *
 * @author Bai Song
 *
 */
public class TestCustomerServiceImpl extends BaseTest
{

    private static Logger logger = Logger.getLogger(TestCustomerServiceImpl.class);
    
    @Autowired
    ICustomerService customerService;
    
    @Autowired
    IQueryService queryService;
    
    
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
/*          testRegister();
          testSyncIdentity();
          testQueryAccount();
          testBindCard();*/
          testQueryBankCard();
//          testQueryService();
        }
        catch(IllegalPlatformAugumentException ipae)
        {
            logger.error("单元测试时发生异常", ipae);
        }

    }

    
    public void testRegister() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testRegister()--------------");
        
        SYN001001Req userRegister = new SYN001001Req(BAISONG_ID);
        
        SYN001001Resp response = customerService.register(userRegister);
        logger.info(response);

    }
    
    public void testSyncIdentity() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testSyncIdentity()--------------");
        
        SYN001001Req syncIdentityReq = new SYN001001Req(BAISONG_ID);
        syncIdentityReq.setUserName(BAISONG_NAME);
        syncIdentityReq.setIdType(IdType.IDENTITY_CARD.getCode());
        syncIdentityReq.setId("370102198004222910");
        
        SYN001001Resp response = customerService.syncIdentity(syncIdentityReq);
        logger.info(response);
    }
    
    
    public void testQueryAccount() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryAccount()--------------");
        SYN001001Req queryAccountReq = new SYN001001Req(BAISONG_ID);
        
        SYN001001Resp response = customerService.queryAccount(queryAccountReq);
        logger.info(response);
    }

    public void testBindCard() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testBindCard()--------------");
        
        SYN001001Req bindCardReq = new SYN001001Req(BAISONG_ID);
        bindCardReq.setUserName(BAISONG_NAME);
        bindCardReq.setAccBankCode(BAISONG_ACC_BANK_CODE);
        bindCardReq.setId("370102198004222910");
        bindCardReq.setAccNum("9558800200141836664");
        bindCardReq.setIdType(IdType.IDENTITY_CARD.getCode());
        //bindCardReq.setAccBank("中国工商银行股份有限公司");
        //bindCardReq.setAccBranchName("北京分行半岛国际支行");
        //bindCardReq.setAccType("00");
        //bindCardReq.setAccProp("PR");
        SYN001001Resp response = customerService.bindBankCard(bindCardReq);
        logger.info(response);
    }
    

    public void testQueryBankCard() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryBankCard()--------------");
        SYN001001Req bindCardQueryReq = new SYN001001Req(BAISONG_ID);
        bindCardQueryReq.setUserName(BAISONG_NAME);
        bindCardQueryReq.setAccName(BAISONG_NAME); //此字段必填，但文档上没注明
        bindCardQueryReq.setId("370102198004222910");
        bindCardQueryReq.setAccNum("9558800200141836664");
        bindCardQueryReq.setIdType(IdType.IDENTITY_CARD.getCode());
        bindCardQueryReq.setAccBankCode(BAISONG_ACC_BANK_CODE);
        //bindCardQueryReq.setAccBranchName("北京分行半岛国际支行");
        //bindCardQueryReq.setAccType("00");
        //bindCardQueryReq.setAccProp("PR");
        SYN001001Resp response = customerService.queryBankCard(bindCardQueryReq);
        logger.info(response);
    }
    
    
    public void testQueryService() throws IllegalPlatformAugumentException
    {
        logger.info("\n\n----------开始testQueryService()--------------");
        
        TRS001010Req queryReq = new TRS001010Req();
        queryReq.setUser(BAISONG_ID);
        TRS001010Resp queryResp = queryService.queryInvestAccount(queryReq);
        System.out.println("投资类账户查询结果"+queryResp);
        
        queryResp = queryService.queryLiabilityAccount(queryReq);
        System.out.println("负债类账户查询结果"+queryResp);
    }
    

}
