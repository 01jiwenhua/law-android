package com.shx.lawwh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.adapter.FavoritePagerAdapter;
import com.shx.lawwh.adapter.MainSearchPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.shx.lawwh.R.id.tabLayout;

/**
 * Created by xuan on 2018/2/4.
 */

public class FavoriteFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private FavoritePagerAdapter mAdatper;
    private Fragment standerFragment,lawFragment,policyFragment,chemicalFragment;
    private List<Fragment> fragments;
    private TextView mTitle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite,null);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout= (TabLayout) view.findViewById(R.id.tl_item);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_item);
        mTitle= (TextView) view.findViewById(R.id.tv_actionBarTitle);
        mTitle.setText("收藏");
        view.findViewById(R.id.iv_actionBarLeft).setVisibility(View.INVISIBLE);
        initData();

    }



    private void initData(){
        standerFragment=new FavoriteLawFragment("bzgf");
        lawFragment=new FavoriteLawFragment("flfg");
        policyFragment=new FavoriteLawFragment("zcwj");
        chemicalFragment=new ChemicalFragment();
        fragments=new ArrayList<>();
        fragments.add(standerFragment);
        fragments.add(lawFragment);
        fragments.add(policyFragment);
        fragments.add(chemicalFragment);
        FragmentManager manager=getChildFragmentManager();
        mAdatper=new FavoritePagerAdapter(manager,fragments);
        mViewPager.setAdapter(mAdatper);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
