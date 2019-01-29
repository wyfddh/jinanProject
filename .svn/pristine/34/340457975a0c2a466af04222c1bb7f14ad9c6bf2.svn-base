package com.tj720.service;

import com.tj720.dto.MuseumShowDto;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @auther: caiming
 * @date: 2018/9/27 10:34
 */
@Service
public interface IMuseumShowService {

    /**
     *
     * 功能描述: 获取展览列表
     *
     * @param: [size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/28 16:02
     */
    public List<MuseumShowDto> getMuseumShowList(Integer size);

    /**
     *
     * 功能描述: 根据省份，获取展览列表
     *
     * @param: [provice, size]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/28 16:02
     */
    public List<MuseumShowDto> getMuseumShowListByProvice(String provice, Integer size);

    /**
     *
     * 功能描述: 根据博物馆id，获取展览分页列表
     *
     * @param: [museumId, page]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/29 16:58
     */
    public List<MuseumShowDto> getMuseumShowListByMuseumId(String museumId, Page page);

    /**
     *
     * 功能描述: 根据查询条件，获取展览分页列表
     *
     * @param: [museumId, page]
     * @return: java.util.List<com.tj720.dto.MuseumShowDto>
     * @auther: caiming
     * @date: 2018/9/29 16:58
     */
    public List<MuseumShowDto> getMuseumShowListBySearch(String key, String type, String museumId, Page page);
    
    /**
     *
     * 功能描述: 获取展览详情
     *
     * @param: [id]
     * @return: com.tj720.dto.MuseumShowDto
     * @auther: caiming
     * @date: 2018/9/30 11:58
     */
    public MuseumShowDto getMuseumShow(String id);

}
