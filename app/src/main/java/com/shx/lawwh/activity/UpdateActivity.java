package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.ResponseVersionInfo;
import com.shx.lawwh.utils.DeviceUtils;

/**
 * Created by adm on 2018/2/4.
 */

public class UpdateActivity extends BaseActivity {
    private TextView mCurrentVersion;
    private TextView mNewVersion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getTopbar().setTitle("检查更新");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mCurrentVersion= (TextView) findViewById(R.id.tv_currentVersion);
        mNewVersion= (TextView) findViewById(R.id.tv_newVersion);
        ResponseVersionInfo versionInfo= (ResponseVersionInfo) getIntent().getSerializableExtra("versionInfo");
        mCurrentVersion.setText("版本信息："+ DeviceUtils.getVersionName(    this));
        mNewVersion.setText("版本信息："+versionInfo.getVersionName());
    }

}
