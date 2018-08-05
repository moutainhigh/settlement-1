package com.yuanheng100.settlement.common.model.system;

import java.io.Serializable;

public class SysBank implements Serializable{
    /**
     *银行代码。如102为工行，103为农行等
     */
    private Short code;

    /**
     *银行简称，如“工商银行”，“招商银行”
     */
    private String shortName;

    /**
     *银行全称
     */
    private String fullName;

    /**
     *首信易代扣-单笔限额
     */
    private Integer payeaseDeductSingleLimit;

    /**
     *首信易代扣-每日限额
     */
    private Integer payeaseDeductDailyLimit;

    /**
     *银生宝代扣-单笔限额
     */
    private Integer unspayDeductSingleLimit;

    /**
     *银生宝代扣-每日限额
     */
    private Integer unspayDeductDailyLimit;

    /**
     *银行代码。如102为工行，103为农行等
     */
    public Short getCode() {
        return code;
    }

    /**
     *银行代码。如102为工行，103为农行等
     */
    public void setCode(Short code) {
        this.code = code;
    }

    /**
     *银行简称，如“工商银行”，“招商银行”
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *银行简称，如“工商银行”，“招商银行”
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     *银行全称
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *银行全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     *首信易代扣-单笔限额
     */
    public Integer getPayeaseDeductSingleLimit() {
        return payeaseDeductSingleLimit;
    }

    /**
     *首信易代扣-单笔限额
     */
    public void setPayeaseDeductSingleLimit(Integer payeaseDeductSingleLimit) {
        this.payeaseDeductSingleLimit = payeaseDeductSingleLimit;
    }

    /**
     *首信易代扣-每日限额
     */
    public Integer getPayeaseDeductDailyLimit() {
        return payeaseDeductDailyLimit;
    }

    /**
     *首信易代扣-每日限额
     */
    public void setPayeaseDeductDailyLimit(Integer payeaseDeductDailyLimit) {
        this.payeaseDeductDailyLimit = payeaseDeductDailyLimit;
    }

    /**
     *银生宝代扣-单笔限额
     */
    public Integer getUnspayDeductSingleLimit() {
        return unspayDeductSingleLimit;
    }

    /**
     *银生宝代扣-单笔限额
     */
    public void setUnspayDeductSingleLimit(Integer unspayDeductSingleLimit) {
        this.unspayDeductSingleLimit = unspayDeductSingleLimit;
    }

    /**
     *银生宝代扣-每日限额
     */
    public Integer getUnspayDeductDailyLimit() {
        return unspayDeductDailyLimit;
    }

    /**
     *银生宝代扣-每日限额
     */
    public void setUnspayDeductDailyLimit(Integer unspayDeductDailyLimit) {
        this.unspayDeductDailyLimit = unspayDeductDailyLimit;
    }
}