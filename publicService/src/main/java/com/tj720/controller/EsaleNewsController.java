package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleNews;
import com.tj720.service.EsaleNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/esaleNews")
public class EsaleNewsController {
	@Autowired
	private EsaleNewsService esaleNewsService;

	@RequestMapping("getNewsList")
	@ResponseBody
	@AuthPassport
	public JsonResult getNewsList(String departId,@RequestParam(defaultValue = "1") Integer currentPage){
		return esaleNewsService.getNewsList(departId,currentPage);
	}

	/**
	 * 发布动态
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNews")
	@ResponseBody
	@AuthPassport
	public JsonResult saveNews(EsaleNews info, String picids, String delpicids){
		return esaleNewsService.saveNews(info,picids,delpicids);
	}

	/**
	 * 动态点赞
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sayGood")
	@ResponseBody
	@AuthPassport
	public JsonResult sayGood(String id,String isGood){
		return esaleNewsService.sayGood(id,isGood);
	}

	/**
	 * 动态统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("percent")
	@ResponseBody
	@AuthPassport
	public JsonResult percent(){
		return esaleNewsService.percent();
	}

	
	
}
