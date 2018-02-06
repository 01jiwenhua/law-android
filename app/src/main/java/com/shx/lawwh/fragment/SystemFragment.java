package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.NewsDetailsActivity;
import com.shx.lawwh.adapter.MessageAdapter;
import com.shx.lawwh.base.OnRecyclerViewItemClickListener;
import com.shx.lawwh.entity.response.ResponseMessage;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.List;

/**
 * Created by adm on 2018/2/4.
 */

public class SystemFragment extends Fragment implements HttpCallBack, OnRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private MessageAdapter mAdapter;
    private List<ResponseMessage> messages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_system,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_list);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RequestCenter.getMessage(1,this);
        return view;
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_MESSAGE)){
            JSONObject mainData = respose.getMainData();
            messages= MyJSON.parseArray(mainData.getString("messageList"),ResponseMessage.class);
            mAdapter=new MessageAdapter(messages,getActivity());
            mAdapter.setmOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
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
    public void onItemClick(View view, Object data) {
        ResponseMessage message=(ResponseMessage)data;
        Intent intent=new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("message",message);
        startActivity(intent);
    }
}
