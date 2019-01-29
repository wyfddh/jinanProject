package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.IntroduceManager.LocalIntroduce;
import com.tj720.service.LocalIntroduceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by chenshiya 2018-09-26
 */
@Controller
@RequestMapping("/introduce")
public class LocalIntroduceController{
	@Autowired
	private LocalIntroduceService localintroduceservice;

	/**
	 * @Author csy
	 * @Description 查询推介列表
	 * @param key
	 * @param dateRange
	 * @param orderBy
	 * @param currentPage
	 * @param size
	 * @return
	 */
	@RequestMapping("getIntroduceList")
	@ResponseBody
	@AuthPassport
	public JSONObject getIntroduceList(String key, String dateRange,  String recommend ,String orderBy,
				@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
		return localintroduceservice.getIntroduceList(key,dateRange,recommend,orderBy,size,currentPage);
	}

	/**
	 * 新增或者修改推介信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveIntroduce")
	@ResponseBody
	@AuthPassport
	public JsonResult saveIntroduce(LocalIntroduce info) throws Exception{
		return localintroduceservice.saveIntroduce(info);
	}

	/**
	 * 删除推介信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteIntroduce")
	@ResponseBody
	@AuthPassport
	public JsonResult deleteShow(String id) throws Exception{
		return localintroduceservice.deleteIntroduce(id);
	}

	/**
	 * 修改推荐项状态
	 * @param recommendStatus
	 * @param recommendId
	 * @param recommendName
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("modifyIntroduceRecommend")
	@ResponseBody
	@AuthPassport
	public JsonResult modifyRecommend(String recommendStatus, String recommendId, String recommendName) throws Exception{
		return localintroduceservice.modifyIntroduceRecommend(recommendStatus,recommendId,recommendName);
	}
}
