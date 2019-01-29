package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

public interface EsaleUserActivityService {

    JSONObject getSignListById(String key, String activityStates, Integer currentPage, Integer size);

    JsonResult operationUserActivity(String id, int operate) throws Exception;

    JSONObject getActivityRecordByUserId(String userId, Integer currentPage, Integer size);

    JsonResult adminActivitySign(Map<String,String> map);

    Workbook export(String key , String activityStates) throws Exception;
}
