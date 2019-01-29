package com.tj720.dao;

import com.tj720.dto.MuseumShowDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IMuseumShowMapper {

    /**
     *
     * 功能描述: 获取首页展览列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/27 16:28
     */
    List<MuseumShowDto> getMuseumShowList(@Param("size") Integer size);

    /**
     *
     * 功能描述: 根据多张id，返回主图url
     *
     * @param: [pictures]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/9/27 16:28
     */
    String getMainPictureUrl(@Param("pictures") List<String> pictures);

    /**
     *
     * 功能描述: 根据多张id，返回主图url
     *
     * @param: [pictures]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/9/27 16:28
     */
    List<Map<String, String>> getPictureUrl(@Param("pictures") List<String> pictures);

    /**
     *
     * 功能描述: 根据省份，获取展览列表
     *
     * @param: [provice, size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/28 16:01
     */
    List<MuseumShowDto> getMuseumShowListByProvice(@Param("provice") String provice,@Param("size")  Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取展览分页列表
     *
     * @param: [museumId, start, size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/29 16:50
     */
    List<MuseumShowDto> getMuseumShowListByMuseumId(@Param("museumId") String museumId, @Param("start") Integer start, @Param("size")  Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取展览数据总数
     *
     * @param: [museumId]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/29 16:51
     */
    int countMuseumShowListByMuseumId(@Param("museumId") String museumId);

    /**
     *
     * 功能描述: 根据查询条件，查询展览列表
     *
     * @param: [key, type, museumId, start, size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/30 11:42
     */
    List<MuseumShowDto> getMuseumShowListBySearch(@Param("key") String key, @Param("type") String type, @Param("museumId") String museumId, @Param("start") Integer start, @Param("size")  Integer size);

    /**
     *
     * 功能描述: 根据查询条件，查询展览总数
     *
     * @param: [key, type, museumId]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/30 11:43
     */
    int countMuseumShowListBySearch(@Param("key") String key, @Param("type") String type, @Param("museumId") String museumId);

    /**
     *
     * 功能描述: 获取展览详情
     *
     * @param: [id]
     * @return: com.tj720.dto.MuseumShowDto
     * @auther: caiming
     * @date: 2018/9/30 11:57
     */
    MuseumShowDto getMuseumShow(@Param("id") String id);

}