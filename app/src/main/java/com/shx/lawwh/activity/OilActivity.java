package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.CommonPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.ResponseTabs;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.List;

/**
 * Created by adm on 2018/2/25.
 */

public class OilActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<ResponseTabs> tabs;
    private CommonPagerAdapter pagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);
        initView();
        initData();
        RequestCenter.getTabs("GB50160-2008",this);
    }

    private void initView(){
        tabLayout= (TabLayout) findViewById(R.id.tl_item);
        mViewPager= (ViewPager) findViewById(R.id.vp_item);
    }

    private void initData(){
        FragmentManager manager=getSupportFragmentManager();

        pagerAdapter=new CommonPagerAdapter()
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_TABS)){
            JSONObject mainData = respose.getMainData();
            tabs= MyJSON.parseArray(mainData.getString("tabs"),ResponseTabs.class);
        }
        return super.doSuccess(respose, requestUrl);
    }
}
