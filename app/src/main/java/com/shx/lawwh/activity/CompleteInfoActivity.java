package com.shx.lawwh.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.APPActivityManager;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.databinding.ActivityCompleteinfoBinding;
import com.shx.lawwh.entity.request.RequestRegisterInfo;
import com.shx.lawwh.entity.response.ResponseCompanyList;
import com.shx.lawwh.entity.response.ResponseDepartmentList;
import com.shx.lawwh.entity.response.ResponseJobList;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.BitmapTools;
import com.shx.lawwh.utils.RegexpUtils;
import com.shx.lawwh.utils.SharedPreferencesUtil;
import com.shx.lawwh.view.AddressInitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by zhou on 2018/1/31.
 * 注册完善个人信息界面
 */

public class CompleteInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int AVATAR_CAMAER_CODE = 101;//头像拍照
    private static final int AVATAR_PICK_CODE = 102;//头像从相册选择
    private ActivityCompleteinfoBinding mBinding;
    private RequestRegisterInfo registerInfo;
    private List<ResponseCompanyList> companyList;
    private List<ResponseDepartmentList> departmentList;
    private List<ResponseJobList> jobList;
    private String mAvatarPath;
    private File mAvatarFile;
    private View registerTip;
    //记录最终选择的地址
    private String address;
    private String[] mPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_completeinfo);
        initView();
        initData();
    }

    private void initView() {
        getTopbar().setTitle("补充个人信息");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mBinding.llAvatar.setOnClickListener(this);
        mBinding.llCompany.setOnClickListener(this);
        mBinding.llDepartment.setOnClickListener(this);
        mBinding.llDistrict.setOnClickListener(this);
        mBinding.llDuty.setOnClickListener(this);
        mBinding.llLicenseType.setOnClickListener(this);
        mBinding.llSex.setOnClickListener(this);
        mBinding.btnFinish.setOnClickListener(this);

    }

    private void initData() {
        registerInfo = new RequestRegisterInfo();
        registerInfo.setPhone(getIntent().getStringExtra("phone"));
        registerInfo.setRegionId("1");
        registerInfo.setSex("1");
        registerInfo.setLicenseType("1");
        registerInfo.setDepartmentId("1");
        registerInfo.setJobId("1");
        mBinding.tvCompany.setText("北京市安全生产监督管理局");
        companyList = new ArrayList<>();
        departmentList = new ArrayList<>();
        jobList = new ArrayList<>();
        mBinding.setRegisterInfo(registerInfo);
        mAvatarPath = getApplicationContext().getFilesDir().getAbsolutePath() + "/avatar.jpg";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_avatar:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    for (String permission : mPermissions) {
//                        //判断是否授权
//                        int result = checkSelfPermission(permission);
//                        if (result == PackageManager.PERMISSION_GRANTED) {
//                            continue;
//                        } else {
//                            //判断是否需要向用户说明权限申请原因
//                            if (shouldShowRequestPermissionRationale(permission)) {
//                                //以对话框的形式呈现，向用户解释申请权限的原因
//                                new AlertDialog.Builder(CompleteInfoActivity.this)
//                                        .setCancelable(false)
//                                        .setTitle("为了不影响使用请开启以下权限")
//                                        .setMessage("1、读写手机存储\n" +
//                                                " 2、相机")
//                                        .setPositiveButton("前往开启", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Intent localIntent = new Intent();
//                                                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                if (Build.VERSION.SDK_INT > 8) {
//                                                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                                                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
//                                                } else {
//                                                    localIntent.setAction(Intent.ACTION_VIEW);
//                                                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
//                                                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
//                                                }
//                                                startActivity(localIntent);
//                                            }
//                                        })
//                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                            }
//                                        }).show();
//                            } else {
//                                //申请权限
//                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
//                            }
//
//                        }
//                    }
//                    doCarmer(AVATAR_CAMAER_CODE, AVATAR_PICK_CODE);
//                } else {
//                    //6.0以下直接调用
//                    doCarmer(AVATAR_CAMAER_CODE, AVATAR_PICK_CODE);
//                }
//                break;
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
            case R.id.btn_finish:
                if (registerInfo.getDepartmentId() == null || registerInfo.getEmail() == null
                        || registerInfo.getIdNo() == null || registerInfo.getJobId() == null
                        || registerInfo.getLicenseType() == null || registerInfo.getPhone() == null
                        || registerInfo.getRealName() == null || registerInfo.getRegionId() == null
                        || registerInfo.getSex() == null) {
                    ToastUtil.getInstance().toastInCenter(this, "请将信息填写完整！");
                } else {
                    if (RegexpUtils.regexEdttext(this, mBinding.etIdNo, mBinding.etMail)) {
                        RequestCenter.regist(registerInfo, this);
                    }
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap image = null;
            if (data != null) {
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                Uri mImageCaptureUri = data.getData();
                //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                if (mImageCaptureUri != null) {
                    try {
                        //这个方法是根据Uri获取Bitmap图片的静态方法
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                        image = extras.getParcelable("data");
                    }
                }

            }
            switch (requestCode) {
                case AVATAR_CAMAER_CODE:
                    if (image != null) {
                        try {
                            BitmapTools.saveBitmap(mAvatarPath, image);
                            mAvatarFile = BitmapTools.compress(mAvatarPath);
                            if (mAvatarFile != null) {
                                //这里写上传图片的接口
                                RequestCenter.uploadAvatar(UserInfo.getUserInfoInstance().getId(),mAvatarFile,this);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case AVATAR_PICK_CODE:
                    if (image != null) {
                        try {
                            BitmapTools.saveBitmap(mAvatarPath, image);
                            mAvatarFile = BitmapTools.compress(mAvatarPath);
                            if (mAvatarFile != null) {
                                //这里写上传图片的接口
                                RequestCenter.uploadAvatar(UserInfo.getUserInfoInstance().getId(),mAvatarFile,this);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    }


    /**
     * 选择证件类型
     */
    private void pickLicenseType() {
        final String[] type = new String[]{"身份证"};
        OptionPicker picker = new OptionPicker(this, type);
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
                registerInfo.setLicenseType(String.valueOf(index + 1));
            }
        });
        picker.show();
    }

    /**
     * 选择性别
     */
    private void pickSex() {
        final String[] sex = new String[]{"女", "男"};
        OptionPicker picker = new OptionPicker(this, sex);
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

    /**
     * 选择公司
     */
    private void pickCompany() {
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

    /**
     * 选择部门
     */
    private void pickDepartment() {
        SinglePicker<ResponseDepartmentList> picker = new SinglePicker<>(this, departmentList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseDepartmentList>() {
            @Override
            public void onItemPicked(int index, ResponseDepartmentList item) {
                mBinding.tvDepartment.setText(item.getName());
                registerInfo.setDepartmentId(String.valueOf(item.getId()));
            }
        });
        picker.show();
    }

    /**
     * 选择职位
     */
    private void pickJob() {
        SinglePicker<ResponseJobList> picker = new SinglePicker<>(this, jobList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseJobList>() {
            @Override
            public void onItemPicked(int index, ResponseJobList item) {
                mBinding.tvDuty.setText(item.getName());
                registerInfo.setJobId(String.valueOf(item.getId()));
            }
        });
        picker.show();
    }

    /**
     * 弹出正在审核的提示框
     */
    private void popRegisterTip() {
        registerTip = LayoutInflater.from(this).inflate(R.layout.pop_registertip, null);
        LinearLayout mLinelayout = (LinearLayout) registerTip.findViewById(R.id.ll_pop);
        DialogManager.getInstance().showCustomDialog(this, registerTip, true);
        Button okBtn = (Button) registerTip.findViewById(R.id.btn_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().dissMissCustomDialog();
                APPActivityManager.getInstance().finishActivities();
                System.exit(0);
            }
        });
    }

    /**
     * 选择地区
     */
    public void pickAddress() {
        new AddressInitTask(this, new AddressInitTask.InitCallback() {
            @Override
            public void onDataInitFailure() {
                ToastUtil.getInstance().toastInCenter(CompleteInfoActivity.this, "数据初始化失败");
            }

            @Override
            public void onDataInitSuccess(ArrayList<Province> provinces) {
                AddressPicker picker = new AddressPicker(CompleteInfoActivity.this, provinces);
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
                        address=provinceName + "," + countyName;
                        mBinding.tvDistrict.setText(address);
                        registerInfo.setRegionId("1");
                    }
                });
                picker.show();
            }
        }).execute();
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_COMPANY_LIST)) {
            JSONObject mainData = respose.getMainData();
            companyList = MyJSON.parseArray(mainData.getString("companyList"), ResponseCompanyList.class);
            pickCompany();
        } else if (requestUrl.equals(RequestCenter.GET_DEPARTMENT_LIST)) {
            JSONObject mainData = respose.getMainData();
            departmentList = MyJSON.parseArray(mainData.getString("departmentList"), ResponseDepartmentList.class);
            pickDepartment();

        } else if (requestUrl.equals(RequestCenter.GET_JOB_LIST)) {
            JSONObject mainData = respose.getMainData();
            jobList = MyJSON.parseArray(mainData.getString("jobList"), ResponseJobList.class);
            pickJob();
        } else if (requestUrl.equals(RequestCenter.REGIST)) {
            SharedPreferencesUtil.saveValue(this, CommonValues.ADDRESS,address);
            SharedPreferencesUtil.saveValue(this,CommonValues.ISMESSAGEOPEN,true);
            popRegisterTip();
        }else if(requestUrl.equals(RequestCenter.UPLOAD_AVATAR)){

        }
        return super.doSuccess(respose, requestUrl);
    }
}
