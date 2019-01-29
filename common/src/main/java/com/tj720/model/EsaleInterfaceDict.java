package com.tj720.model;

import java.io.Serializable;

/**
 * 藏品字典
 * @author 杜昶
 */
public class EsaleInterfaceDict implements Serializable {
    private static final long serialVersionUID = -6203587872892579245L;

    private String id;

    private String typecode;

    private String typename;

    private String typegroupid;

    private String typepid;

    private String value;

    private String sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypegroupid() {
        return typegroupid;
    }

    public void setTypegroupid(String typegroupid) {
        this.typegroupid = typegroupid;
    }

    public String getTypepid() {
        return typepid;
    }

    public void setTypepid(String typepid) {
        this.typepid = typepid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
