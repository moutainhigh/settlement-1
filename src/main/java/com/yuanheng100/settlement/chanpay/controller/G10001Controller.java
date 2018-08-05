package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/G10001")
public class G10001Controller {
	
	public static final Logger LOG = Logger.getLogger(G10001Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public G10001Bean sendMessage(G10001Bean data) {
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		return data;
	}//method
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G10001Bean data) {
		try {
			String cjReqmsg = buildCjmsg(data);

			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			
			// 发送报文
			String cjRespmsg = CjMsgSendHelper.sendAndGetString(signMsg);

			// 验证签名
			CjSignHelper.VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
			if (!verifyResult.result) {
				throw new Exception("验证CJ返回数据的签名失败！" + verifyResult.errMsg);
			}
			parseCjMsgToDto(cjRespmsg, data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method
	
	private void parseCjMsgToDto(String cjRespmsg, G10001Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method

	public String buildCjmsg(G10001Bean data) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_单笔实时收款);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("BUSINESS_CODE").setText(U.nvl(data.getBusinessCode()));
		bodyEl.addElement("CORP_ACCT_NO").setText(U.nvl(data.getCorpAccNo()));
		bodyEl.addElement("PRODUCT_CODE").setText(U.nvl(data.getProductCode()));
		bodyEl.addElement("ACCOUNT_PROP").setText(Cj.ACCOUNT_PROP_对私);
		bodyEl.addElement("SUB_MERCHANT_ID").setText(U.nvl(data.getSubMertid()));
		bodyEl.addElement("BANK_GENERAL_NAME").setText(U.nvl(data.getBankGeneralName()));
		bodyEl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
		bodyEl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		bodyEl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
		bodyEl.addElement("PROVINCE").setText(U.nvl(data.getProvince()));
		bodyEl.addElement("CITY").setText(U.nvl(data.getCity()));
		bodyEl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
		bodyEl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
		bodyEl.addElement("DRCT_BANK_CODE").setText(U.nvl(data.getDrctBankCode()));
		bodyEl.addElement("PROTOCOL_NO").setText(U.nvl(data.getProtocolNo()));
		bodyEl.addElement("CURRENCY").setText(U.nvl(data.getCurrency()));
		bodyEl.addElement("AMOUNT").setText(U.money_yuan2cent(data.getAmount()).toString());
		bodyEl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
		bodyEl.addElement("ID").setText(U.nvl(data.getId()));
		bodyEl.addElement("TEL").setText(U.nvl(data.getTel()));
		bodyEl.addElement("CORP_FLOW_NO").setText(U.nvl(data.getCorpFlowNo()));
		bodyEl.addElement("SUMMARY").setText(U.nvl(data.getSummary()));
		bodyEl.addElement("POSTSCRIPT").setText(U.nvl(data.getPostscript()));
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G10001单笔实时收款：" + U.substringByByte(xml, 512));
		return xml;
	}//method
}//class
