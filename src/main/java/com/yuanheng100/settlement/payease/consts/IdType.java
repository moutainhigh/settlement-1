package com.yuanheng100.settlement.payease.consts;

/**
 * 首信易-证件类型
 * @author Bai Song
 *
 */
public enum IdType
{

    IDENTITY_CARD("01", "身份证"),
    
    HU_KOU_BEN("02", "户口本"),
    
    PASSPORT("03", "护照"),
    ;
    
    private final String code;

    private final String name;

    private IdType(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
}
