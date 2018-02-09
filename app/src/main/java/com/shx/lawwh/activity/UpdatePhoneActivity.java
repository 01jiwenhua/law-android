package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.CountDownTimerUtils;

/**
 * Created by zhou on 2018/2/9.
 */

public class UpdatePhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEt;
    private EditText verifyCodeEt;
    private Button updateBtn;
    private TextView verifyCodeTv;
    private CountDownTimerUtils mCountDownTimerUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        getTopbar().setTitle("修改手机号");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        phoneEt= (EditText) findViewById(R.id.et_phone);
        verifyCodeEt= (EditText) findViewById(R.id.et_verifyCode);
        verifyCodeTv= (TextView) findViewById(R.id.tv_verifyCode);
        updateBtn= (Button) findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(this);
        verifyCodeTv.setOnClickListener(this);
        //初始化验证码倒计时工具
        mCountDownTimerUtils= new CountDownTimerUtils(verifyCodeTv, 60000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_verifyCode:
                if(!TextUtils.isEmpty(phoneEt.getText().toString())){
                    RequestCenter.getVerifyCode(phoneEt.getText().toString(),this);
                }else{
                    ToastUtil.getInstance().toastInCenter(this,"请输入手机号！");
                }
                break;
            case R.id.btn_update:
                if(phoneEt.getText().toString()==null){
                   ToastUtil.getInstance().toastInCenter(this,"请输入手机号!");
                   return;
                }
                if(verifyCodeEt.getText().toString()==null){
                    ToastUtil.getInstance().toastInCenter(this,"请输入验证码!");
                    return;
                }
                RequestCenter.changePhone(phoneEt.getText(),verifyCodeEt.getText(),this);
                break;
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_VERIFYCODE)){
            mCountDownTimerUtils.start();
        }else if (requestUrl.equals(RequestCenter.CHANGE_PHONE)){
            Intent intent=getIntent();
            intent.putExtra("phone",phoneEt.getText().toString());
            setResult(RESULT_OK,intent);
        }
        return super.doSuccess(respose, requestUrl);
    }
}
