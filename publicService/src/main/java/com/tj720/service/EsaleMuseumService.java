package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleMuseum;
import com.tj720.model.EsaleMuseumWithBLOBs;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EsaleMuseumService {

    JSONObject getBaseInfoList(String key,  String museumType,String orderBy, Integer size, Integer currentPage);

    JSONObject getBaseInfoAllList(String key,  String museumType);

    JsonResult museumBaseInfoSave(EsaleMuseumWithBLOBs esaleMuseumWithBLOBs, String isMain, String picids, String delpicids);

    JsonResult museumBaseInfoDelete(String id, String dataState);

    EsaleMuseumWithBLOBs selectByPrimaryKey(String id);

    List<EsaleMuseum> getList();

    JsonResult getAreaByMuseumId(String id);

}
