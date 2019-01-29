package com.tj720.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * esale_activity
 * @author
 */
public class PCesaleActivity implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动地点
     */
    private String activityAddr;

    /**
     * 活动时间
     */
    private String activityTime;

    /**
     * 名额
     */
    private String quota;

    /**
     * 费用
     */
    private String cost;

    /**
     * 要求
     */
    private String demand;

    /**
     * 活动封面照片id
     */
    private String pictureId;

    /**
     * 活动类型
     */
    private String type;

    /**
     * 数据状态（0：删除1:正常）
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

    /**
     * 活动详情
     */
    private String description;

    //类型描述
    private String typeDes;

    //报名状态
    private String status;

    //活动图片
    private String picUrl;

    private String joinState;

    //y用户列表

    private List<PCEsalePubUser> userList;
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityAddr() {
        return activityAddr;
    }

    public void setActivityAddr(String activityAddr) {
        this.activityAddr = activityAddr;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String pictureUrl;//活动封面照片链接

    private int signCount;//已报名人数

    private int assessCount;//评论人数

    private int cancelSign;//取消报名

    private int realJoin;//实际参加人数

    private int leftNum;//剩下名额

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getAssessCount() {
        return assessCount;
    }

    public void setAssessCount(int assessCount) {
        this.assessCount = assessCount;
    }

    public int getCancelSign() {
        return cancelSign;
    }

    public void setCancelSign(int cancelSign) {
        this.cancelSign = cancelSign;
    }

    public int getRealJoin() {
        return realJoin;
    }

    public void setRealJoin(int realJoin) {
        this.realJoin = realJoin;
    }

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getJoinState() {
        return joinState;
    }

    public void setJoinState(String joinState) {
        this.joinState = joinState;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(int leftNum) {
        this.leftNum = leftNum;
    }

    public List<PCEsalePubUser> getUserList() {
        return userList;
    }

    public void setUserList(List<PCEsalePubUser> userList) {
        this.userList = userList;
    }
}