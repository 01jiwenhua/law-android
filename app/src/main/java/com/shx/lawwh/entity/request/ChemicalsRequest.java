package com.shx.lawwh.entity.request;

import java.io.Serializable;

/**
 * Created by xuan on 2017/12/25.
 */

public class ChemicalsRequest implements Serializable{
    private Integer page;
    private Integer pageSize;
    private String name;
    private String status;
    private String color;
    private String smell;
    private String taste;
    private String specific_air;
    private String specific_water;
    private String ph;
    private String transparency;
    private String nervous;
    private String eye;
    private String ear;
    private String mouth_throat;
    private String cardiovascular;
    private String respiratory;
    private String gastro_urinary;
    private String skin;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getSpecific_air() {
        return specific_air;
    }

    public void setSpecific_air(String specific_air) {
        this.specific_air = specific_air;
    }

    public String getSpecific_water() {
        return specific_water;
    }

    public void setSpecific_water(String specific_water) {
        this.specific_water = specific_water;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getNervous() {
        return nervous;
    }

    public void setNervous(String nervous) {
        this.nervous = nervous;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getEar() {
        return ear;
    }

    public void setEar(String ear) {
        this.ear = ear;
    }

    public String getMouth_throat() {
        return mouth_throat;
    }

    public void setMouth_throat(String mouth_throat) {
        this.mouth_throat = mouth_throat;
    }

    public String getCardiovascular() {
        return cardiovascular;
    }

    public void setCardiovascular(String cardiovascular) {
        this.cardiovascular = cardiovascular;
    }

    public String getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(String respiratory) {
        this.respiratory = respiratory;
    }

    public String getGastro_urinary() {
        return gastro_urinary;
    }

    public void setGastro_urinary(String gastro_urinary) {
        this.gastro_urinary = gastro_urinary;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
