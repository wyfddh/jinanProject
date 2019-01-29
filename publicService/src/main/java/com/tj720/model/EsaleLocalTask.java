package com.tj720.model;

import java.io.Serializable;

/**
 * 本系统事项
 * @author
 */
public class EsaleLocalTask implements Serializable {

    private static final long serialVersionUID = 1L;
    private  String id;
    private String dataName;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}