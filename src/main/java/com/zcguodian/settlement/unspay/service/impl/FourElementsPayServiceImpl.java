package com.zcguodian.settlement.unspay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.consts.VerifyStatus;
import com.zcguodian.settlement.unspay.mapper.UnspayFourElementsPayMapper;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;
import com.zcguodian.settlement.unspay.utils.UnspayZCGDUtil;
import com.zcguodian.util.HttpUtil;
import com.zcguodian.util.MD5Util;

@Service("fourElementsPayService")
public class FourElementsPayServiceImpl implements IFourElementsPayService
{
	private static Logger logger = Logger.getLogger(FourElementsPayServiceImpl.class);
	@Autowired
	private UnspayFourElementsPayMapper unspayFourElementsPayMapper;
	@Autowired
	private SysStaffMapper sysStaffMapper;
	
	@Override
	public boolean fourElementsPay(UnspayFourElementsPay unspayFourElementsPay)
	{
		// http://xtwu42.natappfree.cc
		StringBuffer stringBuffer = new StringBuffer();
//		if(unspayFourElementsPay.getAccountId() != null)
//		{
//			stringBuffer.append("accountId=").append(unspayFourElementsPay.getAccountId()).append("&");
//		}
		if(unspayFourElementsPay.getName() != null)
		{
			stringBuffer.append("name=").append(unspayFourElementsPay.getName()).append("&");
		}
		if(unspayFourElementsPay.getCardNo() != null)
		{
			stringBuffer.append("cardNo=").append(unspayFourElementsPay.getCardNo()).append("&");
		}
		if(unspayFourElementsPay.getOrderId() != null)
		{
			stringBuffer.append("orderId=").append(unspayFourElementsPay.getOrderId()).append("&");
		}
		if(unspayFourElementsPay.getPurpose() != null)
		{
			stringBuffer.append("purpose=").append(unspayFourElementsPay.getPurpose()).append("&");
		}
		if(unspayFourElementsPay.getAmount() != null)
		{
			stringBuffer.append("amount=").append(unspayFourElementsPay.getAmount()).append("&");
		}
		if(unspayFourElementsPay.getIdCardNo() != null)
		{
			stringBuffer.append("idCardNo=").append(unspayFourElementsPay.getIdCardNo()).append("&");
		}
		if(unspayFourElementsPay.getSummary() != null)
		{
			stringBuffer.append("summary=").append(unspayFourElementsPay.getSummary()).append("&");
		}
		if(unspayFourElementsPay.getPhoneNo() != null)
		{
			stringBuffer.append("phoneNo=").append(unspayFourElementsPay.getPhoneNo()).append("&");
		}
//		if(unspayFourElementsPay.getResponseUrl() != null)
//		{
//			stringBuffer.append("responseUrl=").append(unspayFourElementsPay.getResponseUrl()).append("&");
//		}
		String str = stringBuffer.toString();
		
//		stringBuffer.append("key=123456abc");
		stringBuffer.append("key=zcgd635635");
		
		str = str + "mac=" + MD5Util.MD5(stringBuffer.toString());
		logger.info("表单：" + str);
		
		logger.info("MAC串：" + stringBuffer.toString());
		
//		unspayFourElementsPay.setMac(MD5Util.MD5(stringBuffer.toString()));
		
//		String result = HttpUtil.sendPost("http://180.166.114.155:7181/delegate-pay-front-dp/delegatePay/fourElementsPay", str);
		String result = HttpUtil.sendPost("https://www.unspay.com/delegate-pay-front/delegatePay/fourElementsPay", str);
		
		logger.info("四要素实时代付响应信息：" + result);
		return false;
	}

	@Override
	public String queryOrderStatus(Long orderId)
	{
		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("accountId=").append("1120180709153050001").append("&");
		stringBuffer.append("accountId=").append("2120180702095307001").append("&");
		stringBuffer.append("orderId=").append(orderId).append("&");
		
		String str = stringBuffer.toString();
//		stringBuffer.append("key=123456abc");
		stringBuffer.append("key=zcgd635635");
		
		String MAC = MD5Util.MD5(stringBuffer.toString());
		str = str + "mac=" + MAC;
		logger.info("订单状态查询发送信息：" + str);
//		String result = HttpUtil.sendPost("http://180.166.114.155:7181/delegate-pay-front-dp/delegatePay/queryOrderStatus", str);
		String result = HttpUtil.sendPost("https://www.unspay.com/delegate-pay-front/delegatePay/queryOrderStatus", str);
		logger.info("订单状态查询响应信息：" + result);
		return result;
	}
	
	@Override
	public String fourElementsPay(String result)
	{
		System.out.println("响应结果" + result);
		logger.info("实时代付结果" + result);
		return null;
	}

	//2120180702095307001
	//1120180709153050001
	@Override
	public String queryBlance()
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("accountId=").append("2120180702095307001").append("&");
		String str = stringBuffer.toString();
		stringBuffer.append("key=zcgd635635");
		String mac = MD5Util.MD5(stringBuffer.toString());
		str = str + "mac=" + mac;
		logger.info("余额查询发送信息：" + str);
//		String result = HttpUtil.sendPost("http://180.166.114.155:7181/delegate-pay-front/delegatePay/queryBlance", str);
		String result = HttpUtil.sendPost("https://www.unspay.com/delegate-pay-front/delegatePay/queryBlance", str);
		logger.info("余额查询响应信息：" + result);
		return result;
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
                if (verifyStatus.equals(VerifyStatus.NOTAUDITED.getCode())) {
                    notVerify++;
                } else if (verifyStatus.equals(VerifyStatus.DISAPPROVE.getCode())) {
                    refused++;
                }

                String resultStatus = ded.getPayResult();
                if (resultStatus != null) {
                    if (UnspayStatus.HANDDING.getCode().equals(resultStatus)) {
                        process++;
                    } else if (resultStatus.equals(UnspayStatus.SUCCESS.getCode())) {
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
			
		}
		
		if (verifyStatus == VerifyStatus.APPROVE.getCode()) {
			//修改状态为处理中
			unspayFourElementsPayMapper.changePayStatus(orderIds, UnspayStatus.HANDDING.getCode());
		}
	} 
}
