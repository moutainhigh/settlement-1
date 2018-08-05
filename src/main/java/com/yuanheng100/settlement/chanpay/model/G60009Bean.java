package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.Gw01MsgBase;

/**
 * Created by jlqian on 2016/9/8.
 */
public class G60009Bean extends Gw01MsgBase {
    /**
     * 短信验证码
     */
    private String smsCode;
    /**
     * 原始交易（短信请求）请求号
     */
    private String orgReqSn;
    /**
     * 二级商户代码
     */
    private String subMerchantId;
    /**
     * 明细号，唯一标识
     */
    private String sn;
    /**
     * 银行通用名称
     */
    private String bankGeneralName;
    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 开户行号
     */
    private String bankCode;
    /**
     * 账号类型
     */
    private String accountType;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 开户证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String id;
    /**
     * 手机号
     */
    private Long tel;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getOrgReqSn() {
        return orgReqSn;
    }

    public void setOrgReqSn(String orgReqSn) {
        this.orgReqSn = orgReqSn;
    }

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBankGeneralName() {
        return bankGeneralName;
    }

    public void setBankGeneralName(String bankGeneralName) {
        this.bankGeneralName = bankGeneralName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
}
