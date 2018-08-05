package com.yuanheng100.settlement.payease.model.trs001010;

import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 账户额度查询交易 TRS001010
 * @author Bai Song
 *
 */
public class TRS001010Req extends PayeaseReq{

    private static final long serialVersionUID = 8176467722226773858L;
	/**
	 * 客户ID
	 */
	private String user;
	/**
	 * 密码
	 */
	private String pin;
	
	
	public TRS001010Req()
    {
        super();
        this.setTransCode(TransCode.TRS001010.getCode());
    }

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}


}
