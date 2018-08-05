package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IWithdrawCallbackListener;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.mapper.TRS001006Mapper;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.service.IWithdrawService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/12/13.
 */
public class WithdrawServiceImpl extends AbstractMessageService<TRS001006Req> implements IWithdrawService
{
    private static Logger logger = Logger.getLogger(WithdrawServiceImpl.class);

    @Autowired
    private TRS001006Mapper trs001006Mapper;

    /**
     * TRS001006-18000 用户从出借虚拟账户提现到银行卡
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    @Override
    public TRS001006Resp withdrawFromLendAccount(TRS001006Req trs001006req) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(trs001006req);
        trs001006req.setOperationCode(OperationCode.TRS001006.WITHDRAW_FROM_LEND_ACCOUNT.getCode());
        logger.info("用户从出借虚拟账户提现到银行卡：" + trs001006req);
        trs001006Mapper.insert(trs001006req);
        TRS001006Resp response = (TRS001006Resp) syncSend(trs001006req);
        response.setMsgid(trs001006req.getMsgid());
        response.setReturnTime(new Date());
        trs001006Mapper.updateByPrimaryKey(response);
        logger.info("收到首信易提现同步返回: " + response);
        return response;
    }

    @Override
    public TRS001006Resp withdrawFromLendAccount(TRS001006Req trs001006req, IWithdrawCallbackListener withdrawCallbackListener) throws IllegalPlatformAugumentException
    {
        TRS001006Resp trs001006Resp = withdrawFromLendAccount(trs001006req);
        CallbackListenerContainer.putWithdrawCallbackListener(trs001006req.getSerlNum(), withdrawCallbackListener);

        return trs001006Resp;
    }

    /**
     * TRS001006-18001 用户从借款虚拟账户提现到银行卡
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    @Override
    public TRS001006Resp withdrawFromBorrowerAccount(TRS001006Req trs001006req) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(trs001006req);
        trs001006req.setOperationCode(OperationCode.TRS001006.WITHDRAW_FROM_BORROWER_ACCOUNT.getCode());
        logger.info("用户从借款虚拟账户提现到银行卡：" + trs001006req);
        trs001006Mapper.insert(trs001006req);
        TRS001006Resp response = (TRS001006Resp) syncSend(trs001006req);
        response.setMsgid(trs001006req.getMsgid());
        response.setReturnTime(new Date());
        trs001006Mapper.updateByPrimaryKey(response);
        logger.info("收到首信易提现同步返回: " + response);
        return response;
    }

    /**
     * TRS001006-18002 查询订单提现处理结果
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    @Override
    public TRS001006Resp queryWithdrawResult(TRS001006Req trs001006req) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(trs001006req);
        trs001006req.setOperationCode(OperationCode.TRS001006.QUERY_WITHDRAWR_ESULT.getCode());
        logger.info("查询提现订单处理结果：" + trs001006req);
        TRS001006Resp response = (TRS001006Resp) syncSend(trs001006req);
        response.setMsgid(trs001006req.getMsgid());
        response.setReturnTime(new Date());
        trs001006Mapper.updateByPrimaryKey(response);
        logger.info("收到首信易提现同步返回: " + response);
        return response;
    }

    /**
     * TRS001006-18003 P2P平台划拨款（与18001操作在功能实现上没有区别，仅作为区分平台划拨与客户自助划拨）
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    @Override
    public TRS001006Resp withdrawFromBorrowerAccountByPlatform(TRS001006Req trs001006req) throws IllegalPlatformAugumentException
    {
        setDefaultParameter(trs001006req);
        trs001006req.setOperationCode(OperationCode.TRS001006.WITHDRAW_FROM_BORROWER_ACCOUNT_BY_PLATFORM.getCode());
        logger.info("P2P平台划拨款：" + trs001006req);
        trs001006Mapper.insert(trs001006req);
        TRS001006Resp response = (TRS001006Resp) syncSend(trs001006req);
        response.setMsgid(trs001006req.getMsgid());
        response.setReturnTime(new Date());
        trs001006Mapper.updateByPrimaryKey(response);
        logger.info("收到首信易提现同步返回: " + response);
        return response;
    }

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     *
     * @return
     */
    @Override
    public List<TRS001006Req> queryLocalWithReturnCode(String returnCode)
    {
        return trs001006Mapper.queryWithReturnCode(returnCode);
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        searchConditions.put("online_plateform_user", Integer.parseInt(ConfigFile.getProperty("payease.online.plateform.user.start")));

        Long totalCount = trs001006Mapper.selectWithdrawCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = trs001006Mapper.selectWithdrawListByCondition(searchConditions);

        page.setResult(list);
    }

    @Override
    public void updateResult(TRS001006Resp trs001006Resp)
    {
        //根据流水号更新代扣
        trs001006Mapper.updateBySerlNum(trs001006Resp);
    }


    /**
     * 如果必要字段为空，设置默认参数
     *
     * @param trs001006Req
     */
    private void setDefaultParameter(TRS001006Req trs001006Req)
    {
        //默认参数设置：
        if (trs001006Req.getAccType() == null)
        {
            trs001006Req.setAccType(ConfigFile.getProperty("payease.bank.corde.type"));
        }
        if (trs001006Req.getAccProp() == null)
        {
            trs001006Req.setAccProp(ConfigFile.getProperty("payease.acc.prop"));
        }
        if (trs001006Req.getFee() == null)
        {
            trs001006Req.setFee(ConfigFile.getProperty("payease.withdraw.fee"));
        }
        if (trs001006Req.getUrgency() == null)
        {
            trs001006Req.setUrgency(ConfigFile.getProperty("payease.urgency"));
        }
        if (trs001006Req.getAmountSplit() == null)
        {
            trs001006Req.setAmountSplit(ConfigFile.getProperty("payease.amount.split"));
        }
        if (trs001006Req.getTotalAmount() == null)
        {
            trs001006Req.setTotalAmount(new BigDecimal(trs001006Req.getAmount()).add(new BigDecimal(trs001006Req.getFee())).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        }
    }
}
