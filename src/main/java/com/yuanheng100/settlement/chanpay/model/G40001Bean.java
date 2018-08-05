package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.Gw01MsgBase;

/**
 * Created by jlqian on 2016/9/23.
 */
public class G40001Bean extends Gw01MsgBase
{
    /**
     *序号
     */
    private Integer sn;

    /**
     *银行代码
     */
    private String bankCode;

    /**
     *支付行号
     */
    private String accountBankCode;

    /**
     *支付行清算行号
     */
    private String clearingBankCode;

    /**
     *开户行名称
     */
    private String accountBankName;

    /**
     *地区码
     */
    private Integer regionCode;

    /**
     *是否支持二代业务
     */
    private String secondGeneration;

    /**
     *序号
     */
    public Integer getSn() {
        return sn;
    }

    /**
     *序号
     */
    public void setSn(Integer sn) {
        this.sn = sn;
    }

    /**
     *银行代码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     *银行代码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     *支付行号
     */
    public String getAccountBankCode() {
        return accountBankCode;
    }

    /**
     *支付行号
     */
    public void setAccountBankCode(String accountBankCode) {
        this.accountBankCode = accountBankCode == null ? null : accountBankCode.trim();
    }

    /**
     *支付行清算行号
     */
    public String getClearingBankCode() {
        return clearingBankCode;
    }

    /**
     *支付行清算行号
     */
    public void setClearingBankCode(String clearingBankCode) {
        this.clearingBankCode = clearingBankCode == null ? null : clearingBankCode.trim();
    }

    /**
     *开户行名称
     */
    public String getAccountBankName() {
        return accountBankName;
    }

    /**
     *开户行名称
     */
    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName == null ? null : accountBankName.trim();
    }

    /**
     *地区码
     */
    public Integer getRegionCode() {
        return regionCode;
    }

    /**
     *地区码
     */
    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    /**
     *是否支持二代业务
     */
    public String getSecondGeneration() {
        return secondGeneration;
    }

    /**
     *是否支持二代业务
     */
    public void setSecondGeneration(String secondGeneration) {
        this.secondGeneration = secondGeneration == null ? null : secondGeneration.trim();
    }
}
