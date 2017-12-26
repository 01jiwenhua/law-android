package com.shx.lawwh.libs.http;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邵鸿轩 on 2016/3/21.
 */
public class ZCRequest implements Serializable {
    private static final long serialVersionUID = -6112816167644453217L;
    private Map<String, Object> params = new HashMap<>();

    private String url;

    @JSONField(serialize = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void putParams(String key, Object value) {
        params.put(key, value);
    }
}
