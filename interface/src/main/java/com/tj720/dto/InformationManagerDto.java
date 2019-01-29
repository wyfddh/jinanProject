package com.tj720.dto;

/**
 * 资讯
 * Created by MyPC on 2018/9/26.
 */
public class InformationManagerDto {
    private String id;

    private String theme;

    private String museumeName;

    private String showDate;

    private String description;

    private String pictureUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMuseumeName() {
        return museumeName;
    }

    public void setMuseumeName(String museumeName) {
        this.museumeName = museumeName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
