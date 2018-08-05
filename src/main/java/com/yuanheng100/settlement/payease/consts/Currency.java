package com.yuanheng100.settlement.payease.consts;

/**
 * 首信易对货币的规定代码
 * @author Bai Song
 *
 */
public enum Currency
{

    /**
     * 人民币
     */
    RMB("CNY","人民币"),
    
    ;
    
    private final String code;

    private final String name;

    private Currency(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }
    
}
