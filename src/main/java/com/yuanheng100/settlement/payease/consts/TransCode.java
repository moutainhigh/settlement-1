package com.yuanheng100.settlement.payease.consts;


/**
 * 首信易交易码定义
 * @author Bai Song
 *
 */
public enum TransCode
{

    SYN001001("SYN001001", "账户同步开通", "synclient"),
    
    TRS001002("TRS001002", "投标冻结解冻", "transbid"),
    
    TRS001003("TRS001003", "标的成功通知", "transbidnotice"),
    
    TRS001006("TRS001006", "提现交易", "transCashFee"),
    
    TRS001007("TRS001007", "转账交易", "transfer"),
    
    TRS001008("TRS001008", "代扣交易", "cutpaymentagent"),
    
    TRS001010("TRS001010", "账户额度查询", "transaccquery"),
    ;

    private final String code;

    private final String name;
    
    /**
     * 首信易各个模块的子url
     */
    private final String subUrl;



    private TransCode(String code, String name, String subUrl)
    {
        this.code = code;
        this.name = name;
        this.subUrl = subUrl;
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

    /**
     * @return 首信易各个模块的子url
     */
    public String getSubUrl()
    {
        return subUrl;
    }
    
    
}
