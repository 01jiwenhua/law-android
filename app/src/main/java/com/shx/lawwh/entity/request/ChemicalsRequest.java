package com.shx.lawwh.entity.request;

/**
 * Created by xuan on 2017/12/25.
 */

public class ChemicalsRequest {
    private Integer page;
    private Integer pageSize;
    private String name;

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
