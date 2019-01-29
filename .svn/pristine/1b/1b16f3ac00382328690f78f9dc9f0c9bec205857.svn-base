package com.tj720.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 大唐星云
 */
@Repository
public interface PostVideoStatisticsMapper {
    /**
     * 新增资料数
     * @param param
     * @return
     */
    Integer newVideoCount(Map<String, Object> param);

    /**
     * 查询总资料数
     * @return
     */
    Integer videoTotle();

    /**
     * 未公开总资料数
     * @return
     */
    Integer videoCountByPermissions(String authSetting);

    /**
     * 查询新增资料统计table行数据
     * @param dictCode
     * @return
     */
    Map<String, Object> newVideoStatisticsLine(Map<String, Object> param);

    /**
     * 查询新增资料统计饼图
     * @param param
     * @return
     */
    List<Map<String, Object>> newVideoStatisticsPie(Map<String, Object> param);

    /**
     * 查询新增资料折线图
     * @param param
     * @return
     */
    List<Map<String, Object>> videoStatisticsLine(Map<String, Object> param);

    /**
     * 数据使用统计柱状图
     * @param param
     * @return
     */
    Map<String, Object> videoUseStatistics(Map<String, Object> param);

    /**
     * 资料Top10
     * @param param
     * @return
     */
    List<Map<String, Object>> videoStatistics(Map<String, Object> param);
}
