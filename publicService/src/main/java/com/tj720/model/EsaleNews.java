package com.tj720.model;

import com.tj720.model.common.MipAttachment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * esale_news
 *
 * @author
 */
public class EsaleNews implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 图片id，以，分隔
     */
    private String pictureids;

    /**
     * 点赞用户id,以，分隔
     */
    private String praiseUserId;

    /**
     * 数据状态（0：已删除，1:正常）
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureids() {
        return pictureids;
    }

    public void setPictureids(String pictureids) {
        this.pictureids = pictureids;
    }

    public String getPraiseUserId() {
        return praiseUserId;
    }

    public void setPraiseUserId(String praiseUserId) {
        this.praiseUserId = praiseUserId;
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

    private String userName;

    private String userDepartName;

    private List<MipAttachment> picList;

    private List<String> praiseNameList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDepartName() {
        return userDepartName;
    }

    public void setUserDepartName(String userDepartName) {
        this.userDepartName = userDepartName;
    }

    public List<MipAttachment> getPicList() {
        return picList;
    }

    public void setPicList(List<MipAttachment> picList) {
        this.picList = picList;
    }

    public List<String> getPraiseNameList() {
        return praiseNameList;
    }

    public void setPraiseNameList(List<String> praiseNameList) {
        this.praiseNameList = praiseNameList;
    }

    private String isGood;

    public String getIsGood() {
        return isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }
}