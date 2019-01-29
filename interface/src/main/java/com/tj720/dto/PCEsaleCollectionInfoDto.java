package com.tj720.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * esale_collection_info
 * @author 
 */
public class PCEsaleCollectionInfoDto implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 藏品名称
     */
    private String collectionName;

    /**
     * 总登记号
     */
    private String totalregistrationno;

    /**
     * 图片地址(1张正视图)
     */
    private String picUrl;

    /**
     * 图片列表集合(全路径[{url1},{url2}])
     */
    private String pictureids;

    /**
     * 文物类别（字典code）；
     */
    private String collectionTypeCode;

    /**
     * 文物类别（字典中文）
     */
    private String collectionTypeDes;

    /**
     * 所属博物馆
     */
    private String museumId;

    /**
     * 藏品来源（字典项code）；
     */
    private String source;

    /**
     * 来源（字典中文）；
     */
    private String sourceName;

    /**
     * 藏品年代code
     */
    private String collectionYearCode;

    /**
     * 藏品年代描述
     */
    private String collectionYearName;

    /**
     * 外形尺寸
     */
    private String concreteLength;

    /**
     * 3d文物链接
     */
    private String threeUrl;

    /**
     * 质地
     */
    private String texture;

    /**
     * 入藏时间
     */
    private String enterTime;

    /**
     * 长
     */
    private String gsLength;

    /**
     * 宽
     */
    private String gsWidth;

    /**
     * 高
     */
    private String gsHeight;

    /**
     * 质量
     */
    private String quality;

    /**
     * 质量单位（字典项code）；
     */
    private String massUnitCode;

    /**
     * 质量单位（字典中文）
     */
    private String massUnitDes;

    /**
     * 文物级别（字典项code）；
     */
    private String culLevel;

    /**
     * 级别（字典中文）；
     */
    private String culLevelName;

    /**
     * 版本
     */
    private String version;

    /**
     * 来源（1-同步 2-后台创建）
     */
    private Boolean sourceType;

    /**
     * 热门推荐
     */
    private String hotRecommend;

    /**
     * 点击量
     */
    private long clickNum;

    /**
     * 收藏量
     */
    private long collectNum;

    /**
     * 数据状态（0：已删除，1:下线,2上线）
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
     * 藏品介绍（藏品描述）
     */
    private String collectionDescription;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTotalregistrationno() {
        return totalregistrationno;
    }

    public void setTotalregistrationno(String totalregistrationno) {
        this.totalregistrationno = totalregistrationno;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPictureids() {
        return pictureids;
    }

    public void setPictureids(String pictureids) {
        this.pictureids = pictureids;
    }

    public String getCollectionTypeCode() {
        return collectionTypeCode;
    }

    public void setCollectionTypeCode(String collectionTypeCode) {
        this.collectionTypeCode = collectionTypeCode;
    }

    public String getCollectionTypeDes() {
        return collectionTypeDes;
    }

    public void setCollectionTypeDes(String collectionTypeDes) {
        this.collectionTypeDes = collectionTypeDes;
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCollectionYearCode() {
        return collectionYearCode;
    }

    public void setCollectionYearCode(String collectionYearCode) {
        this.collectionYearCode = collectionYearCode;
    }

    public String getCollectionYearName() {
        return collectionYearName;
    }

    public void setCollectionYearName(String collectionYearName) {
        this.collectionYearName = collectionYearName;
    }

    public String getConcreteLength() {
        return concreteLength;
    }

    public void setConcreteLength(String concreteLength) {
        this.concreteLength = concreteLength;
    }

    public String getThreeUrl() {
        return threeUrl;
    }

    public void setThreeUrl(String threeUrl) {
        this.threeUrl = threeUrl;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getGsLength() {
        return gsLength;
    }

    public void setGsLength(String gsLength) {
        this.gsLength = gsLength;
    }

    public String getGsWidth() {
        return gsWidth;
    }

    public void setGsWidth(String gsWidth) {
        this.gsWidth = gsWidth;
    }

    public String getGsHeight() {
        return gsHeight;
    }

    public void setGsHeight(String gsHeight) {
        this.gsHeight = gsHeight;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMassUnitCode() {
        return massUnitCode;
    }

    public void setMassUnitCode(String massUnitCode) {
        this.massUnitCode = massUnitCode;
    }

    public String getMassUnitDes() {
        return massUnitDes;
    }

    public void setMassUnitDes(String massUnitDes) {
        this.massUnitDes = massUnitDes;
    }

    public String getCulLevel() {
        return culLevel;
    }

    public void setCulLevel(String culLevel) {
        this.culLevel = culLevel;
    }

    public String getCulLevelName() {
        return culLevelName;
    }

    public void setCulLevelName(String culLevelName) {
        this.culLevelName = culLevelName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getSourceType() {
        return sourceType;
    }

    public void setSourceType(Boolean sourceType) {
        this.sourceType = sourceType;
    }

    public String getHotRecommend() {
        return hotRecommend;
    }

    public void setHotRecommend(String hotRecommend) {
        this.hotRecommend = hotRecommend;
    }

    public long getClickNum() {
        return clickNum;
    }

    public void setClickNum(long clickNum) {
        this.clickNum = clickNum;
    }

    public long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(long collectNum) {
        this.collectNum = collectNum;
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

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        this.collectionDescription = collectionDescription;
    }

    private String museumName;//博物馆名称

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    private List<String> picList;//图片集合

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    private String isCollect;

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }
}