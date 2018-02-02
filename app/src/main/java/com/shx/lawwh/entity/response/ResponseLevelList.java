package com.shx.lawwh.entity.response;

/**
 * Created by zhou on 2018/2/2.
 */

public class ResponseLevelList {

    /**
     * categoryCode : flfg
     * categoryName : 法律法规
     * code : bmgz
     * id : 132
     * name : 部门规章
     */

    private String categoryCode;
    private String categoryName;
    private String code;
    private int id;
    private String name;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
