package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import com.yuanheng100.settlement.common.model.system.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/7.
 */
public interface IRechargeService {

    /**
     * 通过Q20003Bean中的属性生成充值地址
     * @param q20003Bean
     * @return
     * @throws IllegalPlatformAugumentException
     */
    String getRechargeUrl(Q20003Bean q20003Bean) throws IllegalPlatformAugumentException;

    /**
     * 通过Q20003Bean中的属性生成充值地址，并为回调做准备
     * @param q20003Bean
     * @param rechargeCallbackListener
     * @return
     * @throws IllegalPlatformAugumentException
     */
    String getRechargeUrl(Q20003Bean q20003Bean, IRechargeCallbackListener rechargeCallbackListener) throws IllegalPlatformAugumentException;

    /**
     * 获取充值记录列表
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String,Object>> page);
}
