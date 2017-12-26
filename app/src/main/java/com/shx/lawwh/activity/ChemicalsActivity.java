package com.shx.lawwh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.UnknownParams;
import com.shx.lawwh.fragment.KnownFragment;
import com.shx.lawwh.fragment.UnKnownFragment;

/**
 * Created by xuan on 2017/12/24.
 */

public class ChemicalsActivity extends BaseActivity implements View.OnClickListener{

    private FrameLayout mContent;
    private Fragment mKnownFragment,mUnknownFragment;
    private View line1,line2;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mFragmentManager;
    private TextView mKnown,mUnknown;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemicals);
        mFragmentManager = getSupportFragmentManager();
        initView();
    }
    private void initView() {
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        line1=findViewById(R.id.line1);
        line2=findViewById(R.id.line2);
        mKnownFragment = new KnownFragment();
        mUnknownFragment=new UnKnownFragment();
        mKnown = (TextView) findViewById(R.id.rb_known);
        mUnknown = (TextView) findViewById(R.id.rb_unknown);
        mContent = (FrameLayout) findViewById(R.id.content);
        mKnown.setOnClickListener(this);
        mUnknown.setOnClickListener(this);
        mKnown.performClick();

    }

    //重置所有文本的选中状态
    private void setSelected() {
        mKnown.setSelected(false);
        mUnknown.setSelected(false);
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case 0:
                transaction.replace(R.id.content, mKnownFragment);
                getTopbar().setTitle("已知物质查询");
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                break;
            case 1:
                transaction.replace(R.id.content, mUnknownFragment);
                getTopbar().setTitle("未知物质查询");
                getTopbar().setRightText("清空");
                getTopbar().setRightTextListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UnKnownFragment.checkMap.clear();
                        ((UnKnownFragment)mUnknownFragment).getmAdapter().notifyDataSetChanged();
                        ((UnKnownFragment)mUnknownFragment).getmAdapter2().notifyDataSetChanged();

                    }
                });
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_known:
                setSelected();
                mKnown.setSelected(true);
                setTabSelection(0);

                break;

            case R.id.rb_unknown:
                setSelected();
                mUnknown.setSelected(true);
                setTabSelection(1);
                break;

        }
    }
}
