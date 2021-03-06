package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleMuseumMapper;
import com.tj720.model.EsaleMuseum;
import com.tj720.model.EsaleMuseumWithBLOBs;
import com.tj720.model.common.MipArea;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.EsaleMuseumService;
import com.tj720.service.MipAreaService;
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
public class EsaleMuseumServiceImpl implements EsaleMuseumService {

    @Autowired
    private EsaleMuseumMapper esaleMuseumMapper;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private MipAreaService mipAreaService;

    @Override
    /**
     * @Author 刘修
     * @Description  
     * @Param key  搜索条件
     * @param level  博物馆级别
     * @param recommend  推荐
     * @param orderBy  排序
     * @param size
     * @param currentPage
     * @return net.sf.json.JSONObject
     **/
    public JSONObject getBaseInfoList(String key,  String museumType,String orderBy, Integer size, Integer currentPage) {

        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(Tools.getUserId())) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录异常");
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
            return jsonObject;
        }

        Page page = new Page();
        page.setSize(size);
        page.setCurrentPage(currentPage);

        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(key)) {
            map.put("key",key);
        }
        if (StringUtils.isNotBlank(museumType)) {
            map.put("museumType",museumType);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            map.put("orderBy",orderBy);
        } else {
            //默认1
            map.put("orderBy",1);
        }
        //符合条件总数
        Integer count = esaleMuseumMapper.countBaseInfoList(map);

        page.setAllRow(count);
        Integer start = page.getStart();
        Integer end = start + page.getSize();
        map.put("start",start);
        map.put("end",end - start);
        //查询分页数据
        List<EsaleMuseumWithBLOBs> list = esaleMuseumMapper.getBaseInfoList(map);
        for (EsaleMuseumWithBLOBs esaleMuseumWithBLOBs : list) {
            //获取图片url
            List<MipAttachment> picList = new ArrayList<MipAttachment>();
            esaleMuseumWithBLOBs.setMainPicUrl("");
            if(StringUtils.isNotBlank(esaleMuseumWithBLOBs.getPictureids())){
                picList = pictureService.getPicList(esaleMuseumWithBLOBs.getPictureids());
                for (MipAttachment mipAttachment : picList) {
                    if (mipAttachment.getIsMain().equals("1")) {
                        esaleMuseumWithBLOBs.setMainPicUrl(mipAttachment.getAttPath());
                        break;
                    }
                }
            }
            esaleMuseumWithBLOBs.setPicList(picList);
        }
        String jsonString = JSON.toJSONString(list);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", page.getAllRow());
        jsonObject.put("data", jsonString);

        return jsonObject;
    }

    @Override
    public JSONObject getBaseInfoAllList(String key,  String museumType) {

        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(Tools.getUserId())) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录异常");
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
            return jsonObject;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(key)) {
            map.put("key",key);
        }
        if (StringUtils.isNotBlank(museumType)) {
            map.put("museumType",museumType);
        }
        //查询分页数据
        List<EsaleMuseumWithBLOBs> list = esaleMuseumMapper.getBaseInfoAllList(map);
        for (EsaleMuseumWithBLOBs esaleMuseumWithBLOBs : list) {
            //获取图片url
            List<MipAttachment> picList = new ArrayList<MipAttachment>();
            esaleMuseumWithBLOBs.setMainPicUrl("");
            if(StringUtils.isNotBlank(esaleMuseumWithBLOBs.getPictureids())){
                picList = pictureService.getPicList(esaleMuseumWithBLOBs.getPictureids());
                for (MipAttachment mipAttachment : picList) {
                    if (mipAttachment.getIsMain().equals("1")) {
                        esaleMuseumWithBLOBs.setMainPicUrl(mipAttachment.getAttPath());
                        break;
                    }
                }
            }
            esaleMuseumWithBLOBs.setPicList(picList);
        }
        String jsonString = JSON.toJSONString(list);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", jsonString);

        return jsonObject;
    }

    /**
     * @Author 刘修
     * @Description  
     * @Param
     * @return void
     **/
    @Override
    public JsonResult museumBaseInfoSave(EsaleMuseumWithBLOBs esaleMuseumWithBLOBs, String isMain, String picids, String delpicids) {
        JsonResult jsonResult = null;
        try {
            /*if (esaleMuseumWithBLOBs.getUpId().equals("0")){
                esaleMuseumWithBLOBs.setUpId(esaleMuseumWithBLOBs.getId());
            }*/
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(picids)) {
                return new JsonResult("200000");
            }
            if (StringUtils.isNotBlank(userId)) {
                //设置主图和删除图片
                if (StringUtils.isNotBlank(isMain)) {
                    try {
                        Boolean flag = pictureService.setMain(picids, isMain, delpicids);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new JsonResult("111116");
                    }
                } else {
                    return new JsonResult("200001");
                }
                //新增
                if (StringUtils.isBlank(esaleMuseumWithBLOBs.getId())) {
                    String nextId = IdUtils.nextId(esaleMuseumWithBLOBs);
                    esaleMuseumWithBLOBs.setId(nextId);
                    if (null == esaleMuseumWithBLOBs.getUpId() || esaleMuseumWithBLOBs.getUpId().equals("")) {
                        esaleMuseumWithBLOBs.setMuseumType("1");
                    } else {
                        esaleMuseumWithBLOBs.setMuseumType("2");
                    }
                    //新建数据默认2上线
                    esaleMuseumWithBLOBs.setPictureids(picids);
                    esaleMuseumWithBLOBs.setDataState("1");
                    esaleMuseumWithBLOBs.setCreateBy(userId);
                    esaleMuseumWithBLOBs.setCreateDate(new Date());
                    esaleMuseumWithBLOBs.setUpdateBy(userId);
                    esaleMuseumWithBLOBs.setUpdateDate(new Date());
                    esaleMuseumMapper.insert(esaleMuseumWithBLOBs);
                    //修改
                } else {
                    if (null == esaleMuseumWithBLOBs.getUpId() || esaleMuseumWithBLOBs.getUpId().equals("")) {
                        esaleMuseumWithBLOBs.setMuseumType("1");
                    } else {
                        esaleMuseumWithBLOBs.setMuseumType("2");
                    }
                    esaleMuseumWithBLOBs.setPictureids(picids);
                    esaleMuseumWithBLOBs.setUpdateBy(userId);
                    esaleMuseumWithBLOBs.setUpdateDate(new Date());
                    esaleMuseumMapper.updateByPrimaryKeySelective(esaleMuseumWithBLOBs);

                }
                jsonResult = new JsonResult(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    @Override
    public  JsonResult museumBaseInfoDelete(String id, String dataState){
        JsonResult jsonResult = null;
        String userId = Tools.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(dataState)) {
                EsaleMuseumWithBLOBs esaleMuseumWithBLOBs = esaleMuseumMapper.selectByPrimaryKey(id);
                if (esaleMuseumWithBLOBs != null) {
                    if (dataState.equals("0")) {
                        esaleMuseumWithBLOBs.setDataState("0");
                    }
                    esaleMuseumWithBLOBs.setUpdateBy(userId);
                    esaleMuseumWithBLOBs.setUpdateDate(new Date());
                    try {
                        esaleMuseumMapper.updateByPrimaryKeySelective((EsaleMuseumWithBLOBs) esaleMuseumWithBLOBs);
                        jsonResult = new JsonResult(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        jsonResult = new JsonResult("111116");
                    }

                } else {
                    jsonResult = new JsonResult("111116");
                }
            } else {
                jsonResult = new JsonResult("111116");
            }
        }

        return jsonResult;
    }


    @Override
    public EsaleMuseumWithBLOBs selectByPrimaryKey(String id) {
        return esaleMuseumMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<EsaleMuseum> getList() {
        return esaleMuseumMapper.getList();
    }

    @Override
    public JsonResult getAreaByMuseumId(String id) {

        EsaleMuseumWithBLOBs esaleMuseumWithBLOBs = esaleMuseumMapper.selectByPrimaryKey(id);
        String province = esaleMuseumWithBLOBs.getProvince();
        String city = esaleMuseumWithBLOBs.getCity();

        List<MipArea> cityList = mipAreaService.getAreaByPid(Integer.parseInt(province));
        List<MipArea> areaList = mipAreaService.getAreaByPid(Integer.parseInt(city));

        Map<String,List<MipArea>> map = new HashMap<String,List<MipArea>>();
        map.put("cityList",cityList);
        map.put("areaList",areaList);

        return new JsonResult(1,map);
    }

}
