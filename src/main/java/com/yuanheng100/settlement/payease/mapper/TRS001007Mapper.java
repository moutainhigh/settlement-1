package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.provider.TransferAccountSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface TRS001007Mapper {


    @Insert({
        "INSERT INTO payease_trs001007 (msgid, reqTime, ",
        "operationCode, serlNum, ",
        "authId, transferOutUser, ",
        "transferOutUserId, transferInUser, ",
        "transferInUserId, transferAmount, ",
        "transferOutUserFee, transferInUserFee, ",
        "moneyRecordId)",
        "VALUES (#{msgid,jdbcType=INTEGER}, #{reqTime,jdbcType=TIMESTAMP}, ",
        "#{operationCode,jdbcType=INTEGER}, #{serlNum,jdbcType=INTEGER}, ",
        "#{authId,jdbcType=INTEGER}, #{transferOutUser,jdbcType=INTEGER}, ",
        "#{transferOutUserId,jdbcType=CHAR}, #{transferInUser,jdbcType=INTEGER}, ",
        "#{transferInUserId,jdbcType=CHAR}, #{transferAmount,jdbcType=DECIMAL}, ",
        "#{transferOutUserFee,jdbcType=DECIMAL}, #{transferInUserFee,jdbcType=DECIMAL}, ",
        "#{moneyRecordId,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(TRS001007Req record);


    @Select({
        "SELECT * FROM payease_trs001007",
        "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    TRS001007Resp selectByPrimaryKey(Integer msgid);


    @Update({
        "UPDATE payease_trs001007",
        "SET moneyRecordId = #{moneyRecordId,jdbcType=INTEGER},",
          "returnCode = #{returnCode,jdbcType=CHAR},",
          "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
          "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
        "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TRS001007Resp record);

    @Select({
            "SELECT",
            "msgid, reqTime, operationCode, serlNum, authId, transferOutUser, transferOutUserId, transferInUser, transferInUserId, transferAmount, ",
            "transferOutUserFee, transferInUserFee, moneyRecordId",
            "FROM payease_trs001007",
            "WHERE (CASE WHEN #{returnCode,jdbcType=VARCHAR} IS NULL THEN `returnCode` IS NULL ELSE `returnCode` = #{returnCode,jdbcType=VARCHAR} END)"
    })
    List<TRS001007Req> queryWithReturnCode(String returnCode);

    @SelectProvider(type = TransferAccountSqlProvider.class, method = "getTransferRecords")
    List<Map<String,Object>> getTransferRecords(Map<String, Object> param);

    @SelectProvider(type = TransferAccountSqlProvider.class, method = "getTransferRecordsCount")
    int getTransferRecordsCount(Map<String, Object> param);
}