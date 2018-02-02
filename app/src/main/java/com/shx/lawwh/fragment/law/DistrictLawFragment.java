package com.shx.lawwh.fragment.law;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LawListAdapter;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.List;

/**
 * Created by zhou on 2018/2/1.
 */

public class DistrictLawFragment extends Fragment implements HttpCallBack{

    private LawListAdapter adapter;
    private RecyclerView recyclerView;
    private LawRequest mRequest;
    private List<LawResponse> lawDatas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_countrylaw,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_countryLayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRequest=new LawRequest();
        mRequest.setTypeCode("法律法规");
        mRequest.setIssue_no(null);
        mRequest.setTypeName(null);
        mRequest.setName(null);
        mRequest.setLevel("地方法规");
        mRequest.setPage(1);
        mRequest.setPageSize(10);
        RequestCenter.getLawList(mRequest,this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_LAWLIST)){
            JSONObject object = respose.getMainData();
            if(object.size()>0){
                lawDatas= MyJSON.parseArray(object.getString("lawList"),LawResponse.class);
                adapter=new LawListAdapter(lawDatas);
                recyclerView.setAdapter(adapter);
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
