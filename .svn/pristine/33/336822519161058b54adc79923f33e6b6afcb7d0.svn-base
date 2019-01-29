package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.IntroduceManager.LocalIntroduce;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.List;

public interface LocalIntroduceService{


    JSONObject getIntroduceList(String key, String dateRange,String recommend,String orderBy, Integer size, Integer currentPage) throws Exception;

    JsonResult saveIntroduce(LocalIntroduce info) throws  Exception;

    JsonResult deleteIntroduce(String id) throws  Exception;

    JsonResult modifyIntroduceRecommend(String recommendStatus, String recommendId, String recommendName) throws  Exception;
}
