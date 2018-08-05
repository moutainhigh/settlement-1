package com.yuanheng100.xiaodai.core.consts.loan;

/**
 * Created by jlqian on 2016/8/22.
 */
public enum RepayPromiseStatus {

    //-----------------总部划扣-----------------

    HQ_DEDUCT_MIN                             ((short) 100, "总部划扣MIN"),

    HQ_DEDUCT_WAIT                  	       ((short) 110, "待总部划扣"),

    HQ_DEDUCT_FAILED                        ((short) 140, "划扣失败"),

    HQ_DEDUCT_SUCCESS                    ((short) 160, "划扣成功"),

    HQ_DEDUCT_MAX                             ((short) 199, "总部划扣MAX"),

    //-----------------门店实时划扣-----------------

    SHOP_REALTIME_MIN                       ((short) 200, "实时划扣MIN"),

    SHOP_REALTIME_WAITING              ((short) 220, "实时划扣排队中"),

    SHOP_REALTIME_PROCESSING       ((short) 230, "实时划扣处理中"),

    SHOP_REALTIME_FAILED                 ((short) 240, "实时划扣失败"),

    SHOP_REALTIME_NO_MONEY          ((short) 241, "实时-余额不足"),

    SHOP_REALTIME_DENIED                ((short) 244, "实时划扣被拒"),

    SHOP_REALTIME_SUCCESS             ((short) 260, "实时划扣成功"),

    SHOP_REALTIME_MAX                       ((short) 299, "实时划扣MAX"),
    ;

    private final short code;

    private final String name;


    private RepayPromiseStatus(short code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public short getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

}
