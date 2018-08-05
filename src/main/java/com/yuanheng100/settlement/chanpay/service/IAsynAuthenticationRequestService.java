package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IAsynAuthenticationCallbackListener;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.common.model.system.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/8.
 */
public interface IAsynAuthenticationRequestService
{

    /**
     * 发送异步身份认证请求
     *
     * @param data
     * @return
     * @throws IllegalPlatformAugumentException
     */
    boolean authenticate(G60001Bean data, IAsynAuthenticationCallbackListener listener) throws IllegalPlatformAugumentException;

    /**
     * 查询发送过异步实名认证请求但是木有进行查询的G60001bean
     *
     * @return
     */
    List<G60001Bean> withoutQuery();

    /**
     * 获取异步实名认证的列表
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
}
