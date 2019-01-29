package com.tj720.dto;

/**
 * 博物馆信息dto
 * Created by MyPC on 2018/9/26.
 */
public class MuseumBaseInfoDto {

    private String id;

    private String museumName;

    private String recommend;

    private String pictureUrl;

    private String region;  //地区
    private String openTime;    //开放时间
    private String address;     //场馆地址
    private String siteIntroduct;     //场馆介绍
    private String history;     //历史沿革
    private String showView;        //展览概况
    private String collection;      //藏品介绍
    private String guide;       //参观指南
    private String latitude;       //经度
    private String longitude;       //纬度
    private String localIntroduceVideo;       //地方推介视频
    private String culturalPromote;     //文化振兴介绍

    private String proviceName;     //省份名称
    private String provicePinyin;       //省份拼音

    private String name1;   //市名称
    private String name2;       //区名称

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getProvicePinyin() {
        return provicePinyin;
    }

    public void setProvicePinyin(String provicePinyin) {
        this.provicePinyin = provicePinyin;
    }

    public String getLocalIntroduceVideo() {
        return localIntroduceVideo;
    }

    public void setLocalIntroduceVideo(String localIntroduceVideo) {
        this.localIntroduceVideo = localIntroduceVideo;
    }

    public String getCulturalPromote() {
        return culturalPromote;
    }

    public void setCulturalPromote(String culturalPromote) {
        this.culturalPromote = culturalPromote;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteIntroduct() {
        return siteIntroduct;
    }

    public void setSiteIntroduct(String siteIntroduct) {
        this.siteIntroduct = siteIntroduct;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getShowView() {
        return showView;
    }

    public void setShowView(String showView) {
        this.showView = showView;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
