package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IPayCallbackListener;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.common.model.system.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/10.
 */
public interface  IPayService {

    boolean pay(G10001Bean bean) throws IllegalPlatformAugumentException;

    G10001Bean pay(G10001Bean bean , IPayCallbackListener payCallbackListener) throws IllegalPlatformAugumentException;

    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
}
