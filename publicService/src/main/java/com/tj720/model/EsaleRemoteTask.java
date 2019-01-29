package com.tj720.model;

import java.io.Serializable;

/**
 * 合作方事项
 *
 * @author
 */
public class EsaleRemoteTask implements Serializable {

    private static final long serialVersionUID = 1L;
    private String PROCESSKEY;//流程编号
    private String PROCESSNAME;//流程名称
    private String TASKNAME;//任务名称
    private String TASKID;//任务id
    private String ARRIVETIME;//任务到达时间
    private String MODELANDVIEW;//跳转链接
    private String BUSINESSKEY;//业务编码

    public String getPROCESSKEY() {
        return PROCESSKEY;
    }

    public void setPROCESSKEY(String PROCESSKEY) {
        this.PROCESSKEY = PROCESSKEY;
    }

    public String getPROCESSNAME() {
        return PROCESSNAME;
    }

    public void setPROCESSNAME(String PROCESSNAME) {
        this.PROCESSNAME = PROCESSNAME;
    }

    public String getTASKNAME() {
        return TASKNAME;
    }

    public void setTASKNAME(String TASKNAME) {
        this.TASKNAME = TASKNAME;
    }

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getARRIVETIME() {
        return ARRIVETIME;
    }

    public void setARRIVETIME(String ARRIVETIME) {
        this.ARRIVETIME = ARRIVETIME;
    }

    public String getMODELANDVIEW() {
        return MODELANDVIEW;
    }

    public void setMODELANDVIEW(String MODELANDVIEW) {
        this.MODELANDVIEW = MODELANDVIEW;
    }

    public String getBUSINESSKEY() {
        return BUSINESSKEY;
    }

    public void setBUSINESSKEY(String BUSINESSKEY) {
        this.BUSINESSKEY = BUSINESSKEY;
    }

    public EsaleRemoteTask() {
    }

    public EsaleRemoteTask(String PROCESSNAME, String TASKNAME, String TASKID, String ARRIVETIME) {
        this.PROCESSNAME = PROCESSNAME;
        this.TASKNAME = TASKNAME;
        this.TASKID = TASKID;
        this.ARRIVETIME = ARRIVETIME;

    }
}