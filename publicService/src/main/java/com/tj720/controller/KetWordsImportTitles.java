package com.tj720.controller;

public enum KetWordsImportTitles {
    /**
     * 导入数据模板
     */
    intercept_name("敏感词");
    String title;

    KetWordsImportTitles(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
