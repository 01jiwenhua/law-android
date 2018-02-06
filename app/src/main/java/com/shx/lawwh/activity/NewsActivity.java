package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.adapter.NewsAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.SystemFragment;
import com.shx.lawwh.fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adm on 2018/2/4.
 * 消息通知界面
 */

public class NewsActivity extends BaseActivity {

    private ViewPager mViewPage;
    private TabLayout mTablayout;
    private NewsAdapter mAdapter;
    private Fragment workFragment;
    private Fragment systemFragment;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getTopbar().setTitle("消息通知");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mViewPage= (ViewPager) findViewById(R.id.vp_item);
        mTablayout= (TabLayout) findViewById(R.id.tl_item);
        workFragment=new WorkFragment();
        systemFragment=new SystemFragment();
        fragments=new ArrayList<>();
        fragments.add(workFragment);
        fragments.add(systemFragment);
        FragmentManager manager=getSupportFragmentManager();
        mAdapter=new NewsAdapter(manager,fragments);
        mViewPage.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPage);
    }
}
