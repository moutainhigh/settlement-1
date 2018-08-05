package com.yuanheng100.settlement.common.mapper;

import com.yuanheng100.settlement.common.model.system.SysStaff;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface SysStaffMapper {


    @Select({
            "SELECT",
            "id, password, staffName, idCardNo, mobile, weChat, email",
            "FROM sys_staff",
            "WHERE id = #{id,jdbcType=INTEGER}",
            "AND password = #{password,jdbcType=CHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="password", property="password", jdbcType=JdbcType.CHAR),
            @Result(column="staffName", property="staffName", jdbcType=JdbcType.VARCHAR),
            @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.CHAR),
            @Result(column="mobile", property="mobile", jdbcType=JdbcType.BIGINT),
            @Result(column="weChat", property="weChat", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR)
    })
    SysStaff selectByIdAndPasswd(@Param("id") Integer id,@Param("password") String passwd);

    @Select({
            "SELECT",
            "staffName",
            "FROM sys_staff",
            "WHERE id = #{id,jdbcType=INTEGER}"
    })
    String selectStaffNameById(@Param("id") Integer id);

    @UpdateProvider(type=SysStaffSqlProvider.class, method="updateByIdSelective")
    int updateByIdSelective(SysStaff record);

    @Delete({
        "DELETE FROM sys_staff",
        "WHERE id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "INSERT INTO sys_staff (id, password, ",
        "staffName, idCardNo, ",
        "mobile, weChat, email)",
        "VALUES (#{id,jdbcType=INTEGER}, #{password,jdbcType=CHAR}, ",
        "#{staffName,jdbcType=VARCHAR}, #{idCardNo,jdbcType=CHAR}, ",
        "#{mobile,jdbcType=BIGINT}, #{weChat,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR})"
    })
    int insert(SysStaff record);

    @InsertProvider(type=SysStaffSqlProvider.class, method="insertSelective")
    int insertSelective(SysStaff record);

    @Select({
        "select",
        "id, password, staffName, idCardNo, mobile, weChat, email",
        "from sys_staff",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="password", property="password", jdbcType=JdbcType.CHAR),
        @Result(column="staffName", property="staffName", jdbcType=JdbcType.VARCHAR),
        @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.CHAR),
        @Result(column="mobile", property="mobile", jdbcType=JdbcType.BIGINT),
        @Result(column="weChat", property="weChat", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR)
    })
    SysStaff selectByPrimaryKey(Integer id);

    @UpdateProvider(type=SysStaffSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SysStaff record);

    @Update({
        "update sys_staff",
        "set password = #{password,jdbcType=CHAR},",
          "staffName = #{staffName,jdbcType=VARCHAR},",
          "idCardNo = #{idCardNo,jdbcType=CHAR},",
          "mobile = #{mobile,jdbcType=BIGINT},",
          "weChat = #{email,jdbcType=VARCHAR}",
          "email = #{email,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SysStaff record);

}