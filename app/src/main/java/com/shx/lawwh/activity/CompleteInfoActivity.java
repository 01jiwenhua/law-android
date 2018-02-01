package com.shx.lawwh.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.databinding.ActivityCompleteinfoBinding;
import com.shx.lawwh.entity.request.RequestRegisterInfo;
import com.shx.lawwh.entity.response.ResponseCompanyList;
import com.shx.lawwh.entity.response.ResponseDepartmentList;
import com.shx.lawwh.entity.response.ResponseJobList;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;

import static android.R.attr.data;

/**
 * Created by zhou on 2018/1/31.
 * 注册完善个人信息界面
 */

public class CompleteInfoActivity extends BaseActivity implements View.OnClickListener{

    private ActivityCompleteinfoBinding mBinding;
    private RequestRegisterInfo registerInfo;
    private List<ResponseCompanyList> companyList;
    private List<ResponseDepartmentList> departmentList;
    private List<ResponseJobList> jobList;
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
        mBinding.btnFinish.setOnClickListener(this);
    }

    private void initData(){
        registerInfo=new RequestRegisterInfo();
        registerInfo.setPhone(getIntent().getStringExtra("phone"));
        companyList=new ArrayList<>();
        departmentList=new ArrayList<>();
        jobList=new ArrayList<>();
        mBinding.setRegisterInfo(registerInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_avatar:
                break;
            case R.id.ll_company:
                RequestCenter.getCompanyList(this);
                break;
            case R.id.ll_department:
                RequestCenter.getDepartmentList("1",this);
                break;
            case R.id.ll_district:
                registerInfo.setRegionId("1");
                break;
            case R.id.ll_duty:
                RequestCenter.getJobList(this);
                break;
            case R.id.ll_licenseType:
                pickLicenseType();
                break;
            case R.id.ll_sex:
                pickSex();
                break;
            case R.id.btn_finish:
                RequestCenter.regist(registerInfo,this);
                break;
        }
    }

    /**
     * 选择证件类型
     * */
    private void pickLicenseType(){
        final String [] type =new String[]{"身份证"};
        OptionPicker picker = new OptionPicker(this,type);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                mBinding.tvLicenseType.setText(type[index]);
                registerInfo.setUserType(String.valueOf(index+1));
            }
        });
        picker.show();
    }

    /**
     * 选择性别
     * */
    private void pickSex(){
        final String [] sex =new String[]{"女","男"};
        OptionPicker picker = new OptionPicker(this,sex);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
              mBinding.tvSex.setText(sex[index]);
              registerInfo.setSex(String.valueOf(index));
            }
        });
        picker.show();
    }

    /**选择公司*/
    private void pickCompany(){
        SinglePicker<ResponseCompanyList> picker = new SinglePicker<>(this, companyList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseCompanyList>() {
            @Override
            public void onItemPicked(int index, ResponseCompanyList item) {
                mBinding.tvCompany.setText(item.getName());
            }
        });
        picker.show();
    }

    /**选择部门*/
    private void pickDepartment(){
        SinglePicker<ResponseDepartmentList> picker = new SinglePicker<>(this, departmentList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseDepartmentList>() {
            @Override
            public void onItemPicked(int index, ResponseDepartmentList item) {
                mBinding.tvDepartment.setText(item.getName());
                registerInfo.setDepartmentId("1");
            }
        });
        picker.show();
    }

    /**
     * 选择职位
     * */
    private void pickJob(){
        SinglePicker<ResponseJobList> picker = new SinglePicker<>(this, jobList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseJobList>() {
            @Override
            public void onItemPicked(int index, ResponseJobList item) {
                mBinding.tvDuty.setText(item.getName());
                registerInfo.setJobId("1");
            }
        });
        picker.show();
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_COMPANY_LIST)){
            JSONObject mainData = respose.getMainData();
            companyList=MyJSON.parseArray(mainData.getString("companyList"),ResponseCompanyList.class);
            pickCompany();
        }else if(requestUrl.equals(RequestCenter.GET_DEPARTMENT_LIST)){
            JSONObject mainData = respose.getMainData();
            departmentList=MyJSON.parseArray(mainData.getString("departmentList"),ResponseDepartmentList.class);
            pickDepartment();

        }else if(requestUrl.equals(RequestCenter.GET_JOB_LIST)){
            JSONObject mainData = respose.getMainData();
            jobList=MyJSON.parseArray(mainData.getString("jobList"),ResponseJobList.class);
            pickJob();
        }else if(requestUrl.equals(RequestCenter.REGIST)){
            ToastUtil.getInstance().toastInCenter(CompleteInfoActivity.this,"注册成功！");
        }
        return super.doSuccess(respose, requestUrl);
    }
}
