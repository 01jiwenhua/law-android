package com.shx.lawwh.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.databinding.ActivityUserInfoBinding;
import com.shx.lawwh.entity.response.ResponseCompanyList;
import com.shx.lawwh.entity.response.ResponseDepartmentList;
import com.shx.lawwh.entity.response.ResponseJobList;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.DateUtil;
import com.shx.lawwh.utils.RegexpUtils;
import com.shx.lawwh.utils.SharedPreferencesUtil;
import com.shx.lawwh.view.AddressInitTask;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;

import static com.shx.lawwh.utils.SharedPreferencesUtil.readObject;

/**
 * Created by adm on 2018/2/4.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUserInfoBinding mBinding;
    private ResponseUserInfo userInfo;
    private List<ResponseCompanyList> companyList;
    private List<ResponseDepartmentList> departmentList;
    private List<ResponseJobList> jobList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
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
        getTopbar().setRightText("保存");
        getTopbar().setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo.getDepartment_id()==0 || userInfo.getEmail()==null
                        || userInfo.getId_no()==null || userInfo.getJob_id()==0
                        || userInfo.getLicense_type()==0 || userInfo.getPhone()==null
                        || userInfo.getReal_name()==null || userInfo.getRegion_id()==0
                        || userInfo.getSex()==-1) {
                    ToastUtil.getInstance().toastInCenter(UserInfoActivity.this,"请将信息填写完整！");
                } else {
                    if(RegexpUtils.regexEdttext(UserInfoActivity.this,mBinding.etIdNo,mBinding.etMail)) {
                        RequestCenter.updateUserInfo(userInfo, UserInfoActivity.this);
                    }
                }
            }
        });
        initListerner();
    }

    private void initListerner() {
        mBinding.llAvatar.setOnClickListener(this);
        mBinding.llCompany.setOnClickListener(this);
        mBinding.llDepartment.setOnClickListener(this);
        mBinding.llDistrict.setOnClickListener(this);
        mBinding.llDuty.setOnClickListener(this);
        mBinding.llLicenseType.setOnClickListener(this);
        mBinding.llSex.setOnClickListener(this);
        mBinding.llPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_avatar:
                break;
            case R.id.ll_company:
                RequestCenter.getCompanyList(this);
                break;
            case R.id.ll_department:
                RequestCenter.getDepartmentList("1", this);
                break;
            case R.id.ll_district:
                pickAddress();
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
            case R.id.ll_phone:
                startActivityForResult(new Intent(this,UpdatePhoneActivity.class),0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==0){
                String phone=data.getStringExtra("phone");
                mBinding.tvPhone.setText(phone);
                userInfo.setPhone(phone);
            }
        }
    }

    /**
     * 选择地区
     * */
    public void pickAddress() {
        new AddressInitTask(this, new AddressInitTask.InitCallback() {
            @Override
            public void onDataInitFailure() {
                ToastUtil.getInstance().toastInCenter(UserInfoActivity.this,"数据初始化失败");
            }

            @Override
            public void onDataInitSuccess(ArrayList<Province> provinces) {
                AddressPicker picker = new AddressPicker(UserInfoActivity.this, provinces);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        String provinceName = province.getName();
                        String cityName = "";
                        if (city != null) {
                            cityName = city.getName();
                            //忽略直辖市的二级名称
                            if (cityName.equals("市辖区") || cityName.equals("市") || cityName.equals("县")) {
                                cityName = "";
                            }
                        }
                        String countyName = "";
                        if (county != null) {
                            countyName = county.getName();
                        }
                        mBinding.tvDistrict.setText(provinceName+","+countyName);
                        userInfo.setRegion_id(1);
                    }
                });
                picker.show();
            }
        }).execute();
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
                userInfo.setLicense_type(index+1);
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
                userInfo.setSex(index);
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
                userInfo.setDepartment_id(1);
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
                userInfo.setJob_id(1);
            }
        });
        picker.show();
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_COMPANY_LIST)){
            JSONObject mainData = respose.getMainData();
            companyList= MyJSON.parseArray(mainData.getString("companyList"),ResponseCompanyList.class);
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
            ToastUtil.getInstance().toastInCenter(this,"修改成功！");
            RequestCenter.getUserInfo(String.valueOf(userInfo.getId()),this);
        }else if(requestUrl.equals(RequestCenter.GET_USERINFO)){
            JSONObject mainData = respose.getMainData();
            ResponseUserInfo userInfo=MyJSON.parseObject(mainData.getString("userInfo"),ResponseUserInfo.class);
            SharedPreferencesUtil.saveObject(UserInfoActivity.this, CommonValues.USERINFO,userInfo);
        }
        return super.doSuccess(respose, requestUrl);
    }
}
