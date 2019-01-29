package com.tj720.dao;

import com.tj720.model.EsaleDigitalshow;

import java.util.List;
import java.util.Map;

public interface EsaleDigitalshowMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleDigitalshow record);

    EsaleDigitalshow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleDigitalshow record);

    Integer countDigitalshow(Map<String, Object> map);

    List<EsaleDigitalshow> selectDigitalshowList(Map<String, Object> map);

}