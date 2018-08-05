package com.yuanheng100.settlement.ghbank.model.loan;

import com.yuanheng100.settlement.ghbank.consts.OperFlag;
import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.model.GHBankResp;

import java.util.Date;

/**
 * Created by jlqian on 2017/4/27.
 */
public class OGW00077Resp extends GHBankResp
{
    /**
     * 操作标识
     */
    private OperFlag operFlag;
    /**
     * 对账日期
     */
    private Date checkDate;
    /**
     * 交易状态
     */
    private ReturnStatus returnStatus;
    /**
     * 失败原因
     */
    private String returnErrorMsg;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件内容
     */
    private String fileContext;

    public OperFlag getOperFlag()
    {
        return operFlag;
    }

    public void setOperFlag(OperFlag operFlag)
    {
        this.operFlag = operFlag;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public ReturnStatus getReturnStatus()
    {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus)
    {
        this.returnStatus = returnStatus;
    }

    public String getReturnErrorMsg()
    {
        return returnErrorMsg;
    }

    public void setReturnErrorMsg(String returnErrorMsg)
    {
        this.returnErrorMsg = returnErrorMsg;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileContext()
    {
        return fileContext;
    }

    public void setFileContext(String fileContext)
    {
        this.fileContext = fileContext;
    }
}
