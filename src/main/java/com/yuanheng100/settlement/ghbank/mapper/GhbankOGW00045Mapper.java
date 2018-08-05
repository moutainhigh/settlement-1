package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00045;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Req;
import com.yuanheng100.settlement.ghbank.model.recharge.OGW00045Resp;

public interface GhbankOGW00045Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00045 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, tTrans, ",
        "acNo, acName, amount, ",
        "remark) VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, ",
        "#{acNo,jdbcType=VARCHAR}, #{acName,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00045Req req45);



    @Select({
        "SELECT * WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"})
    GhbankOGW00045 selectByPrimaryKey(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00045",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "orderStatus = #{orderStatus,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00045Resp resp45);
}