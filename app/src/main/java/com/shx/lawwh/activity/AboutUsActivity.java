package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.SystemConfig;

/**
 * Created by adm on 2018/2/4.
 */

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout versionLL,opinionLL,protocolLL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    private void initView(){
        getTopbar().setTitle("关于我们");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.ll_versions).setOnClickListener(this);
        findViewById(R.id.ll_opinion).setOnClickListener(this);
        findViewById(R.id.ll_protocol).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_versions:
                Intent versionIntent=new Intent(this,CommonWebActivity.class);
                versionIntent.putExtra("title","版本说明");
                versionIntent.putExtra("url", SystemConfig.VERSIONURL);

                startActivity(versionIntent);
                break;
            case R.id.ll_opinion:
                startActivity(new Intent(this,OpinionActivity.class));
                break;
            case R.id.ll_protocol:
                Intent proIntent=new Intent(this,CommonWebActivity.class);
                proIntent.putExtra("title","服务协议");
                proIntent.putExtra("url", SystemConfig.SERVICEITEMURL);
                startActivity(proIntent);
                break;
        }
    }
}
