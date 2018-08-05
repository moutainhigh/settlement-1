package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G40001Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface G40001BeanMapper {

    @Insert({
            "INSERT INTO chanpay_g40001_bean (sn, bankCode, ",
            "accountBankCode, clearingBankCode, ",
            "accountBankName, regionCode, ",
            "secondGeneration)",
            "VALUES (#{sn,jdbcType=INTEGER}, #{bankCode,jdbcType=VARCHAR}, ",
            "#{accountBankCode,jdbcType=VARCHAR}, #{clearingBankCode,jdbcType=VARCHAR}, ",
            "#{accountBankName,jdbcType=VARCHAR}, #{regionCode,jdbcType=INTEGER}, ",
            "#{secondGeneration,jdbcType=VARCHAR})"
    })
    int insert(G40001Bean record);

    @Select({
            "SELECT * FROM chanpay_g40001_bean",
            "WHERE bankCode = #{bankCode,jdbcType=VARCHAR}",
            "AND accountBankName LIKE \"%\"#{accountBankName,jdbcType=VARCHAR}\"%\" LIMIT 0 , 10"
    })
    List<G40001Bean> selectByBankCodeAndAccountBankName(@Param("bankCode") String bankCode, @Param("accountBankName") String accountBankName);

    /**
     * 根据支行行号获取G40001Bean
     *
     * @param accountBankCode
     * @return
     */
    @Select({
            "SELECT * FROM chanpay_g40001_bean",
            "WHERE accountBankCode = #{accountBankCode,jdbcType=VARCHAR}"
    })
    G40001Bean selectByAccountBankCode(String accountBankCode);
}