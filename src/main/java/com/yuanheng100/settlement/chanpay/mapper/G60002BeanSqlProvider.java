package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G60002Bean;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class G60002BeanSqlProvider {

    public String insertSelective(G60002Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_g60002_bean");
        
        if (record.getReqSn() != null) {
            VALUES("reqSn", "#{reqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getTimestamp() != null) {
            VALUES("timestamp", "#{timestamp,jdbcType=TIMESTAMP}");
        }
        
        if (record.getQryReqSn() != null) {
            VALUES("qryReqSn", "#{qryReqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getBatchQryReqSn() != null) {
            VALUES("batchQryReqSn", "#{batchQryReqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getBatchRetCode() != null) {
            VALUES("batchRetCode", "#{batchRetCode,jdbcType=VARCHAR}");
        }
        
        if (record.getBatchErrMsg() != null) {
            VALUES("batchErrMsg", "#{batchErrMsg,jdbcType=VARCHAR}");
        }
        
        if (record.getDtlsn() != null) {
            VALUES("dtlsn", "#{dtlsn,jdbcType=VARCHAR}");
        }
        
        if (record.getDtlRetCode() != null) {
            VALUES("dtlRetCode", "#{dtlRetCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDtlErrMsg() != null) {
            VALUES("dtlErrMsg", "#{dtlErrMsg,jdbcType=VARCHAR}");
        }
        
        if (record.getDtlaccountNo() != null) {
            VALUES("dtlaccountNo", "#{dtlaccountNo,jdbcType=VARCHAR}");
        }
        
        if (record.getDtlaccountName() != null) {
            VALUES("dtlaccountName", "#{dtlaccountName,jdbcType=VARCHAR}");
        }
        
        if (record.getRetCode() != null) {
            VALUES("retCode", "#{retCode,jdbcType=VARCHAR}");
        }
        
        if (record.getErrMsg() != null) {
            VALUES("errMsg", "#{errMsg,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }
}