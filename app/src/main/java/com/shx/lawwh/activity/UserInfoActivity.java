package com.shx.lawwh.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.databinding.ActivityUserInfoBinding;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.utils.DateUtil;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import static com.shx.lawwh.utils.SharedPreferencesUtil.readObject;

/**
 * Created by adm on 2018/2/4.
 */

public class UserInfoActivity extends BaseActivity {
    private ActivityUserInfoBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
        if(userInfo==null){
            startActivity(new Intent(this,LoginActivity.class));
        }else {
            mBinding.setUserInfo(userInfo);
        }
        getTopbar().setTitle("个人资料");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
