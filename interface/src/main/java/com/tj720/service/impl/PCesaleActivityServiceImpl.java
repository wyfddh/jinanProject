package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCEsalePubUserMapper;
import com.tj720.dao.PCesaleActivityMapper;
import com.tj720.dto.*;
import com.tj720.service.PCEsalePubUserService;
import com.tj720.service.PCesaleActivityService;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PCesaleActivityServiceImpl implements PCesaleActivityService {

    @Autowired
    private PCesaleActivityMapper esaleActivityMapper;

    @Autowired
    private PCEsalePubUserMapper esalePubUserMapper;

    @Autowired
    private PCEsalePubUserService esalePubUserService;

    @Autowired
    private Config config;

    @Override
    public Integer countEsaleActivityList(Map<String, Object> map) {
        return esaleActivityMapper.countEsaleActivityList(map);
    }

    ;

    @Override
    public List<PCesaleActivity> getEsaleActivityList(Map<String, Object> map) {
        return esaleActivityMapper.getEsaleActivityList(map);
    }

    ;

    @Override
    public PCesaleActivity getActivityDetail(Map<String, Object> map) {
        //活动详情
        PCesaleActivity activityInfo = esaleActivityMapper.getActivityDetail(map);
        if (activityInfo != null) {
            activityInfo.setPicUrl(config.getRootUrl() + activityInfo.getPicUrl());
        }
        System.out.println("activityInfo -----------------------  "+activityInfo.getType());
        if (null != map.get("userId") && !"".equals(map.get("userId"))) {
            esalePubUserService.addInfomation("0", (String) map.get("userId"), activityInfo);
        }
        //报名用户list
        List<PCEsalePubUser> userList = new ArrayList<PCEsalePubUser>();
        userList = esaleActivityMapper.getJoinUserList(map);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long nowms = System.currentTimeMillis();
            long subms = 0;
            for (PCEsalePubUser user : userList) {
                subms = nowms - sdf.parse(user.getEnrollTime()).getTime();
                subms /= (1000 * 60);
                if (subms < 60) {
                    user.setEnrollTime(subms + "分钟前");
                } else if (subms < 1440) {
                    subms /= 60;
                    user.setEnrollTime(subms + "小时前");
                } else if (subms < 43200) {
                    subms /= (60 * 24);
                    user.setEnrollTime(subms + "天前");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userList.size() > 0) {
            activityInfo.setUserList(userList);
        }
        //已经报名人数
        activityInfo.setSignCount(userList.size());

        return activityInfo;
    }

    ;

    @Override
    public int cancelUserActivity(String userId, String activityId) {
        PCEsaleUserActivityDto pcEsaleUserActivityDto = new PCEsaleUserActivityDto();
        pcEsaleUserActivityDto.setUserId(userId);
        pcEsaleUserActivityDto.setActivityId(activityId);
        pcEsaleUserActivityDto.setDataState("0");
        return esaleActivityMapper.updateByTwoId(pcEsaleUserActivityDto);
    }

    @Override
    public Integer countCommentList(Map<String, Object> map) {
        return esaleActivityMapper.countCommentList(map);
    }

    ;

    @Override
    public List<PCEsaleAssessActivity> getCommentList(Map<String, Object> map) {
        return esaleActivityMapper.getCommentList(map);
    }

    ;

    @Override
    public JsonResult insertComment(String activityId, String userId, String content) {
        try {
            PCEsalePubUserDto pcEsalePubUser = esalePubUserMapper.selectByPrimaryKey(userId);
            if (pcEsalePubUser != null) {
                PCEsaleAssessActivity info = new PCEsaleAssessActivity();
                info.setRealName(pcEsalePubUser.getRealName());
                info.setActivityId(activityId);
                info.setUserId(userId);
                info.setUserName(pcEsalePubUser.getUserName());
                info.setUserPicUrl(pcEsalePubUser.getAvatarUrl());
                info.setCommentTime(new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date()));
                info.setContent(content);
                info.setId(IdUtils.nextId(info));
                info.setDataState("1");
                info.setCreateDate(new Date());
                info.setCreateBy("system");
                info.setUpdateDate(new Date());
                info.setUpdateBy("system");
                Integer count = esaleActivityMapper.insertComment(info);
                if (count > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long nowms = System.currentTimeMillis();
                    long subms = 0;
                        subms = nowms - sdf.parse(info.getCommentTime()).getTime();
                        subms /= (1000*60);
                        if(subms<60){
                            info.setCommentTime(subms+"分钟前");
                        }else if(subms<1440){
                            subms /= 60;
                            info.setCommentTime(subms+"小时前");
                        }else if(subms<43200){
                            subms /= (60*24);
                            info.setCommentTime(subms+"天前");
                        }
                    return new JsonResult(1, info);
                } else {
                    return new JsonResult(0, null, "000001");
                }
            } else {
                return new JsonResult(0, null, "111116");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new JsonResult(0, null, "111116");
        }

    }

    @Override
    public boolean SaveMobileActivitySign(PCEsaleUserActivityDto pcEsaleUserActivityDto) {
        int i = esaleActivityMapper.insertMobileSign(pcEsaleUserActivityDto);
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int checkUserActivitySign(String activityId, String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("activityId", activityId);
        List<PCEsaleUserActivityDto> userActivityDtoList = esaleActivityMapper.getUserActivityList(map);
        System.out.println("userActivityDtoList  ==  "+userActivityDtoList.size());
        if (userActivityDtoList.size() == 1) {
            if (("1").equals(userActivityDtoList.get(0).getDataState())) {
                //已报名
                return 1;
            } else {
                //已取消
                return 2;
            }
        } else if (userActivityDtoList.size() == 0) {
            //未报名
            return 0;
        } else {
            return 3;
        }
    }

    @Override
    public PCesaleActivity getActivityDetailById(String id) {
        return esaleActivityMapper.selectActivityDetailById(id);
    }

    @Override
    public PCEsalePubUserDto getPubUserDetail(String id) {
        return esalePubUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public String getUserActivityForId(String userId, String activityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("activityId", activityId);
        return esaleActivityMapper.selectUserActivityOfTwoId(map);
    }

    @Override
    public PCEsalePubUserDto getPubUserDetail(String id, String activityId) {
        return esaleActivityMapper.getPubUserDetailByActityId(id, activityId);
    }

}
