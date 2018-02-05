package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LawBaseAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.common.SystemConfig;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.entity.response.ResponseLevelList;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuan on 2017/12/24.
 */

public class LawActivity extends BaseActivity implements TextWatcher, BaseQuickAdapter.OnItemClickListener {

    private TabLayout mTablayout;
    private List<ResponseLevelList> levelDatas;

    private LawBaseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LawRequest mRequest;
    private List<LawResponse> lawDatas;

    private EditText keyworldEt;
    private int a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        initView();
        initData();
    }
    private void initView(){
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTablayout=(TabLayout)findViewById(R.id.tl_law);
        keyworldEt= (EditText) findViewById(R.id.et_keyworld);
        keyworldEt.addTextChangedListener(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_countryLayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData(){
        String typeCode=getIntent().getStringExtra("typeCode");
        String title=getIntent().getStringExtra("title");
        getTopbar().setTitle(title);
        defaultRequest(typeCode);
        levelDatas=new ArrayList<>();
        lawDatas=new ArrayList<>();
        RequestCenter.getLevelList(typeCode,this);
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mRequest.setLevel(levelDatas.get(tab.getPosition()).getCode());
                RequestCenter.getLawList(mRequest,LawActivity.this);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void defaultRequest(String typeCode) {
        mRequest=new LawRequest();
        mRequest.setIssue_no(null);
        mRequest.setTypeCode(typeCode);
        mRequest.setTypeName(null);
        mRequest.setName(null);
        mRequest.setLevel(null);
        mRequest.setPage(1);
        mRequest.setPageSize(10);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.GET_LEVELLIST)){
            JSONObject mainData = respose.getMainData();
            levelDatas= MyJSON.parseArray(mainData.getString("levelList"),ResponseLevelList.class);
            for(int i=0;i<levelDatas.size();i++){
                mTablayout.addTab(mTablayout.newTab().setText("          "+levelDatas.get(i).getName()+"   "));
            }
            mRequest.setLevel(levelDatas.get(0).getCode());
            RequestCenter.getLawList(mRequest,this);
        }else if(requestUrl.equals(RequestCenter.GET_LAWLIST)){
            JSONObject object = respose.getMainData();
            if(object.size()>0){
                if(lawDatas.size()>0){
                    lawDatas.clear();
                }
                lawDatas= MyJSON.parseArray(object.getString("lawList"),LawResponse.class);
                mAdapter =new LawBaseAdapter(lawDatas);
                mAdapter.bindToRecyclerView(mRecyclerView);
                mAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setEmptyView(R.layout.layout_empty_view);
                mAdapter.disableLoadMoreIfNotFullPage();
                mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        LogGloble.d("setOnLoadMoreListener", "==============");
                        //loadMoreData();

                    }
                }, mRecyclerView);
            }
        }
        return super.doSuccess(respose, requestUrl);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
       /* mPage = 1;
        isLastPage = false;*/

        String keyword = editable.toString();
        if(TextUtils.isEmpty(keyword)){
            mRequest.setName("");
        }else {
            mRequest.setName(keyword);
        }

        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        initData();
        mAdapter.setLight(true, mRequest);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LawResponse item = (LawResponse) adapter.getItem(position);
        LogGloble.d("MainFragment", item.getFilePath() + "");
        if (TextUtils.isEmpty(item.getFilePath())) {
            ToastUtil.getInstance().toastInCenter(this, "该文件不存在！");
            return;
        }
        if (item.getFilePath().endsWith(".pdf")) {
            Intent intent = new Intent(this, PdfViewActivity.class);
//            intent.putExtra("URL", item.getFilePath());
            intent.putExtra("URL",SystemConfig.BASEURL);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("URL", SystemConfig.BASEURL);
            startActivity(intent);
        }
    }
}
