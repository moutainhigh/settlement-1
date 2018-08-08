package com.zcguodian.settlement.unspay.utils;

import com.alibaba.fastjson.JSONObject;
import com.yuanheng100.util.ConfigFile;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayResponse;
import com.zcguodian.util.HttpUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * 银生宝交互的工具类
 */
public class UnspayZCGDUtil {

    private static Logger logger = Logger.getLogger(UnspayZCGDUtil.class);
    /**
     * 中城国典银生宝地址
     */
    private static final String UNSPAYSITE = ConfigFile.getProperty("unspay.zcguodian.website");
    /**
     * 商户编号（商户在银生宝注册用户的客户编号，由银生分配，可在账户信息中查到）
     */
    private static final String UNSPAYACCOUNTID = ConfigFile.getProperty("unspay.zcguodian.accountId");
    /**
     * 银生宝提供的秘钥
     */
    private static final String UNSPAYKEY = ConfigFile.getProperty("unspay.zcguodian.key");
    /**
     * 银生宝实时代付路径
     */
    private static final String UNSPAYPAYPATH = ConfigFile.getProperty("unspay.zcguodian.fourElementsPay");
    /**
     * 银生宝代付回调地址
     */
    private static final String UNSPAYPAYCALLBACK = ConfigFile.getProperty("unspay.zcguodian.payCallback");
    /**
     * 银生宝代付订单查询路径
     */
    private static final String UNSPAYQUERYORDERSTATUSPATH = ConfigFile.getProperty("unspay.zcguodian.queryOrderStatus");
    /**
     * 银生宝商户账户余额及保证金余额查询路径
     */
    private static final String UNSPAYQUERYBALANCEPATH = ConfigFile.getProperty("unspay.zcguodian.queryBlance");

    /**
     * 检验代付结果通知是否为银生宝发送
     *
     * @param resultCode
     * @param resultMsg
     * @param amount
     * @param orderId
     * @param mac
     * @return
     */
    public static boolean checkUnspayCallBack(String resultCode, String resultMsg, BigDecimal amount, String orderId, String mac) {

        logger.debug("校验回调是否是银生宝，参数为{result_code:" + resultCode + ",result_msg:" + resultMsg + "amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");

        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", UNSPAYACCOUNTID);
        hashMap.put("orderId", orderId);
        hashMap.put("amount", String.valueOf(amount));
        hashMap.put("result_code", resultCode);
        if (resultMsg != null) {
            hashMap.put("result_msg", resultMsg);
        }
        hashMap.put("key", UNSPAYKEY);

        String macString = getMacString(hashMap);
        String localMac = DigestUtils.md5Hex(macString).toUpperCase();
        if (localMac.equals(mac)) {
            return true;
        }
        return false;
    }

    /**
     * 代付
     *
     * @param unspayFourElementsPay
     * @return
     */
    public static UnspayFourElementsPayResponse pay(UnspayFourElementsPay unspayFourElementsPay) {
        String url = UNSPAYSITE + UNSPAYPAYPATH;
        //校验数据
        String name, cardNo, purpose, idCardNo;
        int orderId;
        BigDecimal amount;
        if ((name = unspayFourElementsPay.getName()) == null || (cardNo = unspayFourElementsPay.getCardNo()) == null || (purpose = unspayFourElementsPay.getPurpose()) == null 
        		|| (idCardNo = unspayFourElementsPay.getIdCardNo()) == null || (orderId = unspayFourElementsPay.getOrderId()) == 0 || (amount = unspayFourElementsPay.getAmount()) == null) {
            throw new RuntimeException("用户代付信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", UNSPAYACCOUNTID);
        hashMap.put("name", name);
        hashMap.put("cardNo", cardNo);
        hashMap.put("orderId", String.valueOf(orderId));
        hashMap.put("purpose", purpose);
        hashMap.put("amount", String.valueOf(amount));
        hashMap.put("idCardNo", idCardNo);
        hashMap.put("summary", purpose);
        hashMap.put("responseUrl", UNSPAYPAYCALLBACK);
        hashMap.put("key", UNSPAYKEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayFourElementsPayResponse unspayFourElementsPayResponse = new UnspayFourElementsPayResponse();
        try {
            post = HttpUtil.http(url, hashMap);
            //返回UnspayFourElementsPayResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayFourElementsPayResponse.setResultCode(responseMap.get("result_code"));
            unspayFourElementsPayResponse.setResultMessage(responseMap.get("result_msg"));

        } catch (Exception e) {
            logger.error("代付请求发送失败", e);
            unspayFourElementsPayResponse.setResultCode("2060");
            unspayFourElementsPayResponse.setResultMessage("网络错误，代付请求发送失败！");
        }
        return unspayFourElementsPayResponse;
    }

    /**
     * 代付订单状态查询
     *
     * @param unspayPay
     * @return
     */
    public static UnspayFourElementsPayResponse queryPayOrderStatus(UnspayFourElementsPay unspayFourElementsPay) {
        String url = UNSPAYSITE + UNSPAYQUERYORDERSTATUSPATH;
        //校验数据
        int orderId;
        if ((orderId = unspayFourElementsPay.getOrderId()) == 0) {
            throw new RuntimeException("用户订单状态查询信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", UNSPAYACCOUNTID);
        hashMap.put("orderId", String.valueOf(orderId));
        hashMap.put("key", UNSPAYKEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayFourElementsPayResponse unspayFourElementsPayResponse = new UnspayFourElementsPayResponse();
        try {
            post = HttpUtil.http(url, hashMap);

            //返回对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayFourElementsPayResponse.setResultCode(responseMap.get("result_code"));
            unspayFourElementsPayResponse.setResultMessage(responseMap.get("result_msg"));
            unspayFourElementsPayResponse.setStatus(Short.valueOf(responseMap.get("status")));
            unspayFourElementsPayResponse.setDesc(responseMap.get("desc"));
        } catch (Exception e) {
            logger.error("请求发送失败", e);
            unspayFourElementsPayResponse.setResultCode("2060");
            unspayFourElementsPayResponse.setResultMessage("网络错误，代付查询发送失败！");
            unspayFourElementsPayResponse.setStatus((short) 20);
            unspayFourElementsPayResponse.setDesc("网络错误，请求发送失败！");
        }
        return unspayFourElementsPayResponse;
    }

    /**
     * 账户余额查询
     *
     * @return Map : result_code result_msg bailBalance:保证金金额 balance：账户余额
     */
    public static Map<String, String> queryBalance() {
    	String url = UNSPAYSITE + UNSPAYQUERYBALANCEPATH;

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", UNSPAYACCOUNTID);
        hashMap.put("key", UNSPAYKEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        Map<String, String> responseMap = new HashMap<String, String>();
        try {
            post = HttpUtil.http(url, hashMap);
            responseMap = getResponseMap(post);
        } catch (Exception e) {
            responseMap.put("result_code", "2060");
            responseMap.put("result_msg", "请求失败！");
            logger.error("请求发送失败", e);
        }
        return responseMap;
    }

    /**
     * 获取MAC字符串
     *
     * @param parameters
     * @return
     */
    private static String getMacString(Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entries = parameters.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null) {
                stringBuilder.append(key).append("=").append(value).append("&");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 解析响应字符串
     *
     * @param responseStr
     * @return
     */
    private static Map<String, String> getResponseMap(String responseStr) {

        HashMap<String, String> responseMap = new HashMap<String, String>();

        JSONObject jsonObject = JSONObject.parseObject(responseStr);

        if (jsonObject != null) {
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();

            for (Map.Entry<String, Object> entry : entries) {
                String key = entry.getKey();
                Object value = entry.getValue();
                responseMap.put(key, value.toString());
            }
        }
        return responseMap;
    }

}