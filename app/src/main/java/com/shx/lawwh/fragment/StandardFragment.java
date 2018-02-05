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

public class StandardFragment extends Fragment implements HttpCallBack ,BaseQuickAdapter.OnItemClickListener{

    private LawBaseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LawRequest mRequest;
    private List<LawResponse> lawList = new ArrayList<>();

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

    private void initData(){
        mRequest=new LawRequest();
        mRequest.setPage(1);
        mRequest.setPageSize(30);
        mRequest.setTypeCode("bzgf");
        RequestCenter.getLawList(mRequest,this);
    }


    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        JSONObject mainData = respose.getMainData();
        if(requestUrl.equals(RequestCenter.GET_LAWLIST)){
            lawList = MyJSON.parseArray(mainData.getString("lawList"), LawResponse.class);
            mAdapter =new LawBaseAdapter(lawList);
            mAdapter.setLight(true,mRequest);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(this);
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
        RequestCenter.getLawList(mRequest,this);
        if(key.toString().isEmpty()){
            mAdapter.setLight(false,mRequest);
        }else {
            mAdapter.setLight(true, mRequest);
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LawResponse item = (LawResponse) mAdapter.getItem(position);
        LogGloble.d("MainFragment", item.getFilePath() + "");
        if (TextUtils.isEmpty(item.getFilePath())) {
            ToastUtil.getInstance().toastInCenter(getContext(), "该文件不存在！");
            return;
        }
        if (item.getFilePath().endsWith(".pdf")) {
            Intent intent = new Intent(getContext(), PdfViewActivity.class);
            intent.putExtra("URL", item.getFilePath());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra("URL", item.getFilePath());
            startActivity(intent);
        }
    }
}
