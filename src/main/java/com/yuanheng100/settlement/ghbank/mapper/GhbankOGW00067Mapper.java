package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00067;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00067Resp;

public interface GhbankOGW00067Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00067 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, tTrans, ",
        "dfFlag, oldReqSeqNo, ",
        "loanNo, bwAcName, ",
        "bwAcNo, amount, ",
        "remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, ",
        "#{dfFlag,jdbcType=SMALLINT}, #{oldReqSeqNo,jdbcType=VARCHAR}, ",
        "#{loanNo,jdbcType=VARCHAR}, #{bwAcName,jdbcType=VARCHAR}, ",
        "#{bwAcNo,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00067Req req67);


    @Select({
        "SELECT * FROM ghbank_ogw00067",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00067 selectByChannelFlow(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00067",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR} ",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00067Resp resp67);
}