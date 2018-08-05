package com.yuanheng100.settlement.unspay.controller;

import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.unspay.model.*;
import com.yuanheng100.settlement.unspay.service.IDeductService;
import com.yuanheng100.settlement.unspay.service.IPayService;
import com.yuanheng100.settlement.unspay.service.ISubContractService;
import com.yuanheng100.settlement.unspay.utils.UnspayExcelUtil;
import com.yuanheng100.settlement.unspay.utils.UnspayUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-22.
 */
@Controller
@RequestMapping("/unspay")
public class UnspayController {

    private static Logger logger = Logger.getLogger(UnspayController.class);

    private static final String UPLOADBACKUPDIR = CommonDef.ROOT_DIR+File.separator+"backup"+File.separator+"upload";

    static {
        File uploadBackupDir = new File(UPLOADBACKUPDIR);
        if(!uploadBackupDir.exists()){
            uploadBackupDir.mkdirs();
        }
    }

    @Autowired
    private LoginStaff loginStaff;
    @Autowired
    private ISubContractService subContractService;
    @Autowired
    private IPayService payService;
    @Autowired
    private IDeductService deductService;

    /**
     * 子协议上传页面
     *
     * @return
     */
    @RequestMapping(value = "/subContractUpload", method = RequestMethod.GET)
    public String uploadSubContractPage(@RequestParam(value = "filename", required = false) String filename,
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
        subContractService.getUploadListPage(searchConditions, page);
        map.put("page", page);
        map.put("loginStaff", loginStaff.getSysStaff());
        map.putAll(searchConditions);
        return "unspay/sub_contract_upload";
    }

    @ResponseBody
    @RequestMapping(value = "/uploadSubContractDetail", method = RequestMethod.GET)
    public List<UnspaySubContract> uploadSubContractDetail(@RequestParam("filename") String filename) {
        return subContractService.uploadFileDetail(filename);
    }

    /**
     * 子协议文件上传
     *
     * @return
     */
    @RequestMapping(value = "/subContractUpload", method = RequestMethod.POST)
    public String uploadSubContract(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes) {

        SysStaff sysStaff = loginStaff.getSysStaff();
        String fileName;
        try {
            fileName = new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            InputStream inputStream = importFile.getInputStream();
            //存储
            File saveFile = new File(UPLOADBACKUPDIR + File.separator + fileName);
            if (saveFile.exists()) {
                redirectAttributes.addFlashAttribute("message", fileName + "文件已存在!");
                return "redirect:/unspay/subContractUpload";
            }
            if(!saveFile(saveFile, inputStream)){
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/unspay/subContractUpload";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<UnspaySubContract> analyseImportUnspay = null;
            analyseImportUnspay = UnspayExcelUtil.analyseImportUnspaySubContract(inputStream);
            inputStream.close();
            if (analyseImportUnspay == null) {
                redirectAttributes.addFlashAttribute("message", "文件不包含客户信息工作表");
                return "redirect:/unspay/subContractUpload";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传子协议文件：" + fileName);
            Date uploadDate = new Date();
            StringBuilder builder = new StringBuilder();
            for (UnspaySubContract unspaySubContract : analyseImportUnspay) {
                //加入额外信息并放入队列
                unspaySubContract.setFilename(fileName);
                unspaySubContract.setUploadDate(uploadDate);
                unspaySubContract.setOperator(sysStaff.getId());
                if (!subContractService.asyncSign(unspaySubContract)) {
                    builder.append(unspaySubContract.getLoanApplyId()).append(",");
                }
            }
            if (builder.length() > 1) {
                redirectAttributes.addFlashAttribute("message", builder.toString() + "进件编号重复!");
                return "redirect:/unspay/subContractUpload";
            }

        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (Exception e) {
            logger.error("Exception错误", e);
        }
        return "redirect:/unspay/subContractUpload";
    }

    @ResponseBody
    @RequestMapping(value = "/subContractDetail", method = RequestMethod.GET)
    public UnspaySubContract subContractDetail(@RequestParam("loanApplyId") Long loanApplyId) {
        return subContractService.querySubContractLocal(loanApplyId);
    }

    @RequestMapping(value = "/subContractEdit", method = RequestMethod.POST)
    public String subContractEdit(@RequestParam("loanApplyId") Long loanApplyId,
                                  @RequestParam("phoneNo") Long phoneNo,
                                  @RequestParam("cardNo") String cardNo,
                                  @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                  @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                  @RequestParam(value = "cycle", required = false) Short cycle,
                                  @RequestParam(value = "triesLimit", required = false) Integer triesLimit) {
        //校验合法性
        UnspaySubContract unspaySubContract = subContractService.querySubContractLocal(loanApplyId);
        if (unspaySubContract != null) {
            logger.info("管理员" + loginStaff.getSysStaff().getStaffName() + "修改子协议，进件编号为：" + loanApplyId);
            unspaySubContract.setLoanApplyId(loanApplyId);
            unspaySubContract.setPhoneNo(phoneNo);
            unspaySubContract.setCardNo(cardNo);
            unspaySubContract.setStartDate(startDate);
            unspaySubContract.setEndDate(endDate);
            unspaySubContract.setCycle(cycle);
            unspaySubContract.setTriesLimit(triesLimit);
            subContractService.edit(unspaySubContract);
        }
        return "redirect:/unspay/subContractUpload";
    }

    /**
     * 协议延期
     *
     * @param loanApplyId
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/subContractExtension")
    public String subContractExtension(@RequestParam("loanApplyId") Long loanApplyId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        logger.info("管理员" + loginStaff.getSysStaff().getStaffName() + "子协议延期操作，进件编号为：" + loanApplyId);
        UnspaySubContract unspaySubContract = subContractService.querySubContractLocal(loanApplyId);
        unspaySubContract.setStartDate(startDate);
        unspaySubContract.setEndDate(endDate);
        subContractService.extension(unspaySubContract);
        return "1";
    }

    /**
     * 子协议查询页面
     *
     * @return
     */
    @RequestMapping(value = "/subContractList", method = RequestMethod.GET)
    public String subContractList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "phoneNo", required = false) Long phoneNo,
                                  @RequestParam(value = "idCardNo", required = false) String idCardNo,
                                  @RequestParam(value = "filename", required = false) String filename,
                                  @RequestParam(value = "signStatus", required = false) String signStatus,
                                  @RequestParam(value = "cardNo", required = false) String cardNo,
                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  Map<String, Object> map) {

        Page<UnspaySubContract> page = new Page<UnspaySubContract>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("name", name);
        searchConditions.put("phoneNo", phoneNo);
        searchConditions.put("idCardNo", idCardNo);
        searchConditions.put("filename", filename);
        searchConditions.put("signStatus", signStatus);
        searchConditions.put("cardNo", cardNo);
        searchConditions.put("pageNo", pageNo);

        subContractService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);

        return "unspay/sub_contract_list";
    }

    /**
     * 加载代扣导入页面
     *
     * @return
     */
    @RequestMapping(value = "/deductUpload", method = RequestMethod.GET)
    public String uploadDeductPage(@RequestParam(value = "filename", required = false) String filename,
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
        deductService.getUploadListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/deduct_upload";
    }

    /**
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadDeductDetail", method = RequestMethod.GET)
    public List<Map<String, Object>> uploadDeductDetail(@RequestParam("filename") String filename) {
        return deductService.uploadFileDetail(filename);
    }

    /**
     * 代扣文件上传
     *
     * @return
     */
    @RequestMapping(value = "/deductUpload", method = RequestMethod.POST)
    public String uploadDeduct(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes) {
        SysStaff sysStaff = loginStaff.getSysStaff();
        String fileName;
        try {
            fileName = new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            InputStream inputStream = importFile.getInputStream();
            //存储
            File saveFile = new File(UPLOADBACKUPDIR + File.separator + fileName);
            if (saveFile.exists()) {
                redirectAttributes.addFlashAttribute("message", fileName + "文件已存在!");
                return "redirect:/unspay/deductUpload";
            }
            if(!saveFile(saveFile, inputStream)){
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/unspay/deductUpload";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<UnspayDeduct> analyseImportUnspay = null;
            analyseImportUnspay = UnspayExcelUtil.analyseImportUnspayDeduct(inputStream);
            inputStream.close();
            if (analyseImportUnspay == null) {
                redirectAttributes.addFlashAttribute("message", "文件不包含还款代扣工作表");
                return "redirect:/unspay/deductUpload";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传代扣文件，文件名称为：" + fileName);
            Date uploadDate = new Date();
            StringBuilder builder = new StringBuilder();
            for (UnspayDeduct unspayDeduct : analyseImportUnspay) {
                //加入子协议编号
                String subContractId = subContractService.queryLocal(unspayDeduct.getLoanApplyId());
                if (subContractId == null) {
                    builder.append(unspayDeduct.getLoanApplyId()).append(",");
                    continue;
                }
                unspayDeduct.setSubContractId(subContractId);
                //加入额外信息并放入队列
                unspayDeduct.setFilename(fileName);
                unspayDeduct.setUploadDate(uploadDate);
                unspayDeduct.setOperator(sysStaff.getId());
                deductService.saveDeduct(unspayDeduct);
            }
            if (builder.length() > 1) {
                redirectAttributes.addFlashAttribute("message", builder.toString() + "进件编号不存在!");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/unspay/deductUpload";
    }

    /**
     * 同意与拒绝代扣
     *
     * @param verifyStatus
     * @param orderIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/varifyDeduct/{verifyStatus}", method = RequestMethod.POST)
    public String varifyDeduct(@PathVariable("verifyStatus") Short verifyStatus, @RequestParam("orderIds") String orderIds) {
        if (verifyStatus == null || orderIds == null || "".equals(orderIds)) {
            return "1";
        }
        logger.info("管理员" + loginStaff.getSysStaff().getStaffName() + "同意或拒绝代扣操作，订单编号为：" + orderIds + "，操作码为：" + verifyStatus);
        deductService.varify(orderIds, verifyStatus, loginStaff.getSysStaff().getId());
        return "1";
    }

    /**
     * 用于银生宝代扣回调
     *
     * @param resultCode
     * @param resultMsg
     * @param amount
     * @param orderId
     * @param mac
     * @return
     */
    @ResponseBody
    @RequestMapping("/deductCallback")
    public String deductCallback(@RequestParam("result_code") String resultCode, @RequestParam(value = "result_msg", required = false) String resultMsg, @RequestParam("amount") BigDecimal amount, @RequestParam("orderId") String orderId, @RequestParam("mac") String mac) {
        try {
            logger.debug("银生宝回调编码校验(系统默认编码)，参数为{result_code:" + resultCode + ",result_msg:" + resultMsg + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(GBK转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("GBK"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(ISO-8859-1转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            resultMsg = new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        //验证是否由银生宝发送
        boolean checked = UnspayUtil.checkDeductCallBack(resultCode, resultMsg, amount, orderId, mac);
        if (checked) {
            //保存代扣结果
            logger.info("银生宝代扣结果回调，订单号" + orderId + "的代扣金额为" + amount + "，代扣结果码为：" + resultCode + "，代扣结果描述为：" + resultMsg);
            Integer id = UnspayUtil.parseOrderId(orderId);
            if (id != null) {
                UnspayDeduct unspayDeduct = deductService.getDeductByOrderId(id);
                unspayDeduct.setDeductResult(resultCode);
                unspayDeduct.setDesc(resultMsg);
                unspayDeduct.setResponseDate(new Date());
                deductService.saveCollectResult(unspayDeduct);
            }
        }
        return "0000";
    }

    @ResponseBody
    @RequestMapping(value = "/queryDeductOrder", method = RequestMethod.GET)
    public UnspayDeductResponse queryDeductOrder(@RequestParam(value = "orderId") Integer orderId) {
        UnspayDeduct deductByOrderId = deductService.getDeductByOrderId(orderId);
        if (deductByOrderId != null) {
            return deductService.queryOrderStatusRemote(orderId);
        }
        return null;
    }

    /**
     * 代扣查询页面
     *
     * @return
     */
    @RequestMapping(value = "/deductList", method = RequestMethod.GET)
    public String deductList(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "phoneNo", required = false) Long phoneNo,
                             @RequestParam(value = "idCardNo", required = false) String idCardNo,
                             @RequestParam(value = "cardNo", required = false) String cardNo,
                             @RequestParam(value = "filename", required = false) String filename,
                             @RequestParam(value = "deductResult", required = false) String deductResult,
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
        searchConditions.put("deductResult", deductResult);
        searchConditions.put("responseStartDate", responseStartDate);
        searchConditions.put("responseEndDate", responseEndDate);
        searchConditions.put("pageNo", pageNo);

        deductService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/deduct_list";
    }

    @RequestMapping(value = "/deductDownload", method = RequestMethod.GET)
    public ResponseEntity<byte[]> deductDownload(@RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "phoneNo", required = false) Long phoneNo,
                                                 @RequestParam(value = "idCardNo", required = false) String idCardNo,
                                                 @RequestParam(value = "cardNo", required = false) String cardNo,
                                                 @RequestParam(value = "filename", required = false) String filename,
                                                 @RequestParam(value = "deductResult", required = false) String deductResult,
                                                 @RequestParam(value = "responseStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseStartDate,
                                                 @RequestParam(value = "responseEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseEndDate) {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(Integer.MAX_VALUE);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("name", name);
        searchConditions.put("phoneNo", phoneNo);
        searchConditions.put("idCardNo", idCardNo);
        searchConditions.put("cardNo", cardNo);
        searchConditions.put("filename", filename);
        searchConditions.put("deductResult", deductResult);
        searchConditions.put("responseStartDate", responseStartDate);
        searchConditions.put("responseEndDate", responseEndDate);
        deductService.getListPage(searchConditions, page);

        List<Map<String, Object>> result = page.getResult();
        //获取Excel表格
        Workbook workbook = UnspayExcelUtil.exportUnspayDeductResult(result);
        String fileName = "划扣入账-银生宝-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
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
     * 加载代付导入页面
     *
     * @return
     */
    @RequestMapping(value = "/payUpload", method = RequestMethod.GET)
    public String uploadPayPage(@RequestParam(value = "filename", required = false) String filename,
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
        payService.getUploadListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/pay_upload";
    }

    /**
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadPayDetail", method = RequestMethod.GET)
    public List<Map<String, Object>> uploadPayDetail(@RequestParam("filename") String filename) {
        return payService.uploadFileDetail(filename);
    }

    /**
     * 代付文件上传
     *
     * @return
     */
    @RequestMapping(value = "/payUpload", method = RequestMethod.POST)
    public String uploadPay(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes) {
        SysStaff sysStaff = loginStaff.getSysStaff();
        String fileName;
        try {
            fileName = new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            InputStream inputStream = importFile.getInputStream();
            //存储
            File saveFile = new File(UPLOADBACKUPDIR + File.separator + fileName);
            if (saveFile.exists()) {
                redirectAttributes.addFlashAttribute("message", fileName + "文件已存在!");
                return "redirect:/unspay/payUpload";
            }
            if(!saveFile(saveFile, inputStream)){
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/unspay/payUpload";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<UnspayPay> analyseImportUnspay = null;
            analyseImportUnspay = UnspayExcelUtil.analyseImportUnspayPay(inputStream);
            inputStream.close();
            if (analyseImportUnspay == null) {
                redirectAttributes.addFlashAttribute("message", "文件不包含放款入账工作表");
                return "redirect:/unspay/payUpload";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传代付文件，文件名称为：" + fileName);
            Date uploadDate = new Date();
            StringBuilder builder = new StringBuilder();
            for (UnspayPay unspayPay : analyseImportUnspay) {
                //加入子协议编号
                UnspaySubContract unspaySubContract = subContractService.querySubContractLocal(unspayPay.getLoanApplyId());
                if (unspaySubContract == null) {
                    builder.append(unspayPay.getLoanApplyId()).append(",");
                    continue;
                }
                if (!unspaySubContract.getName().equals(unspayPay.getName()) || !unspaySubContract.getCardNo().equals(unspayPay.getCardNo())) {
                    continue;
                }
                //加入额外信息并放入队列
                unspayPay.setFilename(fileName);
                unspayPay.setUploadDate(uploadDate);
                unspayPay.setOperator(sysStaff.getId());
                payService.savePay(unspayPay);
            }
            if (builder.length() > 1) {
                redirectAttributes.addFlashAttribute("message", builder.toString() + "进件编号不存在!");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/unspay/payUpload";
    }

    /**
     * 同意与拒绝代扣
     *
     * @param verifyStatus
     * @param orderIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/varifyPay/{verifyStatus}", method = RequestMethod.POST)
    public String varifyPay(@PathVariable("verifyStatus") Short verifyStatus, @RequestParam("orderIds") String orderIds) {
        if (verifyStatus == null || orderIds == null || "".equals(orderIds)) {
            return "1";
        }
        logger.info("管理员" + loginStaff.getSysStaff().getStaffName() + "同意或拒绝代付操作，订单编号为：" + orderIds + "，操作码为：" + verifyStatus);
        payService.varify(orderIds, verifyStatus, loginStaff.getSysStaff().getId());
        return "1";
    }

    /**
     * 代付结果查询
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPayOrder", method = RequestMethod.GET)
    public UnspayPayResponse queryPayOrder(@RequestParam(value = "orderId") Integer orderId) {
        UnspayPay payByOrderId = payService.getPayByOrderId(orderId);
        if (payByOrderId != null) {
            return payService.queryOrderStatusRemote(payByOrderId);
        }
        return null;
    }

    /**
     * 用于银生宝代扣回调
     *
     * @param resultCode
     * @param resultMsg
     * @param amount
     * @param orderId
     * @param mac
     * @return
     */
    @ResponseBody
    @RequestMapping("/payCallback")
    public String payCallback(@RequestParam("result_code") String resultCode, @RequestParam(value = "result_msg", required = false) String resultMsg, @RequestParam("amount") BigDecimal amount, @RequestParam("orderId") String orderId, @RequestParam("mac") String mac) {
        try {
            logger.debug("银生宝回调编码校验(系统默认编码)，参数为{result_code:" + resultCode + ",result_msg:" + resultMsg + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(GBK转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("GBK"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            logger.debug("银生宝回调编码校验(ISO-8859-1转UTF-8)，参数为{result_code:" + resultCode + ",result_msg:" + new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8") + ",amount:" + amount + ",orderId:" + orderId + ",mac:" + mac + "}");
            resultMsg = new String(resultMsg.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        //验证是否由银生宝发送
        boolean checked = UnspayUtil.checkDeductCallBack(resultCode, resultMsg, amount, orderId, mac);
        if (checked) {
            //保存代扣结果
            logger.info("银生宝代付结果回调，订单号" + orderId + "的代付金额为" + amount + "，代付结果码为：" + resultCode + "，代付结果描述为：" + resultMsg);
            Integer id = UnspayUtil.parseOrderId(orderId);
            if (id != null) {
                UnspayPay unspayPay = new UnspayPay();
                unspayPay.setOrderId(id);
                unspayPay.setPayResult(resultCode);
                unspayPay.setDesc(resultMsg);
                unspayPay.setResponseDate(new Date());
                payService.savePayResult(unspayPay);
            }
        }
        return "0000";
    }

    /**
     * 代付查询页面
     *
     * @return
     */
    @RequestMapping(value = "/payList", method = RequestMethod.GET)
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

        payService.getListPage(searchConditions, page);
        map.put("page", page);
        map.putAll(searchConditions);
        return "unspay/pay_list";
    }

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
        payService.getListPage(searchConditions, page);

        List<Map<String, Object>> result = page.getResult();
        //获取Excel表格
        Workbook workbook = UnspayExcelUtil.exportUnspayPayResult(result);
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
     * 报表
     *
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Map<String, Object> map) {
        //银生宝余额
        map.putAll(UnspayUtil.queryBalance());
        return "unspay/report";
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
