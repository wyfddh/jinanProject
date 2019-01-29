package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostVideoStatisticsMapper;
import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.SysDict;
import com.tj720.service.PostVideoStatisticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杜昶
 */
@Service
public class PostVideoStatisticsServiceImpl implements PostVideoStatisticsService {

    @Autowired
    private PostVideoStatisticsMapper postVideoStatisticsMapper;

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public JsonResult videoSummaryStatistics(Map<String, Object> param) {
        Integer newVideoCount = postVideoStatisticsMapper.newVideoCount(param);
        Integer videoTotle = postVideoStatisticsMapper.videoTotle();
        Integer undisclosedCount = postVideoStatisticsMapper.videoCountByPermissions("1");
        Integer canQueryCount = postVideoStatisticsMapper.videoCountByPermissions("2");
        Integer canDownCount = postVideoStatisticsMapper.videoCountByPermissions("3");
        Map<String, Object> result = new HashMap<String, Object>(5);
        result.put("newVideoCount", newVideoCount);
        result.put("videoTotle", videoTotle);
        result.put("undisclosedCount", undisclosedCount);
        result.put("canQueryCount", canQueryCount);
        result.put("canDownCount", canDownCount);
        return new JsonResult(1, result);
    }

    @Override
    public JsonResult newVideoStatistics(Map<String, Object> param) {
        List<SysDict> dictList = sysDictMapper.getSysDictListByType("video_type");
        List<Map<String, Object>> tableLineList = new ArrayList<Map<String, Object>>(dictList.size());
        for (SysDict dict : dictList) {
            param.put("videoType", dict.getDictCode());
            Map<String, Object> line = postVideoStatisticsMapper.newVideoStatisticsLine(param);
            line.put("title", dict.getDictName());
            tableLineList.add(line);
        }

        List<Map<String, Object>> pie = postVideoStatisticsMapper.newVideoStatisticsPie(param);
        if (CollectionUtils.isEmpty(pie)) {
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", 0);
            map.put("name", "无数据");
            pie.add(map);
        }
        List<String> legend = new ArrayList<String>(1);
        legend.add("新增资料");
        List<Map<String, Object>> lineList = postVideoStatisticsMapper.videoStatisticsLine(param);
        List<String> xAxis = new ArrayList<String>();
        for (Map<String, Object> line : lineList){
            xAxis.add(line.get("updateTime").toString());
        }
        List<Map<String,Object>> yAxis = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < legend.size(); i++) {
            Map<String,Object> temp = new HashMap<String,Object>(4);
            temp.put("name",legend.get(i));
            List<Object> values = new ArrayList<Object>();
            for (Map<String, Object> map : lineList) {
                values.add(map.get(legend.get(i)));
            }
            temp.put("data",values);
            temp.put("type","line");
            temp.put("stack","总量");
            yAxis.add(temp);
        }
        Map<String,Object> lineData = new HashMap<String,Object>(3);
        lineData.put("data",yAxis);
        lineData.put("legend",legend);
        lineData.put("xAxis",xAxis);
        Map<String, Object> result = new HashMap<String, Object>(3);
        result.put("table", tableLineList);
        result.put("pie", pie);
        result.put("line", lineData);
        return new JsonResult(1, result);
    }

    @Override
    public JsonResult videoUseStatistics(Map<String, Object> param) {
        String[] legend = new String[]{"申请", "下载"};
        List<SysDict> dictList = sysDictMapper.getSysDictListByType("video_type");
        List<Long> dataApply = new ArrayList<Long>(dictList.size());
        List<Long> dataDown = new ArrayList<Long>(dictList.size());
        String[] xAxis = new String[dictList.size()];
        for (int i = 0; i < dictList.size(); i++) {
            SysDict dict = dictList.get(i);
            param.put("videoType", dict.getDictCode());
            Map<String, Object> dataList = postVideoStatisticsMapper.videoUseStatistics(param);
            dataApply.add((Long) dataList.get(legend[0]));
            dataDown.add((Long) dataList.get(legend[1]));
            xAxis[i] = dict.getDictName();

        }
        Map<String, Object> applyMap = new HashMap<String, Object>(3);
        applyMap.put("name", "申请");
        applyMap.put("type", "bar");
        applyMap.put("data", dataApply);
        Map<String, Object> downMap = new HashMap<String, Object>(3);
        downMap.put("name", "下载");
        downMap.put("type", "bar");
        downMap.put("data", dataDown);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(2);
        data.add(applyMap);
        data.add(downMap);
        Map<String, Object> histogram = new HashMap<String, Object>(3);
        histogram.put("legend", legend);
        histogram.put("xAxis", xAxis);
        histogram.put("data", data);

        List<Map<String, Object>> dataList2 = postVideoStatisticsMapper.videoStatistics(param);
        List<Long> apply = new ArrayList<Long>(dataList2.size());
        List<Long> down = new ArrayList<Long>(dataList2.size());
        String[] yAxis = new String[dataList2.size()];
        for (int i = 0; i < dataList2.size(); i++) {
            Map<String, Object> map = dataList2.get(i);
            apply.add(0, (Long) map.get("申请"));
            down.add(0, (Long) map.get("下载"));
            yAxis[i] = (String) map.get("videoName");
        }
        Map<String, Object> applyMap2 = new HashMap<String, Object>(3);
        applyMap2.put("name", "申请");
        applyMap2.put("type", "bar");
        applyMap2.put("data", apply);
        Map<String, Object> downMap2 = new HashMap<String, Object>(3);
        downMap2.put("name", "下载");
        downMap2.put("type", "bar");
        downMap2.put("data", down);
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>(2);
        data2.add(applyMap2);
        data2.add(downMap2);
        Map<String, Object> histogram2 = new HashMap<String, Object>(3);
        histogram2.put("legend", legend);
        histogram2.put("yAxis", yAxis);
        histogram2.put("data", data2);
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("histogram", histogram);
        result.put("histogram2", histogram2);
        return new JsonResult(1, result);
    }
}
