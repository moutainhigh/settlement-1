package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00047;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Req;
import com.yuanheng100.settlement.ghbank.model.withdraw.OGW00047Resp;

public interface GhbankOGW00047Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00047 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, tTrans, ",
        "acNo, acName, amount, ",
        "remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, ",
        "#{acNo,jdbcType=VARCHAR}, #{acName,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00047Req req47);

    @Select({
        "SELECT * FROM ghbank_ogw00047",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00047 selectByPrimaryKey(String channelFlow);

    @Update({
        "update ghbank_ogw00047",
        "set serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "withdrawStatus = #{withdrawStatus,jdbcType=SMALLINT}",
        "where channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00047Resp resp47);
}