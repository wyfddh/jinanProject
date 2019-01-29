package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleDigitalshow;
import com.tj720.service.EsaleDigitalshowService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/esaleDigitalshow")
public class EsaleDigitalshowController  {
	@Autowired
	private EsaleDigitalshowService esaleDigitalshowService;


	/**
	 * @Author 刘修
	 * @Description 查询数字展厅列表
	 * @return
	 */
	@RequestMapping("getDigitalshowList")
	@ResponseBody
	@AuthPassport
	public JSONObject getDigitalshowList(String key , String museumId , String orderBy,
									   @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception {
		return esaleDigitalshowService.getDigitalshowList(key,museumId,orderBy,size,currentPage);
	}


	/**
	 * 新增或者修改数字展厅信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveDigitalshow")
	@ResponseBody
	@AuthPassport
	public JsonResult saveDigitalshow(EsaleDigitalshow info, String picids, String delpicids) throws Exception{
		return esaleDigitalshowService.saveDigitalshow(info,picids,delpicids);
	}

	@RequestMapping("deleteDigitalshow")
	@ResponseBody
	@AuthPassport
	public JsonResult deleteDigitalshow(String id) throws Exception{
		return esaleDigitalshowService.deleteDigitalshow(id);
	}


}
