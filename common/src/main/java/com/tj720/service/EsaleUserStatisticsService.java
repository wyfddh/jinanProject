package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleUserStatistics;

/**
 * @author 杜昶
 */
public interface EsaleUserStatisticsService {
    /**
     * 保存用户统计信息
     * @param esaleUserStatistics
     * @return
     */
    JsonResult insertUserStatistics(EsaleUserStatistics esaleUserStatistics);
}
