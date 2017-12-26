package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.shx.lawwh.R;
import com.shx.lawwh.adapter.UserGuideAdapter;
import com.shx.lawwh.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户向导Activity
 * Created by 周正一 on 2017/2/21.
 */

public class  UserGuideActivity extends BaseActivity{
    private ViewPager mViewPager;
    private List<View> views;
    private UserGuideAdapter mAdapter;
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
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_user_guide);
        mViewPager= (ViewPager) findViewById(R.id.vp_userGuide);
        views=new ArrayList<>();
    }

    private void initData(){
        //设置长宽为全屏
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        View view1= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView1= (ImageView) view1.findViewById(R.id.img);
        imageView1.setImageResource(pics[0]);

        View view2= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView2= (ImageView) view2.findViewById(R.id.img);
        imageView2.setImageResource(pics[1]);

        View view3= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView3= (ImageView) view3.findViewById(R.id.img);
        imageView3.setImageResource(pics[2]);

        View view4= LayoutInflater.from(this).inflate(R.layout.item_userguide,null);
        ImageView imageView4= (ImageView) view4.findViewById(R.id.img);
        imageView4.setImageResource(pics[3]);
        view4.findViewById(R.id.tv_gotomain).setVisibility(View.VISIBLE);

//        for(int i=0; i<pics.length; i++) {
//
////            ImageView iv = new ImageView(this);
////            iv.setLayoutParams(mParams);
//            imageView.setImageResource(pics[i]);
////            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            views.add(view1);
//        }
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        mAdapter=new UserGuideAdapter(views);
        mViewPager.setAdapter(mAdapter);

        views.get(views.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
    }
}
