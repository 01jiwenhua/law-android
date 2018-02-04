package com.shx.lawwh.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.fragment.FavoriteFragment;
import com.shx.lawwh.fragment.MainFragment;


/**
 * 首页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout mContent;
    private Fragment mMainFragment, mPersonCenterFragment, mFavoriteFragment;
    private TextView mMain, mFavorite, mAccountCenter;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        mFragmentManager = getSupportFragmentManager();
        mMain.performClick();
    }


    @Override
    public void onBackPressed() {
        //连续按两次back键退出APP
        dealAppBack();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        mMain = (TextView) findViewById(R.id.rb_main);
        mFavorite = (TextView) findViewById(R.id.rb_favorite);
        mAccountCenter = (TextView) findViewById(R.id.rb_my);
        mContent = (FrameLayout) findViewById(R.id.content);
        mMain.setOnClickListener(this);
        mFavorite.setOnClickListener(this);
        mAccountCenter.setOnClickListener(this);
        setSelected();
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */


    //重置所有文本的选中状态
    private void setSelected() {
        mMain.setSelected(false);
        mFavorite.setSelected(false);
        mAccountCenter.setSelected(false);
    }


    private void showMainFragment() {
        mMainFragment = new MainFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content, mMainFragment);
        transaction.commitAllowingStateLoss();

    }

    private void showfavoriteFragment() {
        mFavoriteFragment = new FavoriteFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content, mFavoriteFragment);
        transaction.commitAllowingStateLoss();

    }

    private void showUserFragment() {
//        mPersonCenterFragment = new UserFragment();
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.replace(R.id.content, mPersonCenterFragment);
//        transaction.commitAllowingStateLoss();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_favorite:
                setSelected();
                mFavorite.setSelected(true);
                showfavoriteFragment();
                break;
            case R.id.rb_main:
                setSelected();
                mMain.setSelected(true);
                showMainFragment();
                break;

            case R.id.rb_my:
                setSelected();
                mAccountCenter.setSelected(true);
                showUserFragment();
                break;
        }

    }
}
