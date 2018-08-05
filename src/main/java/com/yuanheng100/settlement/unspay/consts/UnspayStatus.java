package com.yuanheng100.settlement.unspay.consts;

/**
 * Created by jlqian on 2016/8/11.
 */
public enum UnspayStatus {

    SUCCESS("0000", "操作成功"),

    HANDDING("10", "处理中"),

    INSUFFICIENT_BALANCE("1111", "余额不足"),

    FAILURE("20", "操作失败");

    private String code;

    private String desc;

    private UnspayStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
