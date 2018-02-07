package com.shx.lawwh.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.databinding.ActivityGasolineResultBinding;
import com.shx.lawwh.entity.response.ResponseGasolineResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by adm on 2018/2/8.
 */

public class GasolineResultActivity extends BaseActivity {

    private ActivityGasolineResultBinding mBinding;
    private ResponseGasolineResult responseGasolineResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_gasoline_result);
        getTopbar().setTitle("查询结果");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        responseGasolineResult= (ResponseGasolineResult) getIntent().getSerializableExtra("result");
        HashMap<Integer,String> oneMap= (HashMap<Integer, String>) getIntent().getSerializableExtra("oneCondition");
        HashMap<Integer,String> twoMap= (HashMap<Integer, String>) getIntent().getSerializableExtra("twoCondition");
        String onekey=getIntent().getStringExtra("oneKey");
        String twokey=getIntent().getStringExtra("twoKey");
        mBinding.tvOneKey.setText(onekey+":");
        mBinding.tvTwoKey.setText(twokey+":");
        mBinding.tvOneValue.setText(oneMap.get(1));
        mBinding.tvTwoValue.setText(twoMap.get(1));
        StringBuilder oneCondition=new StringBuilder();
        StringBuilder twoCondition=new StringBuilder();
        Iterator<Map.Entry<Integer, String>> iterator = oneMap.entrySet().iterator();
        while ((iterator.hasNext())){
            Map.Entry<Integer, String> next = iterator.next();
            oneCondition.append(next.getValue()+">>");
        }
        Iterator<Map.Entry<Integer, String>> iterator1 = twoMap.entrySet().iterator();
        while(iterator1.hasNext()){
            Map.Entry<Integer, String> next = iterator1.next();
            twoCondition.append(next.getValue()+">>");
        }
        mBinding.tvOneCondition.setText(oneCondition.toString());
        mBinding.tvTwoCondition.setText(twoCondition.toString());
        mBinding.tvDistance.setText(responseGasolineResult.getDistance()+"m");
        mBinding.tvStandard.setText(responseGasolineResult.getStandard());
        mBinding.tvDeclare.setText(responseGasolineResult.getInstruction());
    }
}
