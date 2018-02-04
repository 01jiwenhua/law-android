package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import static org.greenrobot.greendao.async.AsyncOperation.OperationType.Load;

/**
 * Loading界面
 */
public class LoadingActivity extends BaseActivity {
    private ImageView mLoadingView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        ResponseUserInfo userInfo = (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
        if(userInfo!=null){
            //调用获取用户信息的接口更新本地用户信息
            RequestCenter.getUserInfo(String.valueOf(userInfo.getId()),this);
        }else{
            handler.sendEmptyMessageDelayed(1,2000);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_loading);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //如果SP中有用户信息，则直接跳转到MainActivity中，如果SP中没有用户信息说明用户是第一次登录，需要跳转到用户引导页面
            if (msg.what == 0) {
                gotoMainActivity();
            }else if (msg.what == 1) {
                startActivity(new Intent(LoadingActivity.this,LoginActivity.class));
            }
            finish();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_USERINFO)){
            JSONObject mainData = respose.getMainData();
            ResponseUserInfo userInfo= MyJSON.parseObject(mainData.getString("userInfo"),ResponseUserInfo.class);
            SharedPreferencesUtil.saveObject(this, CommonValues.USERINFO,userInfo);
            handler.sendEmptyMessageDelayed(0,2000);
        }
        return super.doSuccess(respose, requestUrl);
    }

    @Override
    public boolean doFaild(HttpTrowable error, String url) {
        ToastUtil.getInstance().toastInCenter(this,"服务器异常！");
        return super.doFaild(error, url);
    }
}
