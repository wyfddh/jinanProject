package com.tj720.service;

import com.tj720.dto.PCEsaleShowDto;
import com.tj720.utils.Page;

import java.util.List;

/**
 * @auther: liuxiu
 */
public interface PCEsaleShowService {

    /**
     *
     * 功能描述: 获取展览列表
     *
     * @param: [id]
     * @return: com.tj720.dto.PCEsaleShowDto
     * @auther: liuxiu
     */
    List<PCEsaleShowDto>  getEsaleShowList(String museumId, String type, Page page);

    /**
     *
     * 功能描述: 获取展览详情
     *
     * @param: [id]
     * @return: com.tj720.dto.PCEsaleShowDto
     * @auther: liuxiu
     */
    PCEsaleShowDto  getEsaleShowById(String id ,String userId);


}
