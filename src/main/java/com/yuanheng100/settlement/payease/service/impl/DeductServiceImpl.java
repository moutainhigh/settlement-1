package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.mapper.TRS001008Mapper;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.service.IDeductService;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author Bai Song
 */
public class DeductServiceImpl extends AbstractMessageService<TRS001008Req> implements IDeductService
{

    private static Logger logger = Logger.getLogger(DeductServiceImpl.class);

    @Autowired
    private TRS001008Mapper trs001008mapper;

    @Override
    public TRS001008Resp deductToInvest(TRS001008Req deductReq) throws IllegalPlatformAugumentException
    {
        if (deductReq.getAccBankCode() == null)
        {
            throw new IllegalPlatformAugumentException("ACCBANK开户银行不能为空");
        }
        setDefaultParameter(deductReq);
        deductReq.setOperationCode(OperationCode.TRS001008.DEDUCT_TO_INVEST.getCode());
        logger.info("开始在首信易代扣" + deductReq);
        trs001008mapper.insert(deductReq);
        TRS001008Resp response = (TRS001008Resp) syncSend(deductReq);
        response.setMsgid(deductReq.getMsgid());
        response.setReturnTime(new Date());
        trs001008mapper.updateByPrimaryKey(response);
        logger.info("收到首信易代扣同步返回: " + response);
        return response;
    }

    @Override
    public TRS001008Resp deductToRepay(TRS001008Req deductReq) throws IllegalPlatformAugumentException
    {
        if (deductReq.getAccBankCode() == null)
        {
            throw new IllegalPlatformAugumentException("ACCBANK开户银行不能为空");
        }
        setDefaultParameter(deductReq);
        deductReq.setOperationCode(OperationCode.TRS001008.DEDUCT_TO_REPAY.getCode());
        logger.info("开始在首信易代扣" + deductReq);
        trs001008mapper.insert(deductReq);
        TRS001008Resp response = (TRS001008Resp) syncSend(deductReq);
        response.setMsgid(deductReq.getMsgid());
        response.setReturnTime(new Date());
        trs001008mapper.updateByPrimaryKey(response);
        logger.info("收到首信易代扣同步返回: " + response);
        return response;
    }

    @Override
    public TRS001008Resp queryDeduct(TRS001008Req deductReq) throws IllegalPlatformAugumentException
    {
        if (deductReq.getAccBankCode() == null)
        {
            throw new IllegalPlatformAugumentException("ACCBANK开户银行不能为空");
        }
        setDefaultParameter(deductReq);
        deductReq.setOperationCode(OperationCode.TRS001008.QUERY_DEDUCT.getCode());
        logger.info("开始在首信易查询代扣结果" + deductReq);
        TRS001008Resp response = (TRS001008Resp) syncSend(deductReq);
        response.setMsgid(deductReq.getMsgid());
        response.setReturnTime(new Date());
        trs001008mapper.updateByPrimaryKey(response);
        logger.info("收到首信易查询代扣结果同步返回: " + response);
        return response;
    }

    @Override
    public void updateResult(TRS001008Resp trs001008Resp)
    {
        //根据流水号更新代扣
        trs001008mapper.updateBySerlNum(trs001008Resp);
    }

    /**
     * 如果必要字段为空，设置默认参数
     *
     * @param trs001008Req
     */
    private void setDefaultParameter(TRS001008Req trs001008Req)
    {
        //默认参数设置：
        if (trs001008Req.getIdType() == null)
        {
            trs001008Req.setIdType(IdType.IDENTITY_CARD.getCode());
        }
        if (trs001008Req.getAccType() == null)
        {
            trs001008Req.setAccType(ConfigFile.getProperty("payease.bank.corde.type"));
        }
        if (trs001008Req.getAccProp() == null)
        {
            trs001008Req.setAccProp(ConfigFile.getProperty("payease.acc.prop"));
        }
    }

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     *
     * @param returnCode
     * @return
     */
    @Override
    public List<TRS001008Req> queryLocalWithReturnCode(String returnCode)
    {
        return trs001008mapper.queryWithReturnCode(returnCode);
    }

    /**
     * 获取代扣页面的列表
     *
     * @param searchConditions
     * @param page
     */
    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = trs001008mapper.selectDeductCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = trs001008mapper.selectDeductListByCondition(searchConditions);
        page.setResult(list);
    }

}
