package com.tj720.service;

import com.tj720.dto.CreativeProductDto;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public interface ICreativeProductService {

    /**
     *
     * 功能描述: 获取首页文创列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.CreativeProductDto>
     * @auther: caiming
     * @date: 2018/9/27 16:09
     */
    public List<CreativeProductDto> getRecommendCreativeProductList(Integer size);

    /**
     *
     * 功能描述: 查询文创分页列表
     *
     * @param: [page]
     * @return: java.util.List<com.tj720.dto.CreativeProductDto>
     * @auther: caiming
     * @date: 2018/9/30 15:06
     */
    public List<CreativeProductDto> getCreativeProductList(Page page);

    /**
     *
     * 功能描述: 根据文创id，获取文创详情
     *
     * @param: 
     * @return: com.tj720.dto.CreativeProductDto
     * @auther: caiming
     * @date: 2018/9/30 15:58
     */
    public CreativeProductDto getCreativeProduct(String id);

    /**
     *
     * 功能描述: 获取相关文创产品
     *
     * @param: [currentId, currentMuseumId, type, size]
     * @return: com.tj720.dto.CreativeProductDto
     * @auther: caiming
     * @date: 2018/9/30 16:21
     */
    public List<CreativeProductDto> getRelevantCreativeProduct(String currentId, String currentMuseumId, String type, Integer size);
}
