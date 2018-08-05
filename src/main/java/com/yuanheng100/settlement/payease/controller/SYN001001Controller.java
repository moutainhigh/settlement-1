package com.yuanheng100.settlement.payease.controller;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.common.service.ISysBankService;
import com.yuanheng100.settlement.payease.callback.IBindBankCardCallbackListener;
import com.yuanheng100.settlement.payease.consts.OperationCode;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.service.ICustomerService;
import com.yuanheng100.settlement.payease.service.IDeductService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.service.IWithdrawService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import com.yuanheng100.settlement.payease.util.PayeaseExcelUtil;
import com.yuanheng100.util.ConfigFile;
import com.yuanheng100.util.VerifyFieldUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payease")
public class SYN001001Controller
{
    private static Logger logger = Logger.getLogger(SYN001001Controller.class);

    private static final String UPLOADBACKUPDIR = CommonDef.ROOT_DIR + File.separator + "backup" + File.separator + "upload";

    static
    {
        File uploadBackupDir = new File(UPLOADBACKUPDIR);
        if (!uploadBackupDir.exists())
        {
            uploadBackupDir.mkdirs();
        }
    }

    @Autowired
    private LoginStaff loginStaff;
    @Autowired
    private ISysBankService sysBankService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDeductService deductService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private ISequenceIdService sequenceIdService;


    @RequestMapping(value = "/syn001001/list", method = RequestMethod.GET)
    public String authenticateList(@RequestParam(value = "userName", required = false) String userName,
                                   @RequestParam(value = "reqTimeStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date reqTimeStartDate,
                                   @RequestParam(value = "reqTimeEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date reqTimeEndDate,
                                   @RequestParam(value = "accNum", required = false) String accNum,
                                   @RequestParam(value = "returnCode", required = false) String returnCode,
                                   @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "plateform", required = false, defaultValue = "0") Integer plateform,
                                   Map<String, Object> map)
    {

        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("userName", userName);
        searchConditions.put("reqTimeStartDate", reqTimeStartDate);
        searchConditions.put("reqTimeEndDate", reqTimeEndDate);
        searchConditions.put("accNum", accNum);
        searchConditions.put("returnCode", returnCode);
        searchConditions.put("plateform", plateform);

        customerService.getListPage(searchConditions, page);
        for (Map<String, Object> resultMap : page.getResult())
        {
            //银行代码转银行名称
            Object accBankCodeObj = resultMap.get("accBankCode");
            if(accBankCodeObj!=null){
                short accBankCode = Short.parseShort(accBankCodeObj.toString());
                resultMap.put("accBank", sysBankService.getBankByCode(accBankCode).getFullName());
            }
        }
        map.put("page", page);
        map.putAll(searchConditions);
        map.put("banks", sysBankService.getAllBanks());

        return "payease/establish_an_account";
    }

    @RequestMapping(value = "/syn001001/add", method = RequestMethod.POST)
    public String authenticateAdd(@RequestParam("userName") String userName,
                                  @RequestParam("id") String id, @RequestParam("accNum") String accNum, @RequestParam("accBankCode") Short accBankCode,
                                  RedirectAttributes redirectAttributes)
    {
        try
        {
            if (!VerifyFieldUtil.verifyChinese(userName))
            {
                throw new IllegalPlatformAugumentException("用户名错误！");
            }
            if (!VerifyFieldUtil.verifyIdCard(id))
            {
                throw new IllegalPlatformAugumentException("身份证错误！");
            }
            if (accNum.length() < 14 || accBankCode == 0)
            {
                throw new IllegalPlatformAugumentException("银行卡错误！");
            }
            //下一个user
            String user = customerService.nextUser();
            SYN001001Req syn001001Req = new SYN001001Req(user);
            syn001001Req.setUserName(userName);
            syn001001Req.setId(id);
            syn001001Req.setAccName(userName);
            syn001001Req.setAccNum(accNum);
            syn001001Req.setAccBankCode(accBankCode);
            SYN001001Resp syn001001Resp = customerService.register(syn001001Req);
            if (ReturnCode.TRANS_SUCCESS.equals(syn001001Resp.getReturnCode()))
            {
                //发送同步银行卡
                customerService.bindBankCard(syn001001Req);
            }
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/payease/syn001001/list";
    }

    @RequestMapping(value = "/syn001001/upload", method = RequestMethod.POST)
    public String authenticateUpload(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes)
    {
        SysStaff sysStaff = loginStaff.getSysStaff();
        String fileName;
        try
        {
            fileName = new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            InputStream inputStream = importFile.getInputStream();
            //存储
            File saveFile = new File(UPLOADBACKUPDIR + File.separator + fileName);
            if (saveFile.exists())
            {
                redirectAttributes.addFlashAttribute("message", fileName + "文件已存在!");
                return "redirect:/payease/syn001001/list";
            }
            if (!saveFile(saveFile, inputStream))
            {
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/payease/syn001001/list";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<SYN001001Req> syn001001Reqs = null;
            syn001001Reqs = PayeaseExcelUtil.analyseImportSYN001001Req(inputStream);
            inputStream.close();
            if (syn001001Reqs == null)
            {
                redirectAttributes.addFlashAttribute("message", "文件不包含开户工作表");
                return "redirect:/payease/syn001001/list";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传首信易开户文件：" + fileName);
            for (SYN001001Req syn001001Req : syn001001Reqs)
            {
                syn001001Req.setUser(customerService.nextUser());
                //同步发送
                SYN001001Resp syn001001Resp = customerService.register(syn001001Req);
                if (ReturnCode.TRANS_SUCCESS.equals(syn001001Resp.getReturnCode()))
                {
                    //发送同步银行卡
                    customerService.bindBankCard(syn001001Req);
                }
            }
        } catch (UnsupportedEncodingException e)
        {
            logger.error("UnsupportedEncodingException错误", e);
        } catch (RuntimeException e)
        {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            logger.error("Exception错误", e);
        } catch (Exception e)
        {
            logger.error("Exception错误", e);
        }
        return "redirect:/payease/syn001001/list";
    }

    @ResponseBody
    @RequestMapping(value = "/syn001001/record", method = RequestMethod.GET)
    public Page<Map<String, Object>> getTradeRecord(@RequestParam("user") String user, @RequestParam(value = "tradeType", required = false) String tradeType, @RequestParam(value = "returnCode", required = false) String returnCode,
                                                    @RequestParam(value = "returnStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnStartTime,
                                                    @RequestParam(value = "returnEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnEndTime,
                                                    @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo)
    {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("user", user);
        searchConditions.put("returnCode", returnCode);
        searchConditions.put("returnStartTime", returnStartTime);
        searchConditions.put("returnEndTime", returnEndTime);
        if (StringUtils.isEmpty(tradeType))
        {
            //获取所有交易
            customerService.getTradeListPage(searchConditions, page);
        } else if (TransCode.TRS001008.getCode().equals(tradeType))
        {
            //获取代扣交易
            deductService.getListPage(searchConditions, page);
        } else if (TransCode.TRS001006.getCode().equals(tradeType))
        {
            //获取提现交易
            withdrawService.getListPage(searchConditions, page);
        }
        for (Map<String, Object> map : page.getResult())
        {
            Object accBankCodeObj = map.get("accBankCode");
            if (accBankCodeObj!=null && !StringUtils.isEmpty(accBankCodeObj.toString()))
            {
                //银行代码转银行名称
                short accBankCode = Short.parseShort(map.get("accBankCode").toString());
                map.put("accBank", sysBankService.getBankByCode(accBankCode).getFullName());
            }

            String operationCode = map.get("operationCode").toString();
            if (OperationCode.TRS001008.DEDUCT_TO_INVEST.getCode().equals(operationCode))
            {
                map.put("operationName", TransCode.TRS001008.getName());
            } else if (OperationCode.TRS001007.INVEST_TO_INVEST.getCode().equals(operationCode))
            {
                map.put("operationName", TransCode.TRS001007.getName());
            } else if (OperationCode.TRS001006.WITHDRAW_FROM_LEND_ACCOUNT.getCode().equals(operationCode))
            {
                map.put("operationName", TransCode.TRS001006.getName());
            }
        }
        return page;
    }


    @ResponseBody
    @RequestMapping("/syn001001/binding")
    public SYN001001Resp bindingCard(@RequestParam("user") String user, @RequestParam("userName") String userName,
                                     @RequestParam("id") String id, @RequestParam("accNum") String accNum, @RequestParam("accBankCode") Short accBankCode)
    {
        SYN001001Resp syn001001Resp = null;
        try
        {
            if (!VerifyFieldUtil.verifyChinese(userName))
            {
                throw new IllegalPlatformAugumentException("用户名错误！");
            }
            if (!VerifyFieldUtil.verifyIdCard(id))
            {
                throw new IllegalPlatformAugumentException("身份证错误！");
            }
            if (accNum.length() < 14 || accBankCode == 0)
            {
                throw new IllegalPlatformAugumentException("银行卡错误！");
            }
            //检查user是否存在
            List<SYN001001Resp> syn001001Resps = customerService.getByUser(user);
            if (!syn001001Resps.isEmpty())
            {
                SYN001001Req syn001001Req = new SYN001001Req(user);
                syn001001Req.setUserName(userName);
                syn001001Req.setId(id);
                syn001001Req.setAccName(userName);
                syn001001Req.setAccNum(accNum);
                syn001001Req.setAccBankCode(accBankCode);
                syn001001Resp = customerService.bindBankCard(syn001001Req);
            }
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error(e);
        }
        return syn001001Resp;
    }

    @ResponseBody
    @RequestMapping("/syn001001/queryBankCard")
    public SYN001001Resp bindingCard(@RequestParam("user") String user)
    {
        //检查user是否存在
        SYN001001Resp syn001001Resp = null;
        List<SYN001001Resp> syn001001Resps = customerService.getByUser(user);
        if (!syn001001Resps.isEmpty())
        {
            SYN001001Resp syn001001Resp1 = syn001001Resps.get(0);

            SYN001001Req syn001001Req = new SYN001001Req(user);
            syn001001Req.setId(syn001001Resp1.getId());
            syn001001Req.setUserName(syn001001Resp1.getUserName());
            syn001001Req.setAccName(syn001001Resp1.getAccName());
            syn001001Req.setAccNum(syn001001Resp1.getAccNum());
            syn001001Req.setAccBankCode(syn001001Resp1.getAccBankCode());
            try
            {
                syn001001Resp = customerService.queryBankCard(syn001001Req);
            } catch (IllegalPlatformAugumentException e)
            {
                logger.error(e);
            }
        }
        return syn001001Resp;
    }

    /**
     * 保存上传文件
     *
     * @param saveFile
     * @param inputStream
     */
    private boolean saveFile(File saveFile, InputStream inputStream)
    {
        FileOutputStream saveFileOutputStream = null;
        try
        {
            saveFileOutputStream = new FileOutputStream(saveFile);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(b)) != -1)
            {
                saveFileOutputStream.write(b, 0, len);
            }
            saveFileOutputStream.flush();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (saveFileOutputStream != null)
            {
                try
                {
                    saveFileOutputStream.close();
                } catch (IOException e)
                {
                }
            }
        }
        return false;
    }

    /**
     * 回调方法。
     * <p/>
     * 用来接收绑卡结果的
     *
     * @param response
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/syn001001/bindCard", method = RequestMethod.POST)
    public String bindCard(HttpServletResponse response, HttpServletRequest request)
    {
        logger.info("开始接收绑卡回调数据");
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String result = in.readLine();
            String showString = EncDecUtil.dec(result);
            SYN001001Resp syn001001Resp = JSON.parseObject(showString, SYN001001Resp.class);
            String accBank = JSON.parseObject(showString).getString("accBank");
            try
            {
                syn001001Resp.setAccBankCode(sysBankService.getBankByFullName(accBank).getCode());
            }
            catch (Exception e)
            {
                syn001001Resp.setAccBankCode(null);
            }
            List<SYN001001Resp> syn001001Resps = customerService.getByUser(syn001001Resp.getUser());
            if (!syn001001Resps.isEmpty())
            {
                for (SYN001001Resp syn001001Resp1 : syn001001Resps)
                {
                    if (syn001001Resp1.getUserName().equals(syn001001Resp.getUserName())
                            && syn001001Resp1.getAccNum().equals(syn001001Resp.getAccNum())
                            && syn001001Resp1.getAccBankCode().equals(syn001001Resp.getAccBankCode()))
                    {
                        syn001001Resp.setMsgid(syn001001Resp1.getMsgid());
                        break;
                    }
                }
                syn001001Resp.setReturnTime(new Date());
                logger.info("接收验卡异步回调结果: " + showString);
                if (syn001001Resp.getMsgid() == 0)
                {
                    throw new IOException("回调使用的卡与本地不一致！");
                }
                if (StringUtils.isEmpty(syn001001Resp.getReturnCode())
                        || StringUtils.isEmpty(syn001001Resp.getReturnMsg()))
                {
                    throw new IOException("回调无结果！");
                }
                if(syn001001Resp.getReturnCode().equals(ReturnCode.TRANS_SUCCESS)){
                    syn001001Resp.setReturnCode(ReturnCode.BIND_CARD_SUCCESS);
                }else{
                    syn001001Resp.setReturnCode(ReturnCode.BIND_CARD_FAILURE);
                }
                customerService.updateBindResult(syn001001Resp);
                //回调
                if(!"己提交此用户验卡信息".equals(syn001001Resp.getReturnMsg())){
                    IBindBankCardCallbackListener iBindBankCardCallbackListener = CallbackListenerContainer.removeBindBankCardCallbackListener(syn001001Resp.getUser());
                    if(iBindBankCardCallbackListener!=null){
                        iBindBankCardCallbackListener.bindCallback(syn001001Resp);
                    }
                }
            }
            else
            {
                logger.error("接收绑卡回复时无此人");
            }
        }
        catch (IOException ioe)
        {
            logger.error("接收绑卡回复时出现异常", ioe);
            return "失败";
        }
        return "成功";
    }

}
