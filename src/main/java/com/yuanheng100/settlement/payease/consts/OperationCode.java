package com.yuanheng100.settlement.payease.consts;

/**
 * 首信易所有OperationCode的定义
 * @author Bai Song
 *
 */
public interface OperationCode
{
    /**
     * 账户同步操作码
     */
    public enum SYN001001
    {
        CUSTOMER_REGISTER("10000", "账户同步开通"),

        /**
         * 同步用户身份信息
         */
        SYN_IDENTITY("10001", "同步用户身份信息"),
        
        SYN_MOBILE("10002", "同步用户手机号码"),
        
        QUERY_ACCOUNT("10003", "查询账户是否开通"),
        
        /**
         * 同步用户银行卡信息
         */
        SYN_BANKCARD("10004", "同步用户银行卡信息"),
        
        QUERY_BANKCARD("10005", "查询银行卡验证结果"),
        
        ;
        
        private final String code;

        private final String name;
        
        private SYN001001(String code, String name)
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
        
        
    }
    
    /**
     * 投标冻结解冻
     *
     */
    public enum TRS001002
    {
        /**
         * 冻结投标（出借账户）金额-12000
         */
        FREEZE("12000", "冻结投标（出借账户）金额"),
        
        /**
         * 解冻投标（出借账户）金额-12001
         */
        UNFREEZE("12001", "解冻投标（出借账户）金额"),
        
        ;
        private final String code;

        private final String name;
        
        private TRS001002(String code, String name)
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
        
    }
    
    /**
     * 标的成功通知
     */
    public enum TRS001003
    {
        /**
         * 标的下所有冻结资金转至借款人虚拟账户-14000
         */
        CLOSE_LOAN("14000", "标的下所有冻结资金转至借款人虚拟账户"),
        
        /**
         * 查询标的转出结果-14001
         */
        QUERY_CLOSE_LOAN("14001", "查询标的转出结果"),
        
        ;
        private final String code;

        private final String name;
        
        private TRS001003(String code, String name)
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
    }

    /**
     * 提现交易
     */
    public enum TRS001006
    {
        /**
         * 用户从出借虚拟账户提现到银行卡
         */
        WITHDRAW_FROM_LEND_ACCOUNT("18000", "用户从出借虚拟账户提现到银行卡"),

        /**
         * 用户从借款虚拟账户提现到银行卡
         */
        WITHDRAW_FROM_BORROWER_ACCOUNT("18001", "用户从借款虚拟账户提现到银行卡"),

        /**
         * 查询订单提现处理结果
         */
        QUERY_WITHDRAWR_ESULT("18002", "查询订单提现处理结果"),

        /**
         * P2P平台划拨款（与18001操作在功能实现上没有区别，仅作为区分平台划拨与客户自助划拨）
         */
        WITHDRAW_FROM_BORROWER_ACCOUNT_BY_PLATFORM("18003", "P2P平台划拨款"),

        ;
        private final String code;

        private final String name;

        private TRS001006(String code, String name)
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

    }
    
    /**
     * 转账交易
     */
    public enum TRS001007
    {
        /**
         * 查询
         */
        QUERY_TRANSFER_RESULT("24001", "查询"),

        /**
         * A用户投资账户向B用户投资账户转账，主要用于债权买卖-24002
         */
        INVEST_TO_INVEST("24002", "A用户投资账户向B用户投资账户转账，主要用于债权买卖"),
        
        /**
         * A用户投资账户向B用户负债账户转账-24004
         */
        INVEST_TO_LIABILITY("24004", "A用户投资账户向B用户负债账户转账"),
        
        /**
         * A用户负债账户向B用户投资账户转账-24005
         */
        LIABILITY_TO_INVEST("24005", "A用户投资账户向B用户负债账户转账"),
        
        ;
        private final String code;

        private final String name;
        
        private TRS001007(String code, String name)
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
        
    }
    
    /**
     * 代扣交易 TRS001008
     */
    public enum TRS001008
    {
        /**
         * 代扣到投资账户/用代扣向虚拟账户充值，TRS001008-20000
         */
        DEDUCT_TO_INVEST("20000", "代扣到投资账户"),
        
        /**
         * 代扣到负债账户/用代扣进行还款，TRS001008-20001
         */
        DEDUCT_TO_REPAY("20001", "代扣到负债账户"),
        
        /**
         * 代扣查询，TRS001008-20002
         */
        QUERY_DEDUCT("20002", "查询代扣结果"),
        
        ;
        private final String code;

        private final String name;
        
        private TRS001008(String code, String name)
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
        
    }
    
    
    /**
     * 账户额度查询-TRS001010
     */
    public enum TRS001010
    {
        /**
         * 查询投资账户-60000
         */
        QUERY_INVEST_ACCOUNT("60000", "查询投资账户"),
        
        /**
         * 查询负债账户-60001
         */
        QUERY_LIABILITY_ACCOUNT("60001", "查询负债账户"),
        
        ;
        private final String code;

        private final String name;
        
        private TRS001010(String code, String name)
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
        
        
    }
    

}
