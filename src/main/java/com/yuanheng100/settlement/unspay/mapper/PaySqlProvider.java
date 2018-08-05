package com.yuanheng100.settlement.unspay.mapper;


import static org.apache.ibatis.jdbc.SqlBuilder.AND;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.LEFT_OUTER_JOIN;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.Map;

import com.yuanheng100.settlement.unspay.model.UnspayPay;

public class PaySqlProvider {

    public String selectPayListCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("unspay_pay up");
        LEFT_OUTER_JOIN("unspay_sub_contract usc ON up.loanApplyId = usc.loanApplyId");
        WHERE("1=1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("usc.name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("usc.phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("usc.idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("usc.cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("up.filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object payResult = searchConditions.get("payResult");
        if (payResult!=null&&!"".equals(payResult)) {
            AND();
            if(payResult.equals("20")){
                WHERE("up.payResult != '10'");
                AND();
                WHERE("up.payResult != '0000'");
            }else{
                WHERE("up.payResult = #{payResult,jdbcType=VARCHAR}");
            }
        }
        Object responseStartDate = searchConditions.get("responseStartDate");
        if (responseStartDate!=null) {
            AND();
            WHERE("up.responseDate >= #{responseStartDate,jdbcType=TIMESTAMP}");
        }
        Object responseEndDate = searchConditions.get("responseEndDate");
        if (responseEndDate!=null) {
            AND();
            WHERE("up.responseDate <= #{responseEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectPayList(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("usc.name, usc.phoneNo, usc.idCardNo, usc.cardNo, usc.bankCode, up.orderId, up.filename,up.loanApplyId, up.amount, up.planDate, up.sendDate, up.responseDate, up.purpose, up.payResult, up.desc");
        FROM("unspay_pay up");
        LEFT_OUTER_JOIN("unspay_sub_contract usc ON up.loanApplyId = usc.loanApplyId");
        WHERE("up.verifyStatus = 1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("usc.name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("usc.phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("usc.idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("usc.cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("up.filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object payResult = searchConditions.get("payResult");
        if (payResult!=null&&!"".equals(payResult)) {
            AND();
            if(payResult.equals("20")){
                WHERE("up.payResult != '10'");
                AND();
                WHERE("up.payResult != '0000'");
            }else{
                WHERE("up.payResult = #{payResult,jdbcType=VARCHAR}");
            }
        }
        Object responseStartDate = searchConditions.get("responseStartDate");
        if (responseStartDate!=null) {
            AND();
            WHERE("up.responseDate >= #{responseStartDate,jdbcType=TIMESTAMP}");
        }
        Object responseEndDate = searchConditions.get("responseEndDate");
        if (responseEndDate!=null) {
            AND();
            WHERE("up.responseDate <= #{responseEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY up.orderId DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String selectPayUploadCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(DISTINCT filename)");
        FROM("unspay_pay");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        if (searchConditions.get("filename")!=null) {
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL();
    }

    public String selectPayUploadList(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("DISTINCT filename, uploadDate, operator");
        FROM("unspay_pay");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        if (searchConditions.get("filename")!=null) {
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL() + " ORDER BY uploadDate DESC, filename DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String insertSelective(UnspayPay record) {
        BEGIN();
        INSERT_INTO("unspay_pay");
        
        VALUES("orderId", "#{orderId,jdbcType=INTEGER}");
        
        if (record.getFilename() != null) {
            VALUES("filename", "#{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getUploadDate() != null) {
            VALUES("uploadDate", "#{uploadDate,jdbcType=DATE}");
        }
        
        if (record.getOperator() != null) {
            VALUES("operator", "#{operator,jdbcType=INTEGER}");
        }
        
        if (record.getAuditor() != null) {
            VALUES("auditor", "#{auditor,jdbcType=INTEGER}");
        }
        
        if (record.getLoanApplyId() != null) {
            VALUES("loanApplyId", "#{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCardNo() != null) {
            VALUES("cardNo", "#{cardNo,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPurpose() != null) {
            VALUES("purpose", "#{purpose,jdbcType=VARCHAR}");
        }

        if (record.getVerifyStatus() != null) {
            VALUES("verifyStatus", "#{verifyStatus,jdbcType=SMALLINT}");
        }

        if (record.getPlanDate() != null) {
            VALUES("planDate", "#{planDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendDate() != null) {
            VALUES("sendDate", "#{sendDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseDate() != null) {
            VALUES("responseDate", "#{responseDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPayResult() != null) {
            VALUES("payResult", "#{payResult,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            VALUES("desc", "#{desc,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(UnspayPay record) {
        BEGIN();
        UPDATE("unspay_pay");
        
        if (record.getFilename() != null) {
            SET("filename = #{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getUploadDate() != null) {
            SET("uploadDate = #{uploadDate,jdbcType=DATE}");
        }
        
        if (record.getOperator() != null) {
            SET("operator = #{operator,jdbcType=INTEGER}");
        }
        
        if (record.getAuditor() != null) {
            SET("auditor = #{auditor,jdbcType=INTEGER}");
        }
        
        if (record.getLoanApplyId() != null) {
            SET("loanApplyId = #{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCardNo() != null) {
            SET("cardNo = #{cardNo,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            SET("amount = #{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPurpose() != null) {
            SET("purpose = #{purpose,jdbcType=VARCHAR}");
        }

        if (record.getVerifyStatus() != null) {
            SET("verifyStatus = #{verifyStatus,jdbcType=SMALLINT}");
        }

        if (record.getPlanDate() != null) {
            SET("planDate = #{planDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendDate() != null) {
            SET("sendDate = #{sendDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseDate() != null) {
            SET("responseDate = #{responseDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPayResult() != null) {
            SET("payResult = #{payResult,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            SET("desc = #{desc,jdbcType=VARCHAR}");
        }
        
        WHERE("orderId = #{orderId,jdbcType=INTEGER}");
        
        return SQL();
    }
}