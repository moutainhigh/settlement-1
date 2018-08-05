package com.yuanheng100.settlement.ghbank.consts;

/**
 * APPID
 * 
 * Created by jlqian on 2017/4/18.
 */
public enum AppId
{
    PC("PC", "个人电脑"),

    APP("APP", "手机"),

    WX("WX", "微信");

    private final String code;

    private final String name;

    private AppId(String code, String name)
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
    
    /**
     * 把得到的appId字符串解析成枚举类。如果不是微信（WX）或手机应用（APP），则都解析为电脑（PC）
     * 
     * @param appIdCode
     * @return
     */
    public static AppId parseString(String appIdCode)
    {
        if(appIdCode==null)
        {
            return AppId.PC;
        }
        else if (appIdCode.equalsIgnoreCase(WX.getCode()))
        {
            return AppId.WX;
        }
        else if (appIdCode.equalsIgnoreCase(APP.getCode()))
        {
            return AppId.APP;
        }
        else
        {
            return AppId.PC;
        }
    }
}
