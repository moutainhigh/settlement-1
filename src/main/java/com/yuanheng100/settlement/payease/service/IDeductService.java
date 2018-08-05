package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代扣服务接口，TRS001008
 * @author Bai Song
 *
 */
public interface IDeductService
{

    /**
     * 代扣到投资账户/用代扣向虚拟账户充值，TRS001008-20000
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ID_TYPE:开户证件类型
     *          ID:证件号
     *          ACC_NAME:开户名
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:代扣金额
     *
     * @param deductReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001008Resp deductToInvest(TRS001008Req deductReq) throws IllegalPlatformAugumentException;
    
    /**
     * 代扣到负债账户/用代扣进行还款，TRS001008-20001
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ID_TYPE:开户证件类型
     *          ID:证件号
     *          ACC_NAME:开户名
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:代扣金额
     *
     * @param deductReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001008Resp deductToRepay(TRS001008Req deductReq) throws IllegalPlatformAugumentException;
    
    /**
     * 代扣查询，TRS001008-20002
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ID_TYPE:开户证件类型
     *          ID:证件号
     *          ACC_NAME:开户名
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:代扣金额
     *
     * @param deductReq
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001008Resp queryDeduct(TRS001008Req deductReq) throws IllegalPlatformAugumentException;

    /**
     * 更新代扣结果
     * @param trs001008Resp
     */
    void updateResult(TRS001008Resp trs001008Resp);

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     * @param returnCode
     * @return
     */
    List<TRS001008Req> queryLocalWithReturnCode(String returnCode);

    /**
     * 获取代扣页面的列表
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

}
