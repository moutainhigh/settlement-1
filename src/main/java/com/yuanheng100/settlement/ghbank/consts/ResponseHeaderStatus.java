package com.yuanheng100.settlement.ghbank.consts;

/**
 * 华兴Response消息头的status（业务状态）字段
 * 
 * @author Baisong
 */
public enum ResponseHeaderStatus
{

    SUCCESS((short)0, "受理成功"),
    
    FAILED((short)1, "受理失败"),
    
    WORKIING((short)2, "受理中"),
    
    OVERTIME((short)3, "受理超时，不确定");
    
    private final short code;

    private final String name;
    
    private ResponseHeaderStatus(short code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public short getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }
    
}
