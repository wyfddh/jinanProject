package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleShowMapper;
import com.tj720.model.EsaleShow;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.EsaleShowService;
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
public class EsaleShowServiceImpl implements EsaleShowService {
	@Autowired
	private EsaleShowMapper esaleShowMapper;

	@Autowired
	private PictureService pictureService;

	@Override
	public JSONObject getShowList(String type , String createDate,  String orderBy, Integer size,
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
			if (StringUtils.isNotBlank(type)) {
				map.put("type",type);
			}
			System.out.println("createDate -----------  "+createDate);
			if (StringUtils.isNotBlank(createDate)) {
				map.put("createDate",createDate);
			}
			if (StringUtils.isNotBlank(orderBy)) {
				//1:更新时间 2：创建时间
				map.put("orderBy",orderBy);
			} else {
				//默认1
				map.put("orderBy",1);
			}
			//符合条件总数
			Integer count = esaleShowMapper.countShowList(map);
			System.out.println("count ====  "+count);
			page.setAllRow(count);
			map.put("start",page.getStart());
			map.put("end",page.getSize());
			//查询分页数据
			List<EsaleShow> list = esaleShowMapper.selectShowList(map);
			for(EsaleShow info :list){
				List<MipAttachment> picList = new ArrayList<MipAttachment>();
				if(StringUtils.isNotBlank(info.getPagePictureid())){
					picList = pictureService.getPicList(info.getPagePictureid());
					if(null != picList && picList.size() != 0){
						info.setPageUrl(picList.get(0).getAttPath());
					}
					picList.clear();
				}
				//获取图片url
				if(StringUtils.isNotBlank(info.getShowPictureids())){
					picList = pictureService.getPicList(info.getShowPictureids());
				}
				info.setPicList(picList);


				System.out.println("info.getCreateDate() ==== "	+info.getCreateDate());
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
	public JsonResult saveShow(EsaleShow info,String picids,String delpicids) throws  Exception{

		try {
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			if(StringUtils.isBlank(info.getShowName())){
				return new JsonResult(0,"展览名称不能为空");
			}
			if(StringUtils.isBlank(info.getPagePictureid())){
				return new JsonResult(0,"封面不能为空");
			}
			if(StringUtils.isBlank(info.getShowDescription())){
				return new JsonResult(0,"详情介绍不能为空");
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

			info.setShowPictureids(picids);
			System.out.println("type   ====  "+info.getType());
			if(StringUtils.isNotBlank(info.getId())){
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				esaleShowMapper.updateByPrimaryKeySelective(info);
			}else{
				info.setCreateBy(userId);
				info.setCreateDate(new Date());
				info.setUpdateBy(userId);
				info.setUpdateDate(new Date());
				info.setDataState("1");
				info.setId(IdUtils.nextId(info));
				esaleShowMapper.insert(info);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(0,"系统异常");
		}
		return new JsonResult(1,info);
	}

	@Override
	public JsonResult deleteShow(String id) throws  Exception{

		try {
			if(StringUtils.isBlank(id)){
				return new JsonResult(0,"参数错误");
			}
			String userId = Tools.getUserId();
			if(StringUtils.isBlank(userId)){
				return new JsonResult(0,"未登录");
			}
			EsaleShow info = new EsaleShow();
			info.setId(id);
			info.setDataState("0");
			info.setUpdateBy(userId);
			info.setUpdateDate(new Date());
			int num = esaleShowMapper.updateByPrimaryKeySelective(info);
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
