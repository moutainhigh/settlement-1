package com.yuanheng100.settlement.unspay.mapper;

import com.yuanheng100.settlement.unspay.model.UnspaySubContract;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class SubContractSqlProvider {

    public String selectSubContractList(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("unspay_sub_contract");
        WHERE("1=1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object signStatus = searchConditions.get("signStatus");
        if (signStatus!=null&&!"".equals(signStatus)) {
            AND();
            if(signStatus.equals("10")){
                WHERE("signStatus IS NULL");
            }else if(signStatus.equals("20")){
                WHERE("signStatus != '0000'");
            }else{
                WHERE("signStatus = #{signStatus,jdbcType=VARCHAR}");
            }
        }
        return SQL() + " ORDER BY uploadDate DESC, filename DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String selectSubContractListCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("unspay_sub_contract");
        WHERE("1=1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object signStatus = searchConditions.get("signStatus");
        if (signStatus!=null&&!"".equals(signStatus)) {
            AND();
            if(signStatus.equals("10")){
                WHERE("signStatus IS NULL");
            }else if(signStatus.equals("20")){
                WHERE("signStatus != '0000'");
            }else{
                WHERE("signStatus = #{signStatus,jdbcType=VARCHAR}");
            }
        }
        return SQL();
    }

    public String selectSubContractUploadCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(DISTINCT filename)");
        FROM("unspay_sub_contract");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL();
    }


    public String selectSubContractUploadList(Map<String,Object> searchConditions) {
        BEGIN();
        SELECT("DISTINCT filename, uploadDate, operator");
        FROM("unspay_sub_contract");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL() + " ORDER BY uploadDate DESC, filename DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String insertSelective(UnspaySubContract record) {
        BEGIN();
        INSERT_INTO("unspay_sub_contract");

        VALUES("id", "#{id,jdbcType=INTEGER}");

        if (record.getFilename() != null) {
            VALUES("filename", "#{filename,jdbcType=VARCHAR}");
        }

        if (record.getUploadDate() != null) {
            VALUES("uploadDate", "#{uploadDate,jdbcType=DATE}");
        }

        if (record.getOperator() != null) {
            VALUES("operator", "#{operator,jdbcType=INTEGER}");
        }

        if (record.getLoanApplyId() != null) {
            VALUES("loanApplyId", "#{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getIdCardNo() != null) {
            VALUES("idCardNo", "#{idCardNo,jdbcType=VARCHAR}");
        }

        if (record.getCardNo() != null) {
            VALUES("cardNo", "#{cardNo,jdbcType=VARCHAR}");
        }

        if (record.getBankCode() != null) {
            VALUES("bankCode", "#{bankCode,jdbcType=SMALLINT}");
        }

        if (record.getPhoneNo() != null) {
            VALUES("phoneNo", "#{phoneNo,jdbcType=INTEGER}");
        }

        if (record.getStartDate() != null) {
            VALUES("startDate", "#{startDate,jdbcType=DATE}");
        }

        if (record.getEndDate() != null) {
            VALUES("endDate", "#{endDate,jdbcType=DATE}");
        }

        if (record.getCycle() != null) {
            VALUES("cycle", "#{cycle,jdbcType=SMALLINT}");
        }

        if (record.getTriesLimit() != null) {
            VALUES("triesLimit", "#{triesLimit,jdbcType=INTEGER}");
        }

        if (record.getSendDate() != null) {
            VALUES("sendDate", "#{sendDate,jdbcType=DATE}");
        }

        if (record.getSubContractId() != null) {
            VALUES("subContractId", "#{subContractId,jdbcType=VARCHAR}");
        }

        return SQL();
    }


    public String updateByLoanApplyIdSelective(UnspaySubContract record) {
        BEGIN();
        UPDATE("unspay_sub_contract");

        if (record.getOperator() != null) {
            SET("operator = #{operator,jdbcType=INTEGER}");
        }

        if (record.getCardNo() != null) {
            SET("cardNo = #{cardNo,jdbcType=VARCHAR}");
        }

        if (record.getBankCode() != null) {
            SET("bankCode = #{bankCode,jdbcType=SMALLINT}");
        }

        if (record.getPhoneNo() != null) {
            SET("phoneNo = #{phoneNo,jdbcType=INTEGER}");
        }

        if (record.getStartDate() != null) {
            SET("startDate = #{startDate,jdbcType=DATE}");
        }

        if (record.getEndDate() != null) {
            SET("endDate = #{endDate,jdbcType=DATE}");
        }

        if (record.getCycle() != null) {
            SET("cycle = #{cycle,jdbcType=SMALLINT}");
        }

        if (record.getTriesLimit() != null) {
            SET("triesLimit = #{triesLimit,jdbcType=INTEGER}");
        }

        if (record.getSendDate() != null) {
            SET("sendDate = #{sendDate,jdbcType=DATE}");
        }

        WHERE("loanApplyId = #{loanApplyId,jdbcType=BIGINT}");

        return SQL();
    }

    public String updateByPrimaryKeySelective(UnspaySubContract record) {
        BEGIN();
        UPDATE("unspay_sub_contract");

        if (record.getFilename() != null) {
            SET("filename = #{filename,jdbcType=VARCHAR}");
        }

        if (record.getUploadDate() != null) {
            SET("uploadDate = #{uploadDate,jdbcType=DATE}");
        }

        if (record.getOperator() != null) {
            SET("operator = #{operator,jdbcType=INTEGER}");
        }

        if (record.getLoanApplyId() != null) {
            SET("loanApplyId = #{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getIdCardNo() != null) {
            SET("idCardNo = #{idCardNo,jdbcType=VARCHAR}");
        }

        if (record.getCardNo() != null) {
            SET("cardNo = #{cardNo,jdbcType=VARCHAR}");
        }

        if (record.getBankCode() != null) {
            SET("bankCode = #{bankCode,jdbcType=SMALLINT}");
        }

        if (record.getPhoneNo() != null) {
            SET("phoneNo = #{phoneNo,jdbcType=INTEGER}");
        }

        if (record.getStartDate() != null) {
            SET("startDate = #{startDate,jdbcType=DATE}");
        }

        if (record.getEndDate() != null) {
            SET("endDate = #{endDate,jdbcType=DATE}");
        }

        if (record.getCycle() != null) {
            SET("cycle = #{cycle,jdbcType=SMALLINT}");
        }

        if (record.getTriesLimit() != null) {
            SET("triesLimit = #{triesLimit,jdbcType=INTEGER}");
        }

        if (record.getSendDate() != null) {
            SET("sendDate = #{sendDate,jdbcType=DATE}");
        }

        if (record.getSubContractId() != null) {
            SET("subContractId = #{subContractId,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }
}