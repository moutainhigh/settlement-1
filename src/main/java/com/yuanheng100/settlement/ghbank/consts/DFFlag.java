package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/27.
 */
public enum DFFlag
{
    /**
     * 正常还款
     */
    NORMAL_PREPAYMENT((short) 1),
    /**
     * 垫付后，借款人还款
     */
    PAY_FOR_BORROWER((short) 2);

    private Short flag;

    private DFFlag(Short flag)
    {
        this.flag = flag;
    }

    public Short getFlag()
    {
        return flag;
    }
}
