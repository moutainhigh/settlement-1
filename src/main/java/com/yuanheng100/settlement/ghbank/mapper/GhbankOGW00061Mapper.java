package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Req;
import com.yuanheng100.settlement.ghbank.model.bond.OGW00061Resp;
import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00061;

public interface GhbankOGW00061Mapper {


    @Insert({
        "INSERT INTO ghbank_ogw00061 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, tTrans, ",
        "oldReqSeqNo, oldReqNumber, ",
        "oldReqName, accNo, ",
        "custName, amount, ",
        "preIncome, remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, ",
        "#{oldReqSeqNo,jdbcType=VARCHAR}, #{oldReqNumber,jdbcType=VARCHAR}, ",
        "#{oldReqName,jdbcType=VARCHAR}, #{accNo,jdbcType=VARCHAR}, ",
        "#{custName,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{preIncome,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00061Req req61);

    @Select({
        "SELECT * FROM ghbank_ogw00061",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00061 selectByPrimaryKey(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00061",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00061Resp resp61);
}