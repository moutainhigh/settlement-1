package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.model.G60003Bean;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/G60003")
public class G60003Controller {
public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(G60003Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public G60003Bean sendMessage(G60003Bean data) {
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		return data;
	}//method
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G60003Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G60003Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");
		
		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(Cj.parseDatetime(infoEl.elementText("TIMESTAMP")));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method
	
	public String buildCjmsg(G60003Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_协议签约);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");

		Element batch = bodyEl.addElement("BATCH");
		batch.addElement("CORP_ACCT_NO").setText(U.nvl(data.getCorpAccountNo()));
		batch.addElement("BUSINESS_CODE").setText(U.nvl(data.getBusinessCode()));
		batch.addElement("ALTER_TYPE").setText(U.nvl(data.getAlterType()));
		batch.addElement("PROTOCOL_TYPE").setText(U.nvl(data.getProtocolType()));
		
		Element details = bodyEl.addElement("TRANS_DETAILS");
		Element dtl = details.addElement("DTL");
		dtl.addElement("SN").setText(U.nvl(data.getSn()));
		dtl.addElement("PROTOCOL_NO").setText(U.nvl(data.getProtocolNo()));
		dtl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
		dtl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
		dtl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
		dtl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		dtl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
		dtl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
		dtl.addElement("ID").setText(U.nvl(data.getId()));
		dtl.addElement("BEGIN_DATE").setText(U.nvl(Cj.formatDate_string(Cj.parseDate(data.getBeginDate()))));	
		dtl.addElement("END_DATE").setText(U.nvl(data.getEndDate()));
		dtl.addElement("TEL").setText(U.nvl(data.getTel()));
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G60003协议签约：" + U.substringByByte(xml, 1024));
		return xml;
	}

}
