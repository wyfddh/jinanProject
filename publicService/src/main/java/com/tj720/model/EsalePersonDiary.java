package com.tj720.model;

import java.io.Serializable;
import java.util.Date;

/**
 * esale_person_diary
 * @author 
 */
public class EsalePersonDiary implements Serializable {
    private String id;

    /**
     * 所属机构
     */
    private String orgid;
    private String department_name;

    /**
     * 创建人
     */
    private String creator;
    private String creatorName;

    /**
     * 创建时间
     */
    private Date creattime;

    private String showDate;
    private String startDate;
    private String endDate;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否补交（0-否  1-是）
     */
    private Short issupply;

    /**
     * 内容
     */
    private String content;

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Short getIssupply() {
        return issupply;
    }

    public void setIssupply(Short issupply) {
        this.issupply = issupply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}