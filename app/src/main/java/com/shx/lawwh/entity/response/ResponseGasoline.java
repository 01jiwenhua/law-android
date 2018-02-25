package com.shx.lawwh.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by adm on 2018/2/8.
 */

public class ResponseGasoline implements Serializable{

    private ResponseGasolineItem parent;
    private List<ResponseGasolineItem> child;

    public ResponseGasolineItem getParent() {
        return parent;
    }

    public void setParent(ResponseGasolineItem parent) {
        this.parent = parent;
    }

    public List<ResponseGasolineItem> getChild() {
        return child;
    }

    public void setChild(List<ResponseGasolineItem> child) {
        this.child = child;
    }
}
