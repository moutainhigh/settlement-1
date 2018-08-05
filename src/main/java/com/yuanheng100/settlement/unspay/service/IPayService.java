package com.yuanheng100.settlement.unspay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.model.UnspayPay;
import com.yuanheng100.settlement.unspay.model.UnspayPayResponse;

/**
 * 银生宝代付相关接口
 * Created by qianjl on 2016-7-14.
 */
public interface IPayService {

    /**
     * 获取代付上传记录页
     *
     * @param searchConditions
     * @param page
     */
    void getUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 获取上传记录页详情页数据
     *
     * @param filename
     * @return
     */
    List<Map<String, Object>> uploadFileDetail(String filename);

    /**
     * 保存新上传的代付对象
     */
    void savePay(UnspayPay unspayPay);

    /**
     * 定时查询需要发送的代付对象ID
     *
     * @return
     */
    List<Integer> queryPayToSend();

    /**
     * 代付
     *
     * @param orderId
     * @return true:对方接受请求成功 false:对方接受失败,不存储Deduct对象
     */
    boolean pay(Integer orderId);

    /**
     * 保存代付结果,用于发送代付请求的回调
     *
     * @param unspayPay
     * @return
     */
    boolean savePayResult(UnspayPay unspayPay);

    /**
     * 根据订单编号查询UnspayPay对象
     *
     * @param orderId
     * @return
     */
    UnspayPay getPayByOrderId(Integer orderId);

    /**
     * 主动查询订单结果（代付结果）
     *
     * @param unspayPay
     * @return PayResponse包含status desc属性
     */
    UnspayPayResponse queryOrderStatusRemote(UnspayPay unspayPay);

    /**
     * 代付列表页面
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 同意与拒绝代付
     *
     * @param orderIds
     * @param verifyStatus
     */
    void varify(String orderIds, Short verifyStatus, Integer staffId);

}
