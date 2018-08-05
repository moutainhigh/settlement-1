package com.yuanheng100.settlement.payease.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Req;
import com.yuanheng100.settlement.payease.model.trs001003.TRS001003Resp;

public interface TRS001003Mapper
{
    @Insert({ "insert into payease_trs001003 (reqTime, ", "operationCode, serlNum, ", "authId, borrower, ",
            "borrowerIdType, borrowerId, ", "borrowerName, contractNum, ", "totalAmount, totalNum)",
            "values (#{reqTime,jdbcType=TIMESTAMP}, ",
            "#{operationCode,jdbcType=SMALLINT}, #{serlNum,jdbcType=INTEGER}, ",
            "#{authId,jdbcType=INTEGER}, #{borrower,jdbcType=INTEGER}, ",
            "#{borrowerIdType,jdbcType=CHAR}, #{borrowerId,jdbcType=CHAR}, ",
            "#{borrowerName,jdbcType=VARCHAR}, #{contractNum,jdbcType=INTEGER}, ",
            "#{totalAmount,jdbcType=DECIMAL}, #{totalNum,jdbcType=SMALLINT})" })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(TRS001003Req record);

    @Select({ "select * where msgid = #{msgid,jdbcType=INTEGER}" })
    TRS001003Resp selectByPrimaryKey(Integer msgid);

    @Update({ "update payease_trs001003 set returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},", "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
            "where msgid = #{msgid,jdbcType=INTEGER}" })
    int updateByPrimaryKey(TRS001003Resp record);

    @Update({ "update payease_trs001003 set returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},", "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
            "where serlNum = #{serlNum,jdbcType=INTEGER}" })
    void updateBySerlNum(TRS001003Resp trs001003Resp);
}