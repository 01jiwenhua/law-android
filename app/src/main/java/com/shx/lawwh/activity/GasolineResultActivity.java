package com.shx.lawwh.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.databinding.ActivityGasolineResultBinding;
import com.shx.lawwh.entity.response.ResponseGasoline;
import com.shx.lawwh.entity.response.ResponseGasolineResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        responseGasolineResult=(ResponseGasolineResult)getIntent().getSerializableExtra("result");
        mBinding.tvOneKey.setText(getIntent().getStringExtra("oneKey")+":");
        mBinding.tvTwoKey.setText(getIntent().getStringExtra("twoKey")+":");
        mBinding.tvOneValue.setText(getIntent().getStringExtra("oneValue"));
        mBinding.tvTwoValue.setText(getIntent().getStringExtra("twoValue"));
        mBinding.tvOneCondition.setText(getIntent().getStringExtra("AFullName"));
        mBinding.tvTwoCondition.setText(getIntent().getStringExtra("BFullName"));
        mBinding.tvDistance.setText(responseGasolineResult.getDistance()+"m");
        mBinding.tvStandard.setText(responseGasolineResult.getStandard());
        mBinding.tvDeclare.setText(responseGasolineResult.getNoteContent());
    }

    /**
     * 根据map中的key值排序，然后把value拼接起来
     * */
    private String sortKey(HashMap<Integer, String> hashMap){
        Object[] key_arr =  hashMap.keySet().toArray();
        StringBuilder stringBuilder=new StringBuilder();
        Arrays.sort(key_arr);
        for(int i=0;i<key_arr.length;i++){
            Object key=key_arr[i];
            stringBuilder.append(hashMap.get(key));
            if((1+i)!=key_arr.length) {
                stringBuilder.append(">>");
            }
        }
        return stringBuilder.toString();
    }
}
