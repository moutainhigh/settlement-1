package com.zcguodian.settlement.unspay.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.zcguodian.settlement.unspay.consts.UnspayZCGDStatus;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayResponse;
import com.zcguodian.settlement.unspay.service.IFourElementsPayService;
import com.zcguodian.settlement.unspay.utils.UnspayZCGDUtil;

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

	 /**
     * 加载中城国典代付导入页面
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
     * 上传文件详细信息
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadZCGDPayDetail", method = RequestMethod.GET)
    public List<Map<String, Object>> uploadZCGDPayDetail(@RequestParam("filename") String filename) {
        return fourElementsPayService.uploadZCGDFileDetail(filename);
    }
    
    /**
     * 实时代付文件上传
     *
     * @return
     */
    @RequestMapping(value = "/zcguodianPayUpload", method = RequestMethod.POST)
    public String uploadPay(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes) {
        SysStaff sysStaff = loginStaff.getSysStaff();
        if (sysStaff == null)
		{
        	return "redirect:/index.jsp";
		}
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
            logger.info("管理员" + sysStaff.getStaffName() + "上传实时代付文件，文件名称为：" + fileName);
            Date uploadDate = new Date();
//            StringBuilder builder = new StringBuilder();
            for (UnspayFourElementsPay unspayFourElementsPay : analyseImportUnspayZCGD) {
                //加入额外信息并放入队列
            	unspayFourElementsPay.setFilename(fileName);
            	unspayFourElementsPay.setUploadDate(uploadDate);
            	unspayFourElementsPay.setOperator(sysStaff.getId());
            	unspayFourElementsPay.setSummary(unspayFourElementsPay.getPurpose());
            	fourElementsPayService.saveFourElementsPay(unspayFourElementsPay);
            }
//            if (builder.length() > 1) {
//                redirectAttributes.addFlashAttribute("message", builder.toString() + "进件编号不存在!");
//            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/zcgdUnspay/zcguodianPayUpload";
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
            return "-1";
        }
        if (loginStaff.getSysStaff() == null)
		{
        	return "0";
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
            return fourElementsPayService.queryOrderStatusRemote(payByOrderId);
        }
        return null;
    }
    
    /**
     * 用于银生宝实时代付回调
     *
     * @param resultCode
     * @param resultMsg
     * @param amount
     * @param orderId
     * @param mac
     * @return
     */
    @ResponseBody
    @RequestMapping("/payZCGDCallback")
//    public String payCallback(HttpServletRequest request,@RequestParam("result_code") String resultCode, @RequestParam(value = "result_msg", required = false) String resultMsg, @RequestParam("amount") BigDecimal amount, @RequestParam("orderId") String orderId, @RequestParam("mac") String mac) {
    public String payCallback(HttpServletRequest request) {
    	String resultCode = request.getParameter("result_code");
    	String resultMsg = request.getParameter("result_msg");
    	BigDecimal amount = new BigDecimal(request.getParameter("amount"));
    	String orderId = request.getParameter("orderId");
    	String mac = request.getParameter("mac");
    	
    	try {
            logger.debug("银生宝回调编码校验(系统默认编码)，参数为{result_code:" + resultCode + ",result_msg:" + resultMsg + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(GBK转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("GBK"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(ISO-8859-1转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
//            resultMsg = new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        //验证是否由银生宝发送
        boolean checked = UnspayZCGDUtil.checkUnspayCallBack(resultCode, resultMsg, amount, orderId, mac);
        if (checked) {
            //保存代扣结果
            logger.info("银生宝代付结果回调，订单号" + orderId + "的代付金额为" + amount + "，代付结果码为：" + resultCode + "，代付结果描述为：" + resultMsg);
            if (orderId != null) {
                UnspayFourElementsPay unspayPay = new UnspayFourElementsPay();
                unspayPay.setOrderId(Integer.valueOf(orderId));
                if(resultCode.equals(UnspayZCGDStatus.ZCGD_SUCCESS.getCode())){
                	unspayPay.setPayResult(UnspayZCGDStatus.ZCGD_TRADE_SUCCESS.getCode());
                }else {
                	unspayPay.setPayResult(resultCode);
                }
                unspayPay.setDesc(resultMsg);
                unspayPay.setResponseDate(new Date());
                fourElementsPayService.saveZCGDPayResult(unspayPay);
            }
        }
        return "0000";
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
     * 导出excel表格
     * @param name
     * @param phoneNo
     * @param idCardNo
     * @param cardNo
     * @param filename
     * @param payResult
     * @param responseStartDate
     * @param responseEndDate
     * @return
     */
    @RequestMapping(value = "/payDownload", method = RequestMethod.GET)
    public ResponseEntity<byte[]> payDownload(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "phoneNo", required = false) Long phoneNo,
                                              @RequestParam(value = "idCardNo", required = false) String idCardNo,
                                              @RequestParam(value = "cardNo", required = false) String cardNo,
                                              @RequestParam(value = "filename", required = false) String filename,
                                              @RequestParam(value = "payResult", required = false) String payResult,
                                              @RequestParam(value = "responseStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseStartDate,
                                              @RequestParam(value = "responseEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseEndDate) {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(Integer.MAX_VALUE);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("name", name);
        searchConditions.put("phoneNo", phoneNo);
        searchConditions.put("idCardNo", idCardNo);
        searchConditions.put("cardNo", cardNo);
        searchConditions.put("filename", filename);
        searchConditions.put("payResult", payResult);
        searchConditions.put("responseStartDate", responseStartDate);
        searchConditions.put("responseEndDate", responseEndDate);
        fourElementsPayService.getListPage(searchConditions, page);

        List<Map<String, Object>> result = page.getResult();
        //获取Excel表格
        Workbook workbook = UnspayExcelUtil.exportUnspayZCGDPayResult(result);
        String fileName = "放款入账-银生宝-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
        String downLoadName;
        try {
            downLoadName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            downLoadName = fileName;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment;fileName=" + downLoadName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            workbook.write(byteArrayOutputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), responseHeaders, HttpStatus.CREATED);
    }

    /**
     * 中城国典银生宝报表
     *
     * @return
     */
    @RequestMapping(value = "/zcguodianReport", method = RequestMethod.GET)
    public String report(Map<String, Object> map) {
        //银生宝余额
        map.putAll(UnspayZCGDUtil.queryBalance());
        return "unspay/zcguodian_report";
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
