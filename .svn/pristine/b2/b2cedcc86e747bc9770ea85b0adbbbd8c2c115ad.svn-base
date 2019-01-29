package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleNewsMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.EsaleNews;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.EsaleNewsService;
import com.tj720.service.PictureService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EsaleNewsServiceImpl implements EsaleNewsService {
    @Autowired
    private EsaleNewsMapper esaleNewsMapper;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private SysUserMapper sysUserMapper;

    public JsonResult getNewsList(String departId, Integer currentPage) {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }

            Page page = new Page();
            page.setSize(5);
            page.setCurrentPage(currentPage);

            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(departId)) {
                map.put("departId", departId);
            }
            //符合条件总数
            Integer count = esaleNewsMapper.countNews(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            //查询分页数据
            List<EsaleNews> list = esaleNewsMapper.selectNewsList(map);
            for (EsaleNews info : list) {
                List<MipAttachment> picList = new ArrayList<MipAttachment>();
                List<String> idList = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                if (StringUtils.isNotBlank(info.getPictureids())) {
                    picList = pictureService.getPicList(info.getPictureids());
                }
                if (StringUtils.isNotBlank(info.getPraiseUserId())) {
                    if (info.getPraiseUserId().indexOf(userId) != -1) {
                        info.setIsGood("1");
                    }
                    idList = Arrays.asList(info.getPraiseUserId().split(","));
                    nameList = sysUserMapper.getUserNameListById(idList);
                }
                info.setPicList(picList);
                info.setPraiseNameList(nameList);
            }
            return new JsonResult(1, list, page);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

    @Override
    public JsonResult saveNews(EsaleNews info, String picids, String delpicids) {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "未登录");
            }
            if (StringUtils.isBlank(info.getContent())) {
                return new JsonResult(0, "动态内容不能为空");
            }

            //设置主图和删除图片
            if (StringUtils.isNotBlank(delpicids)) {
                try {
                    Boolean flag = pictureService.setMain(picids, picids, delpicids);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new JsonResult(0, "保存异常");
                }
            }

            info.setPictureids(picids);
            info.setUserId(userId);
            if (StringUtils.isNotBlank(info.getId())) {
                info.setUpdateBy(userId);
                info.setUpdateDate(new Date());
                esaleNewsMapper.updateByPrimaryKeySelective(info);
            } else {
                info.setCreateBy(userId);
                info.setCreateDate(new Date());
                info.setUpdateBy(userId);
                info.setUpdateDate(new Date());
                info.setDataState("1");
                info.setId(IdUtils.nextId(info));
                esaleNewsMapper.insert(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常");
        }
        return new JsonResult(1, info);
    }

    @Override
    public JsonResult sayGood(String id, String isGood) {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "未登录");
            }
            if (StringUtils.isBlank(id)) {
                return new JsonResult(0, "参数错误");
            }
            if (StringUtils.isBlank(isGood)) {
                return new JsonResult(0, "参数错误");
            }
            EsaleNews info = esaleNewsMapper.selectByPrimaryKey(id);
            if (isGood.equals("1")) {
                info.setPraiseUserId(info.getPraiseUserId().replace(userId + ",", ""));
            } else {
                info.setPraiseUserId(null == info.getPraiseUserId() ? "" : info.getPraiseUserId() + userId + ",");
            }
            info.setUserId(userId);
            info.setUpdateBy(userId);
            //点赞不更新时间
//            info.setUpdateDate(new Date());
            esaleNewsMapper.updateByPrimaryKeySelective(info);
            return new JsonResult(1, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常");
        }
    }

    @Override
    public JsonResult percent() {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }
            List<Map<String, String>> mapList = esaleNewsMapper.getPercent();
            List<String> departList = esaleNewsMapper.getDepartList();
            Map<String, String> percentMap = new HashMap<>();
            StringBuffer departsBuffer = new StringBuffer();
            for (Map<String, String> map : mapList) {
                percentMap.put(map.get("key"),map.get("value"));
                departsBuffer.append(map.get("key"));
                departsBuffer.append(",");
            }
            String departs = departsBuffer.toString();
            for (String s : departList) {
                if (departs.indexOf(s) == -1) {
                    percentMap.put(s, "0");
                }
            }
            return new JsonResult(1, percentMap);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

}
