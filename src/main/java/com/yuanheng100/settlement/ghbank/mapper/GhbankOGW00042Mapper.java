package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00042;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Req;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;

public interface GhbankOGW00042Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00042 (priority, ",
        "invokeMethod, channelFlow, ",
        "channelDateTime, transCode, ",
        "appId, tTrans, acName, ",
        "idType, idNo, mobile, ",
        "email, custMngrNo)",
        "values (#{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelFlow,jdbcType=CHAR}, ",
        "#{channelDateTime,jdbcType=TIMESTAMP}, #{transCode,jdbcType=VARCHAR}, ",
        "#{appId,jdbcType=CHAR}, #{tTrans,jdbcType=SMALLINT}, #{acName,jdbcType=VARCHAR}, ",
        "#{idType,jdbcType=INTEGER}, #{idNo,jdbcType=VARCHAR}, #{mobile,jdbcType=BIGINT}, ",
        "#{email,jdbcType=VARCHAR}, #{custMngrNo,jdbcType=VARCHAR})"
    })
    int insert(OGW00042Req req);


    @Select({"SELECT * FROM ghbank_ogw00042 WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"})
    GhbankOGW00042 selectByChannelFlow(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00042",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "oldReqSeqNo = #{oldReqSeqNo,jdbcType=CHAR},",
          "respAcName = #{respAcName,jdbcType=VARCHAR},",
          "respIdType = #{respIdType,jdbcType=INTEGER},",
          "respIdNo = #{respIdNo,jdbcType=VARCHAR},",
          "respMobile = #{respMobile,jdbcType=BIGINT},",
          "acNo = #{acNo,jdbcType=VARCHAR}",
        "WHERE channelFlow = #{oldReqSeqNo,jdbcType=CHAR}"
    })
    int updateByChannelFlow(OGW00042Resp resp);
}