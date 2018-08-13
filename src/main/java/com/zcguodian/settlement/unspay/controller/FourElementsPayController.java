package com.zcguodian.settlement.unspay.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.unspay.utils.UnspayExcelUtil;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayResponse;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;

@RequestMapping("/zcgdUnspay")
@Controller
public class FourElementsPayController
{
	private static Logger logger = Logger.getLogger(FourElementsPayController.class);
	
	private static final String UPLOADFILEDIR = CommonDef.ROOT_DIR+File.separator+"backup"+File.separator+"upload";

    static {
        File uploadBackupDir = new File(UPLOADFILEDIR);
        if(!uploadBackupDir.exists()){
            uploadBackupDir.mkdirs();
        }
    }
	
	@Autowired
	private IFourElementsPayService fourElementsPayService;
	@Autowired
	private LoginStaff loginStaff;

	@RequestMapping(value = "/queryOrderStatus")
	public void queryOrderStatus()
	{
		fourElementsPayService.queryOrderStatus(1531898954930L);
	}

	@RequestMapping(value = "/responseResult")
	@ResponseBody
	public String responseResult(HttpServletRequest request)
	{
		logger.info(request.getParameterMap());
		logger.info(request.getMethod());
		logger.info(request.getParameter("result_code"));
		logger.info(request.getParameter("result_msg"));
		logger.info(request.getParameter("amount"));
		logger.info(request.getParameter("orderId"));
		logger.info(request.getParameter("mac"));
		// fourElementsPayService.fourElementsPay(str);
		return "success";
	}

	@RequestMapping(value = "queryBlance")
	public void queryBlance()
	{
		fourElementsPayService.queryBlance();
	}
	
	 /**
     * 加载中城国典代付导入页面
     *
     * @return
     */
    @RequestMapping(value = "/zcguodianPayUpload", method = RequestMethod.GET)
    public String uploadZCGDPayPage(@RequestParam(value = "filename", required = false) String filename,
                                @RequestParam(value = "uploadStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date uploadStartDate,
                                @RequestParam(value = "uploadEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date uploadEndDate,
                                @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                Map<String, Object> map) {

        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("filename", filename);
        searchConditions.put("uploadStartDate", uploadStartDate);
        searchConditions.put("uploadEndDate", uploadEndDate);
        searchConditions.put("pageNo", pageNo);
        fourElementsPayService.getZCGDUploadListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/zcguodian_pay_upload";
    }
    
    /**
     * 实时代付文件上传
     *
     * @return
     */
    @RequestMapping(value = "/zcguodianPayUpload", method = RequestMethod.POST)
    public String uploadPay(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes) {
        SysStaff sysStaff = loginStaff.getSysStaff();
        String fileName;
        try {
            fileName = new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            InputStream inputStream = importFile.getInputStream();
            //存储
            File saveFile = new File(UPLOADFILEDIR + File.separator + fileName);
            if (saveFile.exists()) {
                redirectAttributes.addFlashAttribute("message", fileName + "文件已存在!");
                return "redirect:/zcgdUnspay/zcguodianPayUpload";
            }
            if(!saveFile(saveFile, inputStream)){
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/zcgdUnspay/zcguodianPayUpload";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<UnspayFourElementsPay> analyseImportUnspayZCGD = null;
            analyseImportUnspayZCGD = UnspayExcelUtil.analyseImportUnspayZCGDPay(inputStream);
            inputStream.close();
            if (analyseImportUnspayZCGD == null) {
                redirectAttributes.addFlashAttribute("message", "文件不包含放款入账工作表");
                return "redirect:/zcgdUnspay/zcguodianPayUpload";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传代付文件，文件名称为：" + fileName);
            Date uploadDate = new Date();
            StringBuilder builder = new StringBuilder();
            for (UnspayFourElementsPay unspayFourElementsPay : analyseImportUnspayZCGD) {
                //加入额外信息并放入队列
            	unspayFourElementsPay.setFilename(fileName);
            	unspayFourElementsPay.setUploadDate(uploadDate);
            	unspayFourElementsPay.setOperator(sysStaff.getId());
            	unspayFourElementsPay.setSummary(unspayFourElementsPay.getPurpose());
            	fourElementsPayService.saveFourElementsPay(unspayFourElementsPay);
            }
            if (builder.length() > 1) {
                redirectAttributes.addFlashAttribute("message", builder.toString() + "进件编号不存在!");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/zcgdUnspay/zcguodianPayUpload";
    }
    
    /**
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadZCGDPayDetail", method = RequestMethod.GET)
    public List<Map<String, Object>> uploadZCGDPayDetail(@RequestParam("filename") String filename) {
        return fourElementsPayService.uploadZCGDFileDetail(filename);
    }
    
    /**
     * 同意与拒绝代付
     *
     * @param verifyStatus
     * @param orderIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/varifyZCGDPay/{verifyStatus}", method = RequestMethod.POST)
    public String varifyPay(@PathVariable("verifyStatus") Short verifyStatus, @RequestParam("orderIds") String orderIds) {
        if (verifyStatus == null || orderIds == null || "".equals(orderIds)) {
            return "1";
        }
        logger.info("管理员" + loginStaff.getSysStaff().getStaffName() + "同意或拒绝代付操作，订单编号为：" + orderIds + "，操作码为：" + verifyStatus);
        //拒绝代付
        if (verifyStatus == 2) {
        	fourElementsPayService.refusePay(orderIds, verifyStatus, loginStaff.getSysStaff().getId());
        }
        //同意代付
        if (verifyStatus == 1) {
        	fourElementsPayService.agreePay(orderIds, verifyStatus, loginStaff.getSysStaff().getId());
        }
        return "1";
    }
    
    /**
     * 实时代付查询页面
     *
     * @return
     */
    @RequestMapping(value = "/zcguodianPayList", method = RequestMethod.GET)
    public String payList(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "phoneNo", required = false) Long phoneNo,
                          @RequestParam(value = "idCardNo", required = false) String idCardNo,
                          @RequestParam(value = "cardNo", required = false) String cardNo,
                          @RequestParam(value = "filename", required = false) String filename,
                          @RequestParam(value = "payResult", required = false) String payResult,
                          @RequestParam(value = "responseStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseStartDate,
                          @RequestParam(value = "responseEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseEndDate,
                          @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                          Map<String, Object> map) {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("name", name);
        searchConditions.put("phoneNo", phoneNo);
        searchConditions.put("idCardNo", idCardNo);
        searchConditions.put("cardNo", cardNo);
        searchConditions.put("filename", filename);
        searchConditions.put("payResult", payResult);
        searchConditions.put("responseStartDate", responseStartDate);
        searchConditions.put("responseEndDate", responseEndDate);
        searchConditions.put("pageNo", pageNo);

        fourElementsPayService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/zcguodian_pay_list";
    }
    
    /**
     * 实时代付结果查询
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryZCGDPayOrder", method = RequestMethod.GET)
    public UnspayFourElementsPayResponse queryPayOrder(@RequestParam(value = "orderId") Integer orderId) {
    	UnspayFourElementsPay payByOrderId = fourElementsPayService.getPayByOrderId(orderId);
        if (payByOrderId != null) {
//            return fourElementsPayService.queryOrderStatusRemote(payByOrderId);
        	return null;
        }
        return null;
    }
    
    /**
     * 保存上传文件
     * @param saveFile
     * @param inputStream
     */
    private boolean saveFile(File saveFile, InputStream inputStream) {
        FileOutputStream saveFileOutputStream = null;
        try {
            saveFileOutputStream = new FileOutputStream(saveFile);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(b)) != -1) {
                saveFileOutputStream.write(b, 0, len);
            }
            saveFileOutputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (saveFileOutputStream != null) {
                try {
                    saveFileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }
}
