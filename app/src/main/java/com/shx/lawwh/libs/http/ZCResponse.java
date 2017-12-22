package com.shx.lawwh.libs.http;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 2016/3/21.
 */
public class ZCResponse {
    private ZCResponseHeader header;
    private ZCResponseData response;

    public ZCResponseData getResponse() {
        return response;
    }

    public void setResponse(ZCResponseData response) {
        this.response = response;
    }

    public ZCResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ZCResponseHeader header) {
        this.header = header;
    }

    @Nullable
    public JSONObject getMainData(){
        try {
            JSONObject object= MyJSON.parseObject( response.getResult().getData());
            return object;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //获取每页显示条数
    public int getSize(){
        return response.getResult().getSize();
    }
    //获取当前页
    public int getcurrentPage(){
        return response.getResult().getCurrentPage();
    }
}
