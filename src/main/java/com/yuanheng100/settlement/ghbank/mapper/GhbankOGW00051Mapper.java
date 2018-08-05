package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00051;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Req;
import com.yuanheng100.settlement.ghbank.model.loan.OGW00051Resp;

public interface GhbankOGW00051Mapper {

    @Insert({
        "INSERT INTO ghbank_ogw00051 (channelFlow, priority, ",
        "invokeMethod, channelDateTime, ",
        "transCode, appId, loanNo, ",
        "investId, investObjName, ",
        "investObjInfo, minInvestAmt, ",
        "maxInvestAmt, investObjAmt, ",
        "investBeginDate, investEndDate, ",
        "repayDate, yearRate, ",
        "investRange, ratesType, ",
        "repaysType, investObjState, ",
        "bwTotalNum, zrFlag, ",
        "refLoanNo, oldReqSeq, ",
        "bwAcName, bwIdType, ",
        "bwIdNo, bwAcNo, ",
        "bwAcBankId, bwAcBankName, ",
        "bwAmt, mortgageId, ",
        "mortgageInfo, checkDate, ",
        "remark)",
        "VALUES (#{channelFlow,jdbcType=CHAR}, #{priority,jdbcType=SMALLINT}, ",
        "#{invokeMethod,jdbcType=SMALLINT}, #{channelDateTime,jdbcType=TIMESTAMP}, ",
        "#{transCode,jdbcType=CHAR}, #{appId,jdbcType=CHAR}, #{loanNo,jdbcType=INTEGER}, ",
        "#{investId,jdbcType=INTEGER}, #{investObjName,jdbcType=VARCHAR}, ",
        "#{investObjInfo,jdbcType=VARCHAR}, #{minInvestAmt,jdbcType=DECIMAL}, ",
        "#{maxInvestAmt,jdbcType=DECIMAL}, #{investObjAmt,jdbcType=DECIMAL}, ",
        "#{investBeginDate,jdbcType=DATE}, #{investEndDate,jdbcType=DATE}, ",
        "#{repayDate,jdbcType=DATE}, #{yearRate,jdbcType=DECIMAL}, ",
        "#{investRange,jdbcType=INTEGER}, #{ratesType,jdbcType=VARCHAR}, ",
        "#{repaysType,jdbcType=VARCHAR}, #{investObjState,jdbcType=SMALLINT}, ",
        "#{bwTotalNum,jdbcType=INTEGER}, #{zrFlag,jdbcType=BIT}, ",
        "#{refLoanNo,jdbcType=INTEGER}, #{oldReqSeq,jdbcType=VARCHAR}, ",
        "#{bwAcName,jdbcType=VARCHAR}, #{bwIdType,jdbcType=INTEGER}, ",
        "#{bwIdNo,jdbcType=VARCHAR}, #{bwAcNo,jdbcType=VARCHAR}, ",
        "#{bwAcBankId,jdbcType=VARCHAR}, #{bwAcBankName,jdbcType=VARCHAR}, ",
        "#{bwAmt,jdbcType=DECIMAL}, #{mortgageId,jdbcType=VARCHAR}, ",
        "#{mortgageInfo,jdbcType=VARCHAR}, #{checkDate,jdbcType=DATE}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(OGW00051Req req51);


    @Select({
        "SELECT * FROM ghbank_ogw00051",
        "WHERE channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    GhbankOGW00051 selectByPrimaryKey(String channelFlow);


    @Update({
        "UPDATE ghbank_ogw00051",
        "SET serverFlow = #{serverFlow,jdbcType=CHAR},",
          "serverDateTime = #{serverDateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT},",
          "errorCode = #{errorCode,jdbcType=VARCHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "resJnlNo = #{resJnlNo,jdbcType=VARCHAR},",
          "transDateTime = #{transDateTime,jdbcType=TIMESTAMP}",
        "where channelFlow = #{channelFlow,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(OGW00051Resp resp);
}