package com.tj720.service;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleDigitalShowDto;
import java.util.List;

/**
 * @auther: liuxiu
 */
public interface PCEsaleDigitalShowService {

    /**
     *
     * 功能描述: 获取数字展厅列表
     *
     * @param: [id]
     * @return: com.tj720.dto.PCEsaleDigitalShowDto
     * @auther: liuxiu
     */
    List<PCEsaleDigitalShowDto>  getEsaleDigitalShowList(String museumId);


    JsonResult digitalBrowseNum(String id);
}
