package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Q20003BeanMapper {

    @Select({
        "SELECT * FROM chanpay_q20003_bean",
        "WHERE outTradeNo = #{outTradeNo,jdbcType=VARCHAR}"
    })
    Q20003Bean selectByOutTradeNo(String outTradeNo);

    @Insert({
        "insert into chanpay_q20003_bean (outTradeNo, tradeAmount, ",
        "actionDesc, orderTime, ",
        "buyerMobile, buyerIp, ",
        "payerBankname, payerBankaccountNo, ",
        "ext1, ext2)",
        "values (#{outTradeNo,jdbcType=VARCHAR}, #{tradeAmount,jdbcType=DECIMAL}, ",
        "#{actionDesc,jdbcType=VARCHAR}, #{orderTime,jdbcType=TIMESTAMP}, ",
        "#{buyerMobile,jdbcType=BIGINT}, #{buyerIp,jdbcType=VARCHAR}, ",
        "#{payerBankname,jdbcType=VARCHAR}, #{payerBankaccountNo,jdbcType=VARCHAR}, ",
        "#{ext1,jdbcType=VARCHAR}, #{ext2,jdbcType=VARCHAR})"
    })
    int insert(Q20003Bean record);

    @InsertProvider(type=Q20003BeanSqlProvider.class, method="insertSelective")
    int insertSelective(Q20003Bean record);

    /**
     * 根据条件查询记录数
     * @param searchConditions
     * @return
     */
    @SelectProvider(type=Q20003BeanSqlProvider.class, method="selectRechargeCountByCondition")
    Long selectRechargeCountByCondition(HashMap<String, Object> searchConditions);

    /**
     * 根据条件查询充值列表
     * @param searchConditions
     * @return
     */
    @SelectProvider(type=Q20003BeanSqlProvider.class, method="selectRechargeListCondition")
    List<Map<String,Object>> selectRechargeListCondition(HashMap<String, Object> searchConditions);
}