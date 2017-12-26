package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.message.EventMessage;
import com.shx.lawwh.view.ViewPageWithIndicator;

import org.greenrobot.eventbus.EventBus;

/**
 * 三司查询Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class SSActivity extends BaseActivity implements View.OnClickListener {
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        initView();
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

    private void initView() {
        mLoopView = (ViewPageWithIndicator) findViewById(R.id.vp_viewpage);
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        initBanner();
        findViewById(R.id.layout_chemicals).setOnClickListener(this);
        findViewById(R.id.layout_fireworks).setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_chemicals:
                goSearch("危化品");
                break;
            case R.id.layout_fireworks:
                goSearch("烟花爆竹");
                break;
        }
    }
    private void goSearch(String typeCode){
        Intent intent=new Intent(this,LawSearchActivity.class);
        intent.putExtra("typeCode",typeCode);
        intent.putExtra("typeName","三司");
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (vps != null) {
            vps.stop();
        }
    }
}
