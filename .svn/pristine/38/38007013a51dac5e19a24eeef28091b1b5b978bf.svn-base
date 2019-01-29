package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleAssessActivityMapper;
import com.tj720.model.EsaleAssessActivity;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.EsaleAssessActivityService;
import com.tj720.service.PictureService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EsaleAssessActivityServiceImpl implements EsaleAssessActivityService {
	@Autowired
	private EsaleAssessActivityMapper esaleAssessActivityMapper;

	@Autowired
	private PictureService pictureService;

	@Override
	public JSONObject getAssessListById(String key, Integer currentPage, Integer size){
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
			//符合条件总数
			Integer count = esaleAssessActivityMapper.countAssess(map);
			page.setAllRow(count);
			map.put("start",page.getStart());
			map.put("end",page.getSize());
			//查询分页数据
			List<EsaleAssessActivity> list = esaleAssessActivityMapper.getAssessListById(map);

			for (EsaleAssessActivity esaleAssessActivity : list) {
				//获取图片url
				List<MipAttachment> picList = new ArrayList<MipAttachment>();
				esaleAssessActivity.setUserPicUrl("");
				if(StringUtils.isNotBlank(esaleAssessActivity.getUserPicId())){
					picList = pictureService.getPicList(esaleAssessActivity.getUserPicId());
					if(null !=picList && picList.size()==1){
						esaleAssessActivity.setUserPicUrl(picList.get(0).getAttPath());
					}
				}
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
	public JsonResult deleteAssessActivity(String id) throws  Exception{

		try {
			if(StringUtils.isBlank(id)){
				return new JsonResult(0,"参数错误");
			}
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			EsaleAssessActivity info = new EsaleAssessActivity();
			info.setId(id);
			info.setDataState("0");
			info.setUpdateBy(userId);
			info.setUpdateDate(new Date());
			int num = esaleAssessActivityMapper.updateByPrimaryKeySelective(info);
			if(num == 0){
				return new JsonResult(0,"删除失败");
			}
			return new JsonResult(1,"删除成功");
		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,"系统异常");
		}
	}
}
