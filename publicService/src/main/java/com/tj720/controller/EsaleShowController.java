package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleShow;
import com.tj720.service.EsaleShowService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/esaleShow")
public class EsaleShowController  {
	@Autowired
	private EsaleShowService esaleShowservice;


	/**
	 * @Author 刘修
	 * @Description 查询展览列表
	 * @return
	 */
	@RequestMapping("getShowList")
	@ResponseBody
	@AuthPassport
	public JSONObject getShowList(String type , String createDate, String orderBy,
									   @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception {
		return esaleShowservice.getShowList(type,createDate,orderBy,size,currentPage);
	}


	/**
	 * 新增或者修改展览信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveShow")
	@ResponseBody
	@AuthPassport
	public JsonResult saveShow(EsaleShow info,String picids,String delpicids) throws Exception{
		return esaleShowservice.saveShow(info,picids,delpicids);
	}

	@RequestMapping("deleteShow")
	@ResponseBody
	@AuthPassport
	public JsonResult deleteShow(String id) throws Exception{
		return esaleShowservice.deleteShow(id);
	}

}
