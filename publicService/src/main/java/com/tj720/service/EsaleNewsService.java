package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleNews;

public interface EsaleNewsService {

    JsonResult getNewsList(String departId, Integer currentPage);

    JsonResult saveNews(EsaleNews info, String picids, String delpicids);

    JsonResult sayGood(String id, String isGood);

    JsonResult percent();

}
