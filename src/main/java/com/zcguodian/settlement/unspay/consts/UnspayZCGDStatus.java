package com.zcguodian.settlement.unspay.consts;

public enum UnspayZCGDStatus {

	ZCGD_SUCCESS("0000", "操作成功"),
	
	ZCGD_TRADE_SUCCESS("00", "交易成功"),

    ZCGD_HANDDING("10", "处理中"),

    ZCGD_INSUFFICIENT_BALANCE("1017", "余额不足"),

    ZCGD_FAILURE("20", "操作失败");

    private String code;

    private String desc;

    private UnspayZCGDStatus(String code, String desc) {
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
