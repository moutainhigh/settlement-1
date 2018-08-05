package com.yuanheng100.settlement.payease.util;

import com.yuanheng100.settlement.payease.callback.IBindBankCardCallbackListener;
import com.yuanheng100.settlement.payease.callback.ICloseLoanCallbackListener;
import com.yuanheng100.settlement.payease.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.payease.callback.IWithdrawCallbackListener;

import java.util.HashMap;

/**
 * Created by jlqian on 2016/10/19.
 */
public class CallbackListenerContainer
{
    /**
     * 存放bindBankCardCallbackListener容器
     */
    private static HashMap<String, IBindBankCardCallbackListener> bindBankCardCallbackListeners = new HashMap<String, IBindBankCardCallbackListener>();

    /**
     * 存放rechargeCallbackListener容器
     */
    private static HashMap<String, IRechargeCallbackListener> rechargeCallbackListeners = new HashMap<String, IRechargeCallbackListener>();

    /**
     * 存放withdrawCallbackListener容器
     */
    private static HashMap<String, IWithdrawCallbackListener> withdrawCallbackListeners = new HashMap<String, IWithdrawCallbackListener>();

    /**
     * 存放closeLoanCallbackListener容器
     */
    private static HashMap<String,ICloseLoanCallbackListener> closeLoanCallbackListeners = new HashMap<String, ICloseLoanCallbackListener>();

    /**
     * 存放bindBankCardCallbackListener
     *
     * @param user
     * @param bindBankCardCallbackListener
     */
    public static void putBindBankCardCallbackListener(String user, IBindBankCardCallbackListener bindBankCardCallbackListener)
    {
        bindBankCardCallbackListeners.put(user, bindBankCardCallbackListener);
    }

    /**
     * 获取bindBankCardCallbackListener
     *
     * @param user
     */
    public static IBindBankCardCallbackListener getBindBankCardCallbackListener(String user)
    {
        return bindBankCardCallbackListeners.get(user);
    }

    /**
     * 移除bindBankCardCallbackListener
     *
     * @param user
     */
    public static IBindBankCardCallbackListener removeBindBankCardCallbackListener(String user)
    {
        return bindBankCardCallbackListeners.remove(user);
    }

    /**
     * 存放rechargeCallbackListener
     *
     * @param v_oid
     * @param rechargeCallbackListener
     */
    public static void putRechargeCallbackListener(String v_oid, IRechargeCallbackListener rechargeCallbackListener)
    {
        rechargeCallbackListeners.put(v_oid, rechargeCallbackListener);
    }

    /**
     * 获取rechargeCallbackListener
     *
     * @param v_oid
     */
    public static IRechargeCallbackListener getRechargeCallbackListener(String v_oid)
    {
        return rechargeCallbackListeners.get(v_oid);
    }

    /**
     * 移除rechargeCallbackListener
     *
     * @param v_oid
     */
    public static IRechargeCallbackListener removeRechargeCallbackListener(String v_oid)
    {
        return rechargeCallbackListeners.remove(v_oid);
    }

    /**
     * 存放withdrawCallbackListener
     *
     * @param serlNum
     * @param withdrawCallbackListener
     */
    public static void putWithdrawCallbackListener(String serlNum, IWithdrawCallbackListener withdrawCallbackListener)
    {
        withdrawCallbackListeners.put(serlNum, withdrawCallbackListener);
    }

    /**
     * 获取withdrawCallbackListener
     *
     * @param serlNum
     */
    public static IWithdrawCallbackListener getWithdrawCallbackListener(String serlNum)
    {
        return withdrawCallbackListeners.get(serlNum);
    }

    /**
     * 移除withdrawCallbackListener
     *
     * @param serlNum
     */
    public static IWithdrawCallbackListener removeWithdrawCallbackListener(String serlNum)
    {
        return withdrawCallbackListeners.remove(serlNum);
    }

    /**
     * 存放closeLoanCallbackListener
     *
     * @param serlNum
     * @param closeLoanCallbackListener
     */
    public static void putCloseLoanCallbackListener(String serlNum, ICloseLoanCallbackListener closeLoanCallbackListener)
    {
        closeLoanCallbackListeners.put(serlNum, closeLoanCallbackListener);
    }

    /**
     * 获取closeLoanCallbackListener
     *
     * @param serlNum
     */
    public static ICloseLoanCallbackListener getCloseLoanCallbackListener(String serlNum)
    {
        return closeLoanCallbackListeners.get(serlNum);
    }

    /**
     * 移除closeLoanCallbackListener
     *
     * @param serlNum
     */
    public static ICloseLoanCallbackListener removeCloseLoanCallbackListener(String serlNum)
    {
        return closeLoanCallbackListeners.remove(serlNum);
    }

}
