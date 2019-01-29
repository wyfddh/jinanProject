package com.tj720.model.common;


import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseVO {

    /**
     * primary key
     */
    String id;
    String createBy;
    Date createDate;
    String updateBy;

    Date updateDate;

    String strCreateTime;
    String strUpdatTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStrCreateTime() {
        if(createDate == null){
            return "";
        }
        return convert(createDate);
    }


    public String getStrUpdatTime() {
        if(updateDate == null){
            return "";
        }
        return convert(updateDate);
    }

    public String convert(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
