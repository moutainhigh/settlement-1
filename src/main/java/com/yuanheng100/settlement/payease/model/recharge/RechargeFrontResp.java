package com.yuanheng100.settlement.payease.model.recharge;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/2/8.
 */
public class RechargeFrontResp implements Serializable
{
    /**
     * 商户发送的v_oid订单编号
     */
    private String v_oid;

    /**
     * 1(已提交，对使用非实时银行卡进行扣款的订单)
     * 20（支付成功，对使用实时银行卡进行扣款的订单）
     * 30（支付失败，对使用实时银行卡进行扣款的订单）
     */
    private Short v_pstatus;

    /**
     * 支付结果信息 = 已提交（当v_pstatus=1时）；
     *                支付完成（当v_pstatus=20时）
     *                失败原因（当v_pstatus=30时，字符串）
     */
    private String v_pstring;

    /**
     * 支付方式（字符串）
     */
    private String v_pmode;

    /**
     * 校验四个参数：v_oid + v_pstatus + v_pstring + v_pmode
     */
    private String v_md5info;

    /**
     * 订单实际支付金额
     */
    private BigDecimal v_amount;

    /**
     * 订单实际支付币种
     */
    private Integer v_moneytype;

    /**
     * 校验两个参数：v_amount + v_moneytype
     */
    private String v_md5money;

    /**
     * 商城数据签名：参与签名的数据 v_oid + v_pstatus + v_amount + v_moneytype
     */
    private String v_sign;

    public String getV_oid()
    {
        return v_oid;
    }

    public void setV_oid(String v_oid)
    {
        this.v_oid = v_oid;
    }

    public Short getV_pstatus()
    {
        return v_pstatus;
    }

    public void setV_pstatus(Short v_pstatus)
    {
        this.v_pstatus = v_pstatus;
    }

    public String getV_pstring()
    {
        return v_pstring;
    }

    public void setV_pstring(String v_pstring)
    {
        this.v_pstring = v_pstring;
    }

    public String getV_pmode()
    {
        return v_pmode;
    }

    public void setV_pmode(String v_pmode)
    {
        this.v_pmode = v_pmode;
    }

    public String getV_md5info()
    {
        return v_md5info;
    }

    public void setV_md5info(String v_md5info)
    {
        this.v_md5info = v_md5info;
    }

    public BigDecimal getV_amount()
    {
        return v_amount;
    }

    public void setV_amount(BigDecimal v_amount)
    {
        this.v_amount = v_amount;
    }

    public Integer getV_moneytype()
    {
        return v_moneytype;
    }

    public void setV_moneytype(Integer v_moneytype)
    {
        this.v_moneytype = v_moneytype;
    }

    public String getV_md5money()
    {
        return v_md5money;
    }

    public void setV_md5money(String v_md5money)
    {
        this.v_md5money = v_md5money;
    }

    public String getV_sign()
    {
        return v_sign;
    }

    public void setV_sign(String v_sign)
    {
        this.v_sign = v_sign;
    }
}
