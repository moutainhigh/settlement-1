package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.model.G50001Bean;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequestMapping("/G50001")
public class G50001Controller {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(G50001Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public G50001Bean sendMessage(G50001Bean data) {
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		return data;
	}//method
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G50001Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G50001Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
		
		Element bodyEl = msgEl.element("BODY");
		if (bodyEl == null) {
			return;
		}
		data.setBalance(bodyEl.elementText("BALANCE"));
		data.setUsableBalance(bodyEl.elementText("USABLE_BALANCE"));

		
	}//method
	
	public String buildCjmsg(G50001Bean data) throws ParseException{
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_银行余额查询);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");

		bodyEl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		bodyEl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));				
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G50001银行余额查询：" + U.substringByByte(xml, 1024));
		return xml;
	}
}
