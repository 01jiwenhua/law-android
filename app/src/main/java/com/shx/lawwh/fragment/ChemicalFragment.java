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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.ChemicalsDetailsActivity;
import com.shx.lawwh.adapter.KnownAdapter;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.response.ChemicalsResponse;
import com.shx.lawwh.entity.response.LawResponse;
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

public class ChemicalFragment extends Fragment implements HttpCallBack, BaseQuickAdapter.OnItemClickListener {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private KnownAdapter mAdapter;
    private ChemicalsRequest mRequest;
    private List<ChemicalsResponse> chemicalsResponseList=new ArrayList<>();
    private int mPage = 1;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isReSearch=false;//是否是通过关键字重新搜索

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

    private void loadMoreData() {
        if (isLastPage) {
            setFooterView();
            mAdapter.loadMoreEnd();
            return;
        }
        mPage++;
        LogGloble.d("loadMoreData", mPage + "");
        mRequest.setPage(mPage);
        RequestCenter.getKnownlist(mRequest, this);
    }

    private void setFooterView() {
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
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
        mAdapter=new KnownAdapter(chemicalsResponseList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.layout_empty_view);
        //RequestCenter.getKnownlist(mRequest, this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isReSearch=false;
                loadMoreData();
            }
        },mRecyclerView);
        //RequestCenter.getLawList(mRequest,this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                mPage = 1;

                chemicalsResponseList.clear();
                mAdapter.getData().clear();
                mRequest.setPage(mPage);
                mRequest.setPageSize(pageSize);
                isReSearch=true;
                RequestCenter.getKnownlist(mRequest,ChemicalFragment.this);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_KNOWNLIST)){
            chemicalsResponseList = MyJSON.parseArray(mainData.getString("chemicalsList"), ChemicalsResponse.class);
            if (mainData.size() > 0) {
                chemicalsResponseList = MyJSON.parseArray(mainData.getString("chemicalsList"), ChemicalsResponse.class);
                if (chemicalsResponseList.size() < pageSize) {
                    isLastPage = true;
                    setFooterView();
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                if(isReSearch) {
                    mAdapter.replaceData(chemicalsResponseList);
                }else{
                    mAdapter.addData(chemicalsResponseList);
                }
                mAdapter.notifyDataSetChanged();
            }
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
        mAdapter.setLight(true,mRequest);
        isReSearch=true;
        RequestCenter.getKnownlist(mRequest,this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChemicalsResponse chemicalsResponse= (ChemicalsResponse) adapter.getData().get(position);
        Intent intent=new Intent(getContext(), ChemicalsDetailsActivity.class);
        intent.putExtra("chemicals",chemicalsResponse);
        startActivity(intent);
    }
}
