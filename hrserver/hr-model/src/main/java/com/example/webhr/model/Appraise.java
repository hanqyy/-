package com.example.webhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Appraise {
    private Integer id;

    private Integer eid;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date appDate;

    private String appResult;

    private String appContent;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppResult() {
        return appResult;
    }

    public void setAppResult(String appResult) {
        this.appResult = appResult;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppCntent(String appContent) {
        this.appContent = appContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}