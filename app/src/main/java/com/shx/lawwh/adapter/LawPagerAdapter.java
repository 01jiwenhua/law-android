package com.shx.lawwh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shx.lawwh.entity.response.ResponseLevelList;

import java.util.List;

/**
 * Created by zhou on 2018/2/1.
 */

public class LawPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;
    private List<ResponseLevelList> datas;

    public LawPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<ResponseLevelList> datas) {
        super(fm);
        this.mFragments = fragments;
        this.datas=datas;
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
        return datas.get(position).getName();
    }
}
