package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleUserStatisticsMapper;
import com.tj720.model.EsaleUserStatistics;
import com.tj720.service.EsaleUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杜昶
 */
@Service
public class EsaleUserStatisticsServiceImpl implements EsaleUserStatisticsService {

    @Autowired
    private EsaleUserStatisticsMapper esaleUserStatisticsMapper;

    @Override
    public JsonResult insertUserStatistics(EsaleUserStatistics esaleUserStatistics) {
        esaleUserStatisticsMapper.insertUserStatistics(esaleUserStatistics);
        return new JsonResult(1);
    }
}
