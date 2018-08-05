package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.Q20008Bean;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class Q20008BeanSqlProvider {

    public String insertSelective(Q20008Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_q20008_bean");
        
        if (record.getNotifyId() != null) {
            VALUES("notifyId", "#{notifyId,jdbcType=VARCHAR}");
        }
        
        if (record.getNotifyType() != null) {
            VALUES("notifyType", "#{notifyType,jdbcType=VARCHAR}");
        }
        
        if (record.getNotifyTime() != null) {
            VALUES("notifyTime", "#{notifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getInputCharset() != null) {
            VALUES("inputCharset", "#{inputCharset,jdbcType=VARCHAR}");
        }
        
        if (record.getSign() != null) {
            VALUES("sign", "#{sign,jdbcType=VARCHAR}");
        }
        
        if (record.getSignType() != null) {
            VALUES("signType", "#{signType,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            VALUES("version", "#{version,jdbcType=DOUBLE}");
        }
        
        if (record.getOuterTradeNo() != null) {
            VALUES("outerTradeNo", "#{outerTradeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getInnerTradeNo() != null) {
            VALUES("innerTradeNo", "#{innerTradeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getTradeStatus() != null) {
            VALUES("tradeStatus", "#{tradeStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getTradeAmount() != null) {
            VALUES("tradeAmount", "#{tradeAmount,jdbcType=DECIMAL}");
        }
        
        if (record.getGmtCreate() != null) {
            VALUES("gmtCreate", "#{gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtPayment() != null) {
            VALUES("gmtPayment", "#{gmtPayment,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtClose() != null) {
            VALUES("gmtClose", "#{gmtClose,jdbcType=TIMESTAMP}");
        }
        
        if (record.getExtension() != null) {
            VALUES("extension", "#{extension,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }
}