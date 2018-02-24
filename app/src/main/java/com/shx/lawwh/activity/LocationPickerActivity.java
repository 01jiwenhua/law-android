package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

/**
 * Created by admin on 2018/2/22.
 */

public class LocationPickerActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
        String parentCode=getIntent().getStringExtra("parentCode");
        RequestCenter.getArchitecture("", parentCode, "GB 50156-2012", this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject mainData = respose.getMainData();
            List<ResponseGasolineItem> responseGasolineList = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasolineItem.class);
            showItemCchoice(responseGasolineList);
        }
        return super.doSuccess(respose, requestUrl);
    }

    private void showItemCchoice(List<ResponseGasolineItem> items) {
        SinglePicker<ResponseGasolineItem> picker = new SinglePicker<>(this, items);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseGasolineItem>() {
            @Override
            public void onItemPicked(int i, ResponseGasolineItem item) {
                Intent intent = new Intent();

                intent.putExtra("result", item); //将计算的值回传回去
               //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(RESULT_OK, intent);
                finish(); //结束当前的activity的生命周期
            }
        });
        picker.show();
    }
}
