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
import com.shx.lawwh.activity.UserInfoActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.databinding.FragmentMyBinding;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import static com.shx.lawwh.utils.SharedPreferencesUtil.readObject;

/**
 * Created by adm on 2018/2/3.
 * 我的界面
 */

public class MyFragment extends Fragment implements View.OnClickListener {

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

                break;
            case R.id.ll_update:

                break;
            case R.id.ll_aboutUs:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.ll_help:

                break;
            case R.id.ll_setting:

                break;
        }

    }
}
