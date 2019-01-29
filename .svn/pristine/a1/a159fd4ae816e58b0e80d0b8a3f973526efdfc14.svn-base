package com.tj720.dao;


import com.tj720.dto.AreaDto;
import com.tj720.dto.CollectionInfoDto;
import com.tj720.dto.ISysDictDto;
import com.tj720.dto.SelectDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICollectionInfoMapper {
    /**
     *
     * 功能描述: 获取所有藏品数量
     *
     * @param: []
     * @return: int
     * @auther: caiming
     * @date: 2018/9/28 11:08
     */
    int countCollection();

    /**
     *
     * 功能描述: 根据省份获取藏品数量
     *
     * @param: [provice]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/28 11:08
     */
    int countCollectionByProvice(@Param("province") String provice);

    /**
     *
     * 功能描述: 根据省份id，获取藏品列表
     *
     * @param: [provice, size]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/28 11:34
     */
    List<CollectionInfoDto> getCollectionInfoListByProvice(@Param("province") String provice, @Param("size") Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取藏品列表
     *
     * @param: [museumId, start, size]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/29 16:39
     */
    List<CollectionInfoDto> getCollectionInfoListByMuseumId(@Param("museumId") String museumId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取藏品总数
     *
     * @param: [museumId]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/29 16:46
     */
    int countCollectionInfoListByMuseumId(@Param("museumId") String museumId);

    /**
     *
     * 功能描述: 根据查询条件，获取藏品列表
     *
     * @param: [collectionType, collectionYear, museumId, start, size]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/29 17:37
     */
    List<CollectionInfoDto> getCollectionInfoListBySearch(@Param("key") String key, @Param("collectionType") String collectionType, @Param("collectionYear") String collectionYear, @Param("museumId") String museumId, @Param("level") String level, @Param("textures") String textures, @Param("province") String province, @Param("city") String city, @Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * 功能描述: 根据查询条件，获取藏品总数
     *
     * @param: [collectionType, collectionYear, museumId]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/29 17:37
     */
    int countCollectionInfoListBySearch(@Param("key") String key, @Param("collectionType") String collectionType, @Param("collectionYear") String collectionYear, @Param("museumId") String museumId, @Param("level") String level, @Param("textures") String textures, @Param("province") String province, @Param("city") String city);

    /**
     *
     * 功能描述: 获取藏品类别
     *
     * @param: []
     * @return: java.util.List<com.tj720.dto.SelectDto>
     * @auther: caiming
     * @date: 2018/9/30 10:24
     */
    List<SelectDto> getCollectionTypeList();

    /**
     *
     * 功能描述: 获取藏品年份
     *
     * @param: []
     * @return: java.util.List<com.tj720.dto.SelectDto>
     * @auther: caiming
     * @date: 2018/9/30 10:24
     */
    List<SelectDto> getCollectionYearList();

    /**
     * 根据字典类型，获取字典数据
     * @param type
     * @return
     */
    List<ISysDictDto> getDictData(@Param("type") String type);

    /**
     * 根据父id获取地区集合
     * @param parent
     * @return
     */
    List<AreaDto> getCityDataByParent(@Param("parent") String parent);

    /**
     * 根据id，获取藏品详情
     * @param id
     * @return
     */
    CollectionInfoDto getCollectionDetail(@Param("id") String id);
}