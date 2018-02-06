package com.shx.lawwh.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.databinding.ActivityNewsDetailsBinding;
import com.shx.lawwh.entity.response.ResponseMessage;

/**
 * Created by adm on 2018/2/6.
 */

public class NewsDetailsActivity extends BaseActivity {

    private ActivityNewsDetailsBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_news_details);
        ResponseMessage message= (ResponseMessage) getIntent().getSerializableExtra("message");
        mBinding.setMessage(message);
        if(message.getType()==1) {
            getTopbar().setTitle("系统消息");
        }else{
            getTopbar().setTitle("工作通知");
        }
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
