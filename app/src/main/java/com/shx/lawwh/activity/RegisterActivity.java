package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.CountDownTimerUtils;

/**
 * Created by zhou on 2018/1/31.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText phoneET;
    private EditText verifyCodeET;
    private TextView requestAuthCodeTV;
    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private  void initView(){
        getTopbar().setTitle("注册");
        phoneET= (EditText) findViewById(R.id.et_phone);
        verifyCodeET= (EditText) findViewById(R.id.et_authCode);
        requestAuthCodeTV= (TextView) findViewById(R.id.tv_requestAuthCode);
        requestAuthCodeTV.setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        //初始化验证码倒计时工具
        mCountDownTimerUtils= new CountDownTimerUtils(requestAuthCodeTV, 60000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_requestAuthCode:
                if(!TextUtils.isEmpty(phoneET.getText().toString())){
                    RequestCenter.getVerifyCode(phoneET.getText().toString(),this);
                }else{
                    ToastUtil.getInstance().toastInCenter(this,"请输入手机号！");
                }
                break;

            case R.id.btn_next:
                if(!TextUtils.isEmpty(phoneET.getText().toString()) && !TextUtils.isEmpty(verifyCodeET.getText().toString())) {
                    RequestCenter.checkRegist(phoneET.getText().toString(),verifyCodeET.getText().toString(),this);
                }else{
                    ToastUtil.getInstance().toastInCenter(this,"请完善信息！");
                }
                break;
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_VERIFYCODE)){
            mCountDownTimerUtils.start();
        }else if(requestUrl.equals(RequestCenter.CHECK_REGIST)){
            Intent intent =new Intent(this,CompleteInfoActivity.class);
            intent.putExtra("phone",phoneET.getText().toString());
            startActivity(intent);
        }
        return super.doSuccess(respose, requestUrl);
    }
}
