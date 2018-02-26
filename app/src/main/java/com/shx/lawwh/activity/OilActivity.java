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
import com.shx.lawwh.fragment.FireproofingFragment;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;

/**
 * Created by adm on 2018/2/25.
 */

public class OilActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<ResponseTabs> tabs;
    private CommonPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);
        initView();
        initData();
    }

    private void initView(){
        tabLayout= (TabLayout) findViewById(R.id.tl_item);
        mViewPager= (ViewPager) findViewById(R.id.vp_item);
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getTopbar().setTitle("石油化工企业设计防火规范");
    }

    private void initData(){
        manager=getSupportFragmentManager();
        fragments=new ArrayList<>();
        RequestCenter.getTabs("GB50160-2008",this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_TABS)){
            JSONObject mainData = respose.getMainData();
                tabs = MyJSON.parseArray(mainData.getString("tabs"), ResponseTabs.class);
                if(tabs.size()!=0) {
                    String[] title = getTitle(tabs);
                    pagerAdapter = new CommonPagerAdapter(manager, fragments,title);
                    mViewPager.setAdapter(pagerAdapter);
                    tabLayout.setupWithViewPager(mViewPager);
            }
        }
        return super.doSuccess(respose, requestUrl);
    }

    private String [] getTitle(List<ResponseTabs>  tabs){
        String [] str=new String[tabs.size()];
        for (int i=0;i<tabs.size();i++) {
            ResponseTabs tab=tabs.get(i);
            Fragment fragment=new FireProofCommonFragment(tab.getName(),tab.getStandard());
            fragments.add(fragment);
            str[i]=tab.getName();
        }
        return str;
    }
}
