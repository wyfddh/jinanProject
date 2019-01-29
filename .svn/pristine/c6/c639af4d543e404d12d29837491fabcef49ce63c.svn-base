package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PCEsaleVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by chenshiya 2018-09-26
 */
@Controller
@RequestMapping("pc/esaleVideo")
public class PCEsaleVideoController {
	@Autowired
	private PCEsaleVideoService esaleVideoService;

	/**
	 * @Author csy
	 * @Description 查询视频列表
	 * @return
	 */
	@RequestMapping("getListData")
	@ResponseBody
	public JsonResult getListData() throws Exception{
		return esaleVideoService.getVideoList();
	}

	/**
	 *
	 * 功能描述: 更新视频查看次数
	 *
	 * @param: [videoId]
	 * @return: com.tj720.controller.framework.JsonResult
	 * @auther: caiming
	 * @date: 2018/12/8 16:46
	 */
	@RequestMapping("viewVideo")
	@ResponseBody
	public JsonResult viewVideo(String videoId) throws Exception{
		return esaleVideoService.viewVideo(videoId);
	}

}
