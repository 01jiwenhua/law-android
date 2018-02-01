package com.shx.lawwh.entity.response;

/**
 * Created by adm on 2018/2/2.
 */

public class ResponseJobList {


    /**
     * code : CZ
     * id : 1
     * name : 处长
     * status : 1
     */

    private String code;
    private int id;
    private String name;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString(){
        return this.name;
    }
}
