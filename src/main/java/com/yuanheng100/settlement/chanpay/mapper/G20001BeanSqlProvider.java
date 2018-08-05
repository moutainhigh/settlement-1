package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G20001Bean;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class G20001BeanSqlProvider {

    public String insertSelective(G20001Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_g20001_bean");
        
        if (record.getReqSn() != null) {
            VALUES("reqSn", "#{reqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getTimestamp() != null) {
            VALUES("timestamp", "#{timestamp,jdbcType=TIMESTAMP}");
        }
        
        if (record.getQryReqSn() != null) {
            VALUES("qryReqSn", "#{qryReqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getCharge() != null) {
            VALUES("charge", "#{charge,jdbcType=DECIMAL}");
        }
        
        if (record.getAccountNo() != null) {
            VALUES("accountNo", "#{accountNo,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountName() != null) {
            VALUES("accountName", "#{accountName,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getCorpFlowNo() != null) {
            VALUES("corpFlowNo", "#{corpFlowNo,jdbcType=VARCHAR}");
        }
        
        if (record.getSummary() != null) {
            VALUES("summary", "#{summary,jdbcType=VARCHAR}");
        }
        
        if (record.getPostscript() != null) {
            VALUES("postscript", "#{postscript,jdbcType=VARCHAR}");
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