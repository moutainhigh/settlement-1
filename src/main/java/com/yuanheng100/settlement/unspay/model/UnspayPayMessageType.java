package com.yuanheng100.settlement.unspay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-28.
 */
public enum UnspayPayMessageType {

    PAY((short) 1, "委托代付"),
    QUERYORDER((short) 2, "订单查询"),
    QUERYBALANCE((short) 2, "余额查询");

    private Short code;
    private String desc;

    private UnspayPayMessageType(Short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static Map<Short, UnspayPayMessageType> unspayPayMessageTypeMap = new HashMap<Short, UnspayPayMessageType>();

    static {
        UnspayPayMessageType[] values = UnspayPayMessageType.values();
        for (UnspayPayMessageType unspayPayMessageType : values) {
            unspayPayMessageTypeMap.put(unspayPayMessageType.getCode(), unspayPayMessageType);
        }
    }

    public static UnspayPayMessageType getUnspayPayMessageTypeByCode(Short code) {
        return unspayPayMessageTypeMap.get(code);
    }

}
