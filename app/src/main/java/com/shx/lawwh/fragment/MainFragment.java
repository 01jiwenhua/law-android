package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.ChemicalsActivity;
import com.shx.lawwh.activity.LawActivity;
import com.shx.lawwh.activity.SSActivity;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.view.ViewPageWithIndicator;

/**
 * 首页的Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private LinearLayout mFlfg,mBzgf,mWxhxp,mFhjj;
    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        mFlfg= (LinearLayout) view.findViewById(R.id.layout_flfgcx);
        mBzgf= (LinearLayout) view.findViewById(R.id.layout_bzgfcx);
        mWxhxp= (LinearLayout) view.findViewById(R.id.layout_whp);
        mFhjj= (LinearLayout) view.findViewById(R.id.layout_fhjjjs);
        mFhjj.setOnClickListener(this);
        mFlfg.setOnClickListener(this);
        mWxhxp.setOnClickListener(this);
        mBzgf.setOnClickListener(this);
        mFhjj.setOnClickListener(this);
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
            ImageView imageView = new ImageView(getContext());
            imageViews[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(res[i]).placeholder(getResources().getDrawable(R.drawable.img_banner)).into(imageView);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_flfgcx:
                startActivity(new Intent(getContext(),LawActivity.class));
                break;
            case R.id.layout_bzgfcx:
                startActivity(new Intent(getContext(),SSActivity.class));
                break;
            case R.id.layout_zcwjcx:
                ToastUtil.getInstance().toastInCenter(getContext(),"该功能暂未开放使用");
                break;
            case R.id.layout_whp:
                startActivity(new Intent(getContext(),ChemicalsActivity.class));

                break;
        }
    }
}
