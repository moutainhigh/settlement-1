package com.yuanheng100.settlement.payease.controller;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.common.service.ISysBankService;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.service.ICustomerService;
import com.yuanheng100.settlement.payease.service.IDeductService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import com.yuanheng100.settlement.payease.util.PayeaseExcelUtil;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/12/26.
 */
@Controller
@RequestMapping("/payease/trs001008")
public class TRS001008Controller
{
    private static Logger logger = Logger.getLogger(TRS001008Controller.class);

    private static final String UPLOADBACKUPDIR = CommonDef.ROOT_DIR + File.separator + "backup" + File.separator + "upload";

    @Autowired
    private LoginStaff loginStaff;
    @Autowired
    private ISysBankService sysBankService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDeductService deductService;
    @Autowired
    private ISequenceIdService sequenceIdService;


    @RequestMapping("/list")
    public String deductList(@RequestParam(value = "accName", required = false) String accName, @RequestParam(value = "accNum", required = false) String accNum,
                             @RequestParam(value = "id", required = false) String id, @RequestParam(value = "user", required = false) String user,
                             @RequestParam(value = "returnCode", required = false) String returnCode,
                             @RequestParam(value = "returnStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnStartTime,
                             @RequestParam(value = "returnEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnEndTime,
                             @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                             Map<String, Object> map)
    {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(pageNo);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("accName", accName);
        searchConditions.put("id", id);
        searchConditions.put("user", user);
        searchConditions.put("returnStartTime", returnStartTime);
        searchConditions.put("returnEndTime", returnEndTime);
        searchConditions.put("accNum", accNum);
        searchConditions.put("returnCode", returnCode);

        deductService.getListPage(searchConditions, page);
        for (Map<String, Object> resultMap : page.getResult())
        {
            //银行代码转银行名称
            short accBankCode = Short.parseShort(resultMap.get("accBankCode").toString());
            resultMap.put("accBank", sysBankService.getBankByCode(accBankCode).getFullName());
        }
        map.put("page", page);
        map.putAll(searchConditions);

        return "payease/deduct";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public TRS001008Resp authenticateAdd(@RequestParam("user") String user, @RequestParam("amount") String amount)
    {
        TRS001008Resp trs001008Resp = null;
        try
        {
            SYN001001Resp byUser = customerService.getBindCardSuccessCustomerByUser(user);
            if (byUser != null)
            {
                int sequenceId = sequenceIdService.getSequenceId();
                TRS001008Req trs001008Req = new TRS001008Req();
                trs001008Req.setSerlNum(String.valueOf(sequenceId));
                trs001008Req.setUser(byUser.getUser());
                trs001008Req.setId(byUser.getId());
                trs001008Req.setAccName(byUser.getAccName());
                trs001008Req.setAccNum(byUser.getAccNum());
                trs001008Req.setAccBankCode(byUser.getAccBankCode());
                trs001008Req.setAmount(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                //同步发送
                trs001008Resp = deductService.deductToInvest(trs001008Req);
            } else
            {
                throw new IllegalPlatformAugumentException("用户未绑定银行卡！");
            }
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error(e);
            trs001008Resp = new TRS001008Resp();
            trs001008Resp.setReturnMsg(e.getMessage());
        }
        return trs001008Resp;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
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
                return "redirect:/payease/trs001008/list";
            }
            if (!saveFile(saveFile, inputStream))
            {
                redirectAttributes.addFlashAttribute("message", fileName + "文件上传失败!");
                return "redirect:/payease/trs001008/list";
            }
            inputStream.close();
            //处理业务
            inputStream = new FileInputStream(saveFile);
            List<TRS001008Req> trs001008Reqs = null;
            trs001008Reqs = PayeaseExcelUtil.analyseImportTRS001008Req(inputStream);
            inputStream.close();
            if (trs001008Reqs == null)
            {
                redirectAttributes.addFlashAttribute("message", "文件不包含代扣工作表");
                return "redirect:/payease/trs001008/list";
            }
            logger.info("管理员" + sysStaff.getStaffName() + "上传首信易代扣文件：" + fileName);

            for (TRS001008Req trs001008Req : trs001008Reqs)
            {
                StringBuilder wrongUser = new StringBuilder();
                SYN001001Resp byUser = customerService.getBindCardSuccessCustomerByUser(trs001008Req.getUser());
                if (byUser != null && byUser.getId().equals(trs001008Req.getId()) && byUser.getAccNum().equals(trs001008Req.getAccNum()))
                {
                    int sequenceId = sequenceIdService.getSequenceId();
                    trs001008Req.setSerlNum(String.valueOf(sequenceId));
                    trs001008Req.setAccBankCode(byUser.getAccBankCode());
                    //同步发送
                    deductService.deductToInvest(trs001008Req);
                } else
                {
                    wrongUser.append(trs001008Req.getUser()).append(",");
                }
                if (wrongUser.length() != 0)
                {
                    wrongUser.deleteCharAt(wrongUser.length() - 1);
                    throw new RuntimeException("用户" + wrongUser.toString() + "不存在或者身份证、银行卡信息不正确！");
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
        return "redirect:/payease/trs001008/list";
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
     * 用来接收代扣结果的
     *
     * @param response
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deduct", method = RequestMethod.POST)
    public String bindCard(HttpServletResponse response, HttpServletRequest request)
    {
        logger.info("开始接收代扣回调数据");
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String result = in.readLine();
            String showString = EncDecUtil.dec(result);
            TRS001008Resp trs001008Resp = JSON.parseObject(showString, TRS001008Resp.class);
            trs001008Resp.setReturnTime(new Date());
            logger.info("更新代扣结果: " + showString);
            deductService.updateResult(trs001008Resp);
        } catch (IOException ioe)
        {
            logger.error("接收代扣回复时出现异常", ioe);
        }

        return "成功";
    }


}
