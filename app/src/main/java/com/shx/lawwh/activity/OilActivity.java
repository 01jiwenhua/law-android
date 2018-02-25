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
        Fragment gcpmbzFragment=new FireProofCommonFragment("工厂平面布置","GB50160-2008");
        Fragment krytFragment=new FireProofCommonFragment("可燃液体的地上储罐","GB50160-2008");
        Fragment krytyhtFragment=new FireProofCommonFragment("可燃液体、液化烃的装卸设施","GB50160-2008");
        Fragment qyghFragment=new FireProofCommonFragment("区域规划","GB50160-2008");
        Fragment wsclFragment=new FireProofCommonFragment("污水处理场和循环水场","GB50160-2008");
        Fragment yhtFragment=new FireProofCommonFragment("液化烃/可燃气体/助燃气体的地上储罐","GB50160-2008");
        Fragment zznbzFragment=new FireProofCommonFragment("装置内布置","GB50160-2008");
        fragments=new ArrayList<>();
        fragments.add(gcpmbzFragment);
        fragments.add(krytFragment);
        fragments.add(krytyhtFragment);
        fragments.add(qyghFragment);
        fragments.add(wsclFragment);
        fragments.add(yhtFragment);
        fragments.add(zznbzFragment);
        RequestCenter.getTabs("GB50160-2008",this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_TABS)){
            JSONObject mainData = respose.getMainData();
                tabs = MyJSON.parseArray(mainData.getString("tabs"), ResponseTabs.class);
                if(tabs.size()!=0) {
                    pagerAdapter = new CommonPagerAdapter(manager, fragments, getTitle(tabs));
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
            str[i]=tab.getName();
        }
        return str;
    }
}
