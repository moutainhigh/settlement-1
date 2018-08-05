package com.yuanheng100.settlement.ghbank.service;

import com.yuanheng100.settlement.ghbank.consts.AppId;
import com.yuanheng100.settlement.ghbank.consts.TrsType;
import com.yuanheng100.settlement.ghbank.model.register.OGW00041Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00042Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00043Resp;
import com.yuanheng100.settlement.ghbank.model.register.OGW00044Resp;

/**
 * 开户，绑卡相关接口
 * @author Baisong
 *
 */
public interface IRegisterService
{
    /**
     * 6.1获取短信验证码(OGW00041)<br>
     * 第三方公司发起向客户注册手机发送手机短信验证码。<br>
     * 一个手机号，一分钟之内只能发送一次获取短信验证码。<br>
     * 暂用于自动投标授权撤销、自动还款授权撤销两接口的发请求前的发送短信验证码。<br>
     * <br>
     * @param app  应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param acNo   华兴e账户账号
     * @param mobileNo  手机号
     * @return
     */
    OGW00041Resp getVerificationCode(AppId app, TrsType trsType, String acNo, Long mobileNo);

    /**
     * 账户开立(OGW00042)
     * @param app 应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param acName 姓名
     * @param idNo 证件号码（只支持二代身份证）
     * @param mobile
     * @return
     */
    String getRegisterParam(AppId app, String acName, String idNo, Long mobile);
    
    /**
     * 收到开户的异步返回时，更新数据库信息
     * @param resp42
     */
    void updateRegisterResponse(OGW00042Resp resp42);

    /**
     * 账户开立结果查询(OGW00043)
     * @param app   应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param oldReqSeqNo  原交易流水号
     * @return
     */
    OGW00043Resp queryOpenAccountResult(AppId app, String oldReqSeqNo);

    /**
     * 绑卡(OGW00044)
     * @param app  应用标识（电脑：PC；手机：APP；微信：WX。可空，默认为PC）
     * @param acNo  华兴e账户账号
     * @return
     */
    String getBindBankCardXML(AppId app, String acNo);
    
    /**
     * 收到绑卡的异步返回时，更新数据库信息
     * @param resp44
     */
    void updateBindCardResponse(OGW00044Resp resp44);
}
