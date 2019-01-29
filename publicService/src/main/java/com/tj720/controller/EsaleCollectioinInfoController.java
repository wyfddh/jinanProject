package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleMuseum;
import com.tj720.service.EsaleCollectionInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/20.
 */
@RestController
@RequestMapping("/esaleCollection")
public class EsaleCollectioinInfoController {

    @Autowired
    private EsaleCollectionInfoService esaleCollectionInfoService;

    @RequestMapping("collectionInfoList")
    @ResponseBody
    @AuthPassport
    public JSONObject collectionInfoList(String key , String collectionType , String collectionYear ,String orderBy,
                                         @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size){
        return esaleCollectionInfoService.getCollectionInfoList(key,collectionType,collectionYear,orderBy,currentPage,size);

    }

    @RequestMapping("modifyRecommend")
    @ResponseBody
    @AuthPassport
    public JsonResult modifyRecommend(String id, String recommendStatus){
        return esaleCollectionInfoService.updateRecommend( id, recommendStatus);
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    @AuthPassport
    public JsonResult updateStatus(String id, String dataStatus){
        return esaleCollectionInfoService.updateStatus( id, dataStatus);
    }

    @RequestMapping("getCollectionListByType")
    @AuthPassport
    public JsonResult getCollectionListByType(String culCategory,String curName) {
        return esaleCollectionInfoService.getCollectionListByType(culCategory,curName);
    }

}
