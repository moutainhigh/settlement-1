package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;
import com.yuanheng100.settlement.payease.model.recharge.RechargeFrontResp;
import com.yuanheng100.settlement.payease.model.recharge.RechargeReq;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/7.
 */
public interface IRechargeService
{
    /**
     * 获取充值信息
     *
     * @param v_amount    订单总金额
     * @param v_ordername 订货人姓名
     * @param v_pmode     支付方式编号
     * @param v_url       返回商户页面地址
     * @param v_merdata1  会员编号
     * @param v_merdata3  证件号码
     * @param v_merdata4  真实姓名
     * @param v_merdata5  手机号码
     * @return
     */
    RechargeReq getRechargeModel(BigDecimal v_amount, String v_ordername, Integer v_pmode, String v_url, String v_merdata1, String v_merdata3, String v_merdata4, String v_merdata5) throws IOException;

    /**
     * 获取充值信息
     *
     * @param v_amount                 订单总金额
     * @param v_ordername              订货人姓名
     * @param v_pmode                  支付方式编号
     * @param v_url                    返回商户页面地址
     * @param v_merdata1               会员编号
     * @param v_merdata3               证件号码
     * @param v_merdata4               真实姓名
     * @param v_merdata5               手机号码
     * @param rechargeCallbackListener
     * @return
     */
    RechargeReq getRechargeModel(BigDecimal v_amount, String v_ordername, Integer v_pmode, String v_url, String v_merdata1, String v_merdata3, String v_merdata4, String v_merdata5, IRechargeCallbackListener rechargeCallbackListener) throws IOException;

    /**
     * 校验前台通知
     *
     * @param rechargeFrontResp
     * @return
     */
    boolean verifyRechargeFrontResp(RechargeFrontResp rechargeFrontResp);

    /**
     * MD5校验
     *
     * @param source_v_md5
     * @param v_md5
     * @return
     * @throws IOException
     */
    boolean verifyMd5(String source_v_md5, String v_md5) throws IOException;

    /**
     * RSA校验
     *
     * @param source_v_sign
     * @param v_sign
     * @return
     * @throws IOException
     */
    boolean verifySign(String source_v_sign, String v_sign) throws IOException;

    /**
     * 保存充值结果，并对P2P进行回调
     *
     * @param rechargeBackResps
     */
    void saveRechargeResult(RechargeBackResp[] rechargeBackResps) throws IllegalPlatformAugumentException;

    /**
     * 充值列表页面
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
}
