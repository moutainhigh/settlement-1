package com.zcguodian.settlement.unspay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.Page;
import com.zcguodian.settlement.unspay.consts.UnspayZCGDStatus;
import com.zcguodian.settlement.unspay.consts.VerifyZCGDStatus;
import com.zcguodian.settlement.unspay.mapper.UnspayFourElementsPayMapper;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayMessageType;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayRequest;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayResponse;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;
import com.zcguodian.settlement.unspay.utils.UnspayZCGDUtil;

@Service("fourElementsPayService")
public class FourElementsPayServiceImpl extends AbstractMessageService<UnspayFourElementsPayRequest> implements IFourElementsPayService
{
	private static Logger logger = Logger.getLogger(FourElementsPayServiceImpl.class);
	@Autowired
	private UnspayFourElementsPayMapper unspayFourElementsPayMapper;
	@Autowired
	private SysStaffMapper sysStaffMapper;
	
	public UnspayFourElementsPayMapper getUnspayFourElementsPayMapper() {
        return unspayFourElementsPayMapper;
    }

	@Override
	public UnspayFourElementsPay getPayByOrderId (Integer orderId) {
		return unspayFourElementsPayMapper.selectByPrimaryKey(orderId);
	}
	
	@Override
    public void getZCGDUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        //查询总计数
        Long totalCount = unspayFourElementsPayMapper.selectFEPayUploadCount(searchConditions);
        if (totalCount == null) {
            totalCount = (long) 0;
        }
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = unspayFourElementsPayMapper.selectFEPayUploadList(searchConditions);
        //查询其它数据
        for (Map<String, Object> pay : list) {
            //操作人
            String staffName = sysStaffMapper.selectStaffNameById(Integer.parseInt(pay.get("operator").toString()));
            pay.put("staffName", staffName);

            List<UnspayFourElementsPay> unspayPays = unspayFourElementsPayMapper.selectFEPayListByFileName(pay.get("filename").toString());
            //状态
            //1.成功
            int success = 0;
            //2.失败
            int failur = 0;
            //3.处理中
            int process = 0;
            //4.未审核数
            int notVerify = 0;
            //5.未通过数
            int refused = 0;
            //6.总计
            int count = unspayPays.size();

            for (UnspayFourElementsPay ded : unspayPays) {

                Short verifyStatus = ded.getVerifyStatus();
                if (verifyStatus.equals(VerifyZCGDStatus.ZCGD_NOTAUDITED.getCode())) {
                    notVerify++;
                } else if (verifyStatus.equals(VerifyZCGDStatus.ZCGD_DISAPPROVE.getCode())) {
                    refused++;
                }

                String resultStatus = ded.getPayResult();
                if (resultStatus != null) {
                    if (UnspayZCGDStatus.ZCGD_HANDDING.getCode().equals(resultStatus) || resultStatus.equals(UnspayZCGDStatus.ZCGD_SUCCESS.getCode())) {
                        process++;
                    } else if (resultStatus.equals(UnspayZCGDStatus.ZCGD_TRADE_SUCCESS.getCode())) {
                        success++;
                    } else {
                        failur++;
                    }
                }
            }

            pay.put("success", success);
            pay.put("failur", failur);
            pay.put("process", process);
            pay.put("notVerify", notVerify);
            pay.put("refused", refused);
            pay.put("count", count);
        }
        page.setResult(list);
    }

	@Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        Long totalCount = unspayFourElementsPayMapper.selectFEPayListCount(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = unspayFourElementsPayMapper.selectFEPayList(searchConditions);
        page.setResult(list);
    }
	
	@Override
    public void saveFourElementsPay(UnspayFourElementsPay unspayFourElementsPay) {
		unspayFourElementsPayMapper.insert(unspayFourElementsPay);
        logger.info("新增支付记录：" + JSON.toJSONString(unspayFourElementsPay));
    }
	
	 @Override
    public List<Map<String, Object>> uploadZCGDFileDetail(String filename) {
        return unspayFourElementsPayMapper.selectZCGDPayMapListByFileName(filename);
    }
	 
    @Override
    public void refusePay(String orderIds, Short verifyStatus, Integer staffId) {
		unspayFourElementsPayMapper.verify(orderIds, verifyStatus, staffId);
    } 
	
	@Transactional
	@Override
	public void agreePay(String orderIds, Short verifyStatus, Integer staffId) {
		unspayFourElementsPayMapper.verify(orderIds, verifyStatus, staffId);
		String[] array = orderIds.split(",");
		for (String orderId : array){
			UnspayFourElementsPay unspayFourElementsPay = unspayFourElementsPayMapper.selectByPrimaryKey(Integer.valueOf(orderId));
			unspayFourElementsPay.setSendDate(new Date());
			//向银生宝发送代付,获取返回信息
			UnspayFourElementsPayResponse unspayFourElementsPayResponse = UnspayZCGDUtil.pay(unspayFourElementsPay);
			unspayFourElementsPay.setPayResult(unspayFourElementsPayResponse.getResultCode());
			unspayFourElementsPay.setDesc(unspayFourElementsPayResponse.getResultMessage());
			unspayFourElementsPay.setResponseDate(new Date());
			//根据返回结果修改实时代付表数据
			unspayFourElementsPayMapper.updateByPrimaryKey(unspayFourElementsPay);
		}
	}

	@Override
	public boolean saveZCGDPayResult(UnspayFourElementsPay unspayFourElementsPay)
	{
		 //保存结果
		unspayFourElementsPayMapper.updateCallbackResult(unspayFourElementsPay);
        logger.info("订单编号：" + unspayFourElementsPay.getOrderId() + "保存支付结果" + JSON.toJSONString(unspayFourElementsPay));
        return true;
	}

	@Override
	public UnspayFourElementsPayResponse queryOrderStatusRemote(UnspayFourElementsPay unspayFourElementsPay)
	{
		UnspayFourElementsPayRequest UnspayPayRequest = new UnspayFourElementsPayRequest();
        UnspayPayRequest.setOrderId(unspayFourElementsPay.getOrderId());
        UnspayPayRequest.setMessageType(UnspayFourElementsPayMessageType.ZCGD_QUERYORDER.getCode());
        logger.info("订单编号：" + unspayFourElementsPay.getOrderId() + "，远程同步查询支付结果" + JSON.toJSONString(unspayFourElementsPay));
        return (UnspayFourElementsPayResponse) syncSend(UnspayPayRequest);
	} 
}
