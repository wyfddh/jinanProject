package com.tj720.dao;

import com.tj720.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PCesaleActivityMapper {

   Integer countEsaleActivityList(Map<String,Object> map);

   List<PCesaleActivity> getEsaleActivityList(Map<String,Object> map);

   PCesaleActivity getActivityDetail(Map<String,Object> map);

   List<PCEsalePubUser> getJoinUserList(Map<String,Object> map);

   int updateByTwoId(PCEsaleUserActivityDto pcEsaleUserActivityDto);

   Integer countCommentList(Map<String,Object> map);

   List<PCEsaleAssessActivity>  getCommentList(Map<String,Object> map);

   Integer insertComment(PCEsaleAssessActivity info);

   int insertMobileSign(PCEsaleUserActivityDto record);

   List<PCEsaleUserActivityDto> getUserActivityList(Map<String,Object> map);

   PCesaleActivity selectActivityDetailById(@Param("id") String id);

   String selectUserActivityOfTwoId(Map map);

   PCEsalePubUserDto getPubUserDetailByActityId(@Param("userId") String userId, @Param("activityId") String activityId);
}