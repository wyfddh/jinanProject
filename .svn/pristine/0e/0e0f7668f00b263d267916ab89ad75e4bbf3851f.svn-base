package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PCEsaleConllectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("pc/conllection")
public class PCConllectionController {

    @Autowired
    private PCEsaleConllectionService pcEsaleConllectionService;

    /**
     * 我的收藏
     * @param uid
     * @param size
     * @param currentPage
     * @return
     */
    @RequestMapping(value="getMyCollection", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getMyCollection(String uid, @RequestParam(defaultValue = "12") Integer size,
                                      @RequestParam(defaultValue = "1") Integer currentPage){
        return pcEsaleConllectionService.getCollectionsByUid(uid, size, currentPage);

    }

    /**
     * 取消收藏
     */
    @RequestMapping(value="cancelCollection", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult cancelCollection(String uid, String cid){
        return pcEsaleConllectionService.cancelCollection(uid,cid);
    }

    /**
     * 用户添加收藏
     */
    @RequestMapping(value="userCollection", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult userCollection(String uid, String cid){
        return pcEsaleConllectionService.userCollection(uid, cid);
    }


    /**
     * 热门推荐收藏
     * @return
     */
    @RequestMapping(value="recommendCollection", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult recommendCollection(String museumId){
        return pcEsaleConllectionService.getRecommend(museumId);
    }

    /**
     * 藏品列表
     * @return
     */
    @RequestMapping(value="getListData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getListData(String museumId,String key,String collectionType,String collectionYear,@RequestParam(defaultValue="1") Integer currentPage,
                                  @RequestParam(defaultValue="12")Integer size){
        return pcEsaleConllectionService.getCollectionInfoList(museumId,key,collectionType,collectionYear,currentPage,size);
    }

    /**
     * 藏品详情
     * @return
     */
    @RequestMapping(value="getCollectionDetail", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getCollectionDetail(String id ,String userId){
        return pcEsaleConllectionService.getCollectionDetail(id,userId);
    }



}
