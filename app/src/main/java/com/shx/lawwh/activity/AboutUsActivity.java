package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;

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
                startActivity(new Intent(this,VersionActivity.class));
                break;
            case R.id.ll_opinion:
                startActivity(new Intent(this,ProtocolActivity.class));
                break;
            case R.id.ll_protocol:
                break;
        }
    }
}
