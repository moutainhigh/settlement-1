package com.yuanheng100.settlement.chanpay.consts;

import com.yuanheng100.util.ConfigFile;

/**
 * Created by jlqian on 2016/9/7.
 */
public class Qpay {
    /** 畅捷通在线收款相关 */
    public static final String QPAY_MERCHANT_PUBLIC_KEY;
    public static final String QPAY_MERCHANT_PRIVATE_KEY;
    public static final String QPAY_PARTNER_ID;
    public static final String QPAY_QPAY_URL;
    public static final String QPAY_RETURN_URL;
    public static final String QPAY_NOTIFY_URL;
    public static final String QPAY_PRODUCT_CODE_RECHARGE;

    static {
        QPAY_MERCHANT_PUBLIC_KEY = ConfigFile.getProperty("cj.merchant.public.key");
        QPAY_MERCHANT_PRIVATE_KEY = ConfigFile.getProperty("cj.merchant.private.key");
        QPAY_PARTNER_ID = ConfigFile.getProperty("cj.partner.id");
        QPAY_QPAY_URL = ConfigFile.getProperty("cj.qpay.url");
        QPAY_RETURN_URL = ConfigFile.getProperty("cj.return.url");
        QPAY_NOTIFY_URL = ConfigFile.getProperty("cj.notify.url");
        QPAY_PRODUCT_CODE_RECHARGE = ConfigFile.getProperty("cj.product.code.recharge");
    }

    public static final String VERSION = "1.0";
    public static final String IS_SUCCESS_TRUE = "T";

    public static final short PAY_METHOD_直连 = 1;
    public static final short PAY_METHOD_收银台 = 2;
    public static final short PAY_METHOD_余额支付 = 3;

    public static final String PAY_TYPE_对公_借记 = "B,DC";
    public static final String PAY_TYPE_对公_贷记 = "B,CC";
    public static final String PAY_TYPE_对公_综合 = "B,GC";
    public static final String PAY_TYPE_对私_借记 = "C,DC";
    public static final String PAY_TYPE_对私_贷记 = "C,CC";
    public static final String PAY_TYPE_对私_综合 = "C,GC";


    public static final String SERVICE_Q20003 = "cjt_create_instant_trade";
    public static final String SERVICE_Q20007 = "cjt_get_paychannel";

    /*通知交易状态*/
    public enum NotifyTradeStatus{
        WAIT_PAY,//交易创建，等待买家付款
        WAIT_BUYER_PAY,//买家付款提交中
        TRADE_SUCCESS,//交易成功
        TRADE_FINISHED,//交易结束
        TRADE_CLOSED;//交易关闭
    }

    
}
