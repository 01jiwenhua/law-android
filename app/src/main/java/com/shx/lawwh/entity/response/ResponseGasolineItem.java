package com.shx.lawwh.entity.response;

/**
 * Created by zhou on 2018/2/7.
 */

public class ResponseGasolineItem {

    /**
     * code : 1020000000000
     * id : 17
     * level : 2
     * name : 站外建（构）筑物
     * parentCode : 1000000000000
     * standard : GB 50156-2012
     */

    private String code;
    private int id;
    private int level;
    private String name;
    private String parentCode;
    private String standard;
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return name;
    }
}
