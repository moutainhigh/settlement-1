package com.yuanheng100.settlement.unspay.utils;

import com.alibaba.fastjson.JSONObject;
import com.yuanheng100.settlement.unspay.model.*;
import com.yuanheng100.util.ConfigFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 银生宝交互的工具类
 * Created by qianjl on 2016-6-28.
 */
public class UnspayUtil {

    private static Logger logger = Logger.getLogger(UnspayUtil.class);
    /**
     * 银生宝地址
     */
    private static final String WEBSITE = ConfigFile.getProperty("unspay.website");
    /**
     * 商户编号（商户在银生宝注册用户的客户编号，由银生分配，可在账户信息中查到）
     */
    private static final String ACCOUNTID = ConfigFile.getProperty("unspay.accountId");
    /**
     * 商户协议编号（商户与银生宝签订委托代扣协议之后生成的协议编号）
     */
    private static final String CONTRACTID = ConfigFile.getProperty("unspay.contractId");
    /**
     * 银生宝提供的秘钥
     */
    private static final String KEY = ConfigFile.getProperty("unspay.key");
    /**
     * 银生宝签署协议的开始时间字符串
     */
    private static final String CONTRACTSTARTDATESTR = ConfigFile.getProperty("unspay.contract.start.date");
    /**
     * 银生宝签署协议的结束时间字符串
     */
    private static final String CONTRACTENDDATESTR = ConfigFile.getProperty("unspay.contract.end.date");
    /**
     * 子协议录入路径
     */
    private static final String SIGNSIMPLESUBCONTRACTPATH = ConfigFile.getProperty("unspay.signSimpleSubContractPath");
    /**
     * 子协议编号查询路径
     */
    private static final String QUERYSUBCONTRACTIDPATH = ConfigFile.getProperty("unspay.querySubContractIdPath");
    /**
     * 子协议延期路径
     */
    private static final String SUBCONSTRACTEXTENSIONPATH = ConfigFile.getProperty("unspay.subConstractExtensionPath");
    /**
     * 银生宝代扣路径
     */
    private static final String DEDUCTPATH = ConfigFile.getProperty("unspay.deductPath");
    /**
     * 银生宝代扣回调地址
     */
    private static final String DEDUCTCALLBACK = ConfigFile.getProperty("unspay.deductCallback");
    /**
     * 银生宝代扣订单查询路径
     */
    private static final String QUERYDEDUCTORDERSTATUSPATH = ConfigFile.getProperty("unspay.queryDeductOrderStatusPath");
    /**
     * 银生宝代付路径
     */
    private static final String PAYPATH = ConfigFile.getProperty("unspay.payPath");
    /**
     * 银生宝代付回调地址
     */
    private static final String PAYCALLBACK = ConfigFile.getProperty("unspay.payCallback");
    /**
     * 银生宝代付订单查询路径
     */
    private static final String QUERYPAYORDERSTATUSPATH = ConfigFile.getProperty("unspay.queryPayOrderStatusPath");
    /**
     * 银生宝商户账户余额及保证金余额查询路径
     */
    private static final String QUERYBALANCEPATH = ConfigFile.getProperty("unspay.queryBalancePath");
    /**
     * 银生宝要求的时间格式
     */
    private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
    /**
     * 银生宝签署协议的开始时间
     */
    private static Date CONTRACTSTARTDATE = null;
    /**
     * 银生宝签署协议的结束时间
     */
    private static Date CONTRACTENDDATE = null;

    static {
        try {
            CONTRACTSTARTDATE = format.parse(CONTRACTSTARTDATESTR);
            CONTRACTENDDATE = format.parse(CONTRACTENDDATESTR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数字格式化：不足6位前面补0，不使用千分位
     */
    private static NumberFormat numberFormat;
    /**
     * 拼成银生宝需要的orderId，需要的数据库自增主键格式化后的长度
     */
    private static Integer orderIdLength;

    static {
        orderIdLength = 6;
        numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setMinimumIntegerDigits(orderIdLength);
        numberFormat.setGroupingUsed(false);
    }


    /**
     * 子协议录入
     *
     * @param unspaySubContract
     * @return
     */
    public static UnspaySubContractResponse signSimpleSubContract(UnspaySubContract unspaySubContract) throws UnsupportedEncodingException {

        String url = WEBSITE + SIGNSIMPLESUBCONTRACTPATH;
        //校验数据
        String name, idCardNo, cardNo;
        Long phoneNo;
        int cycle, triesLimit;
        Date startDate, endDate;
        if ((name = unspaySubContract.getName()) == null || (idCardNo = unspaySubContract.getIdCardNo()) == null || (cardNo = unspaySubContract.getCardNo()) == null || (phoneNo = unspaySubContract.getPhoneNo()) == null || phoneNo == 0 || (startDate = unspaySubContract.getStartDate()) == null || (endDate = unspaySubContract.getEndDate()) == null) {
            throw new RuntimeException("用户录入子协议的信息不全");
        }
        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("contractId", CONTRACTID);
        hashMap.put("name", name);
        hashMap.put("phoneNo", String.valueOf(phoneNo));
        hashMap.put("cardNo", cardNo);
        hashMap.put("idCardNo", idCardNo);
        hashMap.put("startDate", startDate.before(CONTRACTSTARTDATE) ? CONTRACTSTARTDATESTR : format.format(startDate));
        hashMap.put("endDate", endDate.after(CONTRACTENDDATE) ? CONTRACTENDDATESTR : format.format(endDate));

        //非必须字段
        if ((cycle = unspaySubContract.getCycle() == null ? 0 : unspaySubContract.getCycle()) > 0 && cycle < 4) {
            hashMap.put("cycle", String.valueOf(cycle));
        }
        if ((triesLimit = unspaySubContract.getTriesLimit() == null ? 0 : unspaySubContract.getTriesLimit()) != 0) {
            hashMap.put("triesLimit", String.valueOf(triesLimit));
        }
        hashMap.put("key", KEY);
        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspaySubContractResponse unspaySubContractResponse = new UnspaySubContractResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回SubContractResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspaySubContractResponse.setResultCode(responseMap.get("result_code"));
            unspaySubContractResponse.setResultMessage(responseMap.get("result_msg"));
            unspaySubContractResponse.setSubContractId(responseMap.get("subContractId"));

        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspaySubContractResponse.setResultCode("2060");
            unspaySubContractResponse.setResultMessage("网络错误，子协议发送失败！");
        }
        return unspaySubContractResponse;
    }

    /**
     * 子协议号查询
     *
     * @param unspaySubContract
     * @return
     */
    public static UnspaySubContractResponse querySubContractId(UnspaySubContract unspaySubContract) {

        String url = WEBSITE + QUERYSUBCONTRACTIDPATH;
        //校验数据
        String name, idCardNo, cardNo;
        if ((name = unspaySubContract.getName()) == null || (idCardNo = unspaySubContract.getIdCardNo()) == null || (cardNo = unspaySubContract.getCardNo()) == null) {
            throw new RuntimeException("用户查询子协议的信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("name", name);
        hashMap.put("cardNo", cardNo);
        hashMap.put("idCardNo", idCardNo);
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspaySubContractResponse unspaySubContractResponse = new UnspaySubContractResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回SubContractResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspaySubContractResponse.setResultCode(responseMap.get("result_code"));
            unspaySubContractResponse.setResultMessage(responseMap.get("result_msg"));
            unspaySubContractResponse.setSubContractId(responseMap.get("subContractId"));

        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspaySubContractResponse.setResultCode("2060");
            unspaySubContractResponse.setResultMessage("网络错误，子协议查询失败！");
        }
        return unspaySubContractResponse;

    }

    /**
     * 子协议延期
     *
     * @param unspaySubContract
     * @return
     */
    public static UnspaySubContractResponse subConstractExtension(UnspaySubContract unspaySubContract) {

        String url = WEBSITE + SUBCONSTRACTEXTENSIONPATH;
        //校验数据
        String subContractId;
        Date startDate, endDate;
        if ((subContractId = unspaySubContract.getSubContractId()) == null || (startDate = unspaySubContract.getStartDate()) == null || (endDate = unspaySubContract.getEndDate()) == null) {
            throw new RuntimeException("用户录入子协议的信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("contractId", CONTRACTID);
        hashMap.put("subContractId", subContractId);
        hashMap.put("startDate", startDate.before(CONTRACTSTARTDATE) ? CONTRACTSTARTDATESTR : format.format(startDate));
        hashMap.put("endDate", endDate.after(CONTRACTENDDATE) ? CONTRACTENDDATESTR : format.format(endDate));
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspaySubContractResponse unspaySubContractResponse = new UnspaySubContractResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回SubContractResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspaySubContractResponse.setResultCode(responseMap.get("result_code"));
            unspaySubContractResponse.setResultMessage(responseMap.get("result_msg"));
            unspaySubContractResponse.setSubContractId(responseMap.get("subContractId"));

        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspaySubContractResponse.setResultCode("2060");
            unspaySubContractResponse.setResultMessage("网络错误，子协议发送失败！");
        }
        return unspaySubContractResponse;
    }

    /**
     * 代扣
     *
     * @param unspayDeduct
     * @return
     */
    public static UnspayDeductResponse deduct(UnspayDeduct unspayDeduct) {
        String url = WEBSITE + DEDUCTPATH;
        //校验数据
        String subContractId, purpose;
        int orderId;
        BigDecimal amount;
        if ((subContractId = unspayDeduct.getSubContractId()) == null || (purpose = unspayDeduct.getPurpose()) == null || (orderId = unspayDeduct.getOrderId()) == 0 || (amount = unspayDeduct.getAmount()) == null) {
            throw new RuntimeException("用户委托代扣信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("subContractId", subContractId);
        hashMap.put("orderId", ACCOUNTID + numberFormat.format(orderId));
        hashMap.put("purpose", purpose);
        hashMap.put("amount", String.valueOf(amount));
        hashMap.put("responseUrl", DEDUCTCALLBACK);
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayDeductResponse unspayDeductResponse = new UnspayDeductResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回SubContractResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayDeductResponse.setResultCode(responseMap.get("result_code"));
            unspayDeductResponse.setResultMessage(responseMap.get("result_msg"));

        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspayDeductResponse.setResultCode("2060");
            unspayDeductResponse.setResultMessage("网络错误，子协议发送失败！");
        }

        return unspayDeductResponse;
    }

    /**
     * 检验代扣、代付结果通知是否为银生宝发送
     *
     * @param resultCode
     * @param resultMsg
     * @param amount
     * @param orderId
     * @param mac
     * @return
     */
    public static boolean checkDeductCallBack(String resultCode, String resultMsg, BigDecimal amount, String orderId, String mac) {

        logger.debug("校验回调是否是银生宝，参数为{result_code:" + resultCode + ",result_msg:" + resultMsg + "amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");

        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("orderId", orderId);
        hashMap.put("amount", String.valueOf(amount));
        hashMap.put("result_code", resultCode);
        if (resultMsg != null) {
            hashMap.put("result_msg", resultMsg);
        }
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String localMac = DigestUtils.md5Hex(macString).toUpperCase();
        if (localMac.equals(mac)) {
            return true;
        }
        return false;
    }

    /**
     * 将银生宝返回的orderId解析为系统需要的数据库主键
     *
     * @param orderIdStr
     * @return
     */
    public static Integer parseOrderId(String orderIdStr) {
        try {
            return numberFormat.parse(orderIdStr.substring(orderIdStr.length() - orderIdLength)).intValue();
        } catch (ParseException e) {
            logger.info(e);
            return null;
        }
    }

    /**
     * 代扣订单状态查询
     *
     * @param unspayDeduct
     * @return
     */
    public static UnspayDeductResponse queryDeductOrderStatus(UnspayDeduct unspayDeduct) {

        String url = WEBSITE + QUERYDEDUCTORDERSTATUSPATH;
        //校验数据
        int orderId;
        if ((orderId = unspayDeduct.getOrderId()) == 0) {
            throw new RuntimeException("用户订单状态查询信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("orderId", ACCOUNTID + numberFormat.format(orderId));
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayDeductResponse unspayDeductResponse = new UnspayDeductResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回DeductResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayDeductResponse.setResultCode(responseMap.get("result_code"));
            unspayDeductResponse.setResultMessage(responseMap.get("result_msg"));
            String status = responseMap.get("status");
            if ("00".equals(status)) {
                unspayDeductResponse.setDeductResult("0000");
            } else {
                unspayDeductResponse.setDeductResult(status);
            }
            unspayDeductResponse.setDesc(responseMap.get("desc"));
        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspayDeductResponse.setResultCode("2060");
            unspayDeductResponse.setResultMessage("网络错误，代扣订单状态查询发送失败！");
            unspayDeductResponse.setDeductResult("20");
            unspayDeductResponse.setDeductResult("网络错误，请求发送失败！");
        }

        return unspayDeductResponse;
    }

    /**
     * 代付
     *
     * @param unspayPay
     * @return
     */
    public static UnspayPayResponse pay(UnspayPay unspayPay) {
        String url = WEBSITE + PAYPATH;
        //校验数据
        String name, cardNo, purpose;
        int orderId;
        BigDecimal amount;
        if ((name = unspayPay.getName()) == null || (cardNo = unspayPay.getCardNo()) == null || (purpose = unspayPay.getPurpose()) == null || (orderId = unspayPay.getOrderId()) == 0 || (amount = unspayPay.getAmount()) == null) {
            throw new RuntimeException("用户委托代扣信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("name", name);
        hashMap.put("cardNo", cardNo);
        hashMap.put("orderId", ACCOUNTID + numberFormat.format(orderId));
        hashMap.put("purpose", purpose);
        hashMap.put("amount", String.valueOf(amount));
        hashMap.put("responseUrl", PAYCALLBACK);
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayPayResponse unspayPayResponse = new UnspayPayResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回PayResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayPayResponse.setResultCode(responseMap.get("result_code"));
            unspayPayResponse.setResultMessage(responseMap.get("result_msg"));

        } catch (IOException e) {
            logger.error("代付请求发送失败", e);
            unspayPayResponse.setResultCode("2060");
            unspayPayResponse.setResultMessage("网络错误，代付请求发送失败！");
        }

        return unspayPayResponse;
    }

    /**
     * 代付订单状态查询
     *
     * @param unspayPay
     * @return
     */
    public static UnspayPayResponse queryPayOrderStatus(UnspayPay unspayPay) {

        String url = WEBSITE + QUERYPAYORDERSTATUSPATH;
        //校验数据
        int orderId;
        if ((orderId = unspayPay.getOrderId()) == 0) {
            throw new RuntimeException("用户订单状态查询信息不全");
        }

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("orderId", ACCOUNTID + numberFormat.format(orderId));
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        UnspayPayResponse unspayPayResponse = new UnspayPayResponse();
        try {
            post = HttpUtils.post(url, hashMap);

            //返回DeductResponse对象
            Map<String, String> responseMap = getResponseMap(post);
            unspayPayResponse.setResultCode(responseMap.get("result_code"));
            unspayPayResponse.setResultMessage(responseMap.get("result_msg"));
            String status = responseMap.get("status");
            if ("00".equals(status)) {
                unspayPayResponse.setDeductResult("0000");
            } else {
                unspayPayResponse.setDeductResult(responseMap.get("status"));
            }
            unspayPayResponse.setDesc(responseMap.get("desc"));
        } catch (IOException e) {
            logger.error("请求发送失败", e);
            unspayPayResponse.setResultCode("2060");
            unspayPayResponse.setResultMessage("网络错误，代付查询发送失败！");
            unspayPayResponse.setDeductResult("20");
            unspayPayResponse.setDesc("网络错误，请求发送失败！");
        }
        return unspayPayResponse;
    }

    /**
     * 账户余额查询
     *
     * @return Map : result_code result_msg bailBalance:保证金金额 balance：账户余额
     */
    public static Map<String, String> queryBalance() {

        String url = WEBSITE + QUERYBALANCEPATH;

        //必须字段
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("accountId", ACCOUNTID);
        hashMap.put("key", KEY);

        String macString = getMacString(hashMap);
        String mac = DigestUtils.md5Hex(macString);
        hashMap.put("mac", mac.toUpperCase());

        String post = null;
        Map<String, String> responseMap = new HashMap<String, String>();
        try {
            post = HttpUtils.post(url, hashMap);
            responseMap = getResponseMap(post);
        } catch (IOException e) {
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