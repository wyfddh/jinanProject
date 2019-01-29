package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleShow;
import net.sf.json.JSONObject;

public interface EsaleShowService {

    JSONObject getShowList(String type , String createDate,  String orderBy, Integer size, Integer currentPage) throws Exception;

    JsonResult saveShow(EsaleShow info, String picids, String delpicids) throws  Exception;

    JsonResult deleteShow(String id) throws  Exception;

}
