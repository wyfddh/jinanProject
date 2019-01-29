package com.tj720.dao;

import com.tj720.model.EsalePubUser;
import com.tj720.model.EsaleUserActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface EsaleUserActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleUserActivity record);

    EsaleUserActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleUserActivity record);

    Integer countSign(Map<String, Object> map);

    List<EsaleUserActivity> getSignListById(Map<String, Object> map);

    List<EsaleUserActivity> getSignListByActivityId(Map<String, Object> map);
    /**
     * 查询某个用户参加活动的次数
     */
    List<EsaleUserActivity> countActivityByUid(String uid);

    List<EsaleUserActivity> getActivityRecordByUserId(Map<String, Object> map);

    Integer countActivityRecordByUserId(Map<String, Object> map);

    List<EsalePubUser> selectUserByPhones(@Param("phone") String phone);

    List<EsaleUserActivity> getUserActivityList(Map<String,Object> map);

    //sjq
    List<EsaleUserActivity> getSignListByActiviytId(Map<String, Object> map);
    //sjq
    Integer countSignActiviyt(Map<String, Object> map);
}