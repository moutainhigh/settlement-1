package com.yuanheng100.settlement.payease.util;

import com.yuanheng100.settlement.payease.model.other.IdcardVerify;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/12/26.
 */
public class PayeaseExcelUtil
{
    private static DecimalFormat df = null;

    private static Logger logger = Logger.getLogger(PayeaseExcelUtil.class);

    static
    {
        df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("0.00");
    }

    /**
     * 解析首信易的开户导入文件
     *
     * @return
     * @throws IOException
     */
    public static List<SYN001001Req> analyseImportSYN001001Req(InputStream is) throws IOException
    {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("开户");
        if (sheet == null)
        {
            return null;
        }
        List<SYN001001Req> list = new ArrayList<SYN001001Req>();
        for (Row row : sheet)
        {
            if (row.getRowNum() == 0)
            {
                continue;
            }
            //获取各个需要的参数[0]userName [1]id [2]accBank [3]accNum
            SYN001001Req syn001001Req = new SYN001001Req("0"); //使用0代替初始化用户编号
            try
            {
                String stringCellValue = row.getCell(0).getStringCellValue();
                syn001001Req.setUserName(stringCellValue);
                syn001001Req.setAccName(stringCellValue);
            } catch (Exception e)
            {
                throw new RuntimeException("真实姓名获取出错！");
            }
            try
            {
                syn001001Req.setId(row.getCell(1).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("身份证号获取出错！");
            }
            try
            {
                Double bankCode = row.getCell(2).getNumericCellValue();
                syn001001Req.setAccBankCode(bankCode.shortValue());
            } catch (Exception e)
            {
                throw new RuntimeException("银行代码获取出错！");
            }
            try
            {
                syn001001Req.setAccNum(row.getCell(3).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("银行卡号获取出错！");
            }
            list.add(syn001001Req);
        }
        wb.close();
        return list;
    }


    /**
     * 认证导入
     * @param is
     * @return
     * @throws IOException
     */
    public static List<IdcardVerify> idcardExcel(InputStream is) throws IOException
    {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        List<IdcardVerify> list = new ArrayList<IdcardVerify>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell cell = row.getCell(0);
            if (cell == null)continue;
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            IdcardVerify idcardVerify = new IdcardVerify();
            idcardVerify.setName(cell.getStringCellValue());
            cell = row.getCell(1);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            idcardVerify.setIdcardNo(cell.getStringCellValue());
            list.add(idcardVerify);
        }
        wb.close();
        return list;
    }


    /**
     * 转账导入
     * @param is
     * @return
     * @throws IOException
     */
    public static List<Map<String,Object>> transferAmount(InputStream is) throws IOException
    {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell cell = row.getCell(0);
            if (cell == null)continue;
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            Map<String,Object> map = new HashMap<String, Object>();
            TRS001007Req trs001007Req = new TRS001007Req();
            trs001007Req.setTransferOutUser(cell.getStringCellValue());
            cell = row.getCell(1);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            if ("2".equals(cell.getStringCellValue()))
            {
                cell = row.getCell(3);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ("2".equals(cell.getStringCellValue()))
                {
                    logger.error("导入转账数据出现负债账户转负债账户，出现错误数据行数：" + row.getRowNum());
                    continue;
                }
                else if ("1".equals(cell.getStringCellValue()))
                {
                    map.put("type","3");
                }
            }
            else if ("1".equals(cell.getStringCellValue()))
            {
                cell = row.getCell(3);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ("2".equals(cell.getStringCellValue()))
                {
                    map.put("type","2");
                }
                else if ("1".equals(cell.getStringCellValue()))
                {
                    map.put("type","1");
                }
            }
            cell = row.getCell(2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            trs001007Req.setTransferInUser(cell.getStringCellValue());
            cell = row.getCell(4);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            trs001007Req.setTransferAmount(String.valueOf(new BigDecimal(cell.getStringCellValue().trim()).setScale(BigDecimal.ROUND_HALF_EVEN)));
            map.put("trans",trs001007Req);
            list.add(map);
        }
        wb.close();
        return list;
    }

    public static List<TRS001008Req> analyseImportTRS001008Req(InputStream is) throws IOException
    {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("代扣");
        if (sheet == null)
        {
            return null;
        }
        List<TRS001008Req> list = new ArrayList<TRS001008Req>();
        for (Row row : sheet)
        {
            if (row.getRowNum() == 0)
            {
                continue;
            }
            //获取各个需要的参数[0]user [1]userName [2]id [3]accNum [4]amount
            TRS001008Req trs001008Req = new TRS001008Req();
            ;
            try
            {
                Double numericCellValue = row.getCell(0).getNumericCellValue();
                int user = numericCellValue.intValue();
                trs001008Req.setUser(String.valueOf(user));
            } catch (Exception e)
            {
                throw new RuntimeException("用户ID获取出错！", e);
            }
            try
            {
                trs001008Req.setAccName(row.getCell(1).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("真实姓名获取出错！");
            }
            try
            {
                trs001008Req.setId(row.getCell(2).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("身份证号获取出错！");
            }
            try
            {
                trs001008Req.setAccNum(row.getCell(3).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("银行卡号获取出错！");
            }
            try
            {
                Double numericCellValue = row.getCell(4).getNumericCellValue();
                trs001008Req.setAmount(df.format(numericCellValue));
            } catch (Exception e)
            {
                throw new RuntimeException("代扣金额获取出错！");
            }
            list.add(trs001008Req);
        }
        wb.close();
        return list;
    }

    public static List<TRS001006Req> analyseImportTRS001006Req(InputStream is) throws IOException
    {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("提现");
        if (sheet == null)
        {
            return null;
        }
        List<TRS001006Req> list = new ArrayList<TRS001006Req>();
        for (Row row : sheet)
        {
            if (row.getRowNum() == 0)
            {
                continue;
            }
            //获取各个需要的参数[0]user [1]userName [2]accNum [3]amount
            TRS001006Req trs001006Req = new TRS001006Req();
            ;
            try
            {
                Double numericCellValue = row.getCell(0).getNumericCellValue();
                int user = numericCellValue.intValue();
                trs001006Req.setUser(String.valueOf(user));
            } catch (Exception e)
            {
                throw new RuntimeException("用户ID获取出错！", e);
            }
            try
            {
                trs001006Req.setAccName(row.getCell(1).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("真实姓名获取出错！");
            }
            try
            {
                trs001006Req.setAccNum(row.getCell(2).getStringCellValue());
            } catch (Exception e)
            {
                throw new RuntimeException("银行卡号获取出错！");
            }
            try
            {
                Double numericCellValue = row.getCell(3).getNumericCellValue();
                trs001006Req.setAmount(df.format(numericCellValue));
            } catch (Exception e)
            {
                throw new RuntimeException("提现金额获取出错！");
            }
            list.add(trs001006Req);
        }
        wb.close();
        return list;
    }
}
