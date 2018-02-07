package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.ChemicalsDetailsActivity;
import com.shx.lawwh.adapter.KnownAdapter;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.response.ChemicalsResponse;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuan on 2017/12/24.
 */

public class KnownFragment extends Fragment implements HttpCallBack,BaseQuickAdapter.OnItemClickListener,View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private KnownAdapter mAdapter;
    private EditText mKeyword;
    //private ImageView mSearchView;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private ChemicalsRequest mRequest;
    private List<ChemicalsResponse> chemicalsResponseList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_known,null);
        return view;
    }
    private void loadMoreData(){
        if (isLastPage) {
            setFooterView();
            mAdapter.loadMoreEnd();
            return;
        }
        page++;
        LogGloble.d("loadMoreData", page + "");
        mRequest.setPage(page);
        //DialogManager.getInstance().showProgressDialog(getActivity());
        RequestCenter.getKnownlist(mRequest, this);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_known);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mKeyword= (EditText) view.findViewById(R.id.et_keyworld);
        mKeyword.addTextChangedListener(this);
        mKeyword.setOnEditorActionListener(this);
//        mSearchView= (ImageView) view.findViewById(R.id.iv_search);
//        mSearchView.setOnClickListener(this);
        mRequest=new ChemicalsRequest();
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new KnownAdapter(chemicalsResponseList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_empty_view);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogGloble.d("setOnLoadMoreListener", "==============");
                loadMoreData();

            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                page = 1;
                mRequest.setPage(page);
                chemicalsResponseList.clear();
                mAdapter.getData().clear();
                initData();
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.setNewData(chemicalsResponseList);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);

            }
        });

    }
    private void initData(){
        page=1;
        mRequest.setPage(page);
        mRequest.setPageSize(pageSize);
        //DialogManager.getInstance().showProgressDialog(getActivity());
        RequestCenter.getKnownlist(mRequest,this);
    }
    private void setFooterView() {
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        //DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_KNOWNLIST)) {
            if (object.size() > 0) {
                chemicalsResponseList = MyJSON.parseArray(object.getString("chemicalsList"), ChemicalsResponse.class);
                if (chemicalsResponseList.size() < pageSize) {
                    isLastPage = true;
                    setFooterView();
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                mAdapter.addData(chemicalsResponseList);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChemicalsResponse chemicalsResponse= chemicalsResponseList.get(position);
        Intent intent=new Intent(getContext(), ChemicalsDetailsActivity.class);
        intent.putExtra("chemicals",chemicalsResponse);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.iv_search:
//                String keyword=mKeyword.getText().toString();
//                mRequest.setName(keyword);
//                page=1;
//                mRequest.setPage(page);
//                chemicalsResponseList.clear();
//                mAdapter.getData().clear();
//                initData();
//                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
//                mAdapter.setNewData(chemicalsResponseList);
//                mRefreshLayout.setRefreshing(false);
//                mAdapter.setLight(true,mRequest);
//                mAdapter.notifyDataSetChanged();
//                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        mRequest.setName(s.toString());
        page=1;
        mRequest.setPage(page);
        chemicalsResponseList.clear();
        mAdapter.getData().clear();
        initData();
        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
        mAdapter.setNewData(chemicalsResponseList);
        mRefreshLayout.setRefreshing(false);
        if(s.toString().isEmpty()){
            mAdapter.setLight(false,mRequest);
        }else {
            mAdapter.setLight(true, mRequest);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
