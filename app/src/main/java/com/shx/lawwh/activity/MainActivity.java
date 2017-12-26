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
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.view.ViewPageWithIndicator;

/**
 * Created by xuan on 2017/12/24.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private LinearLayout mFgbzk,mAjzjk,mWxhxp,mFhjj;
    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        mFgbzk= (LinearLayout) findViewById(R.id.layout_fgbz);
        mAjzjk= (LinearLayout) findViewById(R.id.layout_ajzj);
        mWxhxp= (LinearLayout) findViewById(R.id.layout_wxhxp);
        mFhjj= (LinearLayout) findViewById(R.id.layout_fhjj);
        mFhjj.setOnClickListener(this);
        mFgbzk.setOnClickListener(this);
        mWxhxp.setOnClickListener(this);
        mAjzjk.setOnClickListener(this);
        mFhjj.setOnClickListener(this);
        mLoopView = (ViewPageWithIndicator) findViewById(R.id.vp_viewpage);
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
            case R.id.layout_fgbz:
                startActivity(new Intent(this,LawActivity.class));
                break;
            case R.id.layout_ajzj:
                startActivity(new Intent(this,SSActivity.class));
                break;
            case R.id.layout_wxhxp:
                startActivity(new Intent(this,ChemicalsActivity.class));
                break;
            case R.id.layout_fhjj:
                ToastUtil.getInstance().toastInCenter(this,"该功能暂未开放使用");
                break;
        }
    }
}
