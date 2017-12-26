package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.adapter.UserGuideAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.view.ViewPageWithIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户向导Activity
 * Created by 周正一 on 2017/2/21.
 */

public class  UserGuideActivity extends BaseActivity{

    private ImageView[] imageViews;
    private UserGuideAdapter loopViewPagerAdapter;
    private ViewPageWithIndicator mLoopView;
    private ViewPagerScheduler vps;
    //引导图片资源
    private static final int[] pics = { R.drawable.img_fg,
            R.drawable.img_ss, R.drawable.img_hxp, R.drawable.img_fhjj};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_guide);
        initView();
//        initData();
    }


        /**
         * 初始化首页Banner
         */
    private void initView() {
        mLoopView = (ViewPageWithIndicator) findViewById(R.id.vp_viewpage);
        mLoopView.getLayoutParams().height = LayoutValue.SCREEN_HEIGHT ;
        imageViews = new ImageView[pics.length];
        //循环创建ImageView，并且用Glide讲图片显示在上面
//        for (int i = 0; i < pics.length; i++) {
//            ImageView imageView = new ImageView(this);
//            imageViews[i] = imageView;
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(this).load(pics[i]).placeholder(getResources().getDrawable(R.drawable.img_banner)).into(imageView);
//        }
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        View view1= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView1= (ImageView) view1.findViewById(R.id.img);
        Glide.with(this).load(pics[0]).placeholder(getResources().getDrawable(R.drawable.img_fg)).into(imageView1);


        View view2= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView2= (ImageView) view2.findViewById(R.id.img);
        Glide.with(this).load(pics[1]).placeholder(getResources().getDrawable(R.drawable.img_fg)).into(imageView2);

        View view3= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView3= (ImageView) view3.findViewById(R.id.img);
        Glide.with(this).load(pics[2]).placeholder(getResources().getDrawable(R.drawable.img_fg)).into(imageView3);

        View view4= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView4= (ImageView) view4.findViewById(R.id.img);
        Glide.with(this).load(pics[3]).placeholder(getResources().getDrawable(R.drawable.img_fg)).into(imageView4);

        view4.findViewById(R.id.tv_gotomain).setVisibility(View.VISIBLE);
        List<View> views=new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.get(views.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
        loopViewPagerAdapter = new UserGuideAdapter(
                views);
        vps = new ViewPagerScheduler(mLoopView.getViewPager());
        loopViewPagerAdapter.setVps(vps);
        vps.stop();
        mLoopView.setIndicatorVisibility(true, 4, 1);
        mLoopView.setAdapter(loopViewPagerAdapter, imageViews.length);
    }

}
