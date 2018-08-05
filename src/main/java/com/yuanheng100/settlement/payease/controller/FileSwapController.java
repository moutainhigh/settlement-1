package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.model.other.IdcardVerify;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.service.IIdCardVerifyService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.service.ITransferService;
import com.yuanheng100.settlement.payease.util.PayeaseExcelUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 *
 * 处理文件上传、下载
 * Created by wangguangshuo on 2017/1/6.
 */
@Controller
@RequestMapping("/payease")
public class FileSwapController
{

    private static Logger logger = Logger.getLogger(FileSwapController.class);

    @Autowired
    private LoginStaff loginStaff;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IIdCardVerifyService idCardVerifyService;

    @Autowired
    private ISequenceIdService payeaseSequenceIdService;

    private static final String UPLOADBACKUPDIR = CommonDef.ROOT_DIR + File.separator + "temp";


    static {
        File uploadBackupDir = new File(UPLOADBACKUPDIR);
        if(!uploadBackupDir.exists()){
            uploadBackupDir.mkdirs();
        }
    }


    /**
     * 导入转账文件
     * @param importFile
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/uploadTransfer")
    public String uploadTransfer(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes)
    {
        SysStaff sysStaff = loginStaff.getSysStaff();
        if (sysStaff == null)
        {
            return "redirect:/index.jsp";
        }

        try
        {
            InputStream inputStream = importFile.getInputStream();
            List<Map<String, Object>> list = PayeaseExcelUtil.transferAmount(inputStream);
            inputStream.close();
            if (list == null)
            {
                redirectAttributes.addFlashAttribute("message", "文件为空");
                return "redirect:/payease/transfer";
            }
            logger.info("用户" + sysStaff.getStaffName() + "上传首信易转账文件：" + new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8"));
            TRS001007Req trs001007Req = null;
            for (Map<String, Object> map : list)
            {
                trs001007Req = (TRS001007Req) map.get("trans");
                trs001007Req.setSerlNum(String.valueOf(payeaseSequenceIdService.getSequenceId()));
                trs001007Req.setTransferOutUserFee("0.00");
                trs001007Req.setTransferInUserFee("0.00");
                transerResult(trs001007Req,String.valueOf(map.get("type")));
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
        return "redirect:/payease/transfer";
    }


    /**
     * 转账
     * @param trs001007Req
     * @param type
     * @return
     */
    private boolean transerResult(TRS001007Req trs001007Req, String type)
    {
        TRS001007Resp trs001007Resp = null;
        try
        {
            if ("1".equals(type))
            {
                trs001007Resp = transferService.transferBetweenInvestAccount(trs001007Req);
            }
            else if ("2".equals(type))
            {
                trs001007Resp = transferService.transferInvestToLiability(trs001007Req);
            }
            else if ("3".equals(type))
            {
                trs001007Resp = transferService.transferLiabilityToInvest(trs001007Req);
            }
            else
            {
                logger.info("请求参数不合法（type："+type+"）");
                return false;
            }
            if (trs001007Resp != null && ReturnCode.TRANS_SUCCESS.equals(trs001007Resp.getReturnCode()))
            {
                return true;
            }
            return false;
        }
        catch (IllegalPlatformAugumentException e)
        {
            logger.error("转账出错",e);
        }
        return false;
    }


    /**
     * 实名认证导入
     * @param importFile
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/uploadIdCardVerify")
    public String uploadIdCardVerify(@RequestParam("importFile") MultipartFile importFile, RedirectAttributes redirectAttributes)
    {
        SysStaff sysStaff = loginStaff.getSysStaff();

        if (sysStaff == null)
        {
            return "redirect:/index.jsp";
        }

        try
        {
            InputStream inputStream = importFile.getInputStream();
            List<IdcardVerify> list = PayeaseExcelUtil.idcardExcel(inputStream);
            inputStream.close();
            if (list == null)
            {
                redirectAttributes.addFlashAttribute("message", "文件为空");
                return "redirect:/payease/idCardVerify";
            }
            logger.info("用户" + sysStaff.getStaffName() + "上传首信易实名认证文件：" + new String(importFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8"));
            for (IdcardVerify idcardVerify : list)
            {
                idCardVerifyService.verifyIdcard(idcardVerify.getName(),idcardVerify.getIdcardNo());
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
        return "redirect:/payease/idCardVerify";
    }

    /**
     * 下载实名认证模板
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/downloadIdCardTem")
    public void downloadIdCardTem(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String contextPath = request.getServletContext().getRealPath("/");
        String path = contextPath + request.getParameter("path");
        File file = new File(path);// path是根据日志路径和文件名拼接出来的
        String filename = file.getName();// 获取日志文件名称
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        os.write(buffer);// 输出文件
        os.flush();
        os.close();
    }



    @RequestMapping("/templete")
    public ResponseEntity<byte[]> templete(@RequestParam("templete") String templete, HttpServletRequest request){

        InputStream resourceAsStream = request.getSession().getServletContext().getResourceAsStream("/templete/"+templete);

        String downLoadName;
        try {
            downLoadName = new String(templete.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            downLoadName = templete;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment;fileName=" + downLoadName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            byte[] b = new byte[1024];
            int len = 0;
            while((len = resourceAsStream.read(b))!=-1){
                byteArrayOutputStream.write(b,0,len);
            }
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), responseHeaders, HttpStatus.CREATED);
    }


}
