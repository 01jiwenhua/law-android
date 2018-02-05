package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.BaseAdapter;
import android.widget.EditText;

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

public class MainSearchActivity extends BaseActivity implements TextWatcher {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private MainSearchPagerAdapter mAdatper;
    private SumFragment sumFragment;
    private StandardFragment standerFragment;
    private LawFragment lawFragment;
    private PolicyFragment policyFragment;
    private ChemicalFragment chemicalFragment;
    private FireproofingFragment fireproofingFragment;
    private List<Fragment> fragments;
    private EditText searchEt;
    private static int currentIndex;
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
        searchEt= (EditText) findViewById(R.id.et_search);
        searchEt.addTextChangedListener(this);
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
        fragments.add(lawFragment);
        fragments.add(standerFragment);
        fragments.add(policyFragment);
        fragments.add(chemicalFragment);
        fragments.add(fireproofingFragment);
        FragmentManager manager=getSupportFragmentManager();
        mAdatper=new MainSearchPagerAdapter(manager,fragments);
        mViewPager.setAdapter(mAdatper);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String key=editable.toString();
        switch (currentIndex) {
            case 0:
                sumFragment.searchKey(key);
                break;
            case 1:
                lawFragment.searchKey(key);
                break;
            case 2:
                standerFragment.searchKey(key);
                break;
            case 3:
                policyFragment.searchKey(key);
                break;
            case 4:
                chemicalFragment.searchKey(key);
                break;

        }
    }
}
