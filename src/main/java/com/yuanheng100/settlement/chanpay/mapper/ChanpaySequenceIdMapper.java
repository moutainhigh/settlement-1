package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.SequenceId;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface ChanpaySequenceIdMapper {


    @Insert({"INSERT INTO chanpay_sequence_id() VALUES ()"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SequenceId sequenceId);



}