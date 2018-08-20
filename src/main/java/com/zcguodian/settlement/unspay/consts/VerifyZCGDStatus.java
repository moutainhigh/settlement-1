package com.zcguodian.settlement.unspay.consts;

public enum VerifyZCGDStatus {

    ZCGD_NOTAUDITED((short) 0, "未审核"),

    ZCGD_APPROVE((short) 1, "审核通过"),

    ZCGD_DISAPPROVE((short) 2, "审核拒绝");

    private Short code;

    private String desc;

    private VerifyZCGDStatus(Short code, String desc) {
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
