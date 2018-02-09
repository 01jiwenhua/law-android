package com.shx.lawwh.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shx.lawwh.R;
import com.shx.lawwh.activity.AboutUsActivity;
import com.shx.lawwh.activity.HelpActivity;
import com.shx.lawwh.activity.NewsActivity;
import com.shx.lawwh.activity.SettingActivity;
import com.shx.lawwh.activity.UpdateActivity;
import com.shx.lawwh.activity.UserInfoActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.databinding.FragmentMyBinding;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.entity.response.ResponseVersionInfo;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.DeviceUtils;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import org.json.JSONObject;

import static com.shx.lawwh.utils.SharedPreferencesUtil.readObject;

/**
 * Created by adm on 2018/2/3.
 * 我的界面
 */

public class MyFragment extends Fragment implements View.OnClickListener,HttpCallBack {

    private LinearLayout loginLL,userInfoLL,newsLL,updateLL,aboutUsLL,helpLL,settingLL;
    private FragmentMyBinding myBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(getActivity(), CommonValues.USERINFO);
        myBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_my,container,false);
        myBinding.setUserInfo(userInfo);
        View view=myBinding.getRoot();
        initView(view);
        return view;
    }

    private void initView(View view){
        loginLL= (LinearLayout) view.findViewById(R.id.ll_login);
        userInfoLL= (LinearLayout) view.findViewById(R.id.ll_userInfo);
        newsLL= (LinearLayout) view.findViewById(R.id.ll_news);
        updateLL= (LinearLayout) view.findViewById(R.id.ll_update);
        aboutUsLL= (LinearLayout) view.findViewById(R.id.ll_aboutUs);
        helpLL= (LinearLayout) view.findViewById(R.id.ll_help);
        settingLL= (LinearLayout) view.findViewById(R.id.ll_setting);

        loginLL.setOnClickListener(this);
        userInfoLL.setOnClickListener(this);
        newsLL.setOnClickListener(this);
        updateLL.setOnClickListener(this);
        aboutUsLL.setOnClickListener(this);
        helpLL.setOnClickListener(this);
        settingLL.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_login:

                break;
            case R.id.ll_userInfo:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.ll_news:
                startActivity(new Intent(getActivity(),NewsActivity.class));
                break;
            case R.id.ll_update:
                RequestCenter.getNewVersion(DeviceUtils.getVersionCode(getActivity()),this);

                break;
            case R.id.ll_aboutUs:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.ll_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.ll_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }

    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        com.alibaba.fastjson.JSONObject mainData=respose.getMainData();
        ResponseVersionInfo responseVersionInfo=MyJSON.parseObject(mainData.getString("versionInfo"), ResponseVersionInfo.class);
        Intent intent=new Intent(getContext(),UpdateActivity.class);
        intent.putExtra("versionInfo",responseVersionInfo);
        startActivity(intent);
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
