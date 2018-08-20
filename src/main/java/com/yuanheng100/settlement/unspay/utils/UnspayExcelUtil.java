package com.yuanheng100.settlement.unspay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.model.UnspayPay;
import com.yuanheng100.settlement.unspay.model.UnspaySubContract;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by qianjl on 2016-7-4.
 */
public class UnspayExcelUtil {

    /**
     * 解析银生宝的子协议导入文件
     *
     * @return
     * @throws IOException
     */
    public static List<UnspaySubContract> analyseImportUnspaySubContract(InputStream is) throws IOException {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("客户信息");
        if (sheet == null) {
            return null;
        }
        List<UnspaySubContract> list = new ArrayList<UnspaySubContract>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            UnspaySubContract unspaySubContract = new UnspaySubContract();
            //获取各个需要的参数[0]loanApplyId [1]name [2]idCardNo [3]phoneNo [4]cardNo [5]bankCode [6]startDate [7]endDate [8]cycle [9]triesLimit
            try {
                unspaySubContract.setLoanApplyId(Long.parseLong(row.getCell(0).getStringCellValue()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("文件进件编号解析出错！");
            }
            try {
                unspaySubContract.setName(row.getCell(1).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议姓名获取出错！");
            }
            try {
                unspaySubContract.setIdCardNo(row.getCell(2).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议身份证号获取出错！");
            }
            try {
                unspaySubContract.setPhoneNo(Long.parseLong(row.getCell(3).getStringCellValue()));
            } catch (Exception e) {
                throw new RuntimeException("子协议手机号获取出错！");
            }
            try {
                unspaySubContract.setCardNo(row.getCell(4).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议银行卡号获取出错！");
            }
            try {
                unspaySubContract.setBankCode(Short.parseShort(row.getCell(5).getStringCellValue()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("子协议银行代码获取出错！");
            }
            try {
                unspaySubContract.setStartDate(row.getCell(6).getDateCellValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议开始日期获取出错！");
            }
            try {
                unspaySubContract.setEndDate(row.getCell(7).getDateCellValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议截止日期获取出错！");
            }
            try {
                Short cycle = ((Double) row.getCell(8).getNumericCellValue()).shortValue();
                if (cycle != null && cycle == 30) {
                    unspaySubContract.setCycle((short) 2);
                }
            } catch (Exception e) {
                throw new RuntimeException("子协议扣款频率获取出错！");
            }
            try {
                unspaySubContract.setTriesLimit(((Double) row.getCell(9).getNumericCellValue()).intValue());
            } catch (Exception e) {
                throw new RuntimeException("子协议扣款次数获取出错！");
            }
            list.add(unspaySubContract);
        }
        wb.close();
        return list;
    }

    /**
     * 解析银生宝的代付导入文件
     *
     * @return
     * @throws IOException
     */
    public static List<UnspayPay> analyseImportUnspayPay(InputStream is) throws IOException {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("放款入账");
        if (sheet == null) {
            return null;
        }
        List<UnspayPay> list = new ArrayList<UnspayPay>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            UnspayPay unspayPay = new UnspayPay();
            //获取各个需要的参数[0]loanApplyId [1]name [2]cardNo [3]repayAmount [4]planRepayTime [5]deductPurpose
            try {
            	unspayPay.setLoanApplyId(Long.parseLong(row.getCell(0).getStringCellValue()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("文件进件编号解析出错！");
            }
            try {
            	unspayPay.setName(row.getCell(1).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("收款人姓名获取出错！");
            }
            try {
            	unspayPay.setCardNo(row.getCell(2).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("收款人银行卡号获取出错！");
            }
            try {
            	unspayPay.setAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
            } catch (Exception e) {
                throw new RuntimeException("划扣金额获取出错！");
            }
            try {
            	unspayPay.setPlanDate(row.getCell(4).getDateCellValue());
            } catch (Exception e) {
                throw new RuntimeException("划扣计划时间获取出错！");
            }
            try {
            	unspayPay.setPurpose(row.getCell(5).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("划扣目的获取出错！");
            }
            list.add(unspayPay);
        }
        wb.close();
        return list;
    }
    
    /**
     * 导出银生宝代付结果
     * @param unspayMaps
     * @return
     */
	public static Workbook exportUnspayPayResult(List<Map<String, Object>> unspayMaps) {
		//创建代扣结果文件
        Workbook wb = new HSSFWorkbook();
        //创建Excel的Sheet
        Sheet sheet = wb.createSheet("放款入账");
        sheet.setDefaultColumnWidth(20);
        Row headRow = sheet.createRow(0);
        UnspayExcelUtil.setHeadRow(headRow, new String[]{"合同编号","入账姓名","入账银行卡号", "付款金额", "计划日期", "付款原因","付款结果"});
        UnspayExcelUtil.setDataRow(sheet, unspayMaps, new String[]{"loanApplyId", "name", "cardNo", "amount", "planDate", "purpose","payResult"});
        return wb;
	}
	
	/**
	 * 解析中城国典银生宝的代付导入文件
	 *
	 * @return
	 * @throws IOException
	 */
	public static List<UnspayFourElementsPay> analyseImportUnspayZCGDPay(InputStream is) throws IOException {
		Workbook wb = new HSSFWorkbook(is);
		Sheet sheet = wb.getSheet("放款入账");
		if (sheet == null) {
			return null;
		}
		List<UnspayFourElementsPay> list = new ArrayList<UnspayFourElementsPay>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			UnspayFourElementsPay unspayFourElementsPay = new UnspayFourElementsPay();
			//获取各个需要的参数[0]loanApplyId [1]name [2]idCardNo [3]cardNo [4]amount [5]purpose
			try {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				unspayFourElementsPay.setLoanApplyId(Long.parseLong(row.getCell(0).getStringCellValue()));
			} catch (NumberFormatException e) {
				throw new NumberFormatException("文件合同编号解析出错！");
			}
			try {
				unspayFourElementsPay.setName(row.getCell(1).getStringCellValue());
			} catch (Exception e) {
				throw new RuntimeException("收款人姓名获取出错！");
			}
			try {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				unspayFourElementsPay.setIdCardNo(row.getCell(2).getStringCellValue());
			} catch (Exception e) {
				throw new RuntimeException("收款人身份证号获取出错！");
			}
			try {
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				unspayFourElementsPay.setCardNo(row.getCell(3).getStringCellValue());
			} catch (Exception e) {
				throw new RuntimeException("收款人银行账号获取出错！");
			}
			try {
				unspayFourElementsPay.setAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
			} catch (Exception e) {
				throw new RuntimeException("付款金额获取出错！");
			}
			try {
				unspayFourElementsPay.setPurpose(row.getCell(5).getStringCellValue());
			} catch (Exception e) {
				throw new RuntimeException("付款目的获取出错！");
			}
			list.add(unspayFourElementsPay);
		}
		wb.close();
		return list;
	}
	
	/**
	 * 导出中城国典银生宝代付结果
	 * @param unspayMaps
	 * @return
	 */
	public static Workbook exportUnspayZCGDPayResult(List<Map<String, Object>> unspayMaps) {
		//创建代扣结果文件
		Workbook wb = new HSSFWorkbook();
		//创建Excel的Sheet
		Sheet sheet = wb.createSheet("放款入账");
		sheet.setDefaultColumnWidth(20);
		Row headRow = sheet.createRow(0);
		UnspayExcelUtil.setHeadRow(headRow, new String[]{"合同编号","入账姓名","收款人身份证号","入账银行卡号", "付款金额","付款目的","付款结果","交易结果描述"});
		UnspayExcelUtil.setDataRow(sheet, unspayMaps, new String[]{"loanApplyId", "name", "idCardNo","cardNo", "amount", "purpose","payResult","desc"});
		return wb;
	}
	
    /**
     * 解析银生宝的代扣导入文件
     *
     * @return
     * @throws IOException
     */
    public static List<UnspayDeduct> analyseImportUnspayDeduct(InputStream is) throws IOException {
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheet("还款代扣");
        if (sheet == null) {
            return null;
        }
        List<UnspayDeduct> list = new ArrayList<UnspayDeduct>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            UnspayDeduct unspayDeduct = new UnspayDeduct();
            //获取各个需要的参数[0]loanApplyId [1]repayPhaseId [2]name [3]cardNo [4]repayAmount [5]planRepayTime [6]deductPurpose
            try {
                unspayDeduct.setLoanApplyId(Long.parseLong(row.getCell(0).getStringCellValue()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("文件进件编号解析出错！");
            }
            try {
                unspayDeduct.setRepayPhaseId(Long.parseLong(row.getCell(1).getStringCellValue()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("文件分期编号解析出错！");
            }
            try {
                unspayDeduct.setAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
            } catch (Exception e) {
                throw new RuntimeException("划扣金额获取出错！");
            }
            try {
                unspayDeduct.setPlanDate(row.getCell(5).getDateCellValue());
            } catch (Exception e) {
                throw new RuntimeException("划扣计划时间获取出错！");
            }
            try {
                unspayDeduct.setPurpose(row.getCell(6).getStringCellValue());
            } catch (Exception e) {
                throw new RuntimeException("划扣目的获取出错！");
            }
            list.add(unspayDeduct);
        }
        wb.close();
        return list;
    }

    public static Workbook exportUnspayDeductResult(List<Map<String, Object>> unspayMaps) {
        //创建代扣结果文件
        Workbook wb = new HSSFWorkbook();
        //创建Excel的Sheet
        Sheet sheet = wb.createSheet("还款代扣");
        sheet.setDefaultColumnWidth(20);
        Row headRow = sheet.createRow(0);
        UnspayExcelUtil.setHeadRow(headRow, new String[]{"进件编号", "分期编号", "真实姓名", "银行卡号", "还款金额", "计划日期", "扣款目的","扣款结果"});
        UnspayExcelUtil.setDataRow(sheet, unspayMaps, new String[]{"loanApplyId", "repayPhaseId", "name", "cardNo", "amount", "planDate", "purpose","deductResult"});
        return wb;
    }

    /**
     * 将数组中的值填充到Excel表格的一行中
     *
     * @param headRow
     * @param headArray
     */
    public static void setHeadRow(Row headRow, String[] headArray) {
        for (int i = 0; i < headArray.length; i++) {
            Cell createCell = headRow.createCell(i);
            createCell.setCellValue(headArray[i]);
        }
    }

    /**
     * 从第二行开始填充sheet多行数据
     *
     * @param sheet
     * @param dataMaps
     * @param dataKey
     */
    public static void setDataRow(Sheet sheet, List<Map<String, Object>> dataMaps, String[] dataKey) {
        for (int i = 1; i <= dataMaps.size(); i++) {
            Row createRow = sheet.createRow(i);
            Map<String, Object> map = dataMaps.get(i - 1);
            for (int j = 0; j < dataKey.length; j++) {
                Cell cell = createRow.createCell(j);
                Object value = map.get(dataKey[j]);
                if (value != null) {
                    DataFormat dataFormat = sheet.getWorkbook().createDataFormat();
                    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
                    if (dataKey[j].equals("amount")) {
                        short format = dataFormat.getFormat("0.00");
                        cellStyle.setDataFormat(format);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else if (dataKey[j].equals("planDate")) {
                        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-m-d h:mm"));
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue((Date) value);
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }

        }
    }
}