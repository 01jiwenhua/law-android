package com.shx.lawwh.fragment;

import android.content.Intent;
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
import com.shx.lawwh.activity.GasolineResultActivity;
import com.shx.lawwh.databinding.FragmentLayoutBinding;
import com.shx.lawwh.databinding.FragmentLocationSelectBinding;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.entity.response.ResponseGasolineResult;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.SinglePicker;

/**
 * Created by zhou on 2018/2/6.
 * 站内平面布局
 */

public class LayoutFragment extends Fragment implements View.OnClickListener, HttpCallBack {
    private FragmentLayoutBinding mBinding;
    private List<ResponseGasolineItem> items;
    private int index = 0;//默认为0
    //用于记录第一条数据的code和第5条数据的code
    private String oneCode, twoCode,threeCode,fourCode,fiveCode,sixCode,sevenCode,eightCode;
    //记录第一次得到的两个Key值，在下个页面用
    private String oneKey,twoKey;
    //记录最后两条记录的id
    private int oneId,twoId;

    private Map<Integer,String> conditionOneSelected=new HashMap<>();
    private Map<Integer,String> conditionTwoSelected=new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_layout, container, false);
        mBinding.llOne.setOnClickListener(this);
        mBinding.llTwo.setOnClickListener(this);
        mBinding.llThree.setOnClickListener(this);
        mBinding.llFour.setOnClickListener(this);
        mBinding.llFive.setOnClickListener(this);
        mBinding.llSix.setOnClickListener(this);
        mBinding.llSeven.setOnClickListener(this);
        mBinding.llEight.setOnClickListener(this);
        mBinding.btnSearch.setOnClickListener(this);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        items = new ArrayList<>();
        RequestCenter.getArchitecture("站内平面布置", "", "GB 50156-2012", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_one:
                index = 1;
                mBinding.llTwo.setVisibility(View.GONE);
                mBinding.llThree.setVisibility(View.GONE);
                mBinding.llFour.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", oneCode, "GB 50156-2012", this);
                break;
            case R.id.ll_two:
                index = 2;
                mBinding.llThree.setVisibility(View.GONE);
                mBinding.llFour.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", twoCode, "GB 50156-2012", this);
                break;
            case R.id.ll_three:
                index = 3;
                mBinding.llFour.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", threeCode, "GB 50156-2012", this);
                break;
            case R.id.ll_four:
                index = 4;
                RequestCenter.getArchitecture("", fourCode, "GB 50156-2012", this);
                break;
            case R.id.ll_five:
                index = 5;
                mBinding.llSix.setVisibility(View.GONE);
                mBinding.llSeven.setVisibility(View.GONE);
                mBinding.llEight.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", fiveCode, "GB 50156-2012", this);
                break;
            case R.id.ll_six:
                index = 6;
                mBinding.llSeven.setVisibility(View.GONE);
                mBinding.llEight.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", sixCode, "GB 50156-2012", this);
                break;
            case R.id.ll_seven:
                index = 7;
                mBinding.llEight.setVisibility(View.GONE);
                RequestCenter.getArchitecture("", sevenCode, "GB 50156-2012", this);
                break;
            case R.id.ll_eight:
                index = 8;
                RequestCenter.getArchitecture("", eightCode, "GB 50156-2012", this);
                break;
            case R.id.btn_search:
                RequestCenter.getDistance(oneId,twoId,this);
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
        switch (index) {
            case 1:
                pick(mBinding.tvOneValue, mBinding.llTwo, mBinding.tvTwoKey);
                break;
            case 2:
                pick(mBinding.tvTwoValue, mBinding.llThree, mBinding.tvThreeKey);

                break;
            case 3:
                pick(mBinding.tvThreeValue, mBinding.llFour, mBinding.tvFourKey);
                break;
            case 4:
                pick(mBinding.tvFourValue);
                break;
            case 5:
                pick(mBinding.tvFiveValue, mBinding.llSix, mBinding.tvSixKey);
                break;
            case 6:
                pick(mBinding.tvSixValue, mBinding.llSeven, mBinding.tvSevenKey);
                break;
            case 7:
                pick(mBinding.tvSevenValue, mBinding.llEight, mBinding.tvEightKey);
                break;
            case 8:
                pick(mBinding.tvEightValue);
                break;
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject mainData = respose.getMainData();
            items = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasolineItem.class);
            if (items.size() > 0) {
                if (items.get(0).getLevel() <= 6) {
                    //判断数据是否为空
                    if (index == 0) {
                        //第一次进来
//                mBinding.one 的右侧显示list(0);
                        mBinding.tvOneKey.setText(items.get(0).getName()+"A");
                        mBinding.tvFiveKey.setText(items.get(0).getName()+"B");
                        oneCode = items.get(0).getCode();
                        fiveCode = items.get(0).getCode();
                        oneKey=items.get(0).getName();
                        twoKey= items.get(0).getName();

                    } else {
                        showItemCchoice();
                    }
                }

            }
        }else if(requestUrl.equals(RequestCenter.GET_DISTANCE)){
            JSONObject mainData = respose.getMainData();
            ResponseGasolineResult responseGasolineResult=MyJSON.parseObject(mainData.getString("distance"),ResponseGasolineResult.class);
            if(responseGasolineResult==null){
                ToastUtil.getInstance().toastInCenter(getActivity(),"数据不存在！");
            }else {
                Intent intent = new Intent(getActivity(), GasolineResultActivity.class);
                intent.putExtra("result", responseGasolineResult);
                intent.putExtra("oneCondition", (Serializable) conditionOneSelected);
                intent.putExtra("twoCondition", (Serializable) conditionTwoSelected);
                intent.putExtra("oneKey", oneKey + "A");
                intent.putExtra("twoKey", twoKey + "B");
                startActivity(intent);
            }
        }
        return false;
    }

    /**
     * 选择
     */
    private void pick(final TextView currentValue, final LinearLayout layout, final TextView nextKey) {
        SinglePicker<ResponseGasolineItem> picker = new SinglePicker<>(getActivity(), items);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseGasolineItem>() {
            @Override
            public void onItemPicked(int i, ResponseGasolineItem item) {
                currentValue.setText(item.getName());
                if(index<5) {
                    conditionOneSelected.put(index, item.getName());
                }else{
                    conditionTwoSelected.put(index,item.getName());
                }
                if(item.getLevel()<6){
                    layout.setVisibility(View.VISIBLE);
                }else{
                    if(index<5){
                        oneId=item.getId();
                    }else{
                        twoId=item.getId();
                    }
                }
                nextKey.setText(item.getName());
                switch (index){
                    case 1:
                        twoCode=item.getCode();
                        break;
                    case 2:
                        threeCode=item.getCode();
                        break;
                    case 3:
                        fourCode=item.getCode();
                        break;
                    case 4:
                        fiveCode=item.getCode();
                        break;
                    case 5:
                        sixCode=item.getCode();
                        break;
                    case 6:
                        sevenCode=item.getCode();
                        break;
                    case 7:
                        eightCode=item.getCode();
                        break;

                }
            }
        });
        picker.show();
    }

    /**
     * 选择
     */
    private void pick(final TextView currentValue) {
        SinglePicker<ResponseGasolineItem> picker = new SinglePicker<>(getActivity(), items);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseGasolineItem>() {
            @Override
            public void onItemPicked(int index, ResponseGasolineItem item) {
                currentValue.setText(item.getName());
                if(index<5) {
                    conditionOneSelected.put(index, item.getName());
                }else{
                    conditionTwoSelected.put(index,item.getName());
                }
                if(item.getLevel()==6){
                    if(index<5){
                        oneId=item.getId();
                    }else{
                        twoId=item.getId();
                    }
                }
                switch (index){
                    case 1:
                        twoCode=item.getCode();
                        break;
                    case 2:
                        threeCode=item.getCode();
                        break;
                    case 3:
                        fourCode=item.getCode();
                        break;
                    case 4:
                        fiveCode=item.getCode();
                        break;
                    case 5:
                        sixCode=item.getCode();
                        break;
                    case 6:
                        sevenCode=item.getCode();
                        break;
                    case 7:
                        eightCode=item.getCode();
                        break;

                }
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
