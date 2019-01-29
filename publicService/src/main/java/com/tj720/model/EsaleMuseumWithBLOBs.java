package com.tj720.model;

import java.io.Serializable;
import java.util.List;

/**
 * esale_museum
 * @author 
 */
public class EsaleMuseumWithBLOBs extends EsaleMuseum implements Serializable {
    /**
     * 场馆介绍
     */
    private String introduct;

    /**
     * 参观须知
     */
    private String guide;


    private static final long serialVersionUID = 1L;

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

}