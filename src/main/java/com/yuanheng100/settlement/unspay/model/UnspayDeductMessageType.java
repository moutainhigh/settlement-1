package com.yuanheng100.settlement.unspay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-28.
 */
public enum UnspayDeductMessageType {

    DEDUCT((short)1,"委托代扣"),
    QUERYORDER((short)2,"订单查询");

    private Short code;
    private String desc;
    private UnspayDeductMessageType(Short code, String desc) {
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

    private static Map<Short,UnspayDeductMessageType> unspayDeductMessageTypeMap = new HashMap<Short,UnspayDeductMessageType>();

    static{
        UnspayDeductMessageType[] values = UnspayDeductMessageType.values();
        for (UnspayDeductMessageType unspayDeductMessageType:values) {
            unspayDeductMessageTypeMap.put(unspayDeductMessageType.getCode(),unspayDeductMessageType);
        }
    }

    public static UnspayDeductMessageType getUnspayDeductMessageTypeByCode(Short code){
        return unspayDeductMessageTypeMap.get(code);
    }


}
