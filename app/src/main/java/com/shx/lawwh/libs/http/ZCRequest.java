package com.shx.lawwh.libs.http;

import java.io.Serializable;

/**
 * Created by 邵鸿轩 on 2016/3/21.
 */
public class ZCRequest implements Serializable {
    private static final long serialVersionUID = -6112816167644453217L;
    private ZCRequestData request;
    public ZCRequest() {
        request = new ZCRequestData();

    }
    public void putParams(String key, Object value) {
        request.getParamsMap().put(key, value);
    }

    public ZCRequestData getRequest() {
        return request;
    }

    public void setRequest(ZCRequestData request) {
        this.request = request;
    }
}
