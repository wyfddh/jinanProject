package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleAssessActivity;
import com.tj720.dto.PCEsalePubUserDto;
import com.tj720.dto.PCEsaleUserActivityDto;
import com.tj720.dto.PCesaleActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by chenshiya
 */

public interface PCesaleActivityService {

    public Integer countEsaleActivityList(Map<String,Object> map);

    public List<PCesaleActivity> getEsaleActivityList(Map<String,Object> map);

    public PCesaleActivity getActivityDetail(Map<String,Object> map);

    int cancelUserActivity(String userId,String activityId);

    public Integer countCommentList(Map<String,Object> map);

    public List<PCEsaleAssessActivity>  getCommentList(Map<String,Object> map);

    public JsonResult insertComment(String activityId,String userId,String content);

    boolean SaveMobileActivitySign(PCEsaleUserActivityDto pcEsaleUserActivityDto);

    int checkUserActivitySign(String activityId,String userId);

    PCesaleActivity getActivityDetailById(String id);

    PCEsalePubUserDto getPubUserDetail(String id);

    String getUserActivityForId(String userId,String activityId);

    PCEsalePubUserDto getPubUserDetail(String id, String activityId);


}
