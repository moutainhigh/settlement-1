package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.Q20008Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Q20008BeanMapper {

    @Insert({
        "INSERT INTO chanpay_q20008_bean (notifyId, notifyType, ",
        "notifyTime, inputCharset, ",
        "sign, signType, ",
        "version, outerTradeNo, ",
        "innerTradeNo, tradeStatus, ",
        "tradeAmount, gmtCreate, ",
        "gmtPayment, gmtClose, ",
        "extension)",
        "VALUES (#{notifyId,jdbcType=VARCHAR}, #{notifyType,jdbcType=VARCHAR}, ",
        "#{notifyTime,jdbcType=TIMESTAMP}, #{inputCharset,jdbcType=VARCHAR}, ",
        "#{sign,jdbcType=VARCHAR}, #{signType,jdbcType=VARCHAR}, ",
        "#{version,jdbcType=DOUBLE}, #{outerTradeNo,jdbcType=VARCHAR}, ",
        "#{innerTradeNo,jdbcType=VARCHAR}, #{tradeStatus,jdbcType=VARCHAR}, ",
        "#{tradeAmount,jdbcType=DECIMAL}, #{gmtCreate,jdbcType=TIMESTAMP}, ",
        "#{gmtPayment,jdbcType=TIMESTAMP}, #{gmtClose,jdbcType=TIMESTAMP}, ",
        "#{extension,jdbcType=VARCHAR})"
    })
    int insert(Q20008Bean record);

    @InsertProvider(type=Q20008BeanSqlProvider.class, method="insertSelective")
    int insertSelective(Q20008Bean record);

    /**
     * 根据订单编号和交易状态查询记录
     * @param outerTradeNo
     * @param tradeStatus
     * @return
     */
    @Select({
          "SELECT * FROM chanpay_q20008_bean",
          "WHERE outerTradeNo = #{outerTradeNo,jdbcType=VARCHAR}",
          "AND tradeStatus = #{tradeStatus,jdbcType=VARCHAR}"
    })
    Q20008Bean selectByOuterTradeNoAndTradeStatus(@Param("outerTradeNo") String outerTradeNo,@Param("tradeStatus") String tradeStatus);
}