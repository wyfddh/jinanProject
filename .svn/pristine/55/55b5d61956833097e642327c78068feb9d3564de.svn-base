package com.tj720.dao;

import com.tj720.dto.PCEsaleShowDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PCEsaleShowMapper {

    /**
     * 查询指定博物馆的展览条数
     * @param map
     * @return
     */
    int countEsaleShow(Map map);

    /**
     * 查询指定博物馆的展览列表
     * @param map
     * @return
     */
    List<PCEsaleShowDto> getEsaleShowListForPage(Map map);


    PCEsaleShowDto getEsaleShowById(@Param("id") String id);

}