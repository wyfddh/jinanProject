package com.tj720.dto;

import com.tj720.model.common.BaseVO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * local_introduce
 * @author 
 */
public class EsaleVideoDto extends BaseVO {

    private String museumId;
    private String museumName;
    /**
     * 主键
     */
    private String id;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 推介地方
     */
    private String introducePlace;

    /**
     * 推介视频
     */
    private String videoId;

    /**
     * 首页推荐（1：是0：否）
     */
    private String pageRecommend;

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

    /**
     * 内容介绍
     */
    private String content;

    /**
     * 首页推荐是否选中
     */
    private String pageRecommendCheck;

    private String createDateStr;

    //视频相对路径
    private String videoUrl;

    //视频绝对路径
    private String videoShowUrl;

    private String province;

    private String city;

    private String area;

    private String address;

    private String provinceDes;

    private String cityDes;

    private String  areaDes;

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    //视频播放次数
    private Integer playNum;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getIntroducePlace() {
        return introducePlace;
    }

    public void setIntroducePlace(String introducePlace) {
        this.introducePlace = introducePlace;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPageRecommend() {
        return pageRecommend;
    }

    public void setPageRecommend(String pageRecommend) {
        this.pageRecommend = pageRecommend;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPageRecommendCheck() {
        return pageRecommendCheck;
    }

    public void setPageRecommendCheck(String pageRecommendCheck) {
        this.pageRecommendCheck = pageRecommendCheck;
    }

    public String getCreateDateStr() {
        if(createDate == null){
            return "";
        }
        return convert(createDate);
    }

    public String convert(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoShowUrl() {
        return videoShowUrl;
    }

    public void setVideoShowUrl(String videoShowUrl) {
        this.videoShowUrl = videoShowUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceDes() {
        return provinceDes;
    }

    public void setProvinceDes(String provinceDes) {
        this.provinceDes = provinceDes;
    }

    public String getCityDes() {
        return cityDes;
    }

    public void setCityDes(String cityDes) {
        this.cityDes = cityDes;
    }

    public String getAreaDes() {
        return areaDes;
    }

    public void setAreaDes(String areaDes) {
        this.areaDes = areaDes;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }
}