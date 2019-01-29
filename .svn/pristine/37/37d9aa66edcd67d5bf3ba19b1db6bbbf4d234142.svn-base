package com.tj720.dao;

import com.tj720.dto.CreativeProductDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICreativeProductMapper {

    /**
     *
     * 功能描述: 查询首页文创列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.CreativeProductDto>
     * @auther: caiming
     * @date: 2018/9/27 16:12
     */
    List<CreativeProductDto> getRecommendCreativeProductList(@Param("size") Integer size);

    /**
     *
     * 功能描述: 查询文创列表
     *
     * @param: [start, size]
     * @return: java.util.List<com.tj720.dto.CreativeProductDto>
     * @auther: caiming
     * @date: 2018/9/30 15:20
     */
    List<CreativeProductDto> getCreativeProductList(@Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * 功能描述: 获取文创产品列表总数
     *
     * @param: []
     * @return: int
     * @auther: caiming
     * @date: 2018/9/30 15:35
     */
    int countCreativeProductList();

    /**
     *
     * 功能描述: 获取文创详情
     *
     * @param: [id]
     * @return: com.tj720.dto.CreativeProductDto
     * @auther: caiming
     * @date: 2018/9/30 15:39
     */
    CreativeProductDto getCreativeProduct(@Param("id") String id);

    /**
     *
     * 功能描述: 获取相关文创
     *
     * @param: [currentId, currentMuseumId, type, size]
     * @return: java.util.List<com.tj720.dto.CreativeProductDto>
     * @auther: caiming
     * @date: 2018/9/30 16:11
     */
    List<CreativeProductDto> getRelevantCreativeProduct(@Param("currentId") String currentId,@Param("currentMuseumId") String currentMuseumId,@Param("type") String type, @Param("size") Integer size);

}