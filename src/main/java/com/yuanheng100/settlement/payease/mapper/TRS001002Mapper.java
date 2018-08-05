package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.provider.TRS001002SqlProvider;
import org.apache.ibatis.annotations.*;

import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Req;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TRS001002Mapper {

    @Insert({
        "insert into payease_trs001002 (reqTime, ",
        "operationCode, serlNum, ",
        "authId, borrower, ",
        "borrowerId, borrowerName, ",
        "contractNum, certNum, ",
        "lender, lenderId, lenderName, ",
        "loanAmount, moneyRecordId)",
        "values (#{reqTime,jdbcType=TIMESTAMP}, ",
        "#{operationCode,jdbcType=SMALLINT}, #{serlNum,jdbcType=INTEGER}, ",
        "#{authId,jdbcType=INTEGER}, #{borrower,jdbcType=INTEGER}, ",
        "#{borrowerId,jdbcType=CHAR}, #{borrowerName,jdbcType=VARCHAR}, ",
        "#{contractNum,jdbcType=INTEGER}, #{certNum,jdbcType=INTEGER}, ",
        "#{lender,jdbcType=INTEGER}, #{lenderId,jdbcType=CHAR}, #{lenderName,jdbcType=VARCHAR}, ",
        "#{loanAmount,jdbcType=DECIMAL}, #{moneyRecordId,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(TRS001002Req record);


    @Select({
        "select * from payease_trs001002",
        "where msgid = #{msgid,jdbcType=INTEGER}"
    })
    TRS001002Resp selectByPrimaryKey(Integer msgid);

    
    @Update({
        "update payease_trs001002",
        "set moneyRecordId = #{moneyRecordId,jdbcType=INTEGER},",
          "returnCode = #{returnCode,jdbcType=CHAR},",
          "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
          "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
        "where msgid = #{msgid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TRS001002Resp record);

    @SelectProvider(type = TRS001002SqlProvider.class,method = "getRecords002")
    List<TRS001002Resp> getRecords002(Map<String, Object> param);

    @SelectProvider(type = TRS001002SqlProvider.class,method = "getRecords002Count")
    int getRecords002Count(Map<String, Object> param);

    @SelectProvider(type = TRS001002SqlProvider.class,method = "selectInvestCountByCondition")
    Long selectInvestCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type = TRS001002SqlProvider.class,method = "selectInvestListByCondition")
    List<Map<String,Object>> selectInvestListByCondition(HashMap<String, Object> searchConditions);
}