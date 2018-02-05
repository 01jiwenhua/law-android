package com.shx.lawwh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.ChemicalsDetailsAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.entity.response.ChemicalsDetailsResponse;
import com.shx.lawwh.entity.response.ChemicalsResponse;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import java.util.List;

public class ChemicalsDetailsActivity extends BaseActivity {
    private ChemicalsResponse chemicalsResponse;
    private TextView mNameCN,mNameEN,mCAS,mMolecularFormula;
    private List<ChemicalsDetailsResponse> chemicalsResponseList;
    private ChemicalsDetailsAdapter mAdapter;
    private ExpandableListView mListView;
    private boolean isCollect= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemicals_details);
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTopbar().setTitle("详情");
        getTopbar().setRightImageVisibility(View.VISIBLE);
        getTopbar().setRightImage(R.drawable.ic_uncollect);
        final String typeCode=getIntent().getStringExtra("typeCode");
        final int id=getIntent().getIntExtra("lawId",-1);
        final ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
        getTopbar().setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCollect){
                    getTopbar().setRightImage(R.drawable.ic_uncollect);
                    RequestCenter.cancelFavorite(typeCode,userInfo.getId(),id,ChemicalsDetailsActivity.this);
                    isCollect=!isCollect;
                }else{
                    getTopbar().setRightImage(R.drawable.ic_collect);
                    RequestCenter.addFavorite(typeCode,userInfo.getId(),id,ChemicalsDetailsActivity.this);
                    isCollect=!isCollect;
                }

            }
        });
        mNameCN= (TextView) findViewById(R.id.tv_name_cn);
        mNameEN= (TextView) findViewById(R.id.tv_name_en);
        mCAS= (TextView) findViewById(R.id.tv_cas);
        mMolecularFormula= (TextView) findViewById(R.id.tv_molecularformula);
        mListView= (ExpandableListView) findViewById(R.id.list);
        chemicalsResponse= (ChemicalsResponse) getIntent().getSerializableExtra("chemicals");
        mNameCN.setText(chemicalsResponse.getNameCn());
        mNameEN.setText(chemicalsResponse.getNameEn());
        mCAS.setText(chemicalsResponse.getCas());
        mMolecularFormula.setText(chemicalsResponse.getMolecularFormula());
        RequestCenter.getChemicalsdetails(String.valueOf(chemicalsResponse.getId()),this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_CHEMICALSDETAILS)) {
            if (object.size() > 0) {

                chemicalsResponseList = MyJSON.parseArray(object.getString("chemicalsDetails"), ChemicalsDetailsResponse.class);
                mAdapter=new ChemicalsDetailsAdapter(this,chemicalsResponseList);
                mListView.setAdapter(mAdapter);
                //遍历所有group,将所有项设置成默认展开
                int intgroupCount = mListView.getCount();
                for (int i=0; i<intgroupCount; i++) {
                    mListView.expandGroup(i);
                }
            }
        }else  if(requestUrl.equals(RequestCenter.ADD_FAVORITE)) {
            ToastUtil.getInstance().toastInCenter(this,"收藏成功!");
        }else if(requestUrl.equals(RequestCenter.CANCEL_FAVORITE)){
            ToastUtil.getInstance().toastInCenter(this,"取消收藏！");
        }
        return super.doSuccess(respose, requestUrl);
    }
}
