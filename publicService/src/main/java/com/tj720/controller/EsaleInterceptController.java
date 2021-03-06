package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleIntercept;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.tj720.service.EsaleInterceptService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/interceptKeyWord")
public class EsaleInterceptController {
	@Autowired
	private EsaleInterceptService esaleinterceptservice;

	/**
	 * 查询敏感词列表
	 */
	@RequestMapping("/queryInterceptWords")
	@ResponseBody
	public JSONObject queryInterceptWords(String interceptName, String orderBy,
									   @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
		return esaleinterceptservice.queryInterceptWords(interceptName,orderBy,size,currentPage);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delInterceptWords")
	@ResponseBody
	public JsonResult delInterceptWords(@RequestParam("id") String id) {
		JsonResult jsonResult = esaleinterceptservice.delInterceptWords(id);
		return jsonResult;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("delAllInterceptWords")
	@ResponseBody
	public JsonResult delAllInterceptWordsv(EsaleIntercept intercept) {
		JsonResult jsonResult = esaleinterceptservice.delAllInterceptWords(intercept.getIds());
		return jsonResult;
	}

	/**
	 * 编辑
	 */
	@RequestMapping("updateInterceptWords")
	@ResponseBody
	public JsonResult updateInterceptWords(EsaleIntercept esaleIntercept) {

		JsonResult jsonResult = esaleinterceptservice.updateInterceptWords(esaleIntercept);

		return jsonResult;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("toUpdate")
	@ResponseBody
	public JsonResult toUpdate(String id) {

		EsaleIntercept esale = esaleinterceptservice.toUpdate(Integer.parseInt(id));
		return new JsonResult(1,esale);
	}

	/**
	 * 下载模板
	 */
	@RequestMapping("/downloadTemp")
	public void downloadTemp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 下载文件的默认名称
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode("敏感词数据导入模板.xlsx", "UTF-8"));
			// 创建Excel对象
			Workbook wb = esaleinterceptservice.generateTemplate();
			// 写出流
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文件下载失败");
		}
	}

	/**
	 * 上传模板
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadTemp")
	@ResponseBody
	public JsonResult uploadTemp(@RequestParam("file") MultipartFile file) {
		String filePath = "C:/upload/";
		try {
			if (file.isEmpty()) {
				return new JsonResult(0,"文件为空");
			}
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			if (!(".xls".equals(suffixName) || ".xlsx".equals(suffixName))) {
				return new JsonResult(0,"模板错误");
			}
			// 设置文件存储路径
			String path = filePath + fileName;
			File dest = new File(path);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			// 文件写入
			file.transferTo(dest);
			// return success("上传完毕");
			return esaleinterceptservice.parseExcel(path);
		} catch (FileNotFoundException e) {
			return  new JsonResult(0,"文件找不到或正在被另一个程序使用");
		} catch (IOException e) {
			return new JsonResult(0,"文件找不到或正在被另一个程序使用");
		}
	}

	/**
	 * 数据导出
	 *
	 * @throws Exception
	 */
	@RequestMapping("/exportData")
	public void exportData(EsaleIntercept intercept ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// 下载文件的默认名称
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode("数据导出.xlsx", "UTF-8"));// 默认Excel名称
			// 创建Excel对象
			Workbook wb = esaleinterceptservice.export(intercept);
			// 写出流
			wb.write(response.getOutputStream());
			/*return  new JsonResult(1);*/
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文件下载失败");
			/*return new JsonResult(0,"导出失败");*/
		}
	}




}
