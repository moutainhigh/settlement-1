package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Req;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;

import java.util.HashMap;
import java.util.Map;

/**
 * 投标冻结/解冻服务接口
 * @author Bai Song
 *
 */
public interface IInvestService
{

    /**
     * 投标冻结资金 TRS001002-12000
     * @param freezeReq
     * 必要参数：
     *      SERL_NUM：交易流水号
     *      AUTH_ID：授权号
     *      BORROWER：P2P网站借款人（转入方）注册名
     *      BORROWER_ID：借款人证件号
     *      BORROWER_NAME：借款款人姓名
     *      CONTRACT_NUM：借款标的号（合同号）
     *      CERT_NUL：出借借据编号（凭证）
     *      CURR：币种（默认为人名币）
     *      LOAN_AMOUNT：出借金额
     *      LENDER：P2P网站出借人（转出方）注册名
     * @return
     * @throws IllegalArgumentException
     */
    public TRS001002Resp investFreeze(TRS001002Req freezeReq) throws IllegalPlatformAugumentException;
    
    /**
     * 解冻投标资金 TRS001002-12001
     * @param unfreezeReq
     * 必要参数：
     *      SERL_NUM：交易流水号
     *      AUTH_ID：授权号
     *      BORROWER：P2P网站借款人（转入方）注册名
     *      BORROWER_ID：借款人证件号
     *      BORROWER_NAME：借款款人姓名
     *      CONTRACT_NUM：借款标的号（合同号）
     *      CERT_NUL：出借借据编号（凭证）
     *      CURR：币种（默认为人名币）
     *      LOAN_AMOUNT：出借金额
     *      LENDER：P2P网站出借人（转出方）注册名
     * @return
     * @throws IllegalArgumentException
     */
    public TRS001002Resp unfreeze(TRS001002Req unfreezeReq) throws IllegalPlatformAugumentException;

    /**
     * 获取投资页面的列表
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
    

}
