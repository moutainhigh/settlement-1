package com.yuanheng100.settlement.unspay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.consts.VerifyStatus;
import com.yuanheng100.settlement.unspay.model.UnspayPay;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.mapper.PayMapper;
import com.yuanheng100.settlement.unspay.model.UnspayPayMessageType;
import com.yuanheng100.settlement.unspay.model.UnspayPayRequest;
import com.yuanheng100.settlement.unspay.model.UnspayPayResponse;
import com.yuanheng100.settlement.unspay.service.IPayService;

public class PayServiceImpl extends AbstractMessageService<UnspayPayRequest> implements IPayService {

    private static Logger logger = Logger.getLogger(PayServiceImpl.class);

    @Autowired
    private PayMapper payMapper;
    @Autowired
    private SysStaffMapper sysStaffMapper;

    public PayMapper getPayMapper() {
        return payMapper;
    }

    @Override
    public void getUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        //查询总计数
        Long totalCount = payMapper.selectPayUploadCount(searchConditions);
        if (totalCount == null) {
            totalCount = (long) 0;
        }
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = payMapper.selectPayUploadList(searchConditions);
        //查询其它数据
        for (Map<String, Object> pay : list) {
            //操作人
            String staffName = sysStaffMapper.selectStaffNameById(Integer.parseInt(pay.get("operator").toString()));
            pay.put("staffName", staffName);

            List<UnspayPay> unspayPays = payMapper.selectPayListByFileName(pay.get("filename").toString());
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

            for (UnspayPay ded : unspayPays) {

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
    public List<Map<String, Object>> uploadFileDetail(String filename) {
        return payMapper.selectPayMapListByFileName(filename);
    }

    @Override
    public void savePay(UnspayPay UnspayPay) {
        payMapper.insert(UnspayPay);
        logger.info("新增支付记录：" + JSON.toJSONString(UnspayPay));
    }


    public List<Integer> queryPayToSend() {
        return payMapper.selectPayToSend(new Date());
    }

    @Override
    public boolean pay(Integer orderId) {

        UnspayPayRequest unspayPayRequest = new UnspayPayRequest();
        unspayPayRequest.setOrderId(orderId);
        unspayPayRequest.setMessageType(UnspayPayMessageType.PAY.getCode());

        return enqueue(unspayPayRequest);
    }

    @Override
    public boolean savePayResult(UnspayPay UnspayPay) {
        //保存结果
        payMapper.updateCallbackResult(UnspayPay);
        logger.info("订单编号：" + UnspayPay.getOrderId() + "保存支付结果" + JSON.toJSONString(UnspayPay));
        return true;
    }

    @Override
    public UnspayPay getPayByOrderId(Integer orderId) {
        return payMapper.selectByPrimaryKey(orderId);
    }

    public UnspayPayResponse queryOrderStatusRemote(UnspayPay UnspayPay) {

        UnspayPayRequest UnspayPayRequest = new UnspayPayRequest();
        UnspayPayRequest.setOrderId(UnspayPay.getOrderId());
        UnspayPayRequest.setMessageType(UnspayPayMessageType.QUERYORDER.getCode());
        logger.info("订单编号：" + UnspayPay.getOrderId() + "远程同步查询支付结果" + JSON.toJSONString(UnspayPay));
        return (UnspayPayResponse) syncSend(UnspayPayRequest);
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        Long totalCount = payMapper.selectPayListCount(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = payMapper.selectPayList(searchConditions);
        page.setResult(list);
    }


    @Transactional
    @Override
    public void varify(String orderIds, Short verifyStatus, Integer staffId) {
        payMapper.varify(orderIds, verifyStatus, staffId);
        if (verifyStatus == VerifyStatus.APPROVE.getCode()) {
            //修改状态为处理中
            payMapper.changePayStatus(orderIds, UnspayStatus.HANDDING.getCode());
        }
    }

}
