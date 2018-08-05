package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yuanheng100.settlement.ghbank.model.dao.GhbankOGW00074RepayItem;
import com.yuanheng100.settlement.ghbank.model.repay.OGW00074Req.RepayItem;

public interface GhbankOGW00074RepayItemMapper {


    @Insert({
        "INSERT INTO ghbank_ogw00074_repayitem (subSeqNo, oldReqSeqNo, ",
        "acNo, acName, incomeDate, ",
        "amount, principalAmt, ",
        "incomeAmt, feeAmt)",
        "VALUES (#{subSeqNo,jdbcType=CHAR}, #{oldReqSeqNo,jdbcType=CHAR}, ",
        "#{acNo,jdbcType=VARCHAR}, #{acName,jdbcType=VARCHAR}, #{incomeDate,jdbcType=DATE}, ",
        "#{amount,jdbcType=DECIMAL}, #{principalAmt,jdbcType=DECIMAL}, ",
        "#{incomeAmt,jdbcType=DECIMAL}, #{feeAmt,jdbcType=DECIMAL})"
    })
    int insert(RepayItem repayItem);


    @Select({
        "SELECT * FROM ghbank_ogw00074_repayitem",
        "WHERE subSeqNo = #{subSeqNo,jdbcType=CHAR}"
    })
    GhbankOGW00074RepayItem selectByPrimaryKey(String subSeqNo);


    @Update({
        "UPDATE ghbank_ogw00074_repayitem",
        "SET oldReqSeqNo = #{oldReqSeqNo,jdbcType=CHAR},",
          "acNo = #{acNo,jdbcType=VARCHAR},",
          "acName = #{acName,jdbcType=VARCHAR},",
          "incomeDate = #{incomeDate,jdbcType=DATE},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "principalAmt = #{principalAmt,jdbcType=DECIMAL},",
          "incomeAmt = #{incomeAmt,jdbcType=DECIMAL},",
          "feeAmt = #{feeAmt,jdbcType=DECIMAL},",
          "status = #{status,jdbcType=CHAR},",
          "errorMsg = #{errorMsg,jdbcType=VARCHAR},",
          "transDateTime = #{transDateTime,jdbcType=TIMESTAMP},",
          "hostJnlNo = #{hostJnlNo,jdbcType=VARCHAR},",
          "extFiled3 = #{extFiled3,jdbcType=VARCHAR}",
        "where subSeqNo = #{subSeqNo,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(GhbankOGW00074RepayItem record);
}