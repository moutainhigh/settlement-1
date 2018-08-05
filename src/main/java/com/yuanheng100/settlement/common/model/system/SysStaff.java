package com.yuanheng100.settlement.common.model.system;

import java.io.Serializable;

public class SysStaff implements Serializable {
    /**
     *员工Id，员工最重要的标示，主键（不是自增）
     */
    private Integer id;

    /**
     *登录密码，MD5 32位
     */
    private String password;

    /**
     *员工姓名
     */
    private String staffName;

    /**
     *身份证号
     */
    private String idCardNo;

    /**
     *联系电话
     */
    private Long mobile;

    /**
     *微信号
     */
    private String weChat;

    /**
     *邮件，要求唯一
     */
    private String email;

    /**
     *员工Id，员工最重要的标示，主键（不是自增）
     */
    public Integer getId() {
        return id;
    }

    /**
     *员工Id，员工最重要的标示，主键（不是自增）
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *登录密码，MD5 32位
     */
    public String getPassword() {
        return password;
    }

    /**
     *登录密码，MD5 32位
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     *员工姓名
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     *员工姓名
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    /**
     *身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     *身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    /**
     *联系电话
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     *联系电话
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     *微信号
     */
    public String getWeChat() {
        return weChat;
    }

    /**
     *微信号
     */
    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    /**
     *邮件，要求唯一
     */
    public String getEmail() {
        return email;
    }

    /**
     *邮件，要求唯一
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}