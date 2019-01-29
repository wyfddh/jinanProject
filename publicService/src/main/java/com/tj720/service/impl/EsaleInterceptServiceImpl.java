package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.ExcelUtils;
import com.tj720.controller.KetWordsExportTitles;
import com.tj720.controller.KetWordsImportTitles;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleInterceptMapper;
import com.tj720.model.EsaleIntercept;
import com.tj720.service.EsaleInterceptService;
import com.tj720.utils.DateUtil;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EsaleInterceptServiceImpl implements EsaleInterceptService {
	@Autowired
	private EsaleInterceptMapper esaleInterceptMapper;


	@Override
	public JSONObject queryInterceptWords(String interceptName, String orderBy, Integer size, Integer currentPage) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (StringUtils.isBlank(Tools.getUserId())) {
				throw new Exception("登录异常");
			}

			Page page = new Page();
			page.setSize(size);
			page.setCurrentPage(currentPage);

			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(interceptName)) {
				map.put("interceptName", interceptName);
			}

			if (StringUtils.isNotBlank(orderBy)) {
				map.put("orderBy", orderBy);
			} else {
				//默认0根据id排序
				map.put("orderBy", 0);
			}

			//符合条件总数
			Integer count = esaleInterceptMapper.countInterceptWords(map);
			System.out.println("count : " + count);
			page.setAllRow(count);
			map.put("start", page.getStart());
			map.put("end", page.getSize());

			List<EsaleIntercept> list = esaleInterceptMapper.queryInterceptWords(map);
			for (EsaleIntercept esaleIntercept:list){
				if(esaleIntercept.getUpdateDate()!=null){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
					String dateToString = DateUtil.DateToString(esaleIntercept.getUpdateDate(), "yyyy-MM-dd HH:mm:ss");
					esaleIntercept.setViewDate(dateToString);
				}
			}

			String jsonString = JSON.toJSONString(list);
			System.out.println("jsonString  :   " + jsonString);
			jsonObject.put("code", 0);
			jsonObject.put("msg", "");
			jsonObject.put("count", page.getAllRow());
			jsonObject.put("data", jsonString);
		} catch (Exception e) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", e.getMessage());
			jsonObject.put("count", 0);
			jsonObject.put("data", null);
		}
		return jsonObject;
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public JsonResult delInterceptWords(String id) {
		JsonResult jsonResult = null;
		try {
			esaleInterceptMapper.deleteByPrimaryKey(Integer.parseInt(id));
			jsonResult = new JsonResult(1);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = new JsonResult("111116");
		}
		return jsonResult;
	}

	/**
	 * 编辑
	 *
	 * @param esaleIntercept
	 * @return
	 */
	@Override
	public JsonResult updateInterceptWords(EsaleIntercept esaleIntercept) {
		JsonResult jsonResult = null;
		try {
			esaleIntercept.setUpdateBy(Tools.getUserId());
			esaleIntercept.setUpdateDate(new Date());
			esaleInterceptMapper.updateByPrimaryKeySelective(esaleIntercept);
			jsonResult = new JsonResult(1);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("111116");
		}
		return jsonResult;
	}

	@Override
	public JsonResult delAllInterceptWords(String[] uids) {
		JsonResult jsonResult = null;
		for (String id : uids
		) {
			jsonResult = delInterceptWords(id);
		}
		return jsonResult;
	}

	@Override
	public EsaleIntercept toUpdate(Integer id) {
		return esaleInterceptMapper.selectByPrimaryKey(id);
	}



	/**
	 * 下载模板
	 * @return
	 */
	@Override
	public Workbook generateTemplate() {
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建工作表
		XSSFSheet sheet = wb.createSheet("敏感词导入模板");
		// sheet.setDefaultColumnWidth(18);
		Row row;// 创建行对象
		Cell cell;// 创建格子对象
		// 设置样式
		CellStyle cellStyle = wb.createCellStyle();
		CellStyle cellStyle2 = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
		font.setFontName("宋体");// 字体
		font.setFontHeight(25);
		cellStyle.setFont(font);// 使用字体
		cellStyle.setWrapText(true); // 换行显示
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式

		XSSFFont font2 = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
		font.setFontName("宋体");// 字体
		font.setFontHeight(14);
		cellStyle2.setFont(font2);// 使用字体
		cellStyle2.setWrapText(true); // 换行显示
		cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
		cellStyle2.setLocked(true);// 只读

		// 创建第一行
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("敏感词导入模板");
		cell.setCellStyle(cellStyle);
		row.setHeight((short) 600);

		// 创建第二行
		row = sheet.createRow(1);
		KetWordsImportTitles[] movieExcelTitles = KetWordsImportTitles.values();
		List<String> title = new ArrayList<>();
		for (KetWordsImportTitles m : movieExcelTitles) {
			title.add(m.getTitle());
		}
		// 写入标题列
		for (int i = 0; i < title.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(title.get(i));
			cell.setCellStyle(cellStyle2);
		}
		int total = row.getLastCellNum();
		// 这个就是合并单元格
		// 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
		// 比如我要合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, total - 1));
		return wb;
	}


	/**
	 * 数据导出模板
	 */
	public Workbook generateExportTemplate() {
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建工作表
		XSSFSheet sheet = wb.createSheet("敏感词数据列表");
		// sheet.setDefaultColumnWidth(18);
		Row row;// 创建行对象
		Cell cell;// 创建格子对象
		// 设置样式
		CellStyle cellStyle = wb.createCellStyle();
		CellStyle cellStyle2 = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
		font.setFontName("宋体");// 字体
		font.setFontHeight(25);
		cellStyle.setFont(font);// 使用字体
		cellStyle.setWrapText(true); // 换行显示
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式

		XSSFFont font2 = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
		font.setFontName("宋体");// 字体
		font.setFontHeight(14);
		cellStyle2.setFont(font2);// 使用字体
		cellStyle2.setWrapText(true); // 换行显示
		cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
		cellStyle2.setLocked(true);// 只读

		// 创建第一行
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("敏感词模板导入");
		cell.setCellStyle(cellStyle);
		row.setHeight((short) 600);

		// 创建第二行
		row = sheet.createRow(1);
		KetWordsExportTitles[] movieExcelTitles = KetWordsExportTitles.values();
		List<String> title = new ArrayList<>();
		for (KetWordsExportTitles m : movieExcelTitles) {
			title.add(m.getTitle());
		}
		// 写入标题列
		for (int i = 0; i < title.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(title.get(i));
			cell.setCellStyle(cellStyle2);
		}
		int total = row.getLastCellNum();
		// 这个就是合并单元格
		// 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
		// 比如我要合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, total - 1));
		return wb;
	}

	/**
	 * 上传模板 解析Excel
	 *
	 * @param path
	 * @return
	 */
	@Override
	public JsonResult parseExcel(String path) {
		// 获取excel对象
		Workbook workbook = ExcelUtils.readExcel(path);
		Sheet sheet = null;
		Row row = null;
		if (workbook != null) {
			// 获取第一个sheet工作表
			sheet = workbook.getSheetAt(0);
			// 获取最大行数(不包括空行)
			int rownum = sheet.getPhysicalNumberOfRows();
			// 获取最大列数
			int colnum = sheet.getRow(1).getPhysicalNumberOfCells();
			String cells = "";// 每格的值
			String msg = "没有数据";// 响应消息
			try {
				// 遍历每行,第2行开始
				for (int i = 1; i < rownum; i++) {
					row = sheet.getRow(i);
					// 遍历每行中的每列
					for (int j = 0; j < colnum - 8; j++) {
						msg = i + 1 + "行" + (j + 1) + "列";
						cells = (String) ExcelUtils.getCellFormatValue(row.getCell(j));
						if (StringUtils.isEmpty(cells)) {
							return new JsonResult(0, msg + "为空");
						}
					}
				}
			} catch (NullPointerException e) {
				return new JsonResult(0, msg + "为空");
			}
			List<EsaleIntercept> interceptList = new ArrayList<>();
			for (int i = 2; i < rownum; i++) {
				row = sheet.getRow(i);
				EsaleIntercept intercept = new EsaleIntercept();
				// 遍历每行中的每列
				for (int j = 0; j < colnum; j++) {
					msg = i + 1 + "行" + (j + 1) + "列";
					cells = (String) ExcelUtils.getCellFormatValue(row.getCell(j));
					if (j==0){
						intercept.setInterceptName(cells);
					}/*else if (j == 1) {
						intercept.setInterceptNum(cells);
					}*/
					/*if (j == 0) {
						intercept.setId(Integer.parseInt(cells));
					} else if (j == 1) {
						intercept.setInterceptName(cells);
					} else if (j == 2) {
						intercept.setInterceptNum(cells);
					}*/
				}
//			int intFlag = (int)(Math.random() * 1000000000);
				/*String id = IdUtils.nextId("id");*/
//				intercept.setId(intFlag);
				intercept.setCreateDate(new Date());
				intercept.setUpdateDate(new Date());
				esaleInterceptMapper.insertSelective(intercept);
			}
		}
		return new JsonResult(1, "解析成功");
	}


	/**
	 * 数据导出
	 *
	 * @param intercept
	 * @param
	 * @return
	 */
	@Override
	public Workbook export(EsaleIntercept intercept) {
		/**
		 * 导出数据
		 */
		XSSFWorkbook wb = (XSSFWorkbook) generateExportTemplate();
		List<EsaleIntercept> interceptList = new ArrayList<>();
		if (null != intercept.getIds()) {
			String[] idList = intercept.getIds();
			for (String id : idList
			) {
				EsaleIntercept esaleIntercept = this.esaleInterceptMapper.selectByPrimaryKey(Integer.parseInt(id));

				interceptList.add(esaleIntercept);
			}
		}
		System.out.println("interceptListsize  ===  "+interceptList.size());
		Sheet sheet = wb.getSheetAt(0);
		Row row;// 创建行对象
		Cell cell;// 创建格子对象

		EsaleIntercept esaleIntercept = null;
		// 设置样式
		CellStyle cellStyle = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
		font.setFontName("宋体");// 字体
		font.setFontHeight(25);
		cellStyle.setFont(font);// 使用字体
		cellStyle.setWrapText(true); // 换行显示
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue("敏感词数据导出模板");
		cell.setCellStyle(cellStyle);
		// 遍历集合
		for (int i = 0; i < interceptList.size(); i++) {
			// 从第三行开始
			row = sheet.createRow(i + 2);
			esaleIntercept = interceptList.get(i);
			System.out.println("esaleIntercept ===== "+esaleIntercept);
			System.out.println("KetWordsExportTitles.values().length ===== "+KetWordsExportTitles.values().length);
			for (int j = 0; j < KetWordsExportTitles.values().length; j++) {

				// 创建单元格
				cell = row.createCell(j);
				if (j==0){
					if (esaleIntercept.getInterceptName()!=null) {
						cell.setCellValue(esaleIntercept.getInterceptName());
					}else {
						cell.setCellValue("");
					}
				}else if (j == 1) {
					if (esaleIntercept.getInterceptNum()!=null) {
						cell.setCellValue(esaleIntercept.getInterceptNum());
					}else {
						cell.setCellValue("");
					}
				}else if (j == 2) {
					if(esaleIntercept.getUpdateDate()!=null) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String format = simpleDateFormat.format(esaleIntercept.getUpdateDate());
						System.out.println("esaleIntercept.getUpdateDate() ==== "+format);
						cell.setCellValue(format);
					}else {
						System.out.println("====================");
						cell.setCellValue("");
					}
				}
				/*

				if (j == 0) {
					// 设置值
					cell.setCellValue(esaleIntercept.getId());
				} else if (j == 1) {
					cell.setCellValue(esaleIntercept.getInterceptName());
				} else if (j == 2) {
					cell.setCellValue(esaleIntercept.getInterceptNum());
				}*/

			}
		}
		return wb;
	}
}
