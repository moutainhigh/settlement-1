package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G60002Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface G60002BeanMapper {

    @Select({
            "SELECT * FROM chanpay_g60002_bean",
            "WHERE qryReqSn = #{qryReqSn,jdbcType=VARCHAR}"
    })
    G60002Bean selectByQryReqSn(String qryReqSn);

    @Insert({
        "INSERT INTO chanpay_g60002_bean (reqSn, timestamp, ",
        "qryReqSn, batchQryReqSn, ",
        "batchRetCode, batchErrMsg, ",
        "dtlsn, dtlRetCode, ",
        "dtlErrMsg, dtlaccountNo, ",
        "dtlaccountName, retCode, ",
        "errMsg)",
        "VALUES (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=TIMESTAMP}, ",
        "#{qryReqSn,jdbcType=VARCHAR}, #{batchQryReqSn,jdbcType=VARCHAR}, ",
        "#{batchRetCode,jdbcType=VARCHAR}, #{batchErrMsg,jdbcType=VARCHAR}, ",
        "#{dtlsn,jdbcType=VARCHAR}, #{dtlRetCode,jdbcType=VARCHAR}, ",
        "#{dtlErrMsg,jdbcType=VARCHAR}, #{dtlaccountNo,jdbcType=VARCHAR}, ",
        "#{dtlaccountName,jdbcType=VARCHAR}, #{retCode,jdbcType=VARCHAR}, ",
        "#{errMsg,jdbcType=VARCHAR})"
    })
    int insert(G60002Bean record);

    @Update({
            "UPDATE chanpay_g60002_bean",
            "SET reqSn = #{reqSn,jdbcType=VARCHAR}, ",
            "timestamp = #{timestamp,jdbcType=TIMESTAMP},",
            "batchQryReqSn = #{batchQryReqSn,jdbcType=VARCHAR}, ",
            "batchRetCode = #{batchRetCode,jdbcType=VARCHAR}, ",
            "batchErrMsg = #{batchErrMsg,jdbcType=VARCHAR}, ",
            "dtlsn = #{dtlsn,jdbcType=VARCHAR}, ",
            "dtlRetCode = #{dtlRetCode,jdbcType=VARCHAR}, ",
            "dtlErrMsg = #{dtlErrMsg,jdbcType=VARCHAR}, ",
            "dtlaccountNo = #{dtlaccountNo,jdbcType=VARCHAR}, ",
            "dtlaccountName = #{dtlaccountName,jdbcType=VARCHAR}, ",
            "retCode = #{retCode,jdbcType=VARCHAR}, ",
            "errMsg = #{errMsg,jdbcType=VARCHAR} ",
            "WHERE reqSn = #{reqSn,jdbcType=VARCHAR}"
    })
    void update(G60002Bean record);

    @InsertProvider(type=G60002BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G60002Bean record);
}