package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00065;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00065Resp;

public interface GhbankOGW00065Mapper {


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
    int insert(OGW00065Req req65);


    @Select({
        "select * from ghbank_ogw00075",
        "where channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00065 selectByChannelFlow(String channelFlow);


    @Update({
        "update ghbank_ogw00075",
        "set serverFlow = #{serverFlow,jdbcType=CHAR},",
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
        "where channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00065Resp resp65);
}