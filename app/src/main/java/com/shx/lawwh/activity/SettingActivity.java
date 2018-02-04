package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.utils.SharedPreferencesUtil;

/**
 * Created by adm on 2018/2/4.
 */

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getTopbar().setTitle("设置");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil.saveObject(SettingActivity.this, CommonValues.USERINFO,null);
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
