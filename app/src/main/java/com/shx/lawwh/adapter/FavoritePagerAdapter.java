package com.shx.lawwh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhou on 2018/2/1.
 */

public class FavoritePagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;
    private final String[] mTitles = new String[]{ "法律规范","标准规范", "政策文件","危险化学品"};

    public FavoritePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
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
}
