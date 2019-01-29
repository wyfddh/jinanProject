package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.uitoolkit.ToolkitStore;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleActivityMapper;
import com.tj720.dao.EsalePersonDiaryMapper;
import com.tj720.model.EsaleActivity;
import com.tj720.model.EsalePersonDiary;
import com.tj720.model.EsalePubUser;
import com.tj720.model.common.MipAttachment;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.service.EsaleActivityService;
import com.tj720.service.EsalePersonDiaryService;
import com.tj720.service.EsalePubUserService;
import com.tj720.service.PictureService;
import com.tj720.utils.*;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

@Service
public class EsalePersonDiaryServiceImpl implements EsalePersonDiaryService {
	@Autowired
	private EsalePersonDiaryMapper bsalePersonDiaryMapper;


	@Override
	public JsonResult insertOrUpdate(EsalePersonDiary personDiary) {
		String userId = Tools.getUserId();
		if(StringUtils.isBlank(userId)){
			return new JsonResult(0,"未登录");
		}

		String content = personDiary.getContent();
		if(StringUtils.isBlank(content)){
			return new JsonResult(0,"日志内容不能为空");
		}

		if(StringUtils.isNotBlank(personDiary.getId())){
			//编辑
			EsalePersonDiary esalePersonDiary = bsalePersonDiaryMapper.selectByPrimaryKey(personDiary.getId());
			esalePersonDiary.setContent(personDiary.getContent());
			esalePersonDiary.setUpdatetime(new Date());
			int i = bsalePersonDiaryMapper.updateByPrimaryKeyWithBLOBs(esalePersonDiary);
			if(i>0){
				return new JsonResult(1, "发布成功");
			}
		}else{
			//新增
			List<EsalePersonDiary> personTodayData = bsalePersonDiaryMapper.getPersonTodayData(userId);
			if(personTodayData != null && personTodayData.size() > 0 && StringUtils.isNotBlank(personTodayData.get(0).getId())){
				return new JsonResult(0,"请勿重复提交");
			}

			String id = IdUtils.nextId(new EsalePersonDiary());
			personDiary.setId(id);
			if (personDiary.getIssupply() == null) {
				personDiary.setCreattime(new Date());
			}
			personDiary.setUpdatetime(new Date());
			personDiary.setCreator(userId);
			//临时将orgid设置为空，登录做好后，会取登录的机构id
			personDiary.setOrgid("");
			int insert = bsalePersonDiaryMapper.insert(personDiary);
			if(insert > 0){
				return new JsonResult(1, "发布成功");
			}
		}
		return new JsonResult(0, "发布失败");
	}

	@Override
	public JsonResult getList(EsalePersonDiary personDiary, Page page) {
		try {
			int allRow = bsalePersonDiaryMapper.countList(personDiary);
			page.setAllRow(allRow);
			List<EsalePersonDiary> list = bsalePersonDiaryMapper.getList(personDiary, page.getStart(), page.getSize());
			return new JsonResult(1, list, page);
		}catch (Exception e){
			e.printStackTrace();
		}
        return new JsonResult(0, "系统异常");
	}

    @Override
    public JSONObject getDiaryList(EsalePersonDiary personDiary, Page page) {
        JSONObject jsonObject = new JSONObject();
        try {
            int allRow = bsalePersonDiaryMapper.countList(personDiary);
            page.setAllRow(allRow);
            List<EsalePersonDiary> list = bsalePersonDiaryMapper.getList(personDiary, page.getStart(), page.getSize());
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", JSON.toJSONString(list));
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "获取数据失败");
        jsonObject.put("count", 0);
        jsonObject.put("data", null);
        return jsonObject;
    }

    @Override
    public JsonResult deleteDiary(String id) {
        int i = bsalePersonDiaryMapper.deleteByPrimaryKey(id);
        if(i > 0){
            return new JsonResult(1,"删除成功");
        }
        return new JsonResult(0, "删除失败");
    }

    @Override
    public JsonResult getDiaryById(String id) {
        EsalePersonDiary personDiary = bsalePersonDiaryMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(personDiary.getContent())){
            return new JsonResult(1,personDiary);
        }
        return new JsonResult(0,null);
    }

	@Override
	public JsonResult getDataByMonth(String year, String month) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			int daysOfMonth = DateUtil.getDaysOfMonth(year, month);
			List<String> dayList = new ArrayList<>();
			for (int i=1; i<=daysOfMonth; i++){
				if(i < 10){
					dayList.add(year+"-"+(month.length()==1?"0"+month:month)+"-0"+i);
				}else{
					dayList.add(year+"-"+(month.length()==1?"0"+month:month)+"-"+i);
				}
			}
			if(dayList != null && dayList.size() > 0){
				List<Map<String, Object>> dataByMonth = bsalePersonDiaryMapper.getDataByMonth(dayList, Tools.getUserId());
				for (Map<String, Object> cdata: dataByMonth) {
					Map<String, Object> current = new HashMap<>();
					String content = (String)cdata.get("content");
					if(StringUtils.isNotBlank(content)){
						current.put("hasDone", true);
						current.put("content", content);
					}else{
						current.put("hasDone", false);
						current.put("content", "");
					}
					result.add(current);
				}
			}
			return new JsonResult(1, result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JsonResult("111116");
	}


}
