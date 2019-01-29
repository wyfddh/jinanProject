package com.tj720.dto;

/**
 * @Auther: caiming
 * @Date: 2018/10/8 15:58
 * @Description:
 */
public class AreaDto {

    private String id;
    private String name;
    private String pinyin;
    private int museumCount;
    private int collectionCount;

    public int getMuseumCount() {
        return museumCount;
    }

    public void setMuseumCount(int museumCount) {
        this.museumCount = museumCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
