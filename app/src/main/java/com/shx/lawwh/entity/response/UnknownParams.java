package com.shx.lawwh.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuan on 2017/12/25.
 */

public class UnknownParams implements Serializable{
    /**
     * "id":36,"name":null,
     * "code":null,"
     * categoryCode":"B_TASTE",
     * "categoryName":"味道","createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"status":null}
     */
    private String id;
    private String name;
    private String code;
    private String categoryCode;
    private String categoryName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
