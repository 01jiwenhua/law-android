package com.shx.lawwh.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shx.lawwh.R;
import com.shx.lawwh.activity.MainActivity;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.view.ActionBarView;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class BaseActivity extends AppCompatActivity implements HttpCallBack{
    protected ActionBarView topbarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPActivityManager.getInstance().addActivity(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
    }
    public ActionBarView getTopbar() {
        if (topbarView == null) {
            View view = findViewById(R.id.topbar_view);
            if (view != null) {
                topbarView = new ActionBarView(view);
            }
        }
        return topbarView;
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    /**
     * 处理返回事件，如果在首页 连续按两次back键退出APP
     * */

    public void dealAppBack() {
        if (!BaseApplication.isExit) {
            BaseApplication.isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            BaseApplication.getContext().mHandlerExit.sendEmptyMessageDelayed(
                    0, 3000);
        } else {
            APPActivityManager.getInstance().finishActivities();
            finish();
            System.exit(0);
        }
    }

    public void gotoMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        return false;
    }

    @Override
    public boolean doFaild(HttpTrowable error, String url) {
        return false;
    }

    @Override
    public boolean httpCallBackPreFilter(String result, String url) {
        return false;
    }
}
