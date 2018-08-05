package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;

/**
 * 查询服务 TRS001010
 * @author Bai Song
 *
 */
public interface IQueryService
{
    /**
     * 查询投资类账户 TRS001010-60000
     *
     * 必填参数:USER:P2P网站注册名
     *
     * @param trs001010Req
     * @return
     */
    public TRS001010Resp queryInvestAccount(TRS001010Req trs001010Req) throws IllegalArgumentException;
    
    /**
     * 查询负债类账户 TRS001010-60001
     *
     * 必填参数:USER:P2P网站注册名
     *
     * @return
     */
    public TRS001010Resp queryLiabilityAccount(TRS001010Req trs001010Req) throws IllegalArgumentException;
}
