package com.tj720.model;

import java.io.Serializable;
import java.util.Date;

/**
 * esale_user_activity
 * @author 
 */
public class EsaleUserActivity implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 报名方式 0pc端报名，1移动端报名
     */
    private String enrollType;

    /**
     * 报名时间
     */
    private Date enrollTime;

    private String enrollTimeStr;

    /**
     * 参加情况 1:已参加 0：未参加
     */
    private String joinState;

    /**
     * 报名状态 1已报名 0：取消报名
     */
    private String dataState;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    public String getUserJoinStatus() {
        return userJoinStatus;
    }

    public void setUserJoinStatus(String userJoinStatus) {
        this.userJoinStatus = userJoinStatus;
    }

    //参加状态
    private String userJoinStatus;


    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    //
    private String statusFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEnrollType() {
        return enrollType;
    }

    public void setEnrollType(String enrollType) {
        this.enrollType = enrollType;
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public String getJoinState() {
        return joinState;
    }

    public void setJoinState(String joinState) {
        this.joinState = joinState;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private String userName;//姓名

    private String userPhone;//手机

    private int absentCount;//累计缺席次数

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    private String activityStates;//活动状态

    public String getActivityStates() {
        return activityStates;
    }

    public void setActivityStates(String activityStates) {
        this.activityStates = activityStates;
    }

    private String activityName;
    private String activityTime;

    public String getActivityAddr() {
        return activityAddr;
    }

    public void setActivityAddr(String activityAddr) {
        this.activityAddr = activityAddr;
    }

    private String activityAddr;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }



    public String getEnrollTimeStr() {
        return enrollTimeStr;
    }

    public void setEnrollTimeStr(String enrollTimeStr) {
        this.enrollTimeStr = enrollTimeStr;
    }
}