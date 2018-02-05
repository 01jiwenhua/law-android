package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.BaseAdapter;

import com.shx.lawwh.R;
import com.shx.lawwh.adapter.FireproofingAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.StandardFragment;
import com.shx.lawwh.fragment.StandardSearchFragment;
import com.shx.lawwh.fragment.VagueSearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2018/2/5.
 */

public class FireproofingActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private FireproofingAdapter mAdapter;
    private Fragment standardSearchFragment,vagueSearchFragment;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fireproofing);
        initView();
        initData();
    }

    private void initView(){
        getTopbar().setTitle("防火间距");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout= (TabLayout) findViewById(R.id.tl_item);
        mViewPager= (ViewPager) findViewById(R.id.vp_item);
    }

    private void initData(){
        standardSearchFragment=new StandardSearchFragment();
        vagueSearchFragment=new VagueSearchFragment();
        fragments=new ArrayList<>();
        fragments.add(standardSearchFragment);
        fragments.add(vagueSearchFragment);
        FragmentManager manager=getSupportFragmentManager();
        mAdapter=new FireproofingAdapter(manager,fragments);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
