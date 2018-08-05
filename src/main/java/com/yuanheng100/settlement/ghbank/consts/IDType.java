package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/19.
 */
public enum IDType
{
    ID_CARD((short)1010);

    private Short type;

    private IDType(Short type)
    {
        this.type = type;
    }

    public Short getType()
    {
        return type;
    }
}
