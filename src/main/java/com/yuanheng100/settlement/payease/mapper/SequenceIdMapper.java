package com.yuanheng100.settlement.payease.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import com.yuanheng100.settlement.payease.model.other.SequenceId;

public interface SequenceIdMapper {


    @Insert({"INSERT INTO payease_sequence_id() VALUES ()"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SequenceId sequenceId);



}