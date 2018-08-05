package com.yuanheng100.settlement.ghbank.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import com.yuanheng100.settlement.ghbank.model.GHBankSequenceId;

public interface GHBankSequenceIdMapper
{

    /**
     * 获取一个自增的sequenceId
     * @return
     */
    @Insert({"INSERT INTO ghbank_sequence_id() VALUES ()"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int getSequenceId(GHBankSequenceId sequenceId);

}