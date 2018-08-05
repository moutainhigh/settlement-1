package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.QpayMsgBase;

import java.util.List;

/**
 * Created by jlqian on 2016/9/24.
 */
public class Q20007Bean extends QpayMsgBase
{
    private String productCode;

    private List<PayInst> payInstList;

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public List<PayInst> getPayInstList()
    {
        return payInstList;
    }

    public void setPayInstList(List<PayInst> payInstList)
    {
        this.payInstList = payInstList;
    }

    public static class PayInst{

        /**
         * 机构ID
         */
        private Integer instId;
        /**
         * 支付模式 机构所属支付模式例如：online_bank (网银支付) qpay(快捷支付) pos（pos支付）
         */
        private String payMode;
        /**
         * 机构代码 qpay(快捷支付)
         */
        private String instCode;
        /**
         * 	机构名称 pos（pos支付）
         */
        private String instName;
        /**
         * 卡类型 贷记=CC，借记=DC，综合=GC
         */
        private String cardType;
        /**
         * 卡属性 对公/对私：B/C
         */
        private char cardAttribute;
        /**
         * 扩展信息
         */
        private String ext;

        public Integer getInstId()
        {
            return instId;
        }

        public void setInstId(Integer instId)
        {
            this.instId = instId;
        }

        public String getPayMode()
        {
            return payMode;
        }

        public void setPayMode(String payMode)
        {
            this.payMode = payMode;
        }

        public String getInstCode()
        {
            return instCode;
        }

        public void setInstCode(String instCode)
        {
            this.instCode = instCode;
        }

        public String getInstName()
        {
            return instName;
        }

        public void setInstName(String instName)
        {
            this.instName = instName;
        }

        public String getCardType()
        {
            return cardType;
        }

        public void setCardType(String cardType)
        {
            this.cardType = cardType;
        }

        public char getCardAttribute()
        {
            return cardAttribute;
        }

        public void setCardAttribute(char cardAttribute)
        {
            this.cardAttribute = cardAttribute;
        }

        public String getExt()
        {
            return ext;
        }

        public void setExt(String ext)
        {
            this.ext = ext;
        }
    }

}