package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G20001Bean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface G20001BeanMapper {

    @Insert({
        "insert into chanpay_g20001_bean (reqSn, timestamp, ",
        "qryReqSn, charge, ",
        "accountNo, accountName, ",
        "amount, corpFlowNo, ",
        "summary, postscript, ",
        "retCode, errMsg)",
        "values (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=TIMESTAMP}, ",
        "#{qryReqSn,jdbcType=VARCHAR}, #{charge,jdbcType=DECIMAL}, ",
        "#{accountNo,jdbcType=VARCHAR}, #{accountName,jdbcType=VARCHAR}, ",
        "#{amount,jdbcType=DECIMAL}, #{corpFlowNo,jdbcType=VARCHAR}, ",
        "#{summary,jdbcType=VARCHAR}, #{postscript,jdbcType=VARCHAR}, ",
        "#{retCode,jdbcType=VARCHAR}, #{errMsg,jdbcType=VARCHAR})"
    })
    int insert(G20001Bean record);

    @InsertProvider(type=G20001BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G20001Bean record);

    @Select({
            "SELECT * FROM chanpay_g20001_bean",
            "WHERE qryReqSn = #{qryReqSn,jdbcType=VARCHAR}"
    })
    List<G20001Bean> selectByQryReqSn(String qryReqSn);

}