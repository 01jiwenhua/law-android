package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.view.ViewPageWithIndicator;

/**
 * Created by xuan on 2017/12/24.
 */

public class LawActivity extends BaseActivity implements View.OnClickListener{
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private LinearLayout mLaw,mRegulations,mStandard;

    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        initView();
    }
    private void initView(){
        mLoopView = (ViewPageWithIndicator) findViewById(R.id.vp_viewpage);
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        initBanner();
        mLaw= (LinearLayout) findViewById(R.id.layout_law);
        mRegulations= (LinearLayout) findViewById(R.id.layout_regulations);
        mStandard= (LinearLayout) findViewById(R.id.layout_standard);
        mLaw.setOnClickListener(this);
        mRegulations.setOnClickListener(this);
        mLaw.setOnClickListener(this);
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
            ImageView imageView = new ImageView(this);
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
            case R.id.layout_law:
                goSearch("law");
                break;
            case R.id.layout_regulations:
                goSearch("regulation");
                break;
            case R.id.layout_standard:
                goSearch("standard");
                break;
        }
    }
    private void goSearch(String type){
        Intent intent=new Intent(this,LawSearchActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

}
