package com.tj720.service;

import com.tj720.controller.framework.JsonResult;

public interface PCEsaleConllectionService {

    /**
     * 查询用户收藏列表
     * @return
     */
    JsonResult getCollectionsByUid(String uid, Integer size, Integer currentPage);

    /**
     * 查询用户收藏列表
     * @return
     */
    JsonResult getRecommend(String museumId);

    /**
     * 查询藏品列表
     * @return
     */
    JsonResult getCollectionInfoList(String museumId,String key,String collectionType,String collectionYear, Integer currentPage,Integer size);

    /**
     * 查询藏品详情
     * @return
     */
    JsonResult getCollectionDetail(String id,String userId);

    /**
     * 取消收藏
     * @param uid
     * @param id
     * @return
     */
    JsonResult cancelCollection(String uid, String id);

    /**
     * 用户进行收藏
     */
    JsonResult userCollection(String uid, String id);

}
