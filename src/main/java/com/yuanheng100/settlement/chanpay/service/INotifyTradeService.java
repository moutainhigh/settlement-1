package com.yuanheng100.settlement.chanpay.service;

import java.util.HashMap;

/**
 * Created by jlqian on 2016/9/8.
 */
public interface INotifyTradeService {

    boolean notifyTrade(HashMap<String,String> stringStringHashMap);
}
