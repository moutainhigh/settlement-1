package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IWithdrawCallbackListener;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现服务 TRS001006
 */
public interface IWithdrawService
{
    /**
     * TRS001006-18000 用户从出借虚拟账户提现到银行卡
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ACC_NAME:开户名
     *          ACC_BANK:开户银行名称
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:提现至银行卡金额【手续费为：config.propertis 中 payease.withdraw.fee.public的值】
     * 可选参数：FEE:手续费【若设置，则覆盖配置文件默认值】
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001006Resp withdrawFromLendAccount(TRS001006Req trs001006req) throws IllegalPlatformAugumentException;

    /**
     * TRS001006-18000 用户从出借虚拟账户提现到银行卡
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ACC_NAME:开户名
     *          ACC_BANK:开户银行名称
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:提现至银行卡金额【手续费为：config.propertis 中 payease.withdraw.fee.public的值】
     * 可选参数：FEE:手续费【若设置，则覆盖配置文件默认值】
     *
     * @param trs001006req
     * @param withdrawCallbackListener
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001006Resp withdrawFromLendAccount(TRS001006Req trs001006req, IWithdrawCallbackListener withdrawCallbackListener) throws IllegalPlatformAugumentException;

    /**
     * TRS001006-18001 用户从借款虚拟账户提现到银行卡
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ACC_NAME:开户名
     *          ACC_BANK:开户银行名称
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:提现至银行卡金额【手续费为：config.propertis 中 payease.withdraw.fee.public的值】
     * 可选参数：FEE:手续费【若设置，则覆盖配置文件默认值】
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001006Resp withdrawFromBorrowerAccount(TRS001006Req trs001006req) throws IllegalPlatformAugumentException;

    /**
     * TRS001006-18002 查询订单提现处理结果
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ACC_NAME:开户名
     *          ACC_BANK:开户银行名称
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:提现至银行卡金额【手续费为：config.propertis 中 payease.withdraw.fee.public的值】
     * 可选参数：FEE:手续费【若设置，则覆盖配置文件默认值】
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001006Resp queryWithdrawResult(TRS001006Req trs001006req) throws IllegalPlatformAugumentException;

    /**
     * TRS001006-18003 P2P平台划拨款（与18001操作在功能实现上没有区别，仅作为区分平台划拨与客户自助划拨）
     *
     * 必填参数:SERL_NUM:交易流水号
     *          UER:P2P网站注册名
     *          ACC_NAME:开户名
     *          ACC_BANK:开户银行名称
     *          ACC_NUM:银行账号
     *          ACC_TYPE:账号类型
     *          ACC_PROP:账号类别
     *          AMOUNT:提现至银行卡金额【手续费为：config.propertis 中 payease.withdraw.fee.public的值】
     * 可选参数：FEE:手续费【若设置，则覆盖配置文件默认值】
     *
     * @param trs001006req
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public TRS001006Resp withdrawFromBorrowerAccountByPlatform(TRS001006Req trs001006req) throws IllegalPlatformAugumentException;

    /**
     * 获取所有结果码为returnCode的记录：若参数为NULL。则返回ReturnCode IS NULL 的记录
     * @return
     */
    public List<TRS001006Req> queryLocalWithReturnCode(String returnCode);

    /**
     * 获取提现页面的列表
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 回调更新提现结果
     * @param trs001006Resp
     */
    void updateResult(TRS001006Resp trs001006Resp);
}
