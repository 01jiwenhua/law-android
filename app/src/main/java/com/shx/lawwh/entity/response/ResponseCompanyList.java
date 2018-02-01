package com.shx.lawwh.entity.response;

/**
 * Created by adm on 2018/2/2.
 */

public class ResponseCompanyList {

    /**
     * code : AJ
     * createTime : 1517366033000
     * createUser : 1
     * id : 1
     * name : 北京市安全生产监督管理局
     * nameEn : bjaj
     * status : 1
     * telNo : 010-7369985
     * updateTime : 1517366036000
     * updateUser : 1
     */

    private String code;
    private long createTime;
    private int createUser;
    private int id;
    private String name;
    private String nameEn;
    private int status;
    private String telNo;
    private long updateTime;
    private int updateUser;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
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

    public String  toString(){
        return this.name;
    }
}
