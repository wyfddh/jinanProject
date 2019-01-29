package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleDigitalshowMapper;
import com.tj720.model.EsaleDigitalshow;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.EsaleDigitalshowService;
import com.tj720.service.PictureService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EsaleDigitalshowServiceImpl implements EsaleDigitalshowService {
	@Autowired
	private EsaleDigitalshowMapper esaleDigitalshowMapper;

	@Autowired
	private PictureService pictureService;

	@Override
	public JSONObject getDigitalshowList(String key , String museumId ,  String orderBy, Integer size,
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
			if (StringUtils.isNotBlank(museumId)) {
				map.put("museumId",museumId);
			}
			if (StringUtils.isNotBlank(orderBy)) {
				//1:更新时间 2：创建时间
				map.put("orderBy",orderBy);
			} else {
				//默认1
				map.put("orderBy",1);
			}
			//符合条件总数
			Integer count = esaleDigitalshowMapper.countDigitalshow(map);
			page.setAllRow(count);
			map.put("start",page.getStart());
			map.put("end",page.getSize());
			//查询分页数据
			List<EsaleDigitalshow> list = esaleDigitalshowMapper.selectDigitalshowList(map);
			for(EsaleDigitalshow info :list){
				List<MipAttachment> picList = new ArrayList<MipAttachment>();
				if(StringUtils.isNotBlank(info.getPageId())){
					picList = pictureService.getPicList(info.getPageId());
					System.out.println(picList.size());
					if(null != picList && picList.size() != 0){
						info.setPageUrl(picList.get(0).getAttPath());
					}
					picList.clear();
				}
			}
			String jsonString = JSON.toJSONString(list);
			jsonObject.put("code", 0);
			jsonObject.put("msg", "");
			jsonObject.put("count", page.getAllRow());
			jsonObject.put("data", jsonString);
		}catch (Exception e){
			e.printStackTrace();
			jsonObject.put("code", 1);
			jsonObject.put("msg", e.getMessage());
			jsonObject.put("count", 0);
			jsonObject.put("data", null);
		}
		return jsonObject;
	}

	@Override
	public JsonResult saveDigitalshow(EsaleDigitalshow info, String picids, String delpicids) throws  Exception{

		try {
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			if(StringUtils.isBlank(picids
			)){
				return new JsonResult(0,"封面不能为空");
			}

			//设置主图和删除图片
			if (StringUtils.isNotBlank(delpicids)) {
				try {
					Boolean flag = pictureService.setMain(picids,picids,delpicids);
				} catch (Exception e) {
					e.printStackTrace();
					return new JsonResult(0,"保存异常");
				}
			}

			info.setPageId(picids);
			if(StringUtils.isNotBlank(info.getId())){
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				esaleDigitalshowMapper.updateByPrimaryKeySelective(info);

			}else{
				info.setCreateBy(userId);
				info.setCreateDate(new Date());
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				info.setDataState("1");
				info.setId(IdUtils.nextId(info));
				esaleDigitalshowMapper.insert(info);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(0,"系统异常");
		}
		return new JsonResult(1,info);
	}

	@Override
	public JsonResult deleteDigitalshow(String id) throws  Exception{

		try {
			if(StringUtils.isBlank(id)){
				return new JsonResult(0,"参数错误");
			}
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			EsaleDigitalshow info = new EsaleDigitalshow();
			info.setId(id);
			info.setDataState("0");
			info.setUpdateBy(userId);
			info.setUpdateDate(new Date());
			int num = esaleDigitalshowMapper.updateByPrimaryKeySelective(info);
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
