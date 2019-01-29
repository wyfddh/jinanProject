package com.tj720.service;

import com.tj720.controller.framework.JsonResult;

import java.text.ParseException;
import java.util.Map;

/**
 * 统计图相关service
 * @author 杜昶
 */
public interface EsaleStatisticsService {

    /**
     * 藏品统计
     * @param
     * @return
     */
    JsonResult colletStatistics();

    /**
     * 藏品浏览统计
     * @param param
     * @return
     */
    JsonResult collectViewStatistics(Map<String, Object> param);

    /**
     * 藏品收藏统计
     * @param param
     * @return
     */
    JsonResult colectionCollectStatistics(Map<String, Object> param);

    /**
     * 用户统计
     * @param param
     * @return
     */
    JsonResult userStatistics(Map<String, Object> param);

    /**
     * 社教活动统计
     * @param param
     * @return
     */
    JsonResult socialEducationSummaryStatistics(Map<String, Object> param);

    /**
     * 活动分类统计
     * @param param
     * @return
     */
    JsonResult socialEducationTypeStatistics(Map<String, Object> param);

    /**
     * 日报统计
     * @param startTime
     * @param endTime
     * @return
     */
    JsonResult diaryStatistics(String startTime, String endTime) throws Exception;

    /**
     * 按日期统计日报
     * @return
     */
    JsonResult diaryStatisticsGroupByDate(Map<String, Object> param) throws Exception;

    /**
     * 根据人员统计日报
     * @return
     */
    JsonResult diaryStatisticsGroupByPerson(String startTime, String endTime) throws Exception;

    /**
     * 根据部门统计日报
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    JsonResult diaryStatisticsGroupByDept(String startTime, String endTime) throws Exception;

    /**
     * 个人日报统计
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    JsonResult personLoopStatistics(String startTime, String endTime) throws Exception;
}
