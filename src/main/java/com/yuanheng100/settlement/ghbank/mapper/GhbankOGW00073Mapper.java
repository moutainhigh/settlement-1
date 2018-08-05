package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00073;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00073Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00073Resp;

public interface GhbankOGW00073Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00073 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, loanNo, ",
        "bwAcName, bwAcNo, ",
        "feeAmt, amount, ",
        "remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{loanNo,jdbcType=VARCHAR}, ",
        "#{bwAcName,jdbcType=VARCHAR}, #{bwAcNo,jdbcType=VARCHAR}, ",
        "#{feeAmt,jdbcType=DECIMAL}, #{amount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00073Req req73);


    @Select({
        "SELECT * FROM ghbank_ogw00073",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00073 selectByChannelFlow(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00073",
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
        "where channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00073Resp resp73);
}