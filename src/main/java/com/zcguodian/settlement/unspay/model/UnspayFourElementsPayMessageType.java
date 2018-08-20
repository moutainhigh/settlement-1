package com.zcguodian.settlement.unspay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-28.
 */
public enum UnspayFourElementsPayMessageType {

    ZCGD_PAY((short) 1, "实时代付"),
    ZCGD_QUERYORDER((short) 2, "订单查询"),
    ZCGD_QUERYBALANCE((short) 3, "余额查询");

    private Short code;
    private String desc;

    private UnspayFourElementsPayMessageType(Short code, String desc) {
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

    private static Map<Short, UnspayFourElementsPayMessageType> unspayZCGDPayMessageTypeMap = new HashMap<Short, UnspayFourElementsPayMessageType>();

    static {
        UnspayFourElementsPayMessageType[] values = UnspayFourElementsPayMessageType.values();
        for (UnspayFourElementsPayMessageType unspayPayMessageType : values) {
            unspayZCGDPayMessageTypeMap.put(unspayPayMessageType.getCode(), unspayPayMessageType);
        }
    }

    public static UnspayFourElementsPayMessageType getUnspayPayMessageTypeByCode(Short code) {
        return unspayZCGDPayMessageTypeMap.get(code);
    }

}
