package com.yuanheng100.settlement.chanpay.util;

import com.yuanheng100.settlement.chanpay.common.QpayMsgBase;
import com.yuanheng100.settlement.chanpay.consts.Charset;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.Qpay;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;


public class CjQpayHelper {

    private static final Logger LOG = Logger.getLogger(CjQpayHelper.class);

    /**
     * 2.3 单笔订单支付接口
     * 服务名称：cjt_create_instant_trade
     * 将调用此方法system.out打印的请求url交给浏览器访问
     * 注：out_trade_no必须唯一
     */
    public static String cjt_create_instant_trade(QpayMsgBase qpayMsgBase) {
        Map<String, String> stringStringMap = convertQBeanToMap(qpayMsgBase);
        return gatewayPost(stringStringMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 将QBean转为Map，并添加全局属性
     * @param qpayMsgBase
     * @return
     */
    public static Map<String,String> convertQBeanToMap(QpayMsgBase qpayMsgBase){
        HashMap<String, String> stringObjectHashMap = new HashMap<String, String>();
        Method[] methods = qpayMsgBase.getClass().getMethods();
        for (Method method: methods) {
            String methodName = method.getName();
            if(methodName.startsWith("get")&&!methodName.equals("getClass")){
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 3 ; i < methodName.length(); i ++){
                    char c = methodName.charAt(i);
                    if(i!=3&&c>=65&&c<=90)
                        stringBuilder.append("_").append(Character.toLowerCase(c));
                    else
                        stringBuilder.append(Character.toLowerCase(c));
                }
                String key = stringBuilder.toString();
                Object value = null;
                try {
                    value = method.invoke(qpayMsgBase);
                    if(value instanceof Date){
                        value = Cj.formatDatetime_string((Date) value);
                    }
                } catch (Exception e) {
                    LOG.error("获取QpayMsgBase"+key+"属性值出错",e);
                }
                if(value!=null){
                    stringObjectHashMap.put(key,value.toString());
                }
            }
        }
        //修正input_charset属性为：_input_charset
        Object inputCharset = stringObjectHashMap.get("input_charset");
        if(inputCharset!=null){
            stringObjectHashMap.put("_input_charset",inputCharset.toString());
            stringObjectHashMap.remove("input_charset");
        }
        //设置通用值
        stringObjectHashMap.put("version", Qpay.VERSION);
        stringObjectHashMap.put("partner_id", Qpay.QPAY_PARTNER_ID); // 畅捷支付分配的商户号
        stringObjectHashMap.put("_input_charset", Charset.UTF8);// 字符集 UTF-8

        return stringObjectHashMap;
    }

    /**
     * 向测试服务器发送post请求
     *
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     */
    private static String gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        String buildRequest = null;
        try {
            String urlStr = Qpay.QPAY_QPAY_URL;
            if(!urlStr.endsWith("?")){
                urlStr = urlStr + "?";
            }
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            buildRequest = urlStr + createLinkString(sPara, true);
            //buildRequest = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, urlStr);//测试发送
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildRequest;
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
     * 如：buildRequest("", "",sParaTemp)
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset, String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest();
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        // params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    /**
     * 异步通知验签仅供参考 2.3单笔支付，2.18，2.19快捷支付api，对应异步通知参数见2.8 2.5退款接口，对应异步通知参数见2.9 2.14付款到卡接口，对应异步通知参数见2.15 2.20订单提现接口，对应异步通知参数见2.15
     */
    public static boolean notifyVerify(Map<String, String> paramMap) {
        String sign = paramMap.remove("sign");
        String signType = paramMap.remove("sign_type");
        String text = createLinkString(paramMap, false);
        paramMap.put("sign",sign);
        paramMap.put("sign_type",signType);
        try {
            return RSA.verify(text, sign, Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将Map转为QpayMsgBase类对象
     * @param stringStringHashMap
     * @param qpayMsgBaseClass
     * @return
     */
    public static QpayMsgBase convertMapToQBean(HashMap<String, String> stringStringHashMap, Class<? extends QpayMsgBase> qpayMsgBaseClass) throws IllegalAccessException, InstantiationException {
        QpayMsgBase qpayMsgBase = qpayMsgBaseClass.newInstance();
        Method[] methods = qpayMsgBaseClass.getMethods();
        for (Method method: methods) {
            String methodName = method.getName();
            if(methodName.startsWith("set")){
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 3 ; i < methodName.length(); i ++){
                    char c = methodName.charAt(i);
                    if(i!=3&&c>=65&&c<=90)
                        stringBuilder.append("_").append(Character.toLowerCase(c));
                    else
                        stringBuilder.append(Character.toLowerCase(c));
                }
                String key = stringBuilder.toString();//属性名
                String value = stringStringHashMap.get(key);
                if(key.equals("input_charset")){
                    value =  stringStringHashMap.get("_input_charset");
                }
                if(value!=null){
                    Object parameter = value;
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if(parameterTypes[0].equals(Date.class)){
                        parameter = Cj.parseDatetime(value);
                    }
                    if(parameterTypes[0].equals(BigDecimal.class)){
                        parameter = new BigDecimal(value);
                    }
                    if(parameterTypes[0].equals(Double.class)){
                        parameter = new Double(value);
                    }
                    try {
                        method.invoke(qpayMsgBase,parameter);
                    } catch (Exception e) {
                        LOG.error("设置QpayMsgBase"+key+"属性值出错",e);
                    }
                }
            }
        }
        return qpayMsgBase;
    }
    /**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp
     *            请求前的参数数组
     * @param signType
     *            RSA
     * @param key
     *            商户自己生成的商户私钥
     * @param inputCharset
     *            UTF-8
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return sPara;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
        Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
        List<String> keys = new ArrayList<String>(params.keySet());
        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;
            try {
                value = URLEncoder.encode(params.get(key), charset);
                encodeParamsValueMap.put(key, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodeParamsValueMap;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            // try {
            // value = URLEncoder.encode(value,charset);
            // } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            // }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 加密，2.14付款到卡接口等部分接口，有参数需要加密
     * 
     * @param src
     *            原值
     * @param publicKey
     *            畅捷支付发送的平台公钥
     * @param charset
     *            UTF-8
     * @return RSA加密后的密文
     */
    private String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 2.14 付款到卡cjt_payment_to_card
     */
    public void pay2card() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_payment_to_card");
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);
        // 2.14 付款到卡
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        origMap.put("outer_trade_no", (out_trade_no));
        System.out.println("pay 2 card out_trade_no :" + out_trade_no);
        origMap.put("bank_account_no", this.encrypt("6214830215878947", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));
        origMap.put("account_name", this.encrypt("测试01", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));
        origMap.put("bank_code", "CMB");// 每家银行简码到底是什么，调用2.7接口去查
        origMap.put("bank_name", "招商银行");
        origMap.put("bank_branch", "中国招商银行上海市浦建路支行");// 务必填写准确
        origMap.put("province", "上海市");
        origMap.put("city", "上海市");
        origMap.put("card_type", "DEBIT");
        origMap.put("card_attribute", "C");// B对公 C对私
        origMap.put("amount", "100.00");
        origMap.put("notify_url", "https://tadmin.chanpay.com/tpu/mag/syncNotify.do");// 换成自己的
        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);

    }

    /**
     * 2.11 日支付对账文件cjt_everyday_trade_file 需要自行从response流里获取xls对账文件 2.12 日退款对账文件和2.13 手续费对账文件参照此接口调用
     */
    public void everyTradeFile() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_everyday_trade_file");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        // 2.11 日支付对账文件
        origMap.put("transDate", "20160728");// 交易日期

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.13 手续费对账文件cjt_fee_trade_file
     */
    public void cjt_fee_trade_file() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        origMap.put("service", "cjt_fee_trade_file");// 支付的接口名
        // 2.13 手续费对账文件
        origMap.put("transDate", "20160606");// 交易日期

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.7查询银行列表接口cjt_get_paychannel
     */
    public void cjt_get_paychannel() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_get_paychannel");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        // 2.7查询银行列表接口
        origMap.put("product_code", "20201");// 产品吗，生产环境，测试环境固定20201

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.5退款接口cjt_create_refund
     */
    public void refund4jc() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "2.0");
        origMap.put("partner_id", "200000200012"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        origMap.put("is_anonymous", "Y");// 是否匿名
        origMap.put("service", "cjt_create_refund");// 支付的接口名
        // 2.5退款接口
        String out_trade_no = (UUID.randomUUID().toString()).replace("-", "");
        System.out.println("out_trade_no:\r\n" + out_trade_no);
        origMap.put("out_trade_no", out_trade_no);// 订单号
        origMap.put("orig_outer_trade_no", "自行填写需要退款的原始订单号");// 原始订单号
        origMap.put("refund_amount", "0.50");// 退款金额
        origMap.put("notify_url", "https://www.baidu.com");// 后台通知的url
        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.6查询交易接口cjt_query_trade 测试环境例子，订单一NO201607062019409608【INSTANT】订单二CJ20160715M200000017【WITHDRAWAL】 快捷支付api的也用【INSTANT】
     */
    public void cjt_query_trade() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_query_trade");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        // 2.6查询交易接口
        origMap.put("outer_trade_no", "76043f7324f44083873dff0574011fd0");// 这里有坑，是outer_trade_no【outer】不是out_trade_no【out】
        origMap.put("trade_type", "INSTANT");// 交易的类型类型包括 即时到账(INSTANT)，担保交易(ENSURE)， 退款(REFUND),提现（WITHDRAWAL）

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.10回单下载cjt_view_receipt 测试环境订单号2016072815470375755736903945
     */
    public void cjt_view_receipt() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        origMap.put("service", "cjt_view_receipt");// 支付的接口名
        // 2.10回单下载
        origMap.put("outer_trade_no", "自行填写已经交易完毕的订单号");// 原始订单号
        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.18快捷支付api cjt_quick_payment
     */
    public void createQPay() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        origMap.put("service", "cjt_quick_payment");// 支付的接口名
        // 2.18快捷支付api 业务参数
        String out_trade_no = (UUID.randomUUID().toString()).replace("-", "");
        System.out.println("out_trade_no:\r\n" + out_trade_no);
        origMap.put("out_trade_no", out_trade_no);// 订单号
        origMap.put("trade_amount", "2.50");// 金额
        origMap.put("buyer_ip", "10.20.31.88");// 金额
        // 2.18快捷支付api 支付相关参数
        origMap.put("card_type", "DC");// 卡类型
        origMap.put("pay_type", "C");// 对公对私
        origMap.put("bank_code", "TESTBANK");// 含义看文档 跳收银台此值为空
        origMap.put("payer_name", this.encrypt("测试01", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));// 含义看文档 收银台写2 直连网银1
        origMap.put("payer_card_no", this.encrypt("6214830215878947", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));// 含义看文档 收银台写2 直连网银1
        origMap.put("id_number", this.encrypt("152801111111111111", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));// 含义看文档 收银台写2 直连网银1
        origMap.put("phone_number", this.encrypt("13511111111", Qpay.QPAY_MERCHANT_PUBLIC_KEY, Charset.UTF8));// 含义看文档 收银台写2 直连网银1
        origMap.put("notify_url", "http://dev.chanpay.com/receive.php");// 前台跳转url

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.19快捷api确认 cjt_quick_payment_confirm
     */
    public void createQPayConfirm() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", Charset.UTF8);// 字符集
        origMap.put("service", "cjt_quick_payment_confirm");// 支付的接口名
        // 2.19快捷api确认
        origMap.put("out_trade_no", "4a7841eaa2e34fff821c0eb451acb137");// 订单号
        origMap.put("verification_code", "123456");// 短信验证码

        this.gatewayPost(origMap, Charset.UTF8, Qpay.QPAY_MERCHANT_PRIVATE_KEY);
    }

    // //////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        Q20003Bean q20003Bean = new Q20003Bean();

        q20003Bean.setOutTradeNo((UUID.randomUUID().toString()).replace("-", ""));
        q20003Bean.setService("cjt_create_instant_trade");
        q20003Bean.setTradeAmount(new BigDecimal(1024.00).setScale(2, RoundingMode.HALF_EVEN));
        q20003Bean.setExt1("[{'hasUserSign':'true','userSign':'yangyang0test'}]");

        String cjt_create_instant_trade = CjQpayHelper.cjt_create_instant_trade(q20003Bean);// 2.3单笔支付
        System.out.println("支付地址："+cjt_create_instant_trade);
        //CjQpayHelper.notifyVerify();// 异步通知验签
        // test.pay2card();// 2.14付款到卡
        // test.everyTradeFile();// 2.11日交易对账文件
        // test.cjt_get_paychannel();// 2.7 查询银行卡列表
        // test.cjt_query_trade();// 2.6 主动查询
        // test.cjt_view_receipt();// 2.10回单下载
        // test.cjt_fee_trade_file();// 2.13手续费对账文件
        // ////////////////////////////////////////////////////////////////
        // step1 调用2.18 然后订单号复制出来
        // test.createQPay();// 2.18快捷api
        // step2 调用2.19，订单号填写刚才复制的
        // test.createQPayConfirm();// 2.19快捷确认
    }

}
