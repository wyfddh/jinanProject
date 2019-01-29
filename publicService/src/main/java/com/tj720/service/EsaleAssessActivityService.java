package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import net.sf.json.JSONObject;

public interface EsaleAssessActivityService {

    JSONObject getAssessListById(String key,  Integer currentPage, Integer size);

    JsonResult deleteAssessActivity(String id) throws  Exception;



    }
