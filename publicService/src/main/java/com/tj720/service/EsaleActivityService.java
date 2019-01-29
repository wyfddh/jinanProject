package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleActivity;
import net.sf.json.JSONObject;

public interface EsaleActivityService {

    JSONObject getActivityList(String key , String type , String status,  String orderBy, Integer size, Integer currentPage) throws Exception;

    JsonResult saveActivity(EsaleActivity info, String picids, String delpicids) throws  Exception;

    JsonResult deleteActivity(String id) throws  Exception;

}
