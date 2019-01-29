package com.tj720.dto;

/**
 * @Auther: caiming
 * @Date: 2018/9/27 16:02
 * @Description: 文创产品
 */
public class CreativeProductDto {

    private String id;
    private String name;
    private String pictureUrl;

    private String museumId;

    private String museumName;
    private String type;        //设计元素/产品类别
    private String developIdea;         //产品简介/设计理念

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevelopIdea() {
        return developIdea;
    }

    public void setDevelopIdea(String developIdea) {
        this.developIdea = developIdea;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
