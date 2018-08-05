package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00044;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;

public interface GhbankOGW00044Mapper {


    @Insert({
        "insert into ghbank_ogw00044 (priority, ",
        "invokeMethod, channelFlow, ",
        "channelDateTime, transCode, ",
        "appId, tTrans, acNo)",
        "values (#{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelFlow,jdbcType=CHAR}, ",
        "#{channelDateTime,jdbcType=TIMESTAMP}, #{transCode,jdbcType=CHAR}, ",
        "#{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, #{acNo,jdbcType=VARCHAR})"
    })
    int insert(OGW00044Req req44);


    @Select({
        "SELECT * FROM ghbank_ogw00044 WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"})
    GhbankOGW00044 selectByChannelFlow(String channelFlow);


    @Update({
        "update ghbank_ogw00044",
        "set serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR}",
        "where channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00044Resp resp);
}