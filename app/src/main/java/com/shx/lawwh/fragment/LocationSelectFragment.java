package com.shx.lawwh.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.databinding.FragmentLocationSelectBinding;
import com.shx.lawwh.entity.response.ResponseCompanyList;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

/**
 * Created by zhou on 2018/2/6.
 * 站址选择
 */

public class LocationSelectFragment extends Fragment implements View.OnClickListener, HttpCallBack {

    private FragmentLocationSelectBinding mBinding;
    private List<ResponseGasolineItem> items;
    private int index = 0;//默认为0

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_select, container, false);
        return mBinding.getRoot();
    }

    private void initView(View view) {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        items = new ArrayList<>();
        RequestCenter.getArchitecture("站址选择", "站址选择", "GB 50156-2012", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_one:
                RequestCenter.getArchitecture("", items.get(0).getCode(), "GB 50156-2012", this);
                index = (int) mBinding.llOne.getTag();
                showItemCchoice();
                break;
            case R.id.ll_two:
                index = (int) mBinding.llTwo.getTag();
                break;
            case R.id.ll_three:
                index = (int) mBinding.llThree.getTag();
                break;
        }
    }

    private void showItemCchoice() {
        //默认数据已经回来了，回不来可以用ProgressDialog控制
        //显示选择管轮弹框
        //当点击弹框里边的数据时即选中了一条
        //接下来出来应该显示哪个数据了
        //list.onItemClicke({
        //  点击了第几个，获取数据
        if (index == 1) {
            //     显示第2个
            mBinding.llTwo.setVisibility(View.VISIBLE);
//            mBinding.llTwo.getChildAt(1);
            //左侧显示mBinding.one的vule值  右侧等待数据返回
        }

        if (index == 2) {
            //     显示第3个
            mBinding.llThree.setVisibility(View.VISIBLE);
            //左侧显示mBinding.llTwo的vule值  右侧等待数据返回
            //  }
            if (index == 3) {
                //  限时第4个
                mBinding.llFour.setVisibility(View.VISIBLE);
//                左侧显示mBinding.llThree的vule值  右侧等待数据返回
            }
            if(index==4){
//                显示第5个
                mBinding.llFive.setVisibility(View.VISIBLE);
//                左侧显示mBinding.llFour的vule值  右侧等待数据返回
            }

        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject mainData = respose.getMainData();
            items = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasolineItem.class);
            //判断数据是否为空
            if (index == 0) {
                //第一次进来
//                mBinding.one 的右侧显示list(0);

            }
            if (index == 1) {
                //在第一条弹出框选中数据以后
//                mBinding.llTwo 的右侧显示list(0);

            }
            if (index == 2) {
                //在第二条弹出框选中数据以后
//                mBinding.llThree 的右侧显示list(0);

            }
            if (index == 3) {
                //在第三条弹出框选中数据以后
//                mBinding.llFour 的右侧显示list(0);

            }
//            for (int i=0;i<items.size();i++){
//                if(items.get(i).getLevel()==2){
//                    if(i==0) {
//                        mBinding.tvOneKey.setText(items.get(i).getName());
//                        mBinding.llOne.setOnClickListener(this);
//                    }else{
//                        mBinding.tvFiveKey.setText(items.get(i).getName());
//                        mBinding.llFive.setOnClickListener(this);
//                    }
//                }
//            }
        }
        return false;
    }

    /**
     * 选择
     */
    private void pick() {
        SinglePicker<ResponseCompanyList> picker = new SinglePicker<>(this, companyList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseCompanyList>() {
            @Override
            public void onItemPicked(int index, ResponseCompanyList item) {
                mBinding.tvCompany.setText(item.getName());
            }
        });
        picker.show();
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
