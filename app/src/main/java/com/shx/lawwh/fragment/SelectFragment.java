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
import com.shx.lawwh.message.EventMessage;
import com.shx.lawwh.view.ViewPageWithIndicator;

import org.greenrobot.eventbus.EventBus;

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
        view.findViewById(R.id.layout_aqbz).setOnClickListener(this);
        view.findViewById(R.id.layout_bmgz).setOnClickListener(this);
        view.findViewById(R.id.layout_gjbz).setOnClickListener(this);
        view.findViewById(R.id.layout_gjfl).setOnClickListener(this);
        view.findViewById(R.id.layout_xzfg).setOnClickListener(this);
        view.findViewById(R.id.layout_dffg).setOnClickListener(this);
        view.findViewById(R.id.layout_hybz).setOnClickListener(this);
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
            case R.id.layout_aqbz:
                EventMessage messageaqbz = new EventMessage();
                messageaqbz.setFrom("SelectFragment");
                messageaqbz.setSearchType("法规");
                messageaqbz.setSelectMenu("安全标准");
                EventBus.getDefault().postSticky(messageaqbz);
                break;
            case R.id.layout_bmgz:
                EventMessage messageBmgz = new EventMessage();
                messageBmgz.setFrom("SelectFragment");
                messageBmgz.setSearchType("法规");
                messageBmgz.setSelectMenu("部门规章");
                EventBus.getDefault().postSticky(messageBmgz);
                break;
            case R.id.layout_gjbz:
                EventMessage messageGjfg = new EventMessage();
                messageGjfg.setFrom("SelectFragment");
                messageGjfg.setSearchType("法规");
                messageGjfg.setSelectMenu("国家标准");
                EventBus.getDefault().postSticky(messageGjfg);
                break;
            case R.id.layout_gjfl:
                EventMessage messageGjfl = new EventMessage();
                messageGjfl.setFrom("SelectFragment");
                messageGjfl.setSearchType("法规");
                messageGjfl.setSelectMenu("国家法律");
                EventBus.getDefault().postSticky(messageGjfl);
                break;
            case R.id.layout_xzfg:
                EventMessage messageXzfg = new EventMessage();
                messageXzfg.setFrom("SelectFragment");
                messageXzfg.setSearchType("法规");
                messageXzfg.setSelectMenu("行政法规");
                EventBus.getDefault().postSticky(messageXzfg);
                break;
            case R.id.layout_hybz:
                EventMessage messageHybz = new EventMessage();
                messageHybz.setFrom("SelectFragment");
                messageHybz.setSearchType("法规");
                messageHybz.setSelectMenu("行业标准");
                EventBus.getDefault().postSticky(messageHybz);
                break;
            case R.id.layout_dffg:
                EventMessage messageDffg = new EventMessage();
                messageDffg.setFrom("SelectFragment");
                messageDffg.setSearchType("法规");
                messageDffg.setSelectMenu("地方法规");
                EventBus.getDefault().postSticky(messageDffg);
                break;
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
