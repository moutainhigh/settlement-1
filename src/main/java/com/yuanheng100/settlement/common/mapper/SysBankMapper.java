package com.yuanheng100.settlement.common.mapper;

import com.yuanheng100.settlement.common.model.system.SysBank;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SysBankMapper
{

    @Insert({
            "INSERT INTO sys_bank (code, shortName, ",
            "fullName, payeaseDeductSingleLimit, ",
            "payeaseDeductDailyLimit, unspayDeductSingleLimit, ",
            "unspayDeductDailyLimit)",
            "VALUES (#{code,jdbcType=SMALLINT}, #{shortName,jdbcType=VARCHAR}, ",
            "#{fullName,jdbcType=VARCHAR}, #{payeaseDeductSingleLimit,jdbcType=INTEGER}, ",
            "#{payeaseDeductDailyLimit,jdbcType=INTEGER}, #{unspayDeductSingleLimit,jdbcType=INTEGER}, ",
            "#{unspayDeductDailyLimit,jdbcType=INTEGER})"
    })
    int insert(SysBank record);

    @Select({
            "SELECT",
            "code, shortName, fullName, payeaseDeductSingleLimit, payeaseDeductDailyLimit, ",
            "unspayDeductSingleLimit, unspayDeductDailyLimit",
            "FROM sys_bank"
    })
    @Results({
            @Result(column = "code", property = "code", jdbcType = JdbcType.SMALLINT, id = true),
            @Result(column = "shortName", property = "shortName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fullName", property = "fullName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "payeaseDeductSingleLimit", property = "payeaseDeductSingleLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "payeaseDeductDailyLimit", property = "payeaseDeductDailyLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "unspayDeductSingleLimit", property = "unspayDeductSingleLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "unspayDeductDailyLimit", property = "unspayDeductDailyLimit", jdbcType = JdbcType.INTEGER)
    })
    List<SysBank> selectAll();

    @Select({
            "SELECT",
            "code, shortName, fullName, payeaseDeductSingleLimit, payeaseDeductDailyLimit, ",
            "unspayDeductSingleLimit, unspayDeductDailyLimit",
            "FROM sys_bank",
            "WHERE code = #{code,jdbcType=SMALLINT}"
    })
    @Results({
            @Result(column = "code", property = "code", jdbcType = JdbcType.SMALLINT, id = true),
            @Result(column = "shortName", property = "shortName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fullName", property = "fullName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "payeaseDeductSingleLimit", property = "payeaseDeductSingleLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "payeaseDeductDailyLimit", property = "payeaseDeductDailyLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "unspayDeductSingleLimit", property = "unspayDeductSingleLimit", jdbcType = JdbcType.INTEGER),
            @Result(column = "unspayDeductDailyLimit", property = "unspayDeductDailyLimit", jdbcType = JdbcType.INTEGER)
    })
    SysBank selectByPrimaryKey(Short code);

    @Update({
            "UPDATE sys_bank",
            "SET shortName = #{shortName,jdbcType=VARCHAR},",
            "fullName = #{fullName,jdbcType=VARCHAR},",
            "payeaseDeductSingleLimit = #{payeaseDeductSingleLimit,jdbcType=INTEGER},",
            "payeaseDeductDailyLimit = #{payeaseDeductDailyLimit,jdbcType=INTEGER},",
            "unspayDeductSingleLimit = #{unspayDeductSingleLimit,jdbcType=INTEGER},",
            "unspayDeductDailyLimit = #{unspayDeductDailyLimit,jdbcType=INTEGER}",
            "WHERE code = #{code,jdbcType=SMALLINT}"
    })
    int updateByPrimaryKey(SysBank record);
}