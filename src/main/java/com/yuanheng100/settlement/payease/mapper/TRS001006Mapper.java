package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TRS001006Mapper
{

    @Insert({
            "INSERT INTO payease_trs001006 (msgid, reqTime, ",
            "operationCode, serlNum, ",
            "authId, user, accName, ",
            "accBankCode, accNum, ",
            "accType, accProp, totalAmount, ",
            "amount, fee, urgency, ",
            "amountSplit, moneyRecordId)",
            "VALUES (#{msgid,jdbcType=INTEGER}, #{reqTime,jdbcType=TIMESTAMP}, ",
            "#{operationCode,jdbcType=SMALLINT}, #{serlNum,jdbcType=INTEGER}, ",
            "#{authId,jdbcType=INTEGER}, #{user,jdbcType=INTEGER}, #{accName,jdbcType=VARCHAR}, ",
            "#{accBankCode,jdbcType=SMALLINT}, #{accNum,jdbcType=VARCHAR}, ",
            "#{accType,jdbcType=CHAR}, #{accProp,jdbcType=CHAR}, #{totalAmount,jdbcType=DECIMAL}, ",
            "#{amount,jdbcType=DECIMAL}, #{fee,jdbcType=DECIMAL}, #{urgency,jdbcType=CHAR}, ",
            "#{amountSplit,jdbcType=CHAR}, #{moneyRecordId,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(TRS001006Req record);

    @Select({
            "SELECT",
            "msgid, reqTime, operationCode, serlNum, authId, user, accName, accBankCode, accNum, ",
            "accType, accProp, totalAmount, amount, fee, urgency, amountSplit, moneyRecordId, ",
            "returnCode, returnMsg, returnTime",
            "FROM payease_trs001006",
            "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "msgid", property = "msgid", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "reqTime", property = "reqTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "operationCode", property = "operationCode", jdbcType = JdbcType.SMALLINT),
            @Result(column = "serlNum", property = "serlNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "authId", property = "authId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user", property = "user", jdbcType = JdbcType.INTEGER),
            @Result(column = "accName", property = "accName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accBankCode", property = "accBank", jdbcType = JdbcType.SMALLINT),
            @Result(column = "accNum", property = "accNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accType", property = "accType", jdbcType = JdbcType.CHAR),
            @Result(column = "accProp", property = "accProp", jdbcType = JdbcType.CHAR),
            @Result(column = "totalAmount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "fee", property = "fee", jdbcType = JdbcType.DECIMAL),
            @Result(column = "urgency", property = "urgency", jdbcType = JdbcType.CHAR),
            @Result(column = "amountSplit", property = "amountSplit", jdbcType = JdbcType.CHAR),
            @Result(column = "moneyRecordId", property = "moneyRecordId", jdbcType = JdbcType.INTEGER),
            @Result(column = "returnCode", property = "returnCode", jdbcType = JdbcType.CHAR),
            @Result(column = "returnMsg", property = "returnMsg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "returnTime", property = "returnTime", jdbcType = JdbcType.TIMESTAMP)
    })
    TRS001006Resp selectByPrimaryKey(Integer msgid);


    @Update({
            "UPDATE payease_trs001006",
            "SET moneyRecordId = #{moneyRecordId,jdbcType=INTEGER},",
            "returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
            "returnTime = #{returnTime,jdbcType=TIMESTAMP}",


            "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TRS001006Resp record);

    @Select({
            "SELECT",
            "msgid, reqTime, operationCode, serlNum, authId, user, accName, accBankCode, accNum, ",
            "accType, accProp, totalAmount, amount, fee, urgency, amountSplit, moneyRecordId",
            "FROM payease_trs001006",
            "WHERE (CASE WHEN #{returnCode,jdbcType=VARCHAR} IS NULL THEN `returnCode` IS NULL ELSE `returnCode` = #{returnCode,jdbcType=VARCHAR} END)"
    })
    @Results({
            @Result(column = "msgid", property = "msgid", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "reqTime", property = "reqTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "operationCode", property = "operationCode", jdbcType = JdbcType.SMALLINT),
            @Result(column = "serlNum", property = "serlNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "authId", property = "authId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user", property = "user", jdbcType = JdbcType.INTEGER),
            @Result(column = "accName", property = "accName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accBankCode", property = "accBank", jdbcType = JdbcType.SMALLINT),
            @Result(column = "accNum", property = "accNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accType", property = "accType", jdbcType = JdbcType.CHAR),
            @Result(column = "accProp", property = "accProp", jdbcType = JdbcType.CHAR),
            @Result(column = "totalAmount", property = "totalAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "fee", property = "fee", jdbcType = JdbcType.DECIMAL),
            @Result(column = "urgency", property = "urgency", jdbcType = JdbcType.CHAR),
            @Result(column = "amountSplit", property = "amountSplit", jdbcType = JdbcType.CHAR),
            @Result(column = "moneyRecordId", property = "moneyRecordId", jdbcType = JdbcType.INTEGER)
    })
    List<TRS001006Req> queryWithReturnCode(String returnCode);

    @SelectProvider(type = TRS001006SqlProvider.class, method = "selectWithdrawCountByCondition")
    Long selectWithdrawCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type = TRS001006SqlProvider.class, method = "selectWithdrawListByCondition")
    List<Map<String, Object>> selectWithdrawListByCondition(HashMap<String, Object> searchConditions);

    @Update({
            "UPDATE payease_trs001006",
            "SET returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
            "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
            "WHERE serlNum = #{serlNum,jdbcType=INTEGER}"
    })
    void updateBySerlNum(TRS001006Resp trs001006Resp);


    @SelectProvider(type = TRS001006SqlProvider.class, method = "getRecords006")
    List<TRS001006Resp> getRecords006(Map<String, Object> param);

    @SelectProvider(type = TRS001006SqlProvider.class, method = "getRecords006Count")
    int getRecords006Count(Map<String, Object> param);
}