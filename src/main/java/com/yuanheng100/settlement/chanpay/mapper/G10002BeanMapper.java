package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface G10002BeanMapper {

    @Insert({
        "INSERT INTO chanpay_g10002_bean (reqSn, timestamp, bankGeneralName, ",
        "accountNo, accountName, ",
        "bankName, amount, ",
        "idType, id, tel, ",
        "corpFlowNo, summary, ",
        "postscript, retCode, ",
        "errMsg)",
        "VALUES (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=TIMESTAMP}, #{bankGeneralName,jdbcType=VARCHAR}, ",
        "#{accountNo,jdbcType=VARCHAR}, #{accountName,jdbcType=VARCHAR}, ",
        "#{bankName,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{idType,jdbcType=VARCHAR}, #{id,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, ",
        "#{corpFlowNo,jdbcType=VARCHAR}, #{summary,jdbcType=VARCHAR}, ",
        "#{postscript,jdbcType=VARCHAR}, #{retCode,jdbcType=VARCHAR}, ",
        "#{errMsg,jdbcType=VARCHAR})"
    })
    int insert(G10001Bean record);

    @InsertProvider(type=G10002BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G10001Bean record);

    @Select({
            "SELECT * FROM chanpay_g10002_bean",
            "WHERE reqSn = #{reqSn,jdbcType=VARCHAR}"
    })
    G10001Bean selectByReqSn(String reqSn);

    @Update({
            "UPDATE chanpay_g10002_bean",
            "SET tradeCode = #{tradeCode,jdbcType=VARCHAR}, ",
            "tradeMsg = #{tradeMsg,jdbcType=VARCHAR}",
            "WHERE reqSn = #{reqSn,jdbcType=VARCHAR}"
    })
    Integer updateTradeResult(G10001Bean record);

    @SelectProvider(type=G10002BeanSqlProvider.class, method="selectPayCountByCondition")
    Long selectPayCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type=G10002BeanSqlProvider.class, method="selectPayListCondition")
    List<Map<String,Object>> selectPayListCondition(HashMap<String, Object> searchConditions);

}