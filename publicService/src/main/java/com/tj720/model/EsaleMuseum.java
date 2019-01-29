package com.tj720.model;

import com.tj720.model.common.MipAttachment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * esale_museum
 *
 * @author
 */
public class EsaleMuseum implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 附属博物馆id
     */
    private String upId;

    /**
     * 博物馆名称
     */
    private String museumName;

    /**
     * 博物馆类型
     */
    private String museumType;

    /**
     * 门票
     */
    private String ticket;

    /**
     * 开放时间
     */
    private String openTime;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 全景地址
     */
    private String viewAddress;

    /**
     * 图片id，以逗号隔开
     */
    private String pictureids;

    /**
     * 数据状态（0：已删除，1:正常）新建默认为1正常
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

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getMuseumType() {
        return museumType;
    }

    public void setMuseumType(String museumType) {
        this.museumType = museumType;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
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

    public String getViewAddress() {
        return viewAddress;
    }

    public void setViewAddress(String viewAddress) {
        this.viewAddress = viewAddress;
    }

    public String getPictureids() {
        return pictureids;
    }

    public void setPictureids(String pictureids) {
        this.pictureids = pictureids;
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

    private int collectionCount;//藏品数量

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    private String mainPicUrl;//主图url

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    private List<MipAttachment> picList;//图片集合

    public List<MipAttachment> getPicList() {
        return picList;
    }

    public void setPicList(List<MipAttachment> picList) {
        this.picList = picList;
    }

}