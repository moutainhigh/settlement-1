package com.yuanheng100.settlement.common.model.customer;

/**
 * 客户开户
 * @author tianwei
 *
 */
public class CustomerRegister extends Customer {
    /**
     * 用户证件号
     */
    private String idNo;
    
    /**
     * 用户真实姓名
     */
    private String userName;
    
    /**
     * 注册用户手机号
     */
    private String mobile;
    
    /**
     * 开户银行名称
     */
    private String accBank;
    
    /**
     * 银行账号
     */
    private String accNum;
    
    /**
     * 账号类型
     */
    private String accType;
    
    /**
     * 账号类别
     */
    private String accProp;
    
    /**
     * 证件类型
     */
    private String idType;
    
    /**
     * 开户名
     */
    private String accName;
    
    /**
     * 开户省
     */
    private String accProvince;
    
    /**
     * 开户市
     */
    private String accCity;
    
    /**
     * 开户分行支行名称
     */
    private String accBranchName;
    
    /**
     * 页面返回url
     */
    private String retUrl;
    
    /**
     * 用户email
     */
    private String usrEmail;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccBank() {
        return accBank;
    }

    public void setAccBank(String accBank) {
        this.accBank = accBank;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccProp() {
        return accProp;
    }

    public void setAccProp(String accProp) {
        this.accProp = accProp;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccProvince() {
        return accProvince;
    }

    public void setAccProvince(String accProvince) {
        this.accProvince = accProvince;
    }

    public String getAccCity() {
        return accCity;
    }

    public void setAccCity(String accCity) {
        this.accCity = accCity;
    }

    public String getAccBranchName() {
        return accBranchName;
    }

    public void setAccBranchName(String accBranchName) {
        this.accBranchName = accBranchName;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }
    
}
