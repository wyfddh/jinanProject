package com.tj720.dao;

import com.tj720.model.EsaleMuseum;
import com.tj720.model.EsaleMuseumWithBLOBs;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EsaleMuseumMapper {

    int deleteByPrimaryKey(String id);

    int insert(EsaleMuseumWithBLOBs record);

    EsaleMuseumWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleMuseumWithBLOBs record);

    Integer countBaseInfoList(Map<String, Object> map);

    List<EsaleMuseumWithBLOBs> getBaseInfoList(Map<String, Object> map);

    List<EsaleMuseumWithBLOBs> getBaseInfoAllList(Map<String, Object> map);

    List<EsaleMuseum> getList();
}