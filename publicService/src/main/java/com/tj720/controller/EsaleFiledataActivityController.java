package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.service.EsaleFiledataActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("esaleFiledataActivity")
public class EsaleFiledataActivityController {
	@Autowired
	private EsaleFiledataActivityService esaleFiledataActivityService;

	@RequestMapping(value = "upload")
	@ResponseBody
	public JsonResult upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file,
							 String id,  HttpServletRequest request) {
		JsonResult saveAttachment = esaleFiledataActivityService.saveFileActivity(file, id);
		return saveAttachment;
	}

	@RequestMapping(value = "download")
	public void download(String id,HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");//设定请求字符编码
			esaleFiledataActivityService.download(id,response);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	}

	@RequestMapping(value = "deleteFile")
	@ResponseBody
	@AuthPassport
	public JsonResult deleteFile(String id) throws Exception {
		return  esaleFiledataActivityService.deleteFileActivity(id);
	}

	@RequestMapping(value = "renameFile")
	@ResponseBody
	@AuthPassport
	public JsonResult renameFile(String id,String fileRealname) throws Exception {
		return  esaleFiledataActivityService.renameFileActivity(id,fileRealname);
	}





}
