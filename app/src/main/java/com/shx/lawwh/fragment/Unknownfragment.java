package com.shx.lawwh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.UnknwoParamsAdapter;
import com.shx.lawwh.entity.response.LawResponse;
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

/**
 * Created by xuan on 2017/12/24.
 */

public class UnKnownFragment extends Fragment implements HttpCallBack {

    private NoScrollGridView mLhGridView;
    private NoScrollGridView mJkwhGridView;
    private UnknwoParamsAdapter mAdapter;
    private UnknwoParamsAdapter mAdapter2;
    private List<UnknownParams> LhList = new ArrayList<>();
    private List<UnknownParams> jkwhList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unknown, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestCenter.getUnknowparams(this);
        mLhGridView = (NoScrollGridView) view.findViewById(R.id.lhtx);
        mJkwhGridView = (NoScrollGridView) view.findViewById(R.id.jkwh);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_UNKNOWPARAMS)) {
            if (object.size() > 0) {
                LhList = MyJSON.parseArray(object.getString("lhList"), UnknownParams.class);
                mAdapter = new UnknwoParamsAdapter(LhList, getContext());
                mLhGridView.setAdapter(mAdapter);
                jkwhList = MyJSON.parseArray(object.getString("jkwhList"), UnknownParams.class);
                mAdapter2=new UnknwoParamsAdapter(jkwhList,getContext());
                mJkwhGridView.setAdapter(mAdapter2);
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
}
