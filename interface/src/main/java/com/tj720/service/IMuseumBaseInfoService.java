package com.tj720.service;

import com.tj720.dto.AreaDto;
import com.tj720.dto.IndexSearchDto;
import com.tj720.dto.MuseumBaseInfoDto;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public interface IMuseumBaseInfoService {

    /**
     * 上线博物馆总数量
     * @return
     */
    public int countOnlineMuseum();

    /**
     * 获取文化振兴轮播
     * @param size  获取的数量
     * @return
     */
    public List<MuseumBaseInfoDto> getMuseumPoorList(Integer size);

    /**
     *
     * 功能描述: 根据省份，获取博物馆列表
     * 获取最新的几个 update_date desc, creat_date desc
     * @param: [province, size]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/27 16:45
     */
    public List<MuseumBaseInfoDto> getRecommendMuseumList(String province, Integer size);

    /**
     *
     * 功能描述: 根据省份，获取上线博物馆数量
     *
     * @param: [province]
     * @return: int
     * @auther: caiming
     * @date: 2018/9/27 16:54
     */
    public int countOnlineByProvice(String province);

    /**
     *
     * 功能描述: 根据关键词，查询省份/博物馆
     * 省份 type-1
     * 博物馆 type-2
     * @param: [key]
     * @return: java.util.List<com.tj720.dto.IndexSearchDto>
     * @auther: caiming
     * @date: 2018/9/27 17:54
     */
    public List<IndexSearchDto> getIndexSearchList(String key, String type);

    /**
     *
     * 功能描述: 获取精彩推荐列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/28 10:10
     */
    public List<MuseumBaseInfoDto> getJctjMuseumList(String province, Integer size);

    /**
     *
     * 功能描述: 根据省份或者区域id，获取博物馆分页列表
     *
     * @param: [province, page]
     * @return: java.util.List<com.tj720.dto.MuseumBaseInfoDto>
     * @auther: caiming
     * @date: 2018/9/28 16:13
     */
    public List<MuseumBaseInfoDto> getRecommendMuseumPageByProviceOrCity(String province, Page page);

    /**
     *
     * 功能描述: 根据博物馆id，获取博物馆信息
     *
     * @param: [museumId]
     * @return: com.tj720.dto.MuseumBaseInfoDto
     * @auther: caiming
     * @date: 2018/9/29 16:33
     */
    public MuseumBaseInfoDto getMuseumBaseInfo(String museumId);

    /**
     *
     * 功能描述: 获取省数据
     *
     * @param: []
     * @return: java.util.List<com.tj720.dto.AreaDto>
     * @auther: caiming
     * @date: 2018/10/8 16:05
     */
    public List<AreaDto> getProviceData();

    /**
     *
     * 功能描述: 根据博物馆id，获取地方推介视频
     * 当找不到时，返回null
     * 找到时，返回视频完整路径
     *
     * @param: [museumId]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/10/19 10:37
            */
    public String getLocalIntroduce(String museumId);

    /**
     *
     * 功能描述: 根据博物馆id，获取文化振兴描述
     * 为空时返回null
     * 找到时，返回描述
     *
     * @param: [museumId]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/10/19 10:42
     */
    public String getCulturalPromote(String museumId);

    /**
     * 根据拼音获取省份id
     * @param pinying
     * @return
     */
    public String getProvinceIdByPinying(String pinying);

}
