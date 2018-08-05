package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G20013Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface G20014BeanMapper {

    @Insert({
        "INSERT INTO chanpay_g20014_bean (reqSn, timestamp, ",
        "trxReqSn, retCode, ",
        "errMsg, corpAcctNo, ",
        "accountNo, accountName, ",
        "protocolNo, amount, ",
        "charge, corpFlowNo, ",
        "summary, postscript)",
        "VALUES (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=TIMESTAMP}, ",
        "#{trxReqSn,jdbcType=VARCHAR}, #{retCode,jdbcType=VARCHAR}, ",
        "#{errMsg,jdbcType=VARCHAR}, #{corpAcctNo,jdbcType=VARCHAR}, ",
        "#{accountNo,jdbcType=VARCHAR}, #{accountName,jdbcType=VARCHAR}, ",
        "#{protocolNo,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{charge,jdbcType=DECIMAL}, #{corpFlowNo,jdbcType=VARCHAR}, ",
        "#{summary,jdbcType=VARCHAR}, #{postscript,jdbcType=VARCHAR})"
    })
    int insert(G20013Bean record);

    @InsertProvider(type=G20014BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G20013Bean record);

    @Select({
            "SELECT * FROM chanpay_g20014_bean",
            "WHERE trxReqSn = #{trxReqSn,jdbcType=VARCHAR}"
    })
    List<G20013Bean> selectByTrxReqSn(String trxReqSn);


}