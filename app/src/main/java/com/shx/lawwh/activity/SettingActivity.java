package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.utils.CacheManager;
import com.shx.lawwh.utils.SharedPreferencesUtil;

/**
 * Created by adm on 2018/2/4.
 */

public class SettingActivity extends BaseActivity {
    private TextView cacheTv;
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
        try {
            String cacheSize=CacheManager.getTotalCacheSize(this);
            cacheTv= (TextView) findViewById(R.id.tv_cache);
            cacheTv.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil.saveObject(SettingActivity.this, CommonValues.USERINFO,null);
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
            }
        });
        findViewById(R.id.ll_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheManager.clearAllCache(SettingActivity.this);
                ToastUtil.getInstance().toastInCenter(SettingActivity.this,"清理成功！");
                cacheTv.setText("0 Byte");
            }
        });
        findViewById(R.id.ll_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,MessageManagerActivity.class));
            }
        });
    }
}
