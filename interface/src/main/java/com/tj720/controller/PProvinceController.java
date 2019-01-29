package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.*;
import com.tj720.service.*;
import com.tj720.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PC端首页接口
 * Created by caiming on 2018/9/26.
 */
@RestController
@RequestMapping("p/province")
public class PProvinceController extends BaseController {
    @Autowired
    private IMuseumBaseInfoService museumBaseInfoService;
    @Autowired
    private ICollectionInfoService collectionInfoService;
    @Autowired
    private IMuseumShowService museumShowService;

    /**
     * 功能描述: PC端省份首页接口
     * @param: [provice]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: MyPC
     * @date: 2018/9/27 9:38
     */
    @RequestMapping(value="getIndexData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getIndexData(String province) throws Exception{
        if(StringUtils.isBlank(province)){
            JsonResult jsonResult = new JsonResult("112002");
            return jsonResult.getJsonpString("cb");
        }

        Map<String, Object> data = new HashMap<>();

        String provinceIdByPinying = museumBaseInfoService.getProvinceIdByPinying(province);
        data.put("id", provinceIdByPinying);

        Page page = new Page();
        page.setSize(1999999999);
        page.setCurrentPage(1);

        //获取当前省份，博物馆列表
        List<MuseumBaseInfoDto> recommendMuseumList = museumBaseInfoService.getRecommendMuseumPageByProviceOrCity(province, page);
        data.put("list", recommendMuseumList);

        //获取当前省份，精彩推荐列表
        List<MuseumBaseInfoDto> jctjMuseumList = museumBaseInfoService.getJctjMuseumList(province, 3);
        data.put("jctjMuseumList", jctjMuseumList);

        //热门展览
        List<MuseumShowDto> museumShowListByProvice = museumShowService.getMuseumShowListByProvice(province, 4);
        data.put("museumShowList", museumShowListByProvice);

        //国宝藏品
        List<CollectionInfoDto> collectionInfoListByProvice = collectionInfoService.getCollectionInfoListByProvice(province, 6);
        data.put("collectionList", collectionInfoListByProvice);

        JsonResult jsonResult = new JsonResult(1, data, page);
        return jsonResult.getJsonpString("cb");
    }

}
