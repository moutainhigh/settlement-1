package com.yuanheng100.settlement.unspay.consts;

/**
 * Created by jlqian on 2016/8/11.
 */
public enum VerifyStatus {

    NOTAUDITED((short) 0, "未审核"),

    APPROVE((short) 1, "审核通过"),

    DISAPPROVE((short) 2, "审核拒绝");

    private Short code;

    private String desc;

    private VerifyStatus(Short code, String desc) {
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
}
