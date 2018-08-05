package com.yuanheng100.settlement.payease.consts;

/**
 * Created by jlqian on 2017/2/7.
 */
public enum MoneyType
{
    CNY((short) 0, "人民币"),

    DOLLAR((short) 1, "美元");

    private Short code;

    private String name;

    private MoneyType(Short code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public Short getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

}
