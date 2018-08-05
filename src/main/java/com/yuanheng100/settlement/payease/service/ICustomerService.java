package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IBindBankCardCallbackListener;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金清算中，关于客户开户等相关的服务接口 SYN001001
 *
 * @author Bai Song
 */
public interface ICustomerService
{

    /**
     * 账户注册SYN001001-10000
     * <p/>
     * 账户同步开通
     * <p/>
     * 如果只是开通账户：
     * 必填参数:USER:P2P网站注册名
     * <p/>
     * 首信易建议：一次性将用户注册ID、身份证信息、提现银行卡信息一次性同步过来
     * 必填参数:UER:P2P网站注册名
     * ID:证件号
     * USER_NAME:经过认证的（身份证或其他证件的用户名
     * ACC_BANK:开户银行名称
     * ACC_NUM:银行账号
     * ACC_TYPE:账号类型
     * ACC_PROP:账号类别
     *
     * @param userRegisterReq
     */
    public SYN001001Resp register(SYN001001Req userRegisterReq) throws IllegalPlatformAugumentException;


    /**
     * 同步身份信息SYN001001-10001
     * <p/>
     * 同步用户身份信息
     * <p/>
     * 必填项：UER:P2P网站注册名
     * ID:证件号
     * USER_NAME:经过认证的（身份证或其他证件的用户名
     *
     * @param syncIdentityReq
     * @return
     * @throws IllegalArgumentException
     */
    public SYN001001Resp syncIdentity(SYN001001Req syncIdentityReq) throws IllegalPlatformAugumentException;


    /**
     * 绑定银行卡
     * <p/>
     * 必填项：UER:P2P网站注册名
     * ACC_NAME:开户名
     * ACC_BANK:开户银行名称
     * ACC_NUM:银行账号
     * ACC_TYPE:账号类型
     * ACC_PROP:账号类别
     *
     * @param bindCardReq
     */
    public SYN001001Resp bindBankCard(SYN001001Req bindCardReq) throws IllegalPlatformAugumentException;

    /**
     * 绑定银行卡
     * <p/>
     * 必填项：UER:P2P网站注册名
     * ACC_NAME:开户名
     * ACC_BANK:开户银行名称
     * ACC_NUM:银行账号
     * ACC_TYPE:账号类型
     * ACC_PROP:账号类别
     *
     * @param bindCardReq
     */
    public SYN001001Resp bindBankCard(SYN001001Req bindCardReq, IBindBankCardCallbackListener listener) throws IllegalPlatformAugumentException;


    /**
     * 查询账户是否开通SYN001001-10003
     * <p/>
     * 必填参数:USER:P2P网站注册名
     *
     * @param customerQueryReq
     */
    public SYN001001Resp queryAccount(SYN001001Req customerQueryReq) throws IllegalPlatformAugumentException;

    /**
     * 查询账户银行卡验证结果
     * <p/>
     * 必填参数:UER:P2P网站注册名
     * ID:证件号
     * USER_NAME:经过认证的（身份证或其他证件的用户名
     * ACC_NAME:开户名
     * ACC_BANK:开户银行名称
     * ACC_NUM:银行账号
     * ACC_TYPE:账号类型
     * ACC_PROP:账号类别
     *
     * @param bankCardQueryReq
     * @return
     * @throws IllegalArgumentException
     */
    public SYN001001Resp queryBankCard(SYN001001Req bankCardQueryReq) throws IllegalPlatformAugumentException;

    /**
     * 获取账户页面的列表
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 通过user获取唯一的SYN001001
     *
     * @param user
     * @return
     */
    List<SYN001001Resp> getByUser(String user);


    /**
     * 获取所有交易列表
     *
     * @param searchConditions
     * @param page
     */
    void getTradeListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 通过user获取绑卡成功的记录
     *
     * @param user
     * @return
     */
    SYN001001Resp getBindCardSuccessCustomerByUser(String user);

    /**
     * 根据Msgid更新绑卡状态
     *
     * @param syn001001Resp
     */
    void updateBindResult(SYN001001Resp syn001001Resp);

    /**
     * SYN001001表下一个user
     *
     * @return
     */
    String nextUser();

}
