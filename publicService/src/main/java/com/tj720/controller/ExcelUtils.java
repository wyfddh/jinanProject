package com.tj720.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

	// 读取excel
	public static Workbook readExcel(String filePath) {
		Workbook wb = null;
		if (filePath == null) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			// 判断Excel的后缀类型
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	// 将Excel解析成双层List集合
	public static List<List<String>> parseExcel(String filePath) {
		List<String> line = null;
		List<List<String>> lines = new ArrayList<List<String>>();
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		wb = readExcel(filePath);
		if (wb != null) {
			// 获取第一个sheet工作表
			sheet = wb.getSheetAt(0);
			// 获取最大行数
			int rownum = sheet.getPhysicalNumberOfRows();
			// 获取第一行
			row = sheet.getRow(0);
			// 获取最大列数
			int colnum = row.getPhysicalNumberOfCells();
			// 读取每行
			for (int i = 1; i < rownum; i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				row = sheet.getRow(i);
				if (row != null) {
					// 读取每行中的每列
					for (int j = 0; j < colnum; j++) {
						line = new ArrayList<String>();
						for (int j2 = 0; j2 < colnum; j2++) {
							line.add((String) getCellFormatValue(row.getCell(j2)));
						}
					}
				} else {
					break;
				}
				lines.add(line);
			}
		}
		return lines;
	}

	// 解析表格数据类型
	public static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			// 数字类型
			case Cell.CELL_TYPE_NUMERIC: {
				// cell.setCellType(Cell.CELL_TYPE_STRING);
//				cellValue = String.valueOf((double) cell.getNumericCellValue());
				cellValue = String.valueOf((int) cell.getNumericCellValue());
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					// 转换为日期格式YYYY-mm-dd
					cellValue = cell.getDateCellValue();
				} else {
					// 数字
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	// 检查空值
	public static String checkNull(String filePath) {
		String message = "";
		ExcelUtils r = new ExcelUtils();
		List<List<String>> lines = r.parseExcel(filePath);
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				if (lines.get(i).get(j) == null || "".equals(lines.get(i).get(j))) {
					// System.out.println(i+"行"+j+"列有空值！！！");
					message += (i + 2) + "行" + (j + 1) + "列有空值！！！\r\n";
				}
			}
		}
		if ("".equals(message)) {
			return "没有空值";
		}
		return message;
	}

	// 检查空值
	public static String checkNull(File file) {
		String message = "";
		String filePath = file.getPath();
		ExcelUtils r = new ExcelUtils();
		List<List<String>> lines = ExcelUtils.parseExcel(filePath);
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				if (lines.get(i).get(j) == null || "".equals(lines.get(i).get(j))) {
					// System.out.println(i+"行"+j+"列有空值！！！");
					message += (i + 2) + "行" + (j + 1) + "列有空值！！！";
				}
			}
		}
		if ("".equals(message)) {
			return "没有空值";
		}
		return message;
	}
}