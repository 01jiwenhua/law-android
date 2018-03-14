package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.ChemicalsActivity;
import com.shx.lawwh.activity.FireproofingActivity;
import com.shx.lawwh.activity.LawActivity;
import com.shx.lawwh.activity.LawSearchActivity;
import com.shx.lawwh.activity.MainSearchActivity;
import com.shx.lawwh.activity.NewsActivity;
import com.shx.lawwh.activity.PdfViewActivity;
import com.shx.lawwh.activity.SSActivity;
import com.shx.lawwh.activity.WebActivity;
import com.shx.lawwh.adapter.LawBaseAdapter;
import com.shx.lawwh.adapter.LawListAdapter;
import com.shx.lawwh.adapter.LoopViewPagerAdapter;
import com.shx.lawwh.adapter.NewLawAdapter;
import com.shx.lawwh.base.LayoutValue;
import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.base.ViewPagerScheduler;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.view.ViewPageWithIndicator;

import java.util.List;

/**
 * 首页的Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class MainFragment extends Fragment implements View.OnClickListener,HttpCallBack ,AdapterView.OnItemClickListener{
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private LinearLayout mFlfg,mBzgf,mWxhxp,mFhjj,mZcwj;
    private int res[] = new int[]{R.drawable.img_banner1};
    private TextView searchTv;
    private ListView mNewListView;
    private NewLawAdapter mAdapter;
    private List<LawResponse> lawList;
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
        RequestCenter.getNewLawList(String.valueOf(UserInfo.getUserInfoInstance().getId()),this);
    }

    private void initView(View view){
        mFlfg= (LinearLayout) view.findViewById(R.id.layout_flfgcx);
        mBzgf= (LinearLayout) view.findViewById(R.id.layout_bzgfcx);
        mZcwj= (LinearLayout) view.findViewById(R.id.layout_zcwjcx);
        mWxhxp= (LinearLayout) view.findViewById(R.id.layout_whp);
        mFhjj= (LinearLayout) view.findViewById(R.id.layout_fhjjjs);
        searchTv= (TextView) view.findViewById(R.id.tv_search);
        mNewListView= (ListView) view.findViewById(R.id.lv_new);
        mNewListView.setOnItemClickListener(this);
        view.findViewById(R.id.iv_message).setOnClickListener(this);
        searchTv.setOnClickListener(this);
        mFhjj.setOnClickListener(this);
        mFlfg.setOnClickListener(this);
        mWxhxp.setOnClickListener(this);
        mBzgf.setOnClickListener(this);
        mFhjj.setOnClickListener(this);
        mZcwj.setOnClickListener(this);
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
                Intent flfgIntent=new Intent(getContext(),LawSearchActivity.class);
                flfgIntent.putExtra("typeCode","flfg");
                flfgIntent.putExtra("title","法律法规");
                startActivity(flfgIntent);
                break;
            case R.id.layout_bzgfcx:
                Intent bzgfIntent=new Intent(getContext(),LawSearchActivity.class);
                bzgfIntent.putExtra("typeCode","bzgf");
                bzgfIntent.putExtra("title","标准规范");
                startActivity(bzgfIntent);
                break;
            case R.id.layout_zcwjcx:
                Intent zcwjIntent=new Intent(getContext(),LawSearchActivity.class);
                zcwjIntent.putExtra("typeCode","zcwj");
                zcwjIntent.putExtra("title","政策文件");
                startActivity(zcwjIntent);
                break;
            case R.id.layout_whp:
                startActivity(new Intent(getContext(),ChemicalsActivity.class));
                break;
            case R.id.layout_fhjjjs:
               startActivity(new Intent(getContext(), FireproofingActivity.class));
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), MainSearchActivity.class));
                break;
            case R.id.iv_message:
                startActivity(new Intent(getActivity(), NewsActivity.class));
                break;
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_NEWLAW)){
            lawList = MyJSON.parseArray(mainData.getString("newList"), LawResponse.class);
            mAdapter =new NewLawAdapter(lawList,getContext());
            mNewListView.setAdapter(mAdapter);
        }
        return false;
    }

    @Override
    public boolean doFaild(HttpTrowable error, String url) {
        return false;
    }

    @Override
    public boolean httpCallBackPreFilter(String result, String url) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LawResponse item = (LawResponse) mAdapter.getItem(position);
        LogGloble.d("MainFragment", item.getFilePath() + "");
        if (TextUtils.isEmpty(item.getFilePath())||item.getStatus()==-1) {
            ToastUtil.getInstance().toastInCenter(getContext(), "该文件不存在！");
            return;
        }
        if (item.getFileFrom().equals("pdf")) {
            Intent intent = new Intent(getContext(), PdfViewActivity.class);
            intent.putExtra("URL", item.getFilePath());
            intent.putExtra("typeCode",item.getTypeCode());
            intent.putExtra("lawId",item.getId());
            intent.putExtra("is_favorite",item.getIs_favorite());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra("URL", item.getFilePath());
            intent.putExtra("typeCode",item.getTypeCode());
            intent.putExtra("lawId",item.getId());
            intent.putExtra("is_favorite",item.getIs_favorite());
            startActivity(intent);
        }
    }
}
