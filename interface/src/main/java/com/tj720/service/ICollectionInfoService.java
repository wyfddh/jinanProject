package com.tj720.service;

import com.tj720.dto.AreaDto;
import com.tj720.dto.CollectionInfoDto;
import com.tj720.dto.ISysDictDto;
import com.tj720.dto.SelectDto;
import com.tj720.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.hibernate.sql.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public interface ICollectionInfoService {
    /**
     *
     * 功能描述: 查询正常的藏品数量
     *
     * @param: []
     * @return: int
     * @auther: caiming
     * @date: 2018/9/27 17:10
     */
    public int countCollection();

    /**
     *
     * 功能描述: 根据省份查询藏品数量
     *
     * @param: [province]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/27 17:10
     */
    public int countCollectionByProvice(String province);

    /**
     *
     * 功能描述: 根据省份，查询藏品信息
     *
     * @param: [provice, size]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/28 11:30
     */
    public List<CollectionInfoDto> getCollectionInfoListByProvice(String provice, Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取藏品分页列表
     *
     * @param: [museumId, size]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/29 16:44
     */
    public List<CollectionInfoDto> getCollectionInfoListByMuseumId(String museumId, Page page);

    /**
     *
     * 功能描述: 根据查询条件，获取藏品分页列表
     *
     * @param: [collectionType, collectionYear, museumId, page]
     * @return: java.util.List<com.tj720.dto.CollectionInfoDto>
     * @auther: caiming
     * @date: 2018/9/30 10:26
     */
    public List<CollectionInfoDto> getCollectionInfoListBySearch(String key, String collectionType, String collectionYear, String museumId, String level, String textures,String city, String province, Page page);

    /**
     *
     * 功能描述: 获取藏品分类数据
     *
     * @param: []
     * @return: java.util.List<com.tj720.dto.SelectDto>
     * @auther: caiming
     * @date: 2018/9/30 10:27
     */
    public List<SelectDto> getCollectionTypeList();

    /**
     *
     * 功能描述: 获取藏品年份数据
     *
     * @param: []
     * @return: java.util.List<com.tj720.dto.SelectDto>
     * @auther: caiming
     * @date: 2018/9/30 10:27
     */
    public List<SelectDto> getCollectionYearList();

    /**
     * 根据type获取数据字典数据
     * @param type
     * @return
     */
    public List<ISysDictDto> getDictData(String type);

    /**
     * 根据父id，获取地区集合
     * @param parent
     * @return
     */
    public List<AreaDto> getCityDataByParent(String parent);

    /**
     * 根据id，获取藏品详情
     * @param id
     * @return
     */
    public CollectionInfoDto getCollectionDetail(String id);

}
