package com.shx.lawwh.fragment;

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
import com.shx.lawwh.adapter.KnownAdapter;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.response.ChemicalsResponse;
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

public class ChemicalFragment extends Fragment implements HttpCallBack {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private KnownAdapter mAdapter;
    private ChemicalsRequest mRequest;
    private List<ChemicalsResponse> chemicalsResponseList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_search_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData(){
        mRequest=new ChemicalsRequest();
        mRequest.setPage(1);
        mRequest.setPageSize(30);
        RequestCenter.getKnownlist(mRequest, this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_KNOWNLIST)){
            chemicalsResponseList = MyJSON.parseArray(mainData.getString("chemicalsList"), ChemicalsResponse.class);
            mAdapter=new KnownAdapter(chemicalsResponseList);
            mAdapter.setLight(true,mRequest);
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

    public void searchKey(String key){
        mRequest.setName(key);
        RequestCenter.getKnownlist(mRequest,this);
        if(key.toString().isEmpty()){
            mAdapter.setLight(false,mRequest);
        }else {
            mAdapter.setLight(true, mRequest);
        }
    }

}
