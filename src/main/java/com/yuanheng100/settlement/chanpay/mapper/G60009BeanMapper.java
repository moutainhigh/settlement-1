package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.HashMap;
import java.util.List;

public interface G60009BeanMapper {

    @Insert({
        "INSERT INTO chanpay_g60009_bean (reqSn, timestamp, ",
        "smsCode, orgReqSn, ",
        "subMerchantId, sn, ",
        "bankGeneralName, bankName, ",
        "bankCode, accountType, ",
        "accountNo, accountName, ",
        "idType, id, tel, ",
        "retCode, errMsg)",
        "VALUES (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=VARCHAR}, ",
        "#{smsCode,jdbcType=VARCHAR}, #{orgReqSn,jdbcType=VARCHAR}, ",
        "#{subMerchantId,jdbcType=VARCHAR}, #{sn,jdbcType=VARCHAR}, ",
        "#{bankGeneralName,jdbcType=VARCHAR}, #{bankName,jdbcType=VARCHAR}, ",
        "#{bankCode,jdbcType=INTEGER}, #{accountType,jdbcType=VARCHAR}, ",
        "#{accountNo,jdbcType=VARCHAR}, #{accountName,jdbcType=VARCHAR}, ",
        "#{idType,jdbcType=VARCHAR}, #{id,jdbcType=VARCHAR}, #{tel,jdbcType=BIGINT}, ",
        "#{retCode,jdbcType=VARCHAR}, #{errMsg,jdbcType=VARCHAR})"
    })
    int insert(G60009Bean record);

    @InsertProvider(type=G60009BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G60009Bean record);

    @SelectProvider(type=G60009BeanSqlProvider.class, method="selectAuthenticationByCondition")
    Long selectAuthenticationByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type=G60009BeanSqlProvider.class, method="selectAuthenticationListByCondition")
    List<G60009Bean> selectAuthenticationListByCondition(HashMap<String, Object> searchConditions);
}