package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;

import java.util.List;
import java.util.Map;

/**
 * 转账服务
 * @author Bai Song
 *
 */
public interface ITransferService
{
    /**
     * TRS001007-240001 查询
     *
     * 必填参数:SERL_NUM:交易流水号
     *          TRANSFER_OUT_USER:转出方P2P网站注册名
     *          TRANSFER_IN_USER:转入方P2P网站注册名
     *          TRANSFER_AMOUNT:转账金额
     *          TRANSFER_OUT_USER_FEE:转出方手续费
     *          TRANSFER_IN_USER_FEE:转入方手续费
     *
     * @param transferReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001007Resp queryTransferResult(TRS001007Req transferReq) throws IllegalPlatformAugumentException;

    /**
     * TRS001007-240002 A用户投资账户向B用户投资账户转账，主要用于债权买卖
     *
     * 必填参数:SERL_NUM:交易流水号
     *          TRANSFER_OUT_USER:转出方P2P网站注册名
     *          TRANSFER_IN_USER:转入方P2P网站注册名
     *          TRANSFER_AMOUNT:转账金额
     *          TRANSFER_OUT_USER_FEE:转出方手续费
     *          TRANSFER_IN_USER_FEE:转入方手续费
     *
     * @param transferReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001007Resp transferBetweenInvestAccount(TRS001007Req transferReq) throws IllegalPlatformAugumentException;

    /**
     * TRS001007-240004 A用户投资账户向B用户负债账户转账
     *
     * 必填参数:SERL_NUM:交易流水号
     *          TRANSFER_OUT_USER:转出方P2P网站注册名
     *          TRANSFER_IN_USER:转入方P2P网站注册名
     *          TRANSFER_AMOUNT:转账金额
     *          TRANSFER_OUT_USER_FEE:转出方手续费
     *          TRANSFER_IN_USER_FEE:转入方手续费
     *
     * @param transferReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001007Resp transferInvestToLiability(TRS001007Req transferReq) throws IllegalPlatformAugumentException;
    
    /**
     * TRS001007-240005 A用户负债账户向B用户投资账户转账
     *
     * 必填参数:SERL_NUM:交易流水号
     *          TRANSFER_OUT_USER:转出方P2P网站注册名
     *          TRANSFER_IN_USER:转入方P2P网站注册名
     *          TRANSFER_AMOUNT:转账金额
     *          TRANSFER_OUT_USER_FEE:转出方手续费
     *          TRANSFER_IN_USER_FEE:转入方手续费
     *
     * @param transferReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001007Resp transferLiabilityToInvest(TRS001007Req transferReq) throws IllegalPlatformAugumentException;

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     * @param returnCode
     * @return
     */
    List<TRS001007Req> queryLocalWithReturnCode(String returnCode);


    /**
     * 转账列表
     * @param param
     * @return
     */
    List<Map<String,Object>> getTransferRecords(Map<String, Object> param);

    /**
     * 转账列表数量
     * @param param
     * @return
     */
    int getTransferRecordsCount(Map<String, Object> param);

    /**
     * 查询用户姓名
     * @param regName
     * @return
     */
    String getTransferName(String regName);
}
