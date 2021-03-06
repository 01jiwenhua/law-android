package com.shx.lawwh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhou on 2018/2/1.
 */

public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private  String[] mTitles;

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragments,String[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles=titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //禁止自动销毁item
        //super.destroyItem(container, position, object);
    }
}
