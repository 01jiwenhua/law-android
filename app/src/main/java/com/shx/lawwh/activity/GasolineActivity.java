package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.adapter.CommonPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.LayoutFragment;
import com.shx.lawwh.fragment.LocationSelectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2018/2/6.
 */

public class GasolineActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private CommonPagerAdapter mAdapter;
    private Fragment locationSelectFragment,layoutFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasoline);
        initView();
        initData();
    }

    private void initView(){
        getTopbar().setTitle("汽油加油加气站设计与施工规范查询");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tl_item);
        mViewPager = (ViewPager) findViewById(R.id.vp_item);
    }

    private void initData(){
        String [] title=new String[]{"站址选择","站内平面布局"};
        locationSelectFragment=new LocationSelectFragment();
        layoutFragment=new LayoutFragment();
        fragments=new ArrayList<>();
        fragments.add(locationSelectFragment);
        fragments.add(layoutFragment);
        FragmentManager manager=getSupportFragmentManager();
        mAdapter=new CommonPagerAdapter(manager,fragments,title);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }


}
