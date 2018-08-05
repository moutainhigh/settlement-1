package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00074;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Resp;

public interface GhbankOGW00074Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00074 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, oldReqSeqNo, ",
        "dfFlag, loanNo, bwAcName, bwAcNo, ",
        "totalNum)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{oldReqSeqNo,jdbcType=VARCHAR}, ",
        "#{dfFlag,jdbcType=SMALLINT}, #{loanNo,jdbcType=VARCHAR}, ",
        "#{bwAcName,jdbcType=VARCHAR}, #{bwAcNo,jdbcType=VARCHAR}, ",
        "#{totalNum,jdbcType=INTEGER})"
    })
    int insert(OGW00074Req req74);


    @Select({
        "SELECT * FROM ghbank_ogw00074",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00074 selectByChannelFlow(String channelFlow);

    @Update({
        "update ghbank_ogw00074",
        "set serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "resJnlNo = #{resJnlNo,jdbcType=VARCHAR},",
          "transDateTime = #{transDateTime,jdbcType=TIMESTAMP},",
          "extFiled1 = #{extFiled1,jdbcType=VARCHAR},",
          "extFiled2 = #{extFiled2,jdbcType=VARCHAR},",
          "extFiled3 = #{extFiled3,jdbcType=VARCHAR}",
        "where channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00074Resp resp74);
}