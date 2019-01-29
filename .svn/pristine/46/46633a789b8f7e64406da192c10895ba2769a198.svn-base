package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.StringUtil;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.LocalIntroduceMapper;
import com.tj720.model.IntroduceManager.LocalIntroduce;
import com.tj720.service.LocalIntroduceService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocalIntroduceServiceImpl implements LocalIntroduceService {

	@Autowired
	private LocalIntroduceMapper _LocalIntroduceMapper;

	@Autowired
	private Config config;
	@Override
	public JSONObject getIntroduceList(String key, String dateRange,String recommend, String orderBy, Integer size,
									  Integer currentPage) throws Exception {

		JSONObject jsonObject = new JSONObject();
		try {
			if (StringUtils.isBlank(Tools.getUserId())) {
				throw new Exception("登录异常");
			}

			Page page = new Page();
			page.setSize(size);
			page.setCurrentPage(currentPage);

			Map<String,Object> map = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(key)) {
				map.put("key",key);
			}
			if (StringUtils.isNotBlank(recommend)) {
				//1：首页推荐
				map.put("recommend",recommend);
			}
			if (StringUtils.isNotBlank(orderBy)) {
				//1:更新时间 2：创建时间
				map.put("orderBy",orderBy);
			} else {
				//默认1
				map.put("orderBy",1);
			}

			String searchDateStart = "";
			String searchDateEnd = "";

			if(StringUtil.isNotEmpty(dateRange)) {
				String[] searchDate = {};
				searchDate = dateRange.split("~");
				if(null != searchDate && searchDate.length > 0) {
					searchDateStart = null != searchDate[0] ? searchDate[0].trim() : "";
					searchDateEnd = null != searchDate[1] ? searchDate[1].trim() : "";
				}
				map.put("startDate",searchDateStart);
				map.put("endDate",searchDateEnd);
			}

			//符合条件总数
			Integer count = _LocalIntroduceMapper.countIntroduceList(map);
			page.setAllRow(count);
			map.put("start",page.getStart());
			map.put("end",page.getSize());
			//查询分页数据
			List<LocalIntroduce> list = _LocalIntroduceMapper.selectIntroduceList(map);
			for(LocalIntroduce introduce:list){
				String videoShowUrl = config.getRootUrl()+introduce.getVideoUrl();
				introduce.setVideoShowUrl(videoShowUrl);

				String provinceDes = introduce.getProvinceDes() == null?"":introduce.getProvinceDes();
				String cityDes = introduce.getCityDes() == null?"":introduce.getCityDes();
				String areaDes = introduce.getAreaDes() == null?"":introduce.getAreaDes();
				introduce.setIntroducePlace(provinceDes+cityDes+areaDes);
			}
			String jsonString = JSON.toJSONString(list);
			jsonObject.put("code", 0);
			jsonObject.put("msg", "");
			jsonObject.put("count", page.getAllRow());
			jsonObject.put("data", jsonString);
		}catch (Exception e){
			jsonObject.put("code", 1);
			jsonObject.put("msg", e.getMessage());
			jsonObject.put("count", 0);
			jsonObject.put("data", null);
		}
		return jsonObject;
	}

	@Override
	public JsonResult saveIntroduce(LocalIntroduce info) throws  Exception{

		try {
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			if(StringUtils.isBlank(info.getVideoName())){
				return new JsonResult(0,"视频名称不能为空");
			}

			if(StringUtils.isBlank(info.getVideoId())){
				return new JsonResult(0,"推介视频不能为空");
			}
			if(StringUtils.isNotBlank(info.getId())){
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				_LocalIntroduceMapper.updateByPrimaryKeyWithBLOBs(info);
			}else{
				info.setDataState("1");
				info.setPageRecommend("0");
				info.setCreateBy(userId);
				info.setCreateDate(new Date());
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				info.setId(IdUtils.nextId(info));
				_LocalIntroduceMapper.insert(info);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(0,"系统异常");
		}
		return new JsonResult(1,info);
	}

	@Override
	public JsonResult deleteIntroduce(String id) throws  Exception{

		try {
			if(StringUtils.isBlank(id)){
				return new JsonResult(0,"参数错误");
			}
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			LocalIntroduce info = new LocalIntroduce();
			info.setId(id);
			info.setDataState("0");
			info.setUpdateBy(userId);
			info.setUpdateDate(new Date());
			_LocalIntroduceMapper.updateByPrimaryKeySelective(info);
			return new JsonResult(1,"删除成功");
		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,"系统异常");
		}
	};

	@Override
	public JsonResult modifyIntroduceRecommend(String recommendStatus, String recommendId, String recommendName) throws  Exception{

		JsonResult jsonResult = new JsonResult();
		try {
			LocalIntroduce updateShow = new LocalIntroduce();
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				throw new Exception("登录异常");
			}
			if(StringUtils.isBlank(recommendId) || StringUtils.isBlank(recommendStatus) || StringUtils.isBlank(recommendName)){
				throw new Exception("数据异常");
			}
			if("pageRecommend".equals(recommendName)){
//				if("1".equals(recommendStatus)){
//					Integer countPage = _LocalIntroduceMapper.countPageRecommend();s
//					if(countPage >0){
//						throw new Exception("首页地方推介已达最大数量，请先取消其他地方推介再选择！");
//					}
//				}
				updateShow.setPageRecommend(recommendStatus);
			}
			updateShow.setId(recommendId);
			updateShow.setUpdateBy(userId);
			updateShow.setUpdateDate(new Date());
			_LocalIntroduceMapper.updateByPrimaryKeySelective(updateShow);
		}catch(Exception e){
			e.printStackTrace();
			jsonResult.setSuccess(0);
			jsonResult.setData(e.getMessage());
		}
		return jsonResult;
	};
}
