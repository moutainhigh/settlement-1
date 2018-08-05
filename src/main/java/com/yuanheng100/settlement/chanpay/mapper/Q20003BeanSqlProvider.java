package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.consts.Qpay;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;

import java.util.HashMap;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class Q20003BeanSqlProvider {

    public String insertSelective(Q20003Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_q20003_bean");
        
        if (record.getOutTradeNo() != null) {
            VALUES("outTradeNo", "#{outTradeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getTradeAmount() != null) {
            VALUES("tradeAmount", "#{tradeAmount,jdbcType=DECIMAL}");
        }
        
        if (record.getActionDesc() != null) {
            VALUES("actionDesc", "#{actionDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderTime() != null) {
            VALUES("orderTime", "#{orderTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getBuyerMobile() != null) {
            VALUES("buyerMobile", "#{buyerMobile,jdbcType=BIGINT}");
        }
        
        if (record.getBuyerIp() != null) {
            VALUES("buyerIp", "#{buyerIp,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerBankname() != null) {
            VALUES("payerBankname", "#{payerBankname,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerBankaccountNo() != null) {
            VALUES("payerBankaccountNo", "#{payerBankaccountNo,jdbcType=VARCHAR}");
        }
        
        if (record.getExt1() != null) {
            VALUES("ext1", "#{ext1,jdbcType=VARCHAR}");
        }
        
        if (record.getExt2() != null) {
            VALUES("ext2", "#{ext2,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectRechargeCountByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("chanpay_q20003_bean q20003");
        LEFT_OUTER_JOIN("chanpay_q20008_bean q20008 ON q20003.outTradeNo = q20008.outerTradeNo");
        WHERE("q20008.tradeStatus IS NULL OR q20008.tradeStatus != 'TRADE_FINISHED'");

        Object outTradeNo = searchConditions.get("outTradeNo");
        if (outTradeNo!=null&&!"".equals(outTradeNo)) {
            AND();
            WHERE("q20003.outTradeNo LIKE \"%\"#{outTradeNo,jdbcType=VARCHAR}\"%\"");
        }
        Object tradeStatus = searchConditions.get("tradeStatus");
        if (tradeStatus!=null&&!"".equals(tradeStatus)) {
            AND();
            if(tradeStatus.equals(Qpay.NotifyTradeStatus.WAIT_PAY.name())){
                WHERE("q20008.tradeStatus != 'TRADE_SUCCESS'");
                AND();
                WHERE("q20008.tradeStatus != 'TRADE_CLOSED'");
            }else if(tradeStatus.equals(Qpay.NotifyTradeStatus.TRADE_SUCCESS.name())){
                WHERE("q20008.tradeStatus = 'TRADE_SUCCESS'");
            }else if(tradeStatus.equals(Qpay.NotifyTradeStatus.TRADE_CLOSED.name())){
                WHERE("q20008.tradeStatus = 'TRADE_CLOSED'");
            }
        }
        Object orderTimeStartDate = searchConditions.get("orderTimeStartDate");
        if (orderTimeStartDate!=null) {
            AND();
            WHERE("q20003.orderTime >= #{orderTimeStartDate,jdbcType=TIMESTAMP}");
        }
        Object orderTimeEndDate = searchConditions.get("orderTimeEndDate");
        if (orderTimeEndDate!=null) {
            AND();
            WHERE("q20003.orderTime <= #{orderTimeEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectRechargeListCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("chanpay_q20003_bean q20003");
        LEFT_OUTER_JOIN("chanpay_q20008_bean q20008 ON q20003.outTradeNo = q20008.outerTradeNo");
        WHERE("q20008.tradeStatus IS NULL OR q20008.tradeStatus != 'TRADE_FINISHED'");

        Object outTradeNo = searchConditions.get("outTradeNo");
        if (outTradeNo!=null&&!"".equals(outTradeNo)) {
            AND();
            WHERE("q20003.outTradeNo LIKE \"%\"#{outTradeNo,jdbcType=VARCHAR}\"%\"");
        }
        Object tradeStatus = searchConditions.get("tradeStatus");
        if (tradeStatus!=null&&!"".equals(tradeStatus)) {
            AND();
            if(tradeStatus.equals(Qpay.NotifyTradeStatus.WAIT_PAY.name())){
                WHERE("q20008.tradeStatus != 'TRADE_SUCCESS'");
                AND();
                WHERE("q20008.tradeStatus != 'TRADE_CLOSED'");
            }else if(tradeStatus.equals(Qpay.NotifyTradeStatus.TRADE_SUCCESS.name())){
                WHERE("q20008.tradeStatus = 'TRADE_SUCCESS'");
            }else if(tradeStatus.equals(Qpay.NotifyTradeStatus.TRADE_CLOSED.name())){
                WHERE("q20008.tradeStatus = 'TRADE_CLOSED'");
            }
        }
        Object orderTimeStartDate = searchConditions.get("orderTimeStartDate");
        if (orderTimeStartDate!=null) {
            AND();
            WHERE("q20003.orderTime >= #{orderTimeStartDate,jdbcType=TIMESTAMP}");
        }
        Object orderTimeEndDate = searchConditions.get("orderTimeEndDate");
        if (orderTimeEndDate!=null) {
            AND();
            WHERE("q20003.orderTime <= #{orderTimeEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY q20003.orderTime DESC LIMIT #{fromIndex},#{endIndex}";
    }

}