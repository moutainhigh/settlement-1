package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00052;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Req;
import com.yuanheng100.settlement.ghbank.model.invest.OGW00052Resp;

public interface GhbankOGW00052Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00052 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, tTrans, ",
        "loanNo, acNo, acName, ",
        "amount, remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, ",
        "#{loanNo,jdbcType=INTEGER}, #{acNo,jdbcType=VARCHAR}, #{acName,jdbcType=VARCHAR}, ",
        "#{amount,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00052Req req52);


    @Select({
        "SELECT * FROM ghbank_ogw00052",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00052 selectByPrimaryKey(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00052",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "extFiled1 = #{extFiled1,jdbcType=VARCHAR},",
          "extFiled2 = #{extFiled2,jdbcType=VARCHAR},",
          "extFiled3 = #{extFiled3,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateBycChannelFlow(OGW00052Resp resp52);
}