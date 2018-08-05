package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/27.
 * 对账类型
 */
public enum OperFlag
{
    /**
     * 金额类对账
     */
    AMOUNT((short) 0);

    private Short flag;

    private OperFlag(Short flag)
    {
        this.flag = flag;
    }

    public Short getFlag()
    {
        return flag;
    }

    public void setFlag(Short flag)
    {
        this.flag = flag;
    }
}
