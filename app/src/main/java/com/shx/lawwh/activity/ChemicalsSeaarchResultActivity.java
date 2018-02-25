package com.shx.lawwh.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.ChemicalsSelectedAdapter;
import com.shx.lawwh.adapter.KnownAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.response.ChemicalsResponse;
import com.shx.lawwh.entity.response.UnknownParams;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.shx.lawwh.fragment.UnknowFragment.checkMap;

/**
 * Created by xuan on 2017/12/24.
 */

public class ChemicalsSeaarchResultActivity extends BaseActivity implements HttpCallBack,BaseQuickAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private KnownAdapter mAdapter;
    private int page = 1;
    private final int pageSize = 50;
    private boolean isLastPage = false;
    private ChemicalsRequest mRequest;
    private List<ChemicalsResponse> chemicalsResponseList=new ArrayList<>();
    private NoScrollGridView characterNsgv,endangerNsgv;
    private   Map<String, UnknownParams> characterMap,endangerMap;
    private List<UnknownParams> characterDatas;
    private List<UnknownParams> endangerDatas;
    private ChemicalsSelectedAdapter characterAdapter;
    private ChemicalsSelectedAdapter endangerAdapter;
    private TextView characterTv,endangerTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemicals_result);
        getTopbar().setTitle("查询结果");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRequest= (ChemicalsRequest) getIntent().getSerializableExtra("request");
        characterMap= (Map<String, UnknownParams>) getIntent().getSerializableExtra("character");
        endangerMap= (Map<String, UnknownParams>) getIntent().getSerializableExtra("endanger");
        characterDatas=new ArrayList<>();
        endangerDatas=new ArrayList<>();
        for(UnknownParams characterParams:characterMap.values()){
            characterDatas.add(characterParams);
        }
        for(UnknownParams endangerParams :endangerMap.values()){
            endangerDatas.add(endangerParams);
        }
        initView();
//        DialogManager.getInstance().showProgressDialog(this);
//        RequestCenter.getKnownlist(mRequest,this);

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
        RequestCenter.getKnownlist(mRequest,this);
    }

    public void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_known);
        mRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.layout_refresh);
        characterNsgv= (NoScrollGridView) findViewById(R.id.nsgv_character);
        endangerNsgv= (NoScrollGridView) findViewById(R.id.nsgv_endanger);
        characterTv= (TextView) findViewById(R.id.tv_character);
        endangerTv= (TextView) findViewById(R.id.tv_endanger);
        ImageView line= (ImageView) findViewById(R.id.iv_line);
        characterNsgv.setNumColumns(2);
        endangerNsgv.setNumColumns(2);
        characterAdapter=new ChemicalsSelectedAdapter(characterDatas,this);
        endangerAdapter=new ChemicalsSelectedAdapter(endangerDatas,this);
        characterNsgv.setAdapter(characterAdapter);
        endangerNsgv.setAdapter(endangerAdapter);
        if(characterDatas.size()>0){
            characterTv.setVisibility(View.VISIBLE);
        }
        if(endangerDatas.size()>0){
            endangerTv.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
        DialogManager.getInstance().showProgressDialog(this);
        RequestCenter.getKnownlist(mRequest,this);
    }
    private void setFooterView() {
        View footer = LayoutInflater.from(this).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
    }
    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChemicalsResponse chemicalsResponse= (ChemicalsResponse) adapter.getData().get(position);
        Intent intent=new Intent(this, ChemicalsDetailsActivity.class);
        intent.putExtra("chemicals",chemicalsResponse);
        intent.putExtra("lawId",chemicalsResponse.getId());

        startActivity(intent);
    }
}
