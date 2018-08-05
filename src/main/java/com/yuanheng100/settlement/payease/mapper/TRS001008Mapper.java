package com.yuanheng100.settlement.payease.mapper;

import org.apache.ibatis.annotations.*;

import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TRS001008Mapper {

    @Insert({
        "INSERT INTO payease_trs001008 (reqTime, ",
        "operationCode, serlNum, ",
        "user, idType, id, ",
        "accName, accNum, ",
        "accBankCode, accType, accProp, ",
        "amount, merdata1, ",
        "moneyRecordId)",
        "VALUES (#{reqTime,jdbcType=TIMESTAMP}, ",
        "#{operationCode,jdbcType=INTEGER}, #{serlNum,jdbcType=INTEGER}, ",
        "#{user,jdbcType=INTEGER}, #{idType,jdbcType=CHAR}, #{id,jdbcType=CHAR}, ",
        "#{accName,jdbcType=VARCHAR}, #{accNum,jdbcType=VARCHAR}, ",
        "#{accBankCode,jdbcType=SMALLINT}, #{accType,jdbcType=CHAR}, #{accProp,jdbcType=CHAR}, ",
        "#{amount,jdbcType=DECIMAL}, #{merdata1,jdbcType=VARCHAR}, ",
        "#{moneyRecordId,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(TRS001008Req record);

    @Select({
        "SELECT * FROM payease_trs001008",
        "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    TRS001008Resp selectByPrimaryKey(Integer msgid);

    @Update({
        "UPDATE payease_trs001008",
        "SET returnCode = #{returnCode,jdbcType=CHAR},",
          "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
          "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
        "where msgid = #{msgid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TRS001008Resp record);

    @Select({
            "SELECT",
            "msgid, reqTime, operationCode, serlNum, user, idType, id, accName, accNum, accBankCode, accType, accProp, amount, merdata1, moneyRecordId",
            "FROM payease_trs001008",
            "WHERE (CASE WHEN #{returnCode,jdbcType=VARCHAR} IS NULL THEN `returnCode` IS NULL ELSE `returnCode` = #{returnCode,jdbcType=VARCHAR} END)"
    })
    List<TRS001008Req> queryWithReturnCode(String returnCode);

    @SelectProvider(type=TRS001008SqlProvider.class,method = "selectDeductCountByCondition")
    Long selectDeductCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type=TRS001008SqlProvider.class,method = "selectDeductListByCondition")
    List<Map<String,Object>> selectDeductListByCondition(HashMap<String, Object> searchConditions);

    @Update({
            "UPDATE payease_trs001008",
            "SET returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
            "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
            "WHERE serlNum = #{serlNum,jdbcType=INTEGER}"
    })
    void updateBySerlNum(TRS001008Resp trs001008Resp);

    @SelectProvider(type = TRS001008SqlProvider.class,method = "getRecords008")
    List<TRS001008Resp> getRecords008(Map<String, Object> param);

    @SelectProvider(type = TRS001008SqlProvider.class,method = "getRecords008Count")
    int getRecords008Count(Map<String, Object> param);
}