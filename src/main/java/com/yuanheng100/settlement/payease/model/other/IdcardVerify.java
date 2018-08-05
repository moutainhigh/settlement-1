package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;
import java.util.Date;

public class IdcardVerify implements Serializable{
    /**
     *验证的用户姓名
     */
    private String name;

    /**
     *验证的用户身份证号
     */
    private String idcardNo;

    /**
     *验证是否存在：0：用户不存在； 1：用户存在
     */
    private Short status;

    /**
     *身份验证时间
     */
    private Date verifyTime;

    /**
     *验证的用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     *验证的用户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     *验证的用户身份证号
     */
    public String getIdcardNo() {
        return idcardNo;
    }

    /**
     *验证的用户身份证号
     */
    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo == null ? null : idcardNo.trim();
    }

    /**
     *验证是否存在：0：用户不存在； 1：用户存在
     */
    public Short getStatus() {
        return status;
    }

    /**
     *验证是否存在：0：用户不存在； 1：用户存在
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     *身份验证时间
     */
    public Date getVerifyTime() {
        return verifyTime;
    }

    /**
     *身份验证时间
     */
    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }
}