package com.yuanheng100.settlement.payease.model;

import java.io.Serializable;
import java.util.Date;

import com.yuanheng100.channel.entity.MessageResponse;

/**
 * 首信易，所有响应结果（Response）类的父类。包含了所有结果中重叠的字段
 * 
 * @author baisong
 * 
 */
public class PayeaseResp extends MessageResponse implements Serializable
{
	
    public PayeaseResp()
	{
		super();
	}

    private int msgid;
    
	/**
     * 交易码
     */
    private String transCode;

    /**
     * 请求日期
     */
    private String reqTime;

    /**
     * 操作码
     */
    private String operationCode;


    /**
     * 结果返回码
     */
    private String returnCode;
    /**
     * 结果描述
     */
    private String returnMsg;

    /**
     * P2P总公司编号
     */
    private String groupId;

    /**
     * P2P子公司编号
     */
    private String branchId;
	/**
	 * 预留字段1
	 */
	private String merdata1;
	/**
	 * 预留字段2
	 */
	private String merdata2;
	
	
	private Date returnTime;
    
	
	
	
    /**
     * @return the msgid
     */
    public int getMsgid()
    {
        return msgid;
    }

    /**
     * @param msgid the msgid to set
     */
    public void setMsgid(int msgid)
    {
        this.msgid = msgid;
    }

    /**
     * @return the transCode
     */
    public String getTransCode()
    {
        return transCode;
    }

    /**
     * @param transCode
     *            the transCode to set
     */
    public void setTransCode(String transCode)
    {
        this.transCode = transCode;
    }

    /**
     * @return the reqTime
     */
    public String getReqTime()
    {
        return reqTime;
    }

    /**
     * @param reqTime
     *            the reqTime to set
     */
    public void setReqTime(String reqTime)
    {
        this.reqTime = reqTime;
    }

    /**
     * @return the operationCode
     */
    public String getOperationCode()
    {
        return operationCode;
    }

    /**
     * @param operationCode
     *            the operationCode to set
     */
    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }

    /**
     * @return the groupId
     */
    public String getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    /**
     * @return the branchId
     */
    public String getBranchId()
    {
        return branchId;
    }

    /**
     * @param branchId
     *            the branchId to set
     */
    public void setBranchId(String branchId)
    {
        this.branchId = branchId;
    }
    
	/**
	 * @return the merdata1
	 */
	public String getMerdata1()
	{
		return merdata1;
	}

	/**
	 * @param merdata1 the merdata1 to set
	 */
	public void setMerdata1(String merdata1)
	{
		this.merdata1 = merdata1;
	}

	/**
	 * @return the merdata2
	 */
	public String getMerdata2()
	{
		return merdata2;
	}

	/**
	 * @param merdata2 the merdata2 to set
	 */
	public void setMerdata2(String merdata2)
	{
		this.merdata2 = merdata2;
	}

    /**
     * @return the returnCode
     */
    public String getReturnCode()
    {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }

    /**
     * @return the returnMsg
     */
    public String getReturnMsg()
    {
        return returnMsg;
    }

    /**
     * @param returnMsg the returnMsg to set
     */
    public void setReturnMsg(String returnMsg)
    {
        this.returnMsg = returnMsg;
    }

    /**
     * @return the returnTime
     */
    public Date getReturnTime()
    {
        return returnTime;
    }

    /**
     * @param returnTime the returnTime to set
     */
    public void setReturnTime(Date returnTime)
    {
        this.returnTime = returnTime;
    }
    
}
