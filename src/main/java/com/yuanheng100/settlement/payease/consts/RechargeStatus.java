package com.yuanheng100.settlement.payease.consts;

/**
 * Created by jlqian on 2017/2/8.
 */
public interface RechargeStatus
{
    public enum Front{

        HANDLING((short)0,"已提交"),
        SUCCESS((short)20,"支付成功"),
        FAILURE((short)30,"支付失败");

        private Short code;

        private String name;

        private Front(Short code,String name){
            this.code = code;
            this.name = name;
        }

        public Short getCode()
        {
            return code;
        }

        public String getName()
        {
            return name;
        }

        private static Front[] values = Front.values();

        public static Front getByCode(Short code)
        {
            for (Front front : values)
            {
                if (front.getCode().equals(code))
                {
                    return front;
                }
            }
            return null;
        }
    }

    public enum Back{

        HANDLING((short)0,"已提交"),
        SUCCESS((short)1,"支付成功"),
        FAILURE((short)3,"支付失败");

        private Short code;

        private String name;

        private Back(Short code,String name){
            this.code = code;
            this.name = name;
        }

        public Short getCode()
        {
            return code;
        }

        public String getName()
        {
            return name;
        }

        private static Back[] values = Back.values();

        public static Back getByCode(Short code)
        {
            for (Back back : values)
            {
                if (back.getCode().equals(code))
                {
                    return back;
                }
            }
            return null;
        }
    }
}
