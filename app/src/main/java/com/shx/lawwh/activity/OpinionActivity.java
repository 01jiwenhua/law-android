package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

/**
 * Created by zhou on 2018/2/7.
 */

public class OpinionActivity extends BaseActivity {
    private EditText mContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        mContent= (EditText) findViewById(R.id.et_content);
        getTopbar().setTitle("意见与反馈");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTopbar().setRightText("提交");
        getTopbar().setRightTextVisibility(View.VISIBLE);
        getTopbar().setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mContent.getText())){
                    ToastUtil.getInstance().toastInCenter(OpinionActivity.this,"请填写意见或建议");
                    return;
                }
                RequestCenter.saveAdvice(mContent.getText().toString(), String.valueOf(UserInfo.getUserInfoInstance().getId()),OpinionActivity.this);
            }
        });
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        ToastUtil.getInstance().toastInCenter(this,"提交成功");
        finish();
        return super.doSuccess(respose, requestUrl);
    }
}
