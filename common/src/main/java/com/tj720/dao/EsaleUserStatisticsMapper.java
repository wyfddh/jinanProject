package com.tj720.dao;

import com.tj720.model.EsaleUserStatistics;
import org.springframework.stereotype.Repository;

/**
 * @author 大唐星云
 */
@Repository
public interface EsaleUserStatisticsMapper {

    /**
     * 保存用户统计信息
     * @param esaleUserStatistics
     * @return
     */
    Integer insertUserStatistics(EsaleUserStatistics esaleUserStatistics);
}
