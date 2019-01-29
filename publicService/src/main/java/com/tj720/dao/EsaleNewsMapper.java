package com.tj720.dao;

import com.tj720.model.EsaleNews;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface EsaleNewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleNews record);

    EsaleNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleNews record);

    int countNews(Map map);

    List<EsaleNews> selectNewsList(Map map);

    List<Map<String , String>> getPercent();

    List<String> getDepartList();

}