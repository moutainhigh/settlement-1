package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.mapper.SYN001001Mapper;
import com.yuanheng100.settlement.payease.mapper.TRS001007Mapper;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.service.ITransferService;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransferServiceImpl  extends AbstractMessageService<TRS001007Req>  implements ITransferService
{
    private static Logger logger = Logger.getLogger(TransferServiceImpl.class);
    
    @Autowired
    private TRS001007Mapper trs001007mapper;

    @Autowired
    private SYN001001Mapper syn001001Mapper;

    /**
     * TRS001007-240001 查询
     * @param transferReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    @Override
    public TRS001007Resp queryTransferResult(TRS001007Req transferReq) throws IllegalPlatformAugumentException
    {
        transferReq.setOperationCode(OperationCode.TRS001007.QUERY_TRANSFER_RESULT.getCode());
        logger.info("开始在首信易查询转账结果："+transferReq);
        TRS001007Resp response = (TRS001007Resp)syncSend(transferReq);
        response.setMsgid(transferReq.getMsgid());
        response.setReturnTime(new Date());
        trs001007mapper.updateByPrimaryKey(response);
        logger.info("收到首信易查询转账结果同步返回: "+response);
        return response;
    }

    @Override
    public TRS001007Resp transferBetweenInvestAccount(TRS001007Req transferReq) throws IllegalPlatformAugumentException
    {
        transferReq.setOperationCode(OperationCode.TRS001007.INVEST_TO_INVEST.getCode());
        logger.info("开始在首信易投资账户之间转账："+transferReq); 
        trs001007mapper.insert(transferReq);
        TRS001007Resp response = (TRS001007Resp)syncSend(transferReq);
        response.setMsgid(transferReq.getMsgid());
        response.setReturnTime(new Date());
        trs001007mapper.updateByPrimaryKey(response);
        logger.info("收到首信易转账同步返回: "+response);
        return response;
    }

    @Override
    public TRS001007Resp transferInvestToLiability(TRS001007Req transferReq) throws IllegalPlatformAugumentException
    {
        transferReq.setOperationCode(OperationCode.TRS001007.INVEST_TO_LIABILITY.getCode());
        logger.info("开始从首信易投资账户转账到负债账户："+transferReq); 
        trs001007mapper.insert(transferReq);
        TRS001007Resp response = (TRS001007Resp)syncSend(transferReq);
        response.setMsgid(transferReq.getMsgid());
        response.setReturnTime(new Date());
        trs001007mapper.updateByPrimaryKey(response);
        logger.info("收到首信易转账同步返回: "+response);
        return response;
    }

    @Override
    public TRS001007Resp transferLiabilityToInvest(TRS001007Req transferReq) throws IllegalPlatformAugumentException
    {
        transferReq.setOperationCode(OperationCode.TRS001007.LIABILITY_TO_INVEST.getCode());
        logger.info("开始从首信易负债账户转账到投资账户："+transferReq); 
        trs001007mapper.insert(transferReq);
        TRS001007Resp response = (TRS001007Resp)syncSend(transferReq);
        response.setMsgid(transferReq.getMsgid());
        response.setReturnTime(new Date());
        trs001007mapper.updateByPrimaryKey(response);
        logger.info("收到首信易转账同步返回: "+response);
        return response;
    }

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     * @param returnCode
     * @return
     */
    @Override
    public List<TRS001007Req> queryLocalWithReturnCode(String returnCode)
    {
        return trs001007mapper.queryWithReturnCode(returnCode);
    }

    @Override
    public List<Map<String, Object>> getTransferRecords(Map<String, Object> param)
    {
        param.put("user", Integer.parseInt(ConfigFile.getProperty("payease.online.plateform.user.start")));

        List<Map<String,Object>> transferRecords = trs001007mapper.getTransferRecords(param);
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for (Map<String,Object> map : transferRecords)
        {
            if (map.get("returnCode") == null || ReturnCode.TRANS_FAILURE.equals(String.valueOf(map.get("returnCode"))))
            {
                map.put("result","失败");
            }else if (ReturnCode.TRANS_TYPEERROR.equals(String.valueOf(map.get("returnCode"))))
            {
                map.put("result","转帐金额格式有问题");
            }
            else if (ReturnCode.TRANS_SUCCESS.equals(String.valueOf(map.get("returnCode"))))
            {
                map.put("result","成功");
            }

            list.add(map);
        }

        return list;
    }


    @Override
    public int getTransferRecordsCount(Map<String, Object> param)
    {
        return trs001007mapper.getTransferRecordsCount(param);
    }

    @Override
    public String getTransferName(String regName)
    {
        List<SYN001001Resp> syn001001Resps = syn001001Mapper.selectByUser(regName);
        if (syn001001Resps != null && syn001001Resps.size() > 0)
        {
            return syn001001Resps.get(0).getUserName();
        }
        return null;
    }

}
