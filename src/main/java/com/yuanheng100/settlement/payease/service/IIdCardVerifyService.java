package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.settlement.payease.model.other.IdcardVerify;

import java.util.List;
import java.util.Map;

/**
 * 首信易身份证验证接口
 */
public interface IIdCardVerifyService
{
    boolean verifyIdcard(String name , String idcardNo);

    List<IdcardVerify> getIdCardVerifyList(Map<String, Object> param);

    int getIdCardVerifyListCount(Map<String, Object> param);
}
