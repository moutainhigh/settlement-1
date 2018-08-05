package com.yuanheng100.settlement.unspay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.callback.IDeductCallback;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.consts.VerifyStatus;
import com.yuanheng100.settlement.unspay.mapper.DeductMapper;
import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.model.UnspayDeductMessageType;
import com.yuanheng100.settlement.unspay.model.UnspayDeductRequest;
import com.yuanheng100.settlement.unspay.model.UnspayDeductResponse;
import com.yuanheng100.settlement.unspay.service.IDeductService;
import com.yuanheng100.xiaodai.core.consts.loan.RepayPromiseStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-21.
 */
public class DeductServiceImpl extends AbstractMessageService<UnspayDeductRequest> implements IDeductService {

    private static Logger logger = Logger.getLogger(DeductServiceImpl.class);

    private static Map<Integer, IDeductCallback> callbackMap = new HashMap<Integer, IDeductCallback>();

    @Autowired
    private DeductMapper deductMapper;
    @Autowired
    private SysStaffMapper sysStaffMapper;


    public DeductMapper getDeductMapper() {
        return deductMapper;
    }

    @Override
    public void getUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        //查询总计数
        Long totalCount = deductMapper.selectDeductUploadCount(searchConditions);
        if (totalCount == null) {
            totalCount = (long) 0;
        }
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = deductMapper.selectDeductUploadList(searchConditions);
        //查询其它数据
        for (Map<String, Object> deduct : list) {
            //操作人
            String staffName = sysStaffMapper.selectStaffNameById(Integer.parseInt(deduct.get("operator").toString()));
            deduct.put("staffName", staffName);
            List<UnspayDeduct> unspayDeducts = deductMapper.selectDeductListByFileName(deduct.get("filename").toString());
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
            int count = unspayDeducts.size();

            for (UnspayDeduct ded : unspayDeducts) {

                Short verifyStatus = ded.getVerifyStatus();
                if (verifyStatus.equals(VerifyStatus.NOTAUDITED.getCode())) {
                    notVerify++;
                } else if (verifyStatus.equals(VerifyStatus.DISAPPROVE.getCode())) {
                    refused++;
                }

                String resultStatus = ded.getDeductResult();
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

            deduct.put("success", success);
            deduct.put("failur", failur);
            deduct.put("process", process);
            deduct.put("notVerify", notVerify);
            deduct.put("refused", refused);
            deduct.put("count", count);
        }
        page.setResult(list);
    }

    @Override
    public List<Map<String, Object>> uploadFileDetail(String filename) {
        return deductMapper.selectDeductMapListByFileName(filename);
    }

    @Override
    public void saveDeduct(UnspayDeduct unspayDeduct) {
        deductMapper.insert(unspayDeduct);
        logger.info("新增扣款记录：" + JSON.toJSONString(unspayDeduct));
    }


    public List<Integer> queryDeductToSend() {
        return deductMapper.selectDeductToSend(new Date());
    }

    @Override
    public List<Integer> queryQueryToSend(Date from, Date to) {
        return deductMapper.selectQueryToSend(from, to);
    }

    @Override
    public boolean collect(Integer orderId) {

        UnspayDeductRequest unspayDeductRequest = new UnspayDeductRequest();
        unspayDeductRequest.setOrderId(orderId);
        unspayDeductRequest.setMessageType(UnspayDeductMessageType.DEDUCT.getCode());

        return enqueue(unspayDeductRequest);
    }

    public boolean saveCollectResult(UnspayDeduct unspayDeduct) {
        //保存结果
        deductMapper.updateCallbackResult(unspayDeduct);
        logger.info("订单编号：" + unspayDeduct.getOrderId() + "保存扣款结果" + JSON.toJSONString(unspayDeduct));
        callback(unspayDeduct);
        return true;
    }

    @Override
    public UnspayDeduct getDeductByOrderId(Integer orderId) {
        return deductMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 发送同步请求，更新结果
     *
     * @param orderId
     * @return
     */
    public UnspayDeductResponse queryOrderStatusRemote(Integer orderId) {

        UnspayDeductRequest unspayDeductRequest = new UnspayDeductRequest();
        unspayDeductRequest.setOrderId(orderId);
        unspayDeductRequest.setMessageType(UnspayDeductMessageType.QUERYORDER.getCode());
        return (UnspayDeductResponse) syncSend(unspayDeductRequest);
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page) {
        Long totalCount = deductMapper.selectDeductListCount(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageNo * pageSize);

        List<Map<String, Object>> list = deductMapper.selectDeductList(searchConditions);
        page.setResult(list);
    }


    @Transactional
    @Override
    public void varify(String orderIds, Short verifyStatus, Integer staffId) {
        deductMapper.varify(orderIds, verifyStatus, staffId);
        if (verifyStatus.equals(VerifyStatus.APPROVE.getCode())) {
            //标记为处理中
            deductMapper.changeDeductStatus(orderIds, UnspayStatus.HANDDING.getCode());
        }
    }

    @Override
    public Integer deductWithCallback(UnspayDeduct unspayDeduct, IDeductCallback deductCallback) {
        saveDeduct(unspayDeduct);
        varify(unspayDeduct.getOrderId().toString(), VerifyStatus.APPROVE.getCode(), 111008);//其它系统调用，直接设置同意
        callbackMap.put(unspayDeduct.getOrderId(), deductCallback);
        return unspayDeduct.getOrderId();
    }

    @Override
    public UnspayDeduct queryLocalForXiaoDai(Integer orderId) {
        UnspayDeduct unspayDeduct = deductMapper.selectByPrimaryKey(orderId);
        return translateDeductStatusForXiaoDai(unspayDeduct);
    }

    @Override
    public UnspayDeduct queryLocalForXiaoDai(String extra) {
        UnspayDeduct unspayDeduct = deductMapper.selectByExtra(extra);
        return translateDeductStatusForXiaoDai(unspayDeduct);
    }

    /**
     * 获取代扣结果后，若callbackMap中存在当前代扣的orderId，则对其进行回调，通知代扣成功
     *
     * @param unspayDeduct
     */
    public void callback(UnspayDeduct unspayDeduct) {
        Integer orderId = unspayDeduct.getOrderId();
        for (Map.Entry<Integer, IDeductCallback> entry : callbackMap.entrySet()) {
            Integer key = entry.getKey();
            if (key.equals(orderId)) {
                //对状态码翻译成小贷系统状态码
                unspayDeduct = translateDeductStatusForXiaoDai(unspayDeduct);
                entry.getValue().call(unspayDeduct);
                logger.info("获取银生宝扣款结果，对小贷系统进行回调" + JSON.toJSONString(unspayDeduct));
                callbackMap.remove(key);
                break;
            }
        }
    }

    /**
     * 将银生宝状态码翻译成小贷的状态码
     *
     * @param unspayDeduct
     * @return
     */
    private UnspayDeduct translateDeductStatusForXiaoDai(UnspayDeduct unspayDeduct) {
        if (unspayDeduct == null) {
            return null;
        }
        String deductResult = unspayDeduct.getDeductResult();
        String desc = unspayDeduct.getDesc();
        if (deductResult == null) {
            unspayDeduct.setDeductResult(String.valueOf(RepayPromiseStatus.SHOP_REALTIME_WAITING.getCode()));
        } else if (UnspayStatus.SUCCESS.getCode().equals(deductResult)) {
            unspayDeduct.setDeductResult(String.valueOf(RepayPromiseStatus.SHOP_REALTIME_SUCCESS.getCode()));
        } else if (UnspayStatus.HANDDING.getCode().equals(deductResult)) {
            unspayDeduct.setDeductResult(String.valueOf(RepayPromiseStatus.SHOP_REALTIME_PROCESSING.getCode()));
        } else if ((UnspayStatus.FAILURE.getCode().equals(deductResult) && UnspayStatus.INSUFFICIENT_BALANCE.getDesc().startsWith(desc)) || UnspayStatus.INSUFFICIENT_BALANCE.getCode().equals(deductResult)) {
            unspayDeduct.setDeductResult(String.valueOf(RepayPromiseStatus.SHOP_REALTIME_NO_MONEY.getCode()));
        } else {
            unspayDeduct.setDeductResult(String.valueOf(RepayPromiseStatus.SHOP_REALTIME_FAILED.getCode()));
        }
        return unspayDeduct;
    }
}
