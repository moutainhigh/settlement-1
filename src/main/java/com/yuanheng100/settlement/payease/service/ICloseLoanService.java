package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.payease.callback.ICloseLoanCallbackListener;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Req;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;

/**
 * TRS001003 - 标的成功通知
 * @author Bai Song
 *
 */
public interface ICloseLoanService
{
    /**
     * 标的成功。TRS001003-14000<br>
     * <br>
     * 首信易收到通知后，将原标的下出借人虚拟账户内的冻结资金扣减，并转账给借款人账户
     * @param closeLoanReq
     *   必要参数：
     *      SERL_NUM：交易流水号
     *      BORROWER：P2P网站借款人（转入方）注册名
     *      BORROWER_ID：借款人证件号
     *      BORROWER_NAME：借款人姓名
     *      CONTRACT_NUM：借款标的号（合同号）
     *      CURR：币种（默认为人名币）
     *      TOTAL_AMOUNT：借款总额
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001003Resp closeLoan(TRS001003Req closeLoanReq) throws IllegalPlatformAugumentException;

    /**
     * 标的成功。TRS001003-14000<br>
     * <br>
     * 首信易收到通知后，将原标的下出借人虚拟账户内的冻结资金扣减，并转账给借款人账户
     * @param closeLoanReq
     *   必要参数：
     *      SERL_NUM：交易流水号
     *      BORROWER：P2P网站借款人（转入方）注册名
     *      BORROWER_ID：借款人证件号
     *      BORROWER_NAME：借款人姓名
     *      CONTRACT_NUM：借款标的号（合同号）
     *      CURR：币种（默认为人名币）
     *      TOTAL_AMOUNT：借款总额
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001003Resp closeLoan(TRS001003Req closeLoanReq, ICloseLoanCallbackListener closeLoanCallbackListener) throws IllegalPlatformAugumentException;

    /**
     * 查询标的转出结果。TRS001003-14001<br>
     * @param queryCloseLoanReq
     *   必要参数：
     *      SERL_NUM：交易流水号
     *      BORROWER：P2P网站借款人（转入方）注册名
     *      BORROWER_ID：借款人证件号
     *      BORROWER_NAME：借款人姓名
     *      CONTRACT_NUM：借款标的号（合同号）
     *      CURR：币种（默认为人名币）
     *      TOTAL_AMOUNT：借款总额
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001003Resp queryCloseLoan(TRS001003Req queryCloseLoanReq) throws IllegalPlatformAugumentException;

    /**
     * 根据serlNum更新结果
     * @param trs001003Resp
     */
    void updateResult(TRS001003Resp trs001003Resp);
}
