package com.shx.lawwh.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuan on 2018/2/9.
 */

public class ResponseVersionInfo implements Serializable{
//    {\"createTime\":1517924901000,\"createUser\":1,\"description\":\"测试更新检查\",\"id\":1,\"versionCode\":2,\"versionName\":\"1.0\"}
    private Date createTime;
    private Integer createUser;
    private String description;
    private String id;
    private String versionCode;
    private String versionName;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
