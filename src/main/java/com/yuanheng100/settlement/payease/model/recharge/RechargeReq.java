package com.yuanheng100.settlement.payease.model.recharge;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jlqian on 2017/2/7.
 */
public class RechargeReq implements Serializable
{
    /**
     * 商户编号：不可为空值，以初始单上所填写的商户编号为准
     */
    private String v_mid;

    /**
     * 订单编号：不可为空值，首信易支付订单编号格式统一为：订单生成日期（yyyymmdd）-商户编号-商户流水号
     */
    private String v_oid;

    /**
     * 收货人姓名：不可为空值，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替
     */
    private String v_rcvname;

    /**
     * 收货人地址：不可为空值，总长不超过128个字符，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替
     */
    private String v_rcvaddr;

    /**
     * 收货人电话：不可为空值，总长不超过32个字符，建议统一用商户编号的值代替
     */
    private String v_rcvtel;

    /**
     * 收货人邮政编码：不可为空值，总长不超过10个字符，建议统一用商户编号的值代替
     */
    private String v_rcvpost;

    /**
     * 订单总金额：不可为空值，单位：元，小数点后保留两位，如13.45
     */
    private BigDecimal v_amount;

    /**
     * 订单产生日期：不可为空值，长度为8位，格式为yyyymmdd，例如：20100101
     */
    private String v_ymd;

    /**
     * 配货状态：商户配货状态，0为未配齐，1为已配齐；一般商户该参数无实际意义，建议统一配置为1的状态
     */
    private Integer v_orderstatus;

    /**
     * 订货人姓名：总长不超过64个字符，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替
     */
    private String v_ordername;

    /**
     * 支付币种：0为人民币
     */
    private Integer v_moneytype;

    /**
     * 支付方式编号：可以为空
     */
    private Integer v_pmode;

    /**
     * 返回商户的页面地址：为消费者完成购物后返回的商户页面
     */
    private String v_url;

    /**
     * 订单数字指纹
     */
    private String v_md5info;

    /**
     * 商户自定义参数1：此参数可选，传递会员ID，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata1;

    /**
     * 商户自定义参数2：此参数可选，传递证件类型，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata2;

    /**
     * 商户自定义参数3：此参数可选，传递证件号码，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata3;

    /**
     * 商户自定义参数4：此参数可选，传递用户真实姓名，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata4;

    /**
     * 商户自定义参数5：此参数可选，传递用户注册手机号码，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata5;

    /**
     * 商户自定义参数6：此参数可选，传递备注信息1，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata6;

    /**
     * 商户自定义参数7：此参数可选，传递备注信息2，必须采用UTF-8字符的URL编码格式
     */
    private String v_merdata7;

    public String getV_mid()
    {
        return v_mid;
    }

    public void setV_mid(String v_mid)
    {
        this.v_mid = v_mid;
    }

    public String getV_oid()
    {
        return v_oid;
    }

    public void setV_oid(String v_oid)
    {
        this.v_oid = v_oid;
    }

    public String getV_rcvname()
    {
        return v_rcvname;
    }

    public void setV_rcvname(String v_rcvname)
    {
        this.v_rcvname = v_rcvname;
    }

    public String getV_rcvaddr()
    {
        return v_rcvaddr;
    }

    public void setV_rcvaddr(String v_rcvaddr)
    {
        this.v_rcvaddr = v_rcvaddr;
    }

    public String getV_rcvtel()
    {
        return v_rcvtel;
    }

    public void setV_rcvtel(String v_rcvtel)
    {
        this.v_rcvtel = v_rcvtel;
    }

    public String getV_rcvpost()
    {
        return v_rcvpost;
    }

    public void setV_rcvpost(String v_rcvpost)
    {
        this.v_rcvpost = v_rcvpost;
    }

    public BigDecimal getV_amount()
    {
        return v_amount;
    }

    public void setV_amount(BigDecimal v_amount)
    {
        this.v_amount = v_amount;
    }

    public String getV_ymd()
    {
        return v_ymd;
    }

    public void setV_ymd(String v_ymd)
    {
        this.v_ymd = v_ymd;
    }

    public Integer getV_orderstatus()
    {
        return v_orderstatus;
    }

    public void setV_orderstatus(Integer v_orderstatus)
    {
        this.v_orderstatus = v_orderstatus;
    }

    public String getV_ordername()
    {
        return v_ordername;
    }

    public void setV_ordername(String v_ordername)
    {
        this.v_ordername = v_ordername;
    }

    public Integer getV_moneytype()
    {
        return v_moneytype;
    }

    public void setV_moneytype(Integer v_moneytype)
    {
        this.v_moneytype = v_moneytype;
    }

    public Integer getV_pmode()
    {
        return v_pmode;
    }

    public void setV_pmode(Integer v_pmode)
    {
        this.v_pmode = v_pmode;
    }

    public String getV_url()
    {
        return v_url;
    }

    public void setV_url(String v_url)
    {
        this.v_url = v_url;
    }

    public String getV_md5info()
    {
        return v_md5info;
    }

    public void setV_md5info(String v_md5info)
    {
        this.v_md5info = v_md5info;
    }

    public String getV_merdata1()
    {
        return v_merdata1;
    }

    public void setV_merdata1(String v_merdata1)
    {
        this.v_merdata1 = v_merdata1;
    }

    public String getV_merdata2()
    {
        return v_merdata2;
    }

    public void setV_merdata2(String v_merdata2)
    {
        this.v_merdata2 = v_merdata2;
    }

    public String getV_merdata3()
    {
        return v_merdata3;
    }

    public void setV_merdata3(String v_merdata3)
    {
        this.v_merdata3 = v_merdata3;
    }

    public String getV_merdata4()
    {
        return v_merdata4;
    }

    public void setV_merdata4(String v_merdata4)
    {
        this.v_merdata4 = v_merdata4;
    }

    public String getV_merdata5()
    {
        return v_merdata5;
    }

    public void setV_merdata5(String v_merdata5)
    {
        this.v_merdata5 = v_merdata5;
    }

    public String getV_merdata6()
    {
        return v_merdata6;
    }

    public void setV_merdata6(String v_merdata6)
    {
        this.v_merdata6 = v_merdata6;
    }

    public String getV_merdata7()
    {
        return v_merdata7;
    }

    public void setV_merdata7(String v_merdata7)
    {
        this.v_merdata7 = v_merdata7;
    }
}
