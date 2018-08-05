package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00075;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Req;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00075Resp;

public interface GhbankOGW00075Mapper {


    @Insert({
        "INSERT INTO ghbank_ogw00075 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, oldReqSeqNo, ",
        "loanNo, subSeqNo)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{oldReqSeqNo,jdbcType=VARCHAR}, ",
        "#{loanNo,jdbcType=INTEGER}, #{subSeqNo,jdbcType=VARCHAR})"
    })
    int insert(OGW00075Req req75);


    @Select({
        "SELECT * FROM ghbank_ogw00075",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00075 selectByChannelFlow(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00075",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "returnStatus = #{returnStatus,jdbcType=CHAR},",
          "returnErrorMsg = #{returnErrorMsg,jdbcType=VARCHAR},",
          "repayReqSeqNo = #{repayReqSeqNo,jdbcType=VARCHAR},",
          "dfFlag = #{dfFlag,jdbcType=SMALLINT},",
          "bwAcName = #{bwAcName,jdbcType=VARCHAR},",
          "bwAcNo = #{bwAcNo,jdbcType=VARCHAR},",
          "totalNum = #{totalNum,jdbcType=INTEGER},",
          "extFiled1 = #{extFiled1,jdbcType=VARCHAR},",
          "extFiled2 = #{extFiled2,jdbcType=VARCHAR},",
          "extFiled3 = #{extFiled3,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00075Resp resp75);
}