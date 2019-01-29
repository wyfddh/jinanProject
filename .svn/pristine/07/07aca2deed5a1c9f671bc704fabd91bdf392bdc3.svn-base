package com.tj720.dao;

import com.tj720.dto.PCEsaleActivityDto;
import com.tj720.dto.PCEsalePubUserDto;

import java.util.List;
import java.util.Map;

public interface PCActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(PCEsaleActivityDto record);

    PCEsaleActivityDto selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PCEsaleActivityDto record);

    int updateByPrimaryKeyWithBLOBs(PCEsaleActivityDto record);

    int updateByPrimaryKey(PCEsaleActivityDto record);

    Integer countShowList(Map<String, Object> map);

    /**
     * 我的活动
     */
    List<PCEsaleActivityDto> getActivityListByUid(Map<String, Object> map);

    /**
     * 查询总条数
     */
    Integer countActivity(Map<String, Object> map);

}