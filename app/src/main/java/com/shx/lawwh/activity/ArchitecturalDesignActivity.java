package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.CommonPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.ResponseTabs;
import com.shx.lawwh.fragment.FireProofCommonFragment;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 建筑设计防火规范
 * Created by xuan on 2018/2/6.
 */

public class ArchitecturalDesignActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private CommonPagerAdapter mAdapter;
    private Fragment mContentFragment;
    private List<ResponseTabs> tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);
        initView();

        RequestCenter.getTabs("GB50016-2014", this);
    }

    private void initView() {
        getTopbar().setTitle("建筑设计防火规范查询");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tl_item);
        mViewPager = (ViewPager) findViewById(R.id.vp_item);
    }

    private void initData(List<ResponseTabs> tabs) {
        String[] title = new String[tabs.size()];
        fragments = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            title[i] = tabs.get(i).getName();
            mContentFragment = new FireProofCommonFragment(title[i],"GB50016-2014");

            fragments.add(mContentFragment);

        }


        FragmentManager manager = getSupportFragmentManager();
        mAdapter = new CommonPagerAdapter(manager, fragments, title);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_TABS)) {
            JSONObject mainData = respose.getMainData();
            tabs = MyJSON.parseArray(mainData.getString("tabs"), ResponseTabs.class);
            initData(tabs);

        }
        return super.doSuccess(respose, requestUrl);
    }


}
