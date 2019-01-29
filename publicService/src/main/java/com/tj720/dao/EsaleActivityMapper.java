package com.tj720.dao;

import com.tj720.model.EsaleActivity;

import java.util.List;
import java.util.Map;

public interface EsaleActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleActivity record);

    EsaleActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleActivity record);

    Integer countActivityList(Map<String, Object> map);

    List<EsaleActivity> selectActivityList(Map<String, Object> map);

}