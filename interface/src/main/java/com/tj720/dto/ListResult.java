package com.tj720.dto;

import java.util.List;

public class ListResult {

    //年
    private String keyYear;

    //月/日
    private String keyDay;



    private List<JsonDate> list ;

    public List<JsonDate> getList() {
        return list;
    }

    public void setList(List<JsonDate> list) {
        this.list = list;
    }

    public String getKeyYear() {
        return keyYear;
    }

    public void setKeyYear(String keyYear) {
        this.keyYear = keyYear;
    }

    public String getKeyDay() {
        return keyDay;
    }

    public void setKeyDay(String keyDay) {
        this.keyDay = keyDay;
    }



}
