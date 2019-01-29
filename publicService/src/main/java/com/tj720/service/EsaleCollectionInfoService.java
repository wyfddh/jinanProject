package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleCollectionInfo;
import net.sf.json.JSONObject;

public interface EsaleCollectionInfoService {

    JSONObject getCollectionInfoList(String key , String collectionTypeCode , String collectionYearCode , String orderBy, Integer currentPage,  Integer size);

    JsonResult updateRecommend(String id, String recommendStatus);

    JsonResult updateStatus(String id, String dataStatus);

    EsaleCollectionInfo queryColl(String id);

    JsonResult getCollectionListByType(String type,String curName);

}
