package com.tj720.dao;

import com.tj720.model.EsaleAssessActivity;

import java.util.List;
import java.util.Map;

public interface EsaleAssessActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleAssessActivity record);

    EsaleAssessActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleAssessActivity record);

    Integer countAssess(Map<String, Object> map);

    List<EsaleAssessActivity> getAssessListById(Map<String, Object> map);

    /**
     * 查询评论条数
     */
    int countComment(String uid);


}