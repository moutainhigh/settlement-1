package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.exception.UnauthorizedException;
import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.model.G20013Bean;
import com.yuanheng100.settlement.chanpay.service.IGwNotifyService;
import com.yuanheng100.settlement.chanpay.service.IPayCallbackService;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
@RequestMapping("/chanpay/gw")
public class GwController {

	public static final Logger LOG = Logger.getLogger(GwController.class);
	@Autowired
	private IGwNotifyService gwNotifyService;
	@Autowired
	private IPayCallbackService payCallbackService;

	@RequestMapping("/report")
	public String report(){
		return "/chanpay/report";
	}

	@ResponseBody
	@RequestMapping(value = "/notify")
	public String notify(HttpServletRequest request) throws DocumentException
	{
		byte[] bytes = new byte[1024];
		ServletInputStream inputStream = null;
		StringBuilder stringBuilder = new StringBuilder();
		try
		{
			inputStream = request.getInputStream();
			int len = -1;
			while((len = inputStream.read(bytes))!=-1){
				stringBuilder.append(new String(bytes,0,len));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if(inputStream!=null){
				try
				{
					inputStream.close();
				} catch (IOException e)
				{}
			}
		}
		String requestStr = stringBuilder.toString();
		LOG.info("畅捷通回调："+requestStr);
		//解析 TRX_CODE
		String parseTrxCode = gwNotifyService.parseTrxCode(requestStr);
		//解析回调
		try
		{
			if(Cj.XMLMSG_TRANS_CODE_单笔实时付款交易结果推送.equals(parseTrxCode)){
				G20013Bean callback = payCallbackService.callback(requestStr);
				if(CjDetailRetcode.$0000_处理成功.getCode().equals(callback.getRetCode())){
					String cjRespMsg = payCallbackService.getCjRespMsg(callback);
					CjSignHelper singHelper = new CjSignHelper();
					return singHelper.signXml$Add(cjRespMsg);
				}
			}else{
				LOG.error("没有找到匹配的回调交易代码："+parseTrxCode);
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
		} catch (UnauthorizedException e)
		{
			e.printStackTrace();
		} catch (IllegalPlatformAugumentException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
