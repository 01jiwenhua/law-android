package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.APPActivityManager;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.CountDownTimerUtils;
import com.shx.lawwh.utils.DeviceUtil;
import com.shx.lawwh.utils.DeviceUtils;
import com.shx.lawwh.utils.SharedPreferencesUtil;

/**
 * Created by zhou on 2018/2/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText phoneEt,verifyCodeEt;
    private TextView verifyCodeTv;
    private Button loginBtn,registerBtn;
    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }
    private void initView(){
        getTopbar().setLeftImageVisibility(View.GONE);
        getTopbar().setTitle("登录");
        phoneEt= (EditText) findViewById(R.id.et_phone);
        verifyCodeEt= (EditText) findViewById(R.id.et_verifyCode);
        verifyCodeTv= (TextView) findViewById(R.id.tv_verifyCode);
        loginBtn=(Button) findViewById(R.id.btn_login);
        registerBtn=(Button) findViewById(R.id.btn_register);

        verifyCodeTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private void initData(){
        //初始化验证码倒计时工具
        mCountDownTimerUtils= new CountDownTimerUtils(verifyCodeTv, 60000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                    if(TextUtils.isEmpty(phoneEt.getText().toString()) || TextUtils.isEmpty(verifyCodeEt.getText().toString())){
                        ToastUtil.getInstance().toastInCenter(this,"请将信息填写完整！");
                    }else{
                        RequestCenter.login(phoneEt.getText().toString(),verifyCodeEt.getText().toString(),this);
                    }
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
//                Intent intent = new Intent(this, PdfViewActivity.class);
//                intent.putExtra("URL", "aa");
//                startActivity(intent);
                break;
            case R.id.tv_verifyCode:
                if(!TextUtils.isEmpty(phoneEt.getText().toString())){
                    RequestCenter.getVerifyCode(phoneEt.getText().toString(),this);
                }else{
                    ToastUtil.getInstance().toastInCenter(this,"请输入手机号！");
                }
                break;
        }
    }

    /**
     * 弹出正在审核的提示框
     */
    private void popRegisterTip() {
        View registerTip = LayoutInflater.from(this).inflate(R.layout.pop_registertip, null);
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

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.LOGIN)){
            ResponseUserInfo userInfo=MyJSON.parseObject(mainData.getString("userInfo"),ResponseUserInfo.class);
            if(userInfo.getStatus()==1) {
                SharedPreferencesUtil.saveObject(LoginActivity.this, CommonValues.USERINFO, userInfo);
                startActivity(new Intent(this, MainActivity.class));
                String cid = SharedPreferencesUtil.getStringValue(this, CommonValues.CID, "");
                RequestCenter.uploadAppid(userInfo.getId(), cid, this);
                finish();
            }else{
                popRegisterTip();
            }
        }else if(requestUrl.equals(RequestCenter.GET_VERIFYCODE)){
            mCountDownTimerUtils.start();
        }
        return super.doSuccess(respose, requestUrl);
    }

}
