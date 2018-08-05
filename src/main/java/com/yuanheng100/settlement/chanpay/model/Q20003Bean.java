package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.QpayMsgBase;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jlqian on 2016/9/7.
 */
public class Q20003Bean extends QpayMsgBase {
    /**
     * 畅捷支付合作商户网站唯一订单号（确保在商户系统中唯一）。 不可空
     */
    private String outTradeNo;
    /**
     * 交易金额 不可空
     */
    private BigDecimal tradeAmount;
    /**
     * 用户手续费
     */
    private BigDecimal userPoundage;
    /**
     * 商户手续费
     */
    private BigDecimal merPoundage;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 交易描述 JSON字符串 可放透传的信息。存放原订单号和原订单付款金额等信息。
     */
    private String actionDesc;
    /**
     * 卖家标示ID 匿名用户is_anonymous为Y，则不要传值，非匿名必填
     */
    private String sellId;
    /**
     * 卖家标示ID类型 表明ID的类型，有这几种：
     * UID/MEMBER_ID/MOBILE/LOGIN_NAME/COMPANY_ID
     * 匿名用户is_anonymous为Y，则不要传值，非匿名必填
     */
    private String sellIdType;
    /**
     * 卖家手机号
     */
    private Long sellMobile;
    /**
     * 商品展示URL	收银台页面上，商品展示的超链接。
     */
    private String producUrl;
    /**
     * 服务器异步通知页面路径 支付服务器主动通知商户网站里指定的页面http路径。
     */
    private String notifyUrl;
    /**
     * 支付过期时间
     * 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭；
     * 处在关闭的交易如收到状态变更，系统会自动发起退款操作。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天。
     * 该参数数值不接受小数点，如1.5h，可转换为90m。
     */
    private String expiredTime;
    /**
     * 商户订单提交时间 yyyyMMddHHmmss
     */
    private Date orderTime;
    /**
     * 买家ID 匿名用户is_anonymous为Y，则不要传值
     */
    private String buyerId;
    /**
     * 买家ID类型 表明ID的类型，有这几种：UID/MEMBER_ID /MOBILE/LOGIN_NAME/COMPANY_ID
     * 匿名用户is_anonymous为Y，则不要传值，非匿名必填
     */
    private String buyerIdType;
    /**
     * 买家手机号
     */
    private Long buyerMobile;
    /**
     * 用户在商户平台下单时候的ip地址
     * 用户在商户平台下单的时候的ip地址，公网IP，不是内网IP用于风控校验
     */
    private String buyerIp;
    /**
     * 支付方式
     * 1: 直连(合作方自己有收银台，选择银行时候，调用该接口直接跳转到选中的银行网银)；
     * 2：收银台（合作方没有收银台，订单支付时候，调用该接口到畅捷支付收银台）；
     * 3：余额支付（合作方选择畅捷支付余额付款时候，到畅捷支付账户余额付款页面）；
     */
    private Short payMethod;
    /**
     * 借记贷记,对公对私
     * 对公：B；对私：C;借记：DC；贷记：CC：综合：GC；
     * pay_method为1时候传递，否则为空；
     */
    private String payType;
    /**
     * 银行简码
     * pay_method为1时候，传递银行简码，否则为空；
     */
    private String bankCode;
    /**
     * 是否匿名支付（跳转收银台的场景使用）
     * Y，表示接入方的用户没有同步为畅捷支付的注册会员
     */
    private String isAnonymous;
    /**
     * 是否同步返回支付URL
     * 目前只支持扫码支付同步返回付款二维码URL地址
     */
    private String isReturnpayurl;
    /**
     * 付款方银行名称（详细到支行）
     */
    private String payerBankname;
    /**
     * 付款方银行账号
     */
    private String payerBankaccountNo;
    /**
     * 交易金额分润账号集
     */
    private String royaltyParameters;
    /**
     * 扩展字段
     * hasUserSign：是否快捷绑卡，此值传true标示需要绑卡，false标示不需要
     * userSign：用户标识（不建议用户手机号作为标识）
     */
    private String ext1;
    /**
     * 扩展字段
     */
    private String ext2;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getUserPoundage() {
        return userPoundage;
    }

    public void setUserPoundage(BigDecimal userPoundage) {
        this.userPoundage = userPoundage;
    }

    public BigDecimal getMerPoundage() {
        return merPoundage;
    }

    public void setMerPoundage(BigDecimal merPoundage) {
        this.merPoundage = merPoundage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getSellIdType() {
        return sellIdType;
    }

    public void setSellIdType(String sellIdType) {
        this.sellIdType = sellIdType;
    }

    public Long getSellMobile() {
        return sellMobile;
    }

    public void setSellMobile(Long sellMobile) {
        this.sellMobile = sellMobile;
    }

    public String getProducUrl() {
        return producUrl;
    }

    public void setProducUrl(String producUrl) {
        this.producUrl = producUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerIdType() {
        return buyerIdType;
    }

    public void setBuyerIdType(String buyerIdType) {
        this.buyerIdType = buyerIdType;
    }

    public Long getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(Long buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerIp() {
        return buyerIp;
    }

    public void setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
    }

    public Short getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Short payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getIsReturnpayurl() {
        return isReturnpayurl;
    }

    public void setIsReturnpayurl(String isReturnpayurl) {
        this.isReturnpayurl = isReturnpayurl;
    }

    public String getPayerBankname() {
        return payerBankname;
    }

    public void setPayerBankname(String payerBankname) {
        this.payerBankname = payerBankname;
    }

    public String getPayerBankaccountNo() {
        return payerBankaccountNo;
    }

    public void setPayerBankaccountNo(String payerBankaccountNo) {
        this.payerBankaccountNo = payerBankaccountNo;
    }

    public String getRoyaltyParameters() {
        return royaltyParameters;
    }

    public void setRoyaltyParameters(String royaltyParameters) {
        this.royaltyParameters = royaltyParameters;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}
