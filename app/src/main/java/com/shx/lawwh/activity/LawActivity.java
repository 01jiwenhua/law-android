package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LawPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.law.AdministrationLayFragment;
import com.shx.lawwh.fragment.law.CountryLawFragment;
import com.shx.lawwh.fragment.law.DepartmentLayFragment;
import com.shx.lawwh.fragment.law.DistrictLayFragment;
import com.shx.lawwh.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuan on 2017/12/24.
 */

public class LawActivity extends BaseActivity{

    private Fragment countryLawFragment;
    private Fragment administrationLayFragment;
    private Fragment departmentLayFragment;
    private Fragment districtLayFragment;
    private List<Fragment> fragments;
    private LawPagerAdapter lawPagerAdapter;
    private MyViewPager mViewPager;
    private TabLayout mTablayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        initView();
        initData();
    }
    private void initView(){
        getTopbar().setTitle("法律法规");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTablayout=(TabLayout)findViewById(R.id.tl_law);
        mViewPager=(MyViewPager)findViewById(R.id.vp_law);


    }

    private void initData(){
        countryLawFragment=new CountryLawFragment();
        administrationLayFragment=new AdministrationLayFragment();
        departmentLayFragment=new DepartmentLayFragment();
        districtLayFragment=new DistrictLayFragment();
        fragments=new ArrayList<>();
        fragments.add(countryLawFragment);
        fragments.add(administrationLayFragment);
        fragments.add(departmentLayFragment);
        fragments.add(districtLayFragment);
        FragmentManager fragmentManager=getSupportFragmentManager();
        lawPagerAdapter=new LawPagerAdapter(fragmentManager,fragments);
        mViewPager.setAdapter(lawPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

    }


}
