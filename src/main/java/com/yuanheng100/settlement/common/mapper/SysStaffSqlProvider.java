package com.yuanheng100.settlement.common.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.yuanheng100.settlement.common.model.system.SysStaff;

public class SysStaffSqlProvider {

    public String updateByIdSelective(SysStaff record){
        BEGIN();
        UPDATE("sys_staff");

        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=CHAR}");
        }

        if (record.getMobile() != null) {
            SET("mobile = #{mobile,jdbcType=BIGINT}");
        }

        if (record.getWeChat() != null) {
            SET("weChat = #{weChat,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            SET("email = #{email,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }

    public String insertSelective(SysStaff record) {
        BEGIN();
        INSERT_INTO("sys_staff");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=CHAR}");
        }
        
        if (record.getStaffName() != null) {
            VALUES("staffName", "#{staffName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdCardNo() != null) {
            VALUES("idCardNo", "#{idCardNo,jdbcType=CHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("mobile", "#{mobile,jdbcType=BIGINT}");
        }

        if (record.getWeChat() != null) {
            VALUES("weChat", "#{weChat,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(SysStaff record) {
        BEGIN();
        UPDATE("sys_staff");
        
        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=CHAR}");
        }
        
        if (record.getStaffName() != null) {
            SET("staffName = #{staffName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdCardNo() != null) {
            SET("idCardNo = #{idCardNo,jdbcType=CHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("mobile = #{mobile,jdbcType=BIGINT}");
        }

        if (record.getWeChat() != null) {
            SET("weChat = #{weChat,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}