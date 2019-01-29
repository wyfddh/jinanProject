package com.tj720.model;

import java.io.Serializable;

/**
 * 外部接口 - 部门信息
 */
public class EsaleInterfaceDepartInfo implements Serializable {
    private static final long serialVersionUID = -3089946008118123279L;
    /** id */
    private String id;
    /** 部门名称 */
    private String departname;
    /** 部门描述 */
    private String description;
    /** 管理层级 */
    private String servicelevel;
    /** 省编码 */
    private String province;
    /** 市编码 */
    private String city;
    /** 县编码 */
    private String county;
    /** 机构编码 */
    private String departcode;
    /** 排序 */
    private String sort;
    /** 状态 */
    private String status;
    /** 单位主体ID */
    private String munitId;
    /** 上级部门ID */
    private String parentdepartid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServicelevel() {
        return servicelevel;
    }

    public void setServicelevel(String servicelevel) {
        this.servicelevel = servicelevel;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDepartcode() {
        return departcode;
    }

    public void setDepartcode(String departcode) {
        this.departcode = departcode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMunitId() {
        return munitId;
    }

    public void setMunitId(String munitId) {
        this.munitId = munitId;
    }

    public String getParentdepartid() {
        return parentdepartid;
    }

    public void setParentdepartid(String parentdepartid) {
        this.parentdepartid = parentdepartid;
    }
}
