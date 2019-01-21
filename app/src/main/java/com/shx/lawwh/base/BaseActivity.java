package com.shx.lawwh.base;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.MainActivity;
import com.shx.lawwh.activity.UserGuideActivity;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.service.PushService;
import com.shx.lawwh.view.ActionBarView;
import com.shx.lawwh.view.ActionSheet;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class BaseActivity extends AppCompatActivity implements HttpCallBack{
    protected ActionBarView topbarView;

    private String[] titles = new String[]{"拍照", "相册"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPActivityManager.getInstance().addActivity(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushService.class);
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
    public void gotoUserGuideActivity() {
        startActivity(new Intent(this,UserGuideActivity.class));
        finish();
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

    public void doCarmer(final int requestCode, final int pickCode) {
        ActionSheet.Builder mBuilder = ActionSheet.createBuilder(BaseApplication.getContext(), getSupportFragmentManager()).setOtherButtonTitles(titles).setCancelButtonTitle("取消").setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                switch (index) {
                    case 0:
                        //拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
                        //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra("autofocus", true); // 自动对焦
                        startActivityForResult(intent, requestCode);
                        break;
                    case 1:
//                        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
//                        intent2.setDataAndType(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                "image/*");
//                        startActivityForResult(intent2, pickCode);
                        try {
                            //选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                            //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                            Intent intent2 = new Intent();
                            intent2.setType("image/*");
                            intent2.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent2, pickCode);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
        mBuilder.show();
    }


}
