package com.shx.lawwh.entity.response;

import java.util.List;

/**
 * Created by xuan on 2017/12/25.
 */

public class ChemicalsDetailsResponse {
    private List<DetailsParams> list;
    private String title;


    public List<DetailsParams> getList() {
        return list;
    }

    public void setList(List<DetailsParams> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
