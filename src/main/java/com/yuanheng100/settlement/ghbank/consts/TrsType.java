package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/18.
 * 操作类型
 */
public enum TrsType
{
    /**
     * 默认
     */
    DEFAULT((short) 0),
    /**
     * 自动投标撤销
     */
    WITHDRAW_AUTO_BID((short) 1),
    /**
     * 自动还款授权撤销
     */
    WITHDRAW_AUTO_PAYMENT((short) 2);

    private Short type;

    private TrsType(Short type)
    {
        this.type = type;
    }


    public Short getType()
    {
        return type;
    }
}
