package com.yuanheng100.settlement.ghbank.consts;

/**
 * Created by jlqian on 2017/4/17.
 * 报文头 相关定义
 */
public class MessageHeader
{
    /**
     * 版本号
     */
    public static final String VERSION_NUMBER = "001";
    /**
     * 类型
     */
    public static Character TYPE = 'X';

    /**
     * 优先级
     * 1：普通
     * 2：紧急
     * 3：特急
     */
    public static enum Priority
    {
        NORMAL((short) 1),
        EMERGENCY((short) 2),
        EXTRA_URGENT((short) 3);

        private Short level;

        private Priority(Short level)
        {
            this.level = level;
        }

        public Short getLevel()
        {
            return level;
        }
    }

    /**
     * 调用方式
     * 1：同步
     * 2：异步（需主动回查）
     */
    public static enum InvokeMethod
    {
        SYNC((short) 1),
        ASYNC((short) 2);

        private Short method;

        private InvokeMethod(Short method)
        {
            this.method = method;
        }

        public Short getMethod()
        {
            return method;
        }
    }

    public static final String RESERVEDDOMAIN = "          ";


    public static final String NORMAL_SYNC = VERSION_NUMBER + TYPE + Priority.NORMAL.getLevel() + InvokeMethod.SYNC.getMethod();
    public static final String NORMAL_ASYNC = VERSION_NUMBER + TYPE + Priority.NORMAL.getLevel() + InvokeMethod.ASYNC.getMethod();

}
