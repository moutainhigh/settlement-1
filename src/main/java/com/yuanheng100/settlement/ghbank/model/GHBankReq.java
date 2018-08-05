package com.yuanheng100.settlement.ghbank.model;

import java.io.Serializable;
import java.util.Date;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader;
import com.yuanheng100.settlement.ghbank.consts.MessageHeader.InvokeMethod;
import com.yuanheng100.util.DateUtil;

/**
 * Created by jlqian on 2017/4/17.
 */
public abstract class GHBankReq implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6686751866854609140L;
    /**
     *
     * 调用方式
     * 1：同步
     * 2：异步（需主动回查）
     */
    protected short invokeMethod;
    
    /**
     * 优先级。<br>
     * <br>
     * 默认设置成普通，不需要在构造函数中赋值<br>a
     * 
     * 1：普通
     * 2：紧急
     * 3：特急
     */
    protected short priority = MessageHeader.Priority.NORMAL.getLevel();
    
    /**
     * 接入渠道 渠道标识，商户发起的请求，具体值由华兴银行统一分配,每个商户不一样)；
     */
    protected String channelCode = "P2P179";
    
    /**
     * 渠道流水号 同一渠道不能重复上送，此为固定长度28位。
     *
     * (商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】），
     * 其中商户流水可由数字、字母（字母区分大小写），或是此两个的组合组成。
     * 如商户流水为00000029UYI，则商户发的开户的渠道流水号为：P2P0012016061204200000029UYI
     */
    protected String channelFlow;
    
    /**
     * 渠道日期 yyyyMMdd
     * 注意日期为实际交易的北京标准时间的日期
     *
     * 渠道时间 HHmmss
     *
     * 注:将报文中的两个字段合二为一，在解析的时候进行处理
     */
    protected Date channelDateTime;
    
    /**
     * 加密域 0~200 暂时为空
     */
    protected String encryptData;
    
    /**
     * 交易码
     */
    protected String transCode;
    
    /**
     * 商户唯一标识 由银行统一分配。直接从配置文件读取，不允许修改
     */
    public final static String merchantId = "HSD";
    
    /**
     * 商户名称
     */
    public final static String merchantName = "P2P好时代";
    
    /**
     * 应用标识
     * 个人电脑:PC（不送则默认PC）
     *          手机：APP
     *          微信：WX
     */
    protected AppId appId;
    
    /**
     * 备用字段1
     */
    protected String extFiled1;
    /**
     * 备用字段2
     */
    protected String extFiled2;
    /**
     * 备用字段3
     */
    private String extFiled3;
    
    /**
     * 商户流水号。2017-6-12柏松增加
     */
    private int sequenceId;
    
    public GHBankReq(int sequenceId)
    {
        this.sequenceId = sequenceId;
        this.channelDateTime = new Date();
        //默认报文头的调用方式为同步。对异步的请求，可覆写子类的构造函数
        this.setInvokeMethod(InvokeMethod.SYNC.getMethod());
    }

    public short getInvokeMethod()
    {
        return invokeMethod;
    }

    public void setInvokeMethod(short invokeMethod)
    {
        this.invokeMethod = invokeMethod;
    }


    public short getPriority()
    {
        return priority;
    }

    public void setPriority(short priority)
    {
        this.priority = priority;
    }

    public String getChannelCode()
    {
        return channelCode;
    }

    public void setChannelCode(String channelCode)
    {
        this.channelCode = channelCode;
    }

    /**
     * 渠道流水号 同一渠道不能重复上送，此为固定长度28位。
     *
     * (商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】），
     * 其中商户流水可由数字、字母（字母区分大小写），或是此两个的组合组成。
     * 如商户流水为00000029UYI，则商户发的开户的渠道流水号为：P2P0012016061204200000029UYI
     */
    public String getChannelFlow()
    {
        return channelFlow;
    }
    
    /**
     * 根据构造函数传入的sequenceId，生成ChannelFlow
     */
    protected void initChannelFlow()
    {
        StringBuffer channelFlow = new StringBuffer();
        channelFlow = channelFlow.append(channelCode);
        channelFlow = channelFlow.append(DateUtil.formatToyyyyMMdd(new Date()));
        channelFlow = channelFlow.append(this.getTransCode().substring(this.getTransCode().length() - 3));
        channelFlow = channelFlow.append(String.format("%011d", this.sequenceId));
        this.channelFlow = channelFlow.toString();
    }

    public Date getChannelDateTime()
    {
        return channelDateTime;
    }

    public void setChannelDateTime(Date channelDateTime)
    {
        this.channelDateTime = channelDateTime;
    }

    public String getEncryptData()
    {
        return encryptData;
    }

    public void setEncryptData(String encryptData)
    {
        this.encryptData = encryptData;
    }

    public String getTransCode()
    {
        return transCode;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    /**
     * @return the appId
     */
    public AppId getAppId()
    {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(AppId appId)
    {
        this.appId = appId;
    }

    public String getExtFiled1()
    {
        return extFiled1;
    }

    public void setExtFiled1(String extFiled1)
    {
        this.extFiled1 = extFiled1;
    }

    public String getExtFiled2()
    {
        return extFiled2;
    }

    public void setExtFiled2(String extFiled2)
    {
        this.extFiled2 = extFiled2;
    }

    public String getExtFiled3()
    {
        return extFiled3;
    }

    public void setExtFiled3(String extFiled3)
    {
        this.extFiled3 = extFiled3;
    }

    public int getSequenceId()
    {
        return sequenceId;
    }
    
    
    
}
