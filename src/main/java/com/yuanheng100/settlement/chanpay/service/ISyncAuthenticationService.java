package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.common.model.system.Page;

import java.util.HashMap;

/**
 * Created by jlqian on 2016/9/8.
 */
public interface ISyncAuthenticationService {

    /**
     * 同步实名认证
     * @param g60009Bean
     * @return
     * @throws IllegalPlatformAugumentException
     */
    boolean authenticate(G60009Bean g60009Bean) throws IllegalPlatformAugumentException;

    /**
     * 获取同步实名认证的列表
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<G60009Bean> page);
}
