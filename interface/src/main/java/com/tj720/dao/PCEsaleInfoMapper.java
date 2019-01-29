package com.tj720.dao;

import com.tj720.dto.PCEsaleInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PCEsaleInfoMapper {

    /**
     * 查询指定博物馆的资讯条数
     * @param map
     * @return
     */
    int countEsaleInfo(Map map);

    /**
     * 查询指定博物馆的资讯列表
     * @param map
     * @return
     */
    List<PCEsaleInfoDto> getEsaleInfoListForPage(Map map);


    PCEsaleInfoDto getEsaleInfoById(@Param("id") String id);

}