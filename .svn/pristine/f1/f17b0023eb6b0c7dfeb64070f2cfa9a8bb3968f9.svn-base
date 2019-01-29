package com.tj720.dao;

import com.tj720.dto.AreaDto;
import com.tj720.dto.IndexSearchDto;
import com.tj720.dto.MuseumBaseInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMuseumBaseInfoMapper {
    /**
     * 获取上线博物馆数量
     * @return
     */
    int countOnline();

    /**
     * 获取文化振兴博物馆列表
     * @param size
     * @return
     */
    List<MuseumBaseInfoDto> getMuseumPoorList(@Param("size")  Integer size);

    /**
     *
     * 功能描述: 获取首页，根据省份获取主要的博物馆列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/27 16:39
     */
    List<MuseumBaseInfoDto> getRecommendMuseumList(@Param("province")  String province, @Param("size")  Integer size);

    /**
     *
     * 功能描述: 根据省份，查询有多少家博物馆
     *
     * @param: [province]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/27 16:51
     */
    int countOnlineByProvice(@Param("province")  String province);

    /**
     *
     * 功能描述:关键词查找省份/博物馆
     *
     * @param: [key]
     * @return: java.util.List<com.tj720.dto.IndexSearchDto>
     * @auther: caiming
     * @date: 2018/9/27 17:50
     */
    List<IndexSearchDto> getIndexSearchList(@Param("key") String key, @Param("type") String type);

    /**
     *
     * 功能描述: 获取精彩推荐列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/28 10:12
     */
    List<MuseumBaseInfoDto> getJxtjMuseumList(@Param("province") String province, @Param("size") Integer size);

    /**
     *
     * 功能描述: 根据省份或者城市id，获取博物馆列表
     *
     * @param: [province, star, size]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/29 16:02
     */
    List<MuseumBaseInfoDto> getRecommendMuseumPageByProviceOrCity(@Param("province") String province, @Param("startRow") Integer star, @Param("size") Integer size);

    MuseumBaseInfoDto getMuseumInfo(@Param("museumId") String museumId);

    List<AreaDto> getProviceData();

    int countRecommendMuseumPageByProviceOrCity(@Param("province") String province);

    /**
     *
     * 功能描述: 根据博物馆id，获取地方推介视频
     *
     * @param: [museumId]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/10/19 10:45
     */
    String getLocalIntroduceVideo(@Param("museumId") String museumId);

    /**
     *
     * 功能描述: 根据博物馆id，获取博物馆相关的文化振兴描述
     *
     * @param: [museumId]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/10/19 10:46
     */
    String getCulturalPromote(@Param("museumId") String museumId);

    /**
     * 根据拼音获取省份id
     * @param pinyin
     * @return
     */
    String getProvinceIdByPinying(@Param("pinyin") String pinyin);
}