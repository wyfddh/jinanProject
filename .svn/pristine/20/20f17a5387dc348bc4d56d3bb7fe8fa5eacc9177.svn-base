package com.tj720.dao;

import com.tj720.model.EsalePersonDiary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EsalePersonDiaryMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsalePersonDiary record);

    int insertSelective(EsalePersonDiary record);

    EsalePersonDiary selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsalePersonDiary record);

    int updateByPrimaryKeyWithBLOBs(EsalePersonDiary record);

    int updateByPrimaryKey(EsalePersonDiary record);

    String getOrgIdByUserId(String userId);

    List<EsalePersonDiary> getList(@Param("record") EsalePersonDiary record, @Param("start") Integer start, @Param("size") Integer size);
    int countList(@Param("record") EsalePersonDiary record);

    List<EsalePersonDiary> getPersonTodayData(@Param("userId") String userId);

    List<Map<String, Object>> getDataByMonth(@Param("list") List<String> dayList, @Param("creator") String creator);
}