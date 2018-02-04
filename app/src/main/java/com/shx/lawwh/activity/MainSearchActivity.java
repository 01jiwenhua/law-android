package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.BaseAdapter;

import com.shx.lawwh.R;
import com.shx.lawwh.adapter.MainSearchPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.ChemicalFragment;
import com.shx.lawwh.fragment.FireproofingFragment;
import com.shx.lawwh.fragment.LawFragment;
import com.shx.lawwh.fragment.PolicyFragment;
import com.shx.lawwh.fragment.StandardFragment;
import com.shx.lawwh.fragment.SumFragment;
import com.shx.lawwh.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adm on 2018/2/4.
 */

public class MainSearchActivity extends BaseActivity{

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private MainSearchPagerAdapter mAdatper;
    private Fragment sumFragment,standerFragment,lawFragment,policyFragment,chemicalFragment,fireproofingFragment;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        initView();
        initData();
    }

    private void initView(){
        tabLayout= (TabLayout) findViewById(R.id.tl_item);
        mViewPager= (ViewPager) findViewById(R.id.vp_item);

    }

    private void initData(){
        sumFragment=new SumFragment();
        standerFragment=new StandardFragment();
        lawFragment=new LawFragment();
        policyFragment=new PolicyFragment();
        chemicalFragment=new ChemicalFragment();
        fireproofingFragment=new FireproofingFragment();
        fragments=new ArrayList<>();
        fragments.add(sumFragment);
        fragments.add(standerFragment);
        fragments.add(lawFragment);
        fragments.add(policyFragment);
        fragments.add(chemicalFragment);
        fragments.add(fireproofingFragment);

        FragmentManager manager=getSupportFragmentManager();
        mAdatper=new MainSearchPagerAdapter(manager,fragments);
        mViewPager.setAdapter(mAdatper);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
