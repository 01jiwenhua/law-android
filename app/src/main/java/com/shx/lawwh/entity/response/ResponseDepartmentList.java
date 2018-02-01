package com.shx.lawwh.entity.response;

/**
 * Created by adm on 2018/2/2.
 */

public class ResponseDepartmentList {

    /**
     * companyId : 1
     * createTime : 1517366334000
     * createUser : 1
     * id : 1
     * name : 安全监督管理一处
     * status : 1
     * updateTime : 1517366337000
     * updateUser : 1
     */

    private int companyId;
    private long createTime;
    private int createUser;
    private int id;
    private String name;
    private int status;
    private long updateTime;
    private int updateUser;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
