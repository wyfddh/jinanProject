package com.tj720.dao;

import com.tj720.model.EsaleFiledataActivity;

import java.util.List;
import java.util.Map;

public interface EsaleFiledataActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleFiledataActivity record);

    EsaleFiledataActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleFiledataActivity record);

    Integer countFile(Map<String, Object> map);

    List<EsaleFiledataActivity> getFileListById(Map<String, Object> map);
}