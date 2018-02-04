package com.shx.lawwh.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.LoginActivity;
import com.shx.lawwh.adapter.LawBaseAdapter;
import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adm on 2018/2/4.
 */

public class FavoriteLawFragment extends Fragment implements HttpCallBack {
    private LawBaseAdapter mAdatper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LawRequest mRequest;
    private List<LawResponse> lawList = new ArrayList<>();
    private String typeCode;
    @SuppressLint("ValidFragment")
    public FavoriteLawFragment(String typeCode){
        super();
        this.typeCode=typeCode;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_law, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData();
        }
    }
    private void initView(View view){
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
    }


    private void initData(){
        mRequest=new LawRequest();
        mRequest.setPage(1);
        mRequest.setPageSize(30);
        if(UserInfo.getUserInfoInstance()==null){
            ToastUtil.getInstance().toastInCenter(getContext(),"请登录");
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
        RequestCenter.getFavoriteList(typeCode,String.valueOf(UserInfo.getUserInfoInstance().getId()),this);
    }


    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_FAVORITE)){
            lawList = MyJSON.parseArray(mainData.getString("lawList"), LawResponse.class);
            mAdatper=new LawBaseAdapter(lawList);
            mRecyclerView.setAdapter(mAdatper);


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
}
