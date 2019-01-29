package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleInfo;
import com.tj720.service.EsaleInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/esaleInfo")
public class EsaleInfoController  {
	@Autowired
	private EsaleInfoService esaleInfoService;


	/**
	 * @Author 刘修
	 * @Description 查询资讯列表
	 * @return
	 */
	@RequestMapping("getInfoList")
	@ResponseBody
	@AuthPassport
	public JSONObject getInfoList(String type ,String createDate, String orderBy,
									   @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception {
		return esaleInfoService.getInfoList(type,createDate,orderBy,size,currentPage);
	}


	/**
	 * 新增或者修改资讯信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveInfo")
	@ResponseBody
	@AuthPassport
	public JsonResult saveInfo(EsaleInfo info, String picids, String delpicids) throws Exception{
		return esaleInfoService.saveInfo(info,picids,delpicids);
	}

	@RequestMapping("deleteInfo")
	@ResponseBody
	@AuthPassport
	public JsonResult deleteInfo(String id) throws Exception{
		return esaleInfoService.deleteInfo(id);
	}


}
