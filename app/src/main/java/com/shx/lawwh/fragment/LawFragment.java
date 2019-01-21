package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.PdfViewActivity;
import com.shx.lawwh.activity.WebActivity;
import com.shx.lawwh.adapter.LawBaseAdapter;
import com.shx.lawwh.common.LogGloble;
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

public class LawFragment extends Fragment implements HttpCallBack , BaseQuickAdapter.OnItemClickListener {
    private LawBaseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LawRequest mRequest;
    private List<LawResponse> lawList = new ArrayList<>();
    private int mPage = 1;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isReSearch=false;//是否是通过关键字重新搜索
    //记录是否是第一次运行，如果是第一次运行则不加载数据。
    private boolean isFirstRun=true;

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
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFirstRun){
            isReSearch=true;
            RequestCenter.getLawList(mRequest, this);
        }
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
        RequestCenter.getLawList(mRequest, this);


    }

    private void setFooterView() {
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
    }

    private void initData(){
        mRequest=new LawRequest();
        mRequest.setPage(1);
        mRequest.setPageSize(30);
        mRequest.setTypeCode("flfg");
        mAdapter = new LawBaseAdapter(lawList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.layout_empty_view);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isReSearch=false;
                loadMoreData();
            }
        },mRecyclerView);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                mPage = 1;

                lawList.clear();
                mAdapter.getData().clear();
                mRequest.setPage(mPage);
                mRequest.setPageSize(pageSize);
                isReSearch=true;
                RequestCenter.getLawList(mRequest, LawFragment.this);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                refreshLayout.setRefreshing(false);
            }
        });
        //RequestCenter.getLawList(mRequest,this);
    }


    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_LAWLIST)){
            if (mainData.size() > 0) {
                lawList = MyJSON.parseArray(mainData.getString("lawList"), LawResponse.class);
                if (lawList.size() < pageSize) {
                    isLastPage = true;
                    setFooterView();
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                if(isReSearch) {
                    mAdapter.replaceData(lawList);
                }else{
                    mAdapter.addData(lawList);
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
        mRequest.setDescription(key);
        mAdapter.setLight(true,mRequest);
        isReSearch=true;
        RequestCenter.getLawList(mRequest,this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LawResponse item = (LawResponse) mAdapter.getItem(position);
        //如果点击了item，之后再返回就不是第一次运行，必须刷新数据，这样才能得到是否收藏的最新状态
        isFirstRun=false;
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
