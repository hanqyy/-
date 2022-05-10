package com.example.webhr.model.vo;

import java.util.List;

/**
 * 后台数据转换成echarts前端数据
 */
public class BarVO {
    private Integer primary; //小学
    private Integer junior; //初中
    private Integer higher; //高中
    private Integer specialty; //大专
    private Integer undergraduate ; //本科
    private Integer master; //硕士
    private Integer doctor; //博士
    private Integer other;

    public Integer getPrimary() {
        return primary;
    }

    public void setPrimary(Integer primary) {
        this.primary = primary;
    }

    public Integer getJunior() {
        return junior;
    }

    public void setJunior(Integer junior) {
        this.junior = junior;
    }

    public Integer getHigher() {
        return higher;
    }

    public void setHigher(Integer higher) {
        this.higher = higher;
    }

    public Integer getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Integer specialty) {
        this.specialty = specialty;
    }

    public Integer getUndergraduate() {
        return undergraduate;
    }

    public void setUndergraduate(Integer undergraduate) {
        this.undergraduate = undergraduate;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
