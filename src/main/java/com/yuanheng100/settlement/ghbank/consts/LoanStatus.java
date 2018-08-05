package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/25.
 */
public enum LoanStatus
{
    /**
     * 正常
     */
    NORMAL((short) 0),
    /**
     * 撤销
     */
    REVOCATORY((short) 1);

    private Short status;

    private LoanStatus(Short status)
    {
        this.status = status;
    }

    public Short getStatus()
    {
        return status;
    }
}
