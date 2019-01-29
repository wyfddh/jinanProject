package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tj720.common.redis.JedisDao;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.EsaleInterfaceDict;
import com.tj720.service.EsaleInterfaceCollectService;
import com.tj720.utils.HttpPostGet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部藏品接口service
 * @author 杜昶
 */
@Service
public class EsaleInterfaceCollectServiceImpl implements EsaleInterfaceCollectService {

    @Autowired
    private Config config;

    /** 查询藏品种类接口 */
    private String selectColelctTypeUrl = "/icmsApi/icmsRestController/getShareCulType";

    /** 根据藏品名称搜索藏品条数 **/
    private Integer size = 200;

    public static final String COLLECT_TYPE_KEY = "collect_type_key_";

    @Override
    public JsonResult getInterfaceCollectSelectType(String typegroupid) {
        JsonResult jsonResult = new JsonResult();
        String key = COLLECT_TYPE_KEY + typegroupid;
        if (JedisDao.isExist(key)) {
            jsonResult.setCode(1);
            jsonResult.setData(JSONObject.parse(JedisDao.get(key)));
            return jsonResult;
        }
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        try {
            StringBuilder sb = new StringBuilder(config.getInterfaceCollectPath());
            sb.append(selectColelctTypeUrl).append("?typegroupid=").append(typegroupid);
            String res = HttpPostGet.get(sb.toString(), null, header);
            JSONObject jsonObject = JSON.parseObject(res);
            Integer status = jsonObject.getInteger("status");
            if (null != status) {
                if (status != 0) {
                    jsonResult.setCode(0);
                    jsonResult.setMsg(jsonObject.getString("message"));
                } else {
                    jsonResult.setCode(1);
                    Object object = jsonObject.get("data");
                    String obj = JSONObject.toJSONString(object);
                    JedisDao.set(key, obj, 86400);
                    jsonResult.setData(JSONArray.parseArray(obj, EsaleInterfaceDict.class));
                }
                return jsonResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }
}
