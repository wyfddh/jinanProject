package com.tj720.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 藏品统计Mapper
 * @author 杜昶
 */
@Repository
public interface EsaleStatisticsMapper {

    /**
     * 查询藏品table数据
     * @return
     */
    List<Map<String, Object>> collectTableStatistics();

    /**
     * 查询藏品总数
     * @return
     */
    Integer collectCount();

    /**
     * 根据类型获取藏品数量
     * @param typecode
     * @return
     */
    Integer getCollectCountByType(String typecode);

    /**
     * 藏品浏览量饼图
     * @return
     */
    List<Map<String, Object>> collectViewStatistics(Map<String, Object> param);

    /**
     * 藏品浏览量top10
     * @param param
     * @return
     */
    List<Map<String, Object>> collectViewHistogramStatistics(Map<String, Object> param);

    /**
     * 藏品收藏饼图
     * @param param
     * @return
     */
    List<Map<String, Object>> colectionCollectStatistics(Map<String, Object> param);

    /**
     * 藏品收藏top10
     * @param param
     * @return
     */
    List<Map<String, Object>> colectionCollectHistogramStatistics(Map<String, Object> param);

    /**
     * 社教活动总数
     * @param param
     * @return
     */
    Integer getActivityCount(Map<String, Object> param);

    /**
     * 社教活动报名人数
     * @param param
     * @return
     */
    Integer signCount(Map<String, Object> param);

    /**
     * 社教活动实际参与人数
     * @param param
     * @return
     */
    Integer realJoin(Map<String, Object> param);

    /**
     * 社教活动总评论人数
     * @param param
     * @return
     */
    Integer commentCount(Map<String, Object> param);

    /**
     * 获取活动分类统计饼图
     * @param param
     * @return
     */
    List<Map<String, Object>> getActivityTypePie(Map<String, Object> param);

    /**
     * 获取活动分类统计折线图
     * @param param
     * @return
     */
    List<Map<String, Object>> getActivityTypeLine(Map<String, Object> param);

    /**
     * 获取用户统计折线图
     * @param param
     * @return
     */
    List<Map<String, Object>> getUserStatisticsLine(Map<String, Object> param);

    /**
     * 查询员工日报数
     * @param param
     * @return
     */
    List<Map<String, Object>> userDiaryCount(Map<String, Object> param);

    /**
     * 按日期统计日报
     * @param param
     * @return
     */
    List<Map<String, Object>> diaryStatisticsGroupByDate(Map<String, Object> param);

    /**
     * 按人员统计日报
     * @param param
     * @return
     */
    List<Map<String, Object>> diaryStatisticsGroupByPerson(Map<String, Object> param);

    /**
     * 按部门统计日报
     * @param param
     * @return
     */
    List<Map<String, Object>> diaryStatisticsGroupByDept(Map<String, Object> param);

    /**
     * 查询个人日报数量
     * @param param
     * @return
     */
    Integer personLoopStatistics(Map<String, Object> param);
}
