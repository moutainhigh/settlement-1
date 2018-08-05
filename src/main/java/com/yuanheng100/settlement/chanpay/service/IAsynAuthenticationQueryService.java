package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.model.G60002Bean;

/**
 * Created by jlqian on 2016/9/28.
 */
public interface IAsynAuthenticationQueryService
{
    /**
     * 查询身份认证结果
     * @param accountName
     * @param accountNo
     * @param id
     * @param tel
     * @return
     * @throws IllegalPlatformAugumentException
     */
    G60002Bean query(String accountName, String accountNo, String id, String tel) throws IllegalPlatformAugumentException;

    /**
     * 查询身份认证结果
     * @param qryReqSn
     * @return
     */
    G60002Bean query(String qryReqSn);

}
