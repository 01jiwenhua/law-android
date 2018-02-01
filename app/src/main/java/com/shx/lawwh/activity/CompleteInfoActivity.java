package com.shx.lawwh.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.databinding.ActivityCompleteinfoBinding;

/**
 * Created by zhou on 2018/1/31.
 * 注册完善个人信息界面
 */

public class CompleteInfoActivity extends BaseActivity implements View.OnClickListener{

    private ActivityCompleteinfoBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_completeinfo);
        initView();
        initData();
    }

    private void initView(){
        mBinding.llAvatar.setOnClickListener(this);
        mBinding.llCompany.setOnClickListener(this);
        mBinding.llDepartment.setOnClickListener(this);
        mBinding.llDistrict.setOnClickListener(this);
        mBinding.llDuty.setOnClickListener(this);
        mBinding.llLicenseType.setOnClickListener(this);
        mBinding.llSex.setOnClickListener(this);
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_avatar:
                break;
            case R.id.ll_company:
                break;
            case R.id.ll_department:
                break;
            case R.id.ll_district:
                break;
            case R.id.ll_duty:
                break;
            case R.id.ll_licenseType:
                break;
            case R.id.ll_sex:
                break;
        }
    }
}
