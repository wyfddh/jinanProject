package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleInfo;
import net.sf.json.JSONObject;

public interface EsaleInfoService {

    JSONObject getInfoList(String type ,String createDate ,  String orderBy, Integer size, Integer currentPage) throws Exception;

    JsonResult saveInfo(EsaleInfo info, String picids, String delpicids) throws  Exception;

    JsonResult deleteInfo(String id) throws  Exception;

}
