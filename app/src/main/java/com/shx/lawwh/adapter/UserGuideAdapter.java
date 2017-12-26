package com.shx.lawwh.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shx.lawwh.base.ViewPagerScheduler;

import java.util.List;


/**
 * 定时播放的ViewPager适配器
 *
 * @author xia
 */
public class UserGuideAdapter extends PagerAdapter {
    private List<View> views;
    ViewPagerScheduler vps;

    public UserGuideAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = views.get(position);
        container.addView(view);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    vps.stop();
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    vps.restart(2000);
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    public void setVps(ViewPagerScheduler vps) {
        this.vps = vps;
    }
}
