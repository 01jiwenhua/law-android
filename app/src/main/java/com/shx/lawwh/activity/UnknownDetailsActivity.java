package com.shx.lawwh.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.UnknowParamsDetailsAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.entity.response.UnknownParams;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.List;

public class UnknownDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private UnknowParamsDetailsAdapter mAdapter;
    private List<UnknownParams> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknown_details);
        mListView= (ListView) findViewById(R.id.list);
        String code=getIntent().getStringExtra("code");
        DialogManager.getInstance().showProgressDialog(this);
        RequestCenter.getUnknowparamsDetails(code,this);
        mListView.setOnItemClickListener(this);
        getTopbar().setTitle("条件筛选");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_UNKNOWPARAMS_DETAILS)) {
            if (object.size() > 0) {
                mList = MyJSON.parseArray(object.getString("list"), UnknownParams.class);
                mAdapter = new UnknowParamsDetailsAdapter(mList,this);
                mListView.setAdapter(mAdapter);

            }
        }
        return super.doSuccess(respose, requestUrl);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UnknownParams unknownParams= (UnknownParams) parent.getAdapter().getItem(position);
        Intent mIntent = new Intent();
        mIntent.putExtra("details", unknownParams);

        // 设置结果，并进行传送
        this.setResult(RESULT_OK, mIntent);
        finish();
    }
}
