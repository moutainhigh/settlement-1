package com.zcguodian.settlement.unspay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuanheng100.settlement.common.model.system.Page;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;

public interface IFourElementsPayService
{
	boolean fourElementsPay(UnspayFourElementsPay unspayFourElementsPay);
	
	String fourElementsPay(String result);
	
	String queryOrderStatus(Long orderId);
	
	String queryBlance();
	
	void getZCGDUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
	
	/**
     * 代付列表页面
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);
    
    /**
     * 保存新上传的代付对象
     */
    void saveFourElementsPay(UnspayFourElementsPay unspayFourElementsPay);
    
    /**
     * 获取上传记录页详情页数据
     *
     * @param filename
     * @return
     */
    List<Map<String, Object>> uploadZCGDFileDetail(String filename);
    
    /**
     * 拒绝代付
     *
     * @param orderIds
     * @param verifyStatus
     */
    void refusePay(String orderIds, Short verifyStatus, Integer staffId);
    /**
     * 同意代付
     *
     * @param orderIds
     * @param verifyStatus
     */
    void agreePay(String orderIds, Short verifyStatus, Integer staffId);
}
