package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.model.G60002Bean;
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
@RequestMapping("/G60002")
public class G60002Controller {
public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(G60002Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public G60002Bean sendMessage(G60002Bean data) {
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		return data;
	}//method
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G60002Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G60002Bean data) throws Exception {
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
		Element batchEl = bodyEl.element("BATCH");
		if(batchEl == null){
			return;
		}
		data.setBatchQryReqSn(batchEl.elementText("QRY_REQ_SN"));
		data.setBatchRetCode(batchEl.elementText("RET_CODE"));
		data.setBatchErrMsg(batchEl.elementText("ERR_MSG"));
		
		Element transEl = bodyEl.element("TRANS_DETAILS");
		if(transEl == null){
			return;
		}
		Element dtlEl = transEl.element("DTL");
		if(dtlEl == null){
			return;
		}
		data.setDtlsn(dtlEl.elementText("SN"));
		data.setDtlRetCode(dtlEl.elementText("RET_CODE"));
		data.setDtlErrMsg(dtlEl.elementText("ERR_MSG"));
		data.setDtlaccountNo(dtlEl.elementText("ACCOUNT_NO"));
		data.setDtlaccountName(dtlEl.elementText("ACCOUNT_NAME"));
		
	}//method
	
	public String buildCjmsg(G60002Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实名认证结果查询);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("QRY_REQ_SN").setText(U.nvl(data.getQryReqSn()));		
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G60002实名认证结果查询：" + U.substringByByte(xml, 1024));
		return xml;
	}

}
