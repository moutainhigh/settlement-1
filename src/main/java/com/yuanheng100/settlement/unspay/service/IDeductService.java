package com.yuanheng100.settlement.unspay.service;

import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.callback.IDeductCallback;
import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.model.UnspayDeductResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银生宝代扣相关接口
 * Created by qianjl on 2016-6-21.
 */
public interface IDeductService {

    /**
     * 获取代扣上传记录页
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
     * 保存新上传的代扣对象
     */
    void saveDeduct(UnspayDeduct unspayDeduct);

    /**
     * 定时查询需要发送的代扣对象ID
     *
     * @return
     */
    List<Integer> queryDeductToSend();

    /**
     * 定时查询结果的代扣对象
     * from 起始发送时间
     * to 结束发送时间
     * @return
     */
    List<Integer> queryQueryToSend(Date from , Date to);

    /**
     * 代扣
     *
     * @param orderId
     * @return true:对方接受请求成功 false:对方接受失败,不存储Deduct对象
     */
    boolean collect(Integer orderId);

    /**
     * 保存代扣结果,用于发送代扣请求的回调
     *
     * @param unspayDeduct
     * @return
     */
    boolean saveCollectResult(UnspayDeduct unspayDeduct);

    /**
     * 根据订单编号查询UnspayDeduct对象
     *
     * @param orderId
     * @return
     */
    UnspayDeduct getDeductByOrderId(Integer orderId);

    /**
     * 主动查询订单结果（代扣结果）
     *
     * @param orderId
     */
    UnspayDeductResponse queryOrderStatusRemote(Integer orderId);

    /**
     * 代扣列表页面
     *
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page);

    /**
     * 同意与拒绝代扣
     *
     * @param orderIds
     * @param verifyStatus
     */
    void varify(String orderIds, Short verifyStatus, Integer staffId);

    /**
     * 存储代扣记录，并在获取代扣结果后进行回调
     * @param unspayDeduct
     * @param deductCallback
     * @return
     */
    Integer deductWithCallback(UnspayDeduct unspayDeduct , IDeductCallback deductCallback);

    /**
     * 通过orderId查询订单状态，并将状态翻译成小贷系统中的状态
     * @param orderId
     * @return
     */
    UnspayDeduct queryLocalForXiaoDai(Integer orderId);

    /**
     * 通过附加字段extra查询订单状态，并将状态翻译成小贷系统中的状态
     * @param extra
     * @return
     */
    UnspayDeduct queryLocalForXiaoDai(String extra);


}
