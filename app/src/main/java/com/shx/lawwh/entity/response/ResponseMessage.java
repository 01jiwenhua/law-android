package com.shx.lawwh.entity.response;

import java.io.Serializable;

/**
 * Created by adm on 2018/2/6.
 */

public class ResponseMessage implements Serializable {


    private static final long serialVersionUID = 7697250003476001428L;
    /**
     * content : 这个是测试消息的内容必须要长长长长长长长长长长长长长长长长长长长长长长长长长长长长长
     * id : 1
     * publishDepartment : 一处
     * publishOrg : 北京市安监局
     * publishTime : 1517923613000
     * status : 1
     * title : 测试消息
     * type : 1
     */

    private String content;
    private int id;
    private String publishDepartment;
    private String publishOrg;
    private long publishTime;
    private int status;
    private String title;
    private int type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublishDepartment() {
        return publishDepartment;
    }

    public void setPublishDepartment(String publishDepartment) {
        this.publishDepartment = publishDepartment;
    }

    public String getPublishOrg() {
        return publishOrg;
    }

    public void setPublishOrg(String publishOrg) {
        this.publishOrg = publishOrg;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
