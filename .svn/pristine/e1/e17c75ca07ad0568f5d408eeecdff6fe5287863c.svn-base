package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.controller.springbeans.Config;
import com.tj720.service.EsaleWorkBenchService;
import com.tj720.service.impl.EsaleSysMenuServiceImpl;
import com.tj720.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/esaleWorkBenchData")
public class EsaleWorkBenchDataController {
	@Autowired
	private EsaleWorkBenchService esaleWorkBenchService;

	@Autowired
	private Config config;

	@Autowired
	private EsaleSysMenuServiceImpl esaleSysMenuService;

	/**
	 * 从config.properties中获取跳转链接
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUrl")
	@ResponseBody
	@AuthPassport
	public JsonResult getUrl(){
		Map<String,String> map = new HashMap<>();
		//藏品管理
		map.put("collectionUrl",config.getInterfaceCollectPath());
		map.put("syncJurisdictionUrl",config.getSyncJurisdictionPath());
		map.put("digitalAssetsUrl",config.getDigitalAssetsPath());
		map.put("publicServerUrl",config.getPublicServerPath());
		map.put("addLogUrl",config.getAddLogPath());
		return new JsonResult(1,map);
	}

	/**
	 * 工作台数据统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("globalStatistics")
	@ResponseBody
	@AuthPassport
	public JsonResult globalStatistics(){
		return esaleWorkBenchService.globalStatistics();
	}

	/**
	 * 工作台事项数据统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTasks")
	@ResponseBody
	@AuthPassport
	public JsonResult getTasks(){
		return esaleWorkBenchService.getTasks();
	}

	/**
	 * 工作台快捷菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMenu")
	@ResponseBody
	@AuthPassport
	public JsonResult getMenu(){
		return esaleWorkBenchService.getMenu();
	}

	/**
	 * 角色对应的全部菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRoleMenu")
	@ResponseBody
	@AuthPassport
	public JsonResult getRoleMenu(){
		String userId = Tools.getUserId();
		if (StringUtils.isBlank(userId)) {
			return new JsonResult(0, "登录异常");
		}
		return esaleSysMenuService.getAllMenuByUser();
	}

	/**
	 * 保存用户菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveMenu")
	@ResponseBody
	@AuthPassport
	public JsonResult saveMenu(String menuIds){
		return esaleWorkBenchService.saveMenu(menuIds);
	}
	
}
