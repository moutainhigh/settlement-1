package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00060;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00060Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00060Resp;

public interface GhbankOGW00060Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00060 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, loanNo, ",
        "oldReqSeqNo, acNo, ",
        "acName, cancelReason)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{loanNo,jdbcType=INTEGER}, ",
        "#{oldReqSeqNo,jdbcType=VARCHAR}, #{acNo,jdbcType=VARCHAR}, ",
        "#{acName,jdbcType=VARCHAR}, #{cancelReason,jdbcType=VARCHAR})"
    })
    int insert(OGW00060Req req60);

    
    @Select({
        "SELECT * FROM ghbank_ogw00060",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00060 selectByChannelFlow(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00060",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "resJnlNo = #{resJnlNo,jdbcType=VARCHAR},",
          "transDateTime = #{transDateTime,jdbcType=TIMESTAMP},",
          "extFiled1 = #{extFiled1,jdbcType=VARCHAR},",
          "extFiled2 = #{extFiled2,jdbcType=VARCHAR},",
          "extFiled3 = #{extFiled3,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00060Resp resp60);
}