package com.tj720.dto;

/**
 * @Auther: caiming
 * @Date: 2018/9/27 17:49
 * @Description: 首页查找dto
 */
public class IndexSearchDto {
    private String id;

    private String name;

    private String type;

    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
