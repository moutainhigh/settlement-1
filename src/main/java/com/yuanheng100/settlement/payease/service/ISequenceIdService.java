package com.yuanheng100.settlement.payease.service;

/**
 * 全局唯一流水号服务
 * @author Bai Song
 *
 */
public interface ISequenceIdService
{
    /**
     * 取得全局唯一的流水号
     * @return 
     */
    public int getSequenceId();
}
