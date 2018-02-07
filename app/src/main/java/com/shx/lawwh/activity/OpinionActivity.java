package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;

/**
 * Created by zhou on 2018/2/7.
 */

public class OpinionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        getTopbar().setTitle("意见与反馈");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTopbar().setRightText("提交");
        getTopbar().setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
