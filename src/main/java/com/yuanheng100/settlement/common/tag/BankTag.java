package com.yuanheng100.settlement.common.tag;

import com.yuanheng100.settlement.common.model.system.SysBank;
import com.yuanheng100.settlement.common.utils.BankUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by jlqian on 2016/8/19.
 */
public class BankTag extends SimpleTagSupport
{

    private Short bankCode;

    public void setBankCode(Short bankCode)
    {
        this.bankCode = bankCode;
    }

    @Override
    public void doTag() throws JspException, IOException
    {
        SysBank bank = BankUtil.getBankByCode(bankCode);
        String bankName = bank.getShortName() == null ? bank.getFullName() : bank.getShortName();
        this.getJspContext().getOut().write(bankName);
    }
}
