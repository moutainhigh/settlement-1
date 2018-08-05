package com.yuanheng100.settlement.chanpay.util;

import com.yuanheng100.settlement.chanpay.callback.IPayCallbackListener;
import com.yuanheng100.settlement.chanpay.callback.IRechargeCallbackListener;

import java.util.HashMap;

/**
 * Created by jlqian on 2016/10/19.
 */
public class CallbackListenerContainer
{
    /**
     * 存放rechargeCallbackListener容器
     */
    private static HashMap<String,IRechargeCallbackListener> rechargeCallbackListeners = new HashMap<String, IRechargeCallbackListener>();

    /**
     * 存放payCallbackListeners容器
     */
    private static HashMap<String,IPayCallbackListener> payCallbackListeners = new HashMap<String, IPayCallbackListener>();

    /**
     * 存放rechargeCallbackListener
     * @param outTradeNo
     * @param rechargeCallbackListener
     */
    public static void putRechargeCallbackListener(String outTradeNo,IRechargeCallbackListener rechargeCallbackListener){
        rechargeCallbackListeners.put(outTradeNo,rechargeCallbackListener);
    }

    /**
     * 获取rechargeCallbackListener
     * @param outTradeNo
     */
    public static IRechargeCallbackListener getRechargeCallbackListener(String outTradeNo){
        return rechargeCallbackListeners.get(outTradeNo);
    }

    /**
     * 移除rechargeCallbackListener
     * @param outTradeNo
     */
    public static IRechargeCallbackListener removeRechargeCallbackListener(String outTradeNo){
        return rechargeCallbackListeners.remove(outTradeNo);
    }

    /**
     * 存放payCallbackListeners
     * @param reqSn
     * @param payCallbackListener
     */
    public static void putPayCallbackListener(String reqSn,IPayCallbackListener payCallbackListener){
        payCallbackListeners.put(reqSn,payCallbackListener);
    }

    /**
     * 获取payCallbackListeners
     * @param trxReqSn
     */
    public static IPayCallbackListener getPayCallbackListener(String trxReqSn){
        return payCallbackListeners.get(trxReqSn);
    }

    /**
     * 移除payCallbackListeners
     * @param trxReqSn
     */
    public static IPayCallbackListener removePayCallbackListener(String trxReqSn){
        return payCallbackListeners.remove(trxReqSn);
    }


}
