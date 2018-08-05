package com.yuanheng100.settlement.ghbank.consts;

import java.util.HashMap;
import java.util.Map;

import com.yuanheng100.exception.IllegalPlatformAugumentException;

/**
 * Created by jlqian on 2017/4/19.
 */
public enum WithdrawStatus
{
    
    /**
     * 订单完成
     */
    ORDER_COMPLETED((short)10, "ORDER_COMPLETED"),
    /**
     * 订单预授权中（非实时到账，约1小时到账）
     */
    ORDER_PRE_AUTHING((short)20, "ORDER_PRE_AUTHING");

    private final short code;

    private final String name;
    
    private WithdrawStatus(short code, String name)
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

    static Map<String, WithdrawStatus> statusMap = new HashMap<String, WithdrawStatus>();
    
    /**
     * 初始化
     */
    static
    {
        for (WithdrawStatus eachStatus : WithdrawStatus.values())
        {
            statusMap.put(eachStatus.name, eachStatus);
        }
        
    }
    
    /**
     * 根据返回的字符串，返回其对应的提现结果枚举类
     * @param statusText
     * @return
     * @throws IllegalPlatformAugumentException
     */
    public static WithdrawStatus parseWithdrawStatusByText(String statusText) throws IllegalPlatformAugumentException
    {
        if(statusText!= null)
        {
            if(statusMap.containsKey(statusText))
            {
                return statusMap.get(statusText);
            }
            else
            {
                throw new IllegalPlatformAugumentException("解析提现结果时，出现不能识别的状态码："+statusText);
            }
        }
        else
        {
            throw new IllegalPlatformAugumentException("解析提现结果时，出现空的状态码：");
        }
    }
}
