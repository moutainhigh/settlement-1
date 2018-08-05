package com.yuanheng100.settlement.unspay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于判定消息类型的枚举类
 * Created by qianjl on 2016-6-28.
 */
public enum UnspaySubContractMessageType {

    SIGN((short)1,"子协议录入"),
    QUERY((short)2,"子协议查询"),
    EXTENSION((short)3,"子协议延期");

    private Short code;
    private String desc;
    private UnspaySubContractMessageType(Short code, String desc) {
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

    private static Map<Short,UnspaySubContractMessageType> unspaySubContractMessageTypeMap = new HashMap<Short,UnspaySubContractMessageType>();

    static{
        UnspaySubContractMessageType[] values = UnspaySubContractMessageType.values();
        for (UnspaySubContractMessageType unspaySubContractMessageType:values) {
            unspaySubContractMessageTypeMap.put(unspaySubContractMessageType.getCode(),unspaySubContractMessageType);
        }
    }

    public static UnspaySubContractMessageType getUnspaySubContractMessageTypeByCode(Short code){
        return unspaySubContractMessageTypeMap.get(code);
    }
}
