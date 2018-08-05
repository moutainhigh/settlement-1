package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IBindBankCardCallbackListener;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.mapper.SYN001001Mapper;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.service.ICustomerService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户同步验证的服务类
 *
 * @author Bai Song
 */
public class CustomerServiceImpl extends AbstractMessageService<SYN001001Req> implements ICustomerService
{

    private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private SYN001001Mapper syn001001mapper;

    @Override
    public SYN001001Resp register(SYN001001Req userRegisterReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(userRegisterReq);
        userRegisterReq.setOperationCode(OperationCode.SYN001001.CUSTOMER_REGISTER.getCode());
        logger.info("开始在首信易开户：" + userRegisterReq);
        syn001001mapper.insert(userRegisterReq);
        SYN001001Resp response = (SYN001001Resp) syncSend(userRegisterReq);
        response.setMsgid(userRegisterReq.getMsgid());
        response.setReturnTime(new Date());
        syn001001mapper.updateByPrimaryKey(response);
        logger.info("收到返回" + response);
        return response;
    }


    @Override
    public SYN001001Resp syncIdentity(SYN001001Req syncIdentityReq) throws IllegalPlatformAugumentException
    {
        syncIdentityReq.setOperationCode(OperationCode.SYN001001.SYN_IDENTITY.getCode());
        logger.info("开始在首信易同步身份信息：" + syncIdentityReq);
        syn001001mapper.insert(syncIdentityReq);
        SYN001001Resp response = (SYN001001Resp) syncSend(syncIdentityReq);
        response.setMsgid(syncIdentityReq.getMsgid());
        response.setReturnTime(new Date());
        syn001001mapper.updateByPrimaryKey(response);
        logger.info("收到返回" + response);
        return response;
    }


    @Override
    public SYN001001Resp bindBankCard(SYN001001Req bindCardReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(bindCardReq);
        bindCardReq.setOperationCode(OperationCode.SYN001001.SYN_BANKCARD.getCode());
        logger.info("开始在首信易绑卡：" + bindCardReq);
        syn001001mapper.insert(bindCardReq);
        SYN001001Resp response = (SYN001001Resp) syncSend(bindCardReq);
        response.setMsgid(bindCardReq.getMsgid());
        response.setReturnTime(new Date());
        syn001001mapper.updateByPrimaryKey(response);
        logger.info("收到返回" + response);
        return response;
    }

    @Override
    public SYN001001Resp bindBankCard(SYN001001Req bindCardReq, IBindBankCardCallbackListener listener) throws IllegalPlatformAugumentException
    {
        CallbackListenerContainer.putBindBankCardCallbackListener(bindCardReq.getUser(), listener);
        return bindBankCard(bindCardReq);
    }

    @Override
    public SYN001001Resp queryAccount(SYN001001Req customerQueryReq) throws IllegalPlatformAugumentException
    {
        customerQueryReq.setOperationCode(OperationCode.SYN001001.QUERY_ACCOUNT.getCode());
        logger.info("开始查询账户开通情况：" + customerQueryReq);
        syn001001mapper.insert(customerQueryReq);
        SYN001001Resp response = (SYN001001Resp) syncSend(customerQueryReq);
        response.setMsgid(customerQueryReq.getMsgid());
        response.setReturnTime(new Date());
        syn001001mapper.updateByPrimaryKey(response);
        logger.info("收到返回" + response);
        return response;
    }


    @Override
    public SYN001001Resp queryBankCard(SYN001001Req bankCardQueryReq) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(bankCardQueryReq);
        bankCardQueryReq.setOperationCode(OperationCode.SYN001001.QUERY_BANKCARD.getCode());
        if (bankCardQueryReq.getAccName() == null)
        {
            throw new IllegalPlatformAugumentException("做查询银行卡操作时，ACC_NAME字段为必填项");
        }
        logger.info("开始查询银行卡验证情况：" + bankCardQueryReq);
        syn001001mapper.insert(bankCardQueryReq);
        SYN001001Resp response = (SYN001001Resp) syncSend(bankCardQueryReq);
        response.setMsgid(bankCardQueryReq.getMsgid());
        response.setReturnTime(new Date());
        syn001001mapper.updateByPrimaryKey(response);
        logger.info("收到返回" + response);
        return response;
    }

    /**
     * 获取账户页面的列表
     *
     * @param searchConditions
     * @param page
     */
    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        searchConditions.put("user", Integer.parseInt(ConfigFile.getProperty("payease.online.plateform.user.start")));

        Long totalCount = syn001001mapper.selectAccountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = syn001001mapper.selectAccountListByCondition(searchConditions);
        for (Map<String, Object> map : list)
        {
            String status = null;
            String returnCode = String.valueOf(map.get("returnCode"));
            String operationCode = map.get("operationCode").toString();
            if ("null".equals(returnCode))
            {
                status = "请求失败！";
            } else if (returnCode.equals(ReturnCode.TRANS_SUCCESS))
            {
                status = "开户";
            } else if (returnCode.equals(ReturnCode.BIND_CARD_SUCCESS))
            {
                status = "开户+银行卡";
            } else if (returnCode.equals(ReturnCode.BIND_CARD_FAILURE))
            {
                status = "开户[" + map.get("returnMsg") + "]";
            } else
            {
                status = "未开户[" + map.get("returnMsg") + "]";
            }
            map.put("status", status);
        }
        page.setResult(list);

    }

    @Override
    public List<SYN001001Resp> getByUser(String user)
    {
        return syn001001mapper.selectByUser(user);
    }

    @Override
    public void getTradeListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = syn001001mapper.selectTradeCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = syn001001mapper.selectTradeListByCondition(searchConditions);
        page.setResult(list);
    }

    @Override
    public SYN001001Resp getBindCardSuccessCustomerByUser(String user)
    {
        return syn001001mapper.selectBindCardCustomer(user, ReturnCode.BIND_CARD_SUCCESS);
    }

    @Override
    public void updateBindResult(SYN001001Resp syn001001Resp)
    {
        syn001001mapper.updateByPrimaryKey(syn001001Resp);
    }

    @Override
    public String nextUser()
    {
        Integer lastUser = syn001001mapper.selectLastUserBefore(Integer.parseInt(ConfigFile.getProperty("payease.online.plateform.user.start")));
        if (lastUser == null)
        {
            lastUser = 20000;
        }
        lastUser++;
        return lastUser.toString();
    }

    /**
     * 如果必要字段为空，设置默认参数
     *
     * @param syn001001Req
     */
    private void setDefaultParameter(SYN001001Req syn001001Req)
    {
        //默认参数设置：
        if (syn001001Req.getIdType() == null)
        {
            syn001001Req.setIdType(IdType.IDENTITY_CARD.getCode());
        }
        if (syn001001Req.getAccType() == null)
        {
            syn001001Req.setAccType(ConfigFile.getProperty("payease.bank.corde.type"));
        }
        if (syn001001Req.getAccProp() == null)
        {
            syn001001Req.setAccProp(ConfigFile.getProperty("payease.acc.prop"));
        }
    }

}
