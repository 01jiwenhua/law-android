package com.shx.lawwh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.view.ViewPageWithIndicator;

/**
 * 三司查询Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class SelectFragment extends Fragment implements View.OnClickListener {
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select, null);
        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //该页面每次显示时 获取新的推荐运力票
        if (vps != null) {
            vps.restart(4000);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initView(View view) {
        setHeaderView(view);
//        view.findViewById(R.id.layout_chemicals).setOnClickListener(this);
//        view.findViewById(R.id.layout_fireworks).setOnClickListener(this);
    }

    private void setHeaderView(View view) {
        mLoopView = (ViewPageWithIndicator) view.findViewById(R.id.vp_viewpage);
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        initBanner();
    }

    /**
     * 初始化首页Banner
     */
    private void initBanner() {
        mLoopView.getLayoutParams().height = LayoutValue.SCREEN_WIDTH * 300 / 640;
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        imageViews = new ImageView[res.length];
        //循环创建ImageView，并且用Glide讲图片显示在上面
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageViews[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getActivity()).load(res[i]).placeholder(getResources().getDrawable(R.drawable.img_banner)).into(imageView);
        }

        loopViewPagerAdapter = new LoopViewPagerAdapter(
                imageViews);
        vps = new ViewPagerScheduler(mLoopView.getViewPager());
        loopViewPagerAdapter.setVps(vps);
        vps.updateCount(imageViews.length);
        vps.restart(4000);
        mLoopView.setIndicatorVisibility(true, 5, 1);
        mLoopView.setAdapter(loopViewPagerAdapter, imageViews.length);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.layout_chemicals:
//                EventMessage messageChemicals = new EventMessage();
//                messageChemicals.setFrom("SelectFragment");
//                messageChemicals.setSearchType("三司");
//                messageChemicals.setSelectMenu("危化品");
//                EventBus.getDefault().postSticky(messageChemicals);
//                break;
//            case R.id.layout_fireworks:
//                EventMessage messageFireworks = new EventMessage();
//                messageFireworks.setFrom("SelectFragment");
//                messageFireworks.setSelectMenu("烟花爆竹");
//                messageFireworks.setSearchType("三司");
//                EventBus.getDefault().postSticky(messageFireworks);
//                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (vps != null) {
            vps.stop();
        }
    }
}
