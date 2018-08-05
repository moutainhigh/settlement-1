package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/19.
 * 交易类型
 */
public enum TransType
{
    /**
     * 绑卡
     */
    BIND_BANK_CARD((short) 1),
    /**
     * 债券转让
     */
    BOND_TRADE((short) 2),
    /**
     * 互联网借贷投标
     */
    INVEST((short) 4),
    /**
     * 互联网借贷还款
     */
    REPAYMENT((short) 5),
    /**
     * 账户开立
     */
    ACCOUNT_OPEN((short) 6),
    /**
     * 充值
     */
    RECHARGE((short)7),
    /**
     * 提现
     */
    WITHDRAW((short)8),
    /**
     * 自动投标授权
     */
    AUTOMATIC_BIDDING_AUTHORIZATION((short)9),
    /**
     * 自动还款授权
     */
    AUTOMATIC_PAYMENT_AUTHORIZATION((short)10);

    private Short type;

    private TransType(Short type)
    {
        this.type = type;
    }


    public Short getType()
    {
        return type;
    }
}
