package com.yuanheng100.settlement.ghbank.service;

/**
 * 配置信息的获取
 * 
 * @author Baisong
 *
 */
public interface IGhbankConfigService
{

    /**
     * 获取提交表单的华兴银行地址
     * @return
     */
    public String getPostFormUrl();
}
