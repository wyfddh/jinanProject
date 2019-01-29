package com.tj720.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * esale_pub_user
 * @author 
 */
public class PCEsalePubUser implements Serializable {

    //报名时间
    private String enrollTime;
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     *  性别(1：男 0：女)
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 常用邮箱
     */
    private String email;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 家长姓名
     */
    private String parentName;

    /**
     * 家长电话
     */
    private String parentTelphone;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 密码
     */
    private String password;

    /**
     * 操作时间
     */
    private Date operationDate;

    /**
     * 是否青少年用户（1：是 0：否）
     */
    private String isYoung;

    /**
     * 冻结时间
     */
    private Date freezTime;

    /**
     * 数据状态（0：已删除，1:正常，2：已冻结）新建默认为1正常
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
     * 个人介绍
     */
    private String description;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentTelphone() {
        return parentTelphone;
    }

    public void setParentTelphone(String parentTelphone) {
        this.parentTelphone = parentTelphone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getIsYoung() {
        return isYoung;
    }

    public void setIsYoung(String isYoung) {
        this.isYoung = isYoung;
    }

    public Date getFreezTime() {
        return freezTime;
    }

    public void setFreezTime(Date freezTime) {
        this.freezTime = freezTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        this.enrollTime = enrollTime;
    }
}