package com.shx.lawwh.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LocationAdapter;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.databinding.FragmentLocationSelect2Binding;
import com.shx.lawwh.entity.response.ResponseGasoline;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.LinkedList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

/**
 * Created by zhou on 2018/2/6.
 * 站址选择
 */

public class LocationSelectFragment2 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, HttpCallBack {
    private FragmentLocationSelect2Binding mBinding;
    private String mCurrentClickItem = "parent";
    private LinkedList<ResponseGasoline> mAList;
    private LinkedList<ResponseGasoline> mBList;
    private LocationAdapter mAdapterA;
    private LocationAdapter mAdapterB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_select2, container, false);
        mBinding.btnSearch.setOnClickListener(this);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mAList = new LinkedList<>();
        mBList = new LinkedList<>();
        mAdapterA = new LocationAdapter(getContext(), mAList, true);
        mAdapterB = new LocationAdapter(getContext(), mBList, true);
        mBinding.lvA.setOnItemClickListener(this);
        mBinding.lvB.setOnItemClickListener(this);
        mBinding.lvA.setAdapter(mAdapterA);
        mBinding.lvB.setAdapter(mAdapterB);
        RequestCenter.getArchitecture("站址选择", "", "GB 50156-2012", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_one:
//                RequestCenter.getArchitecture("", oneCode, "GB 50156-2012", this);
                break;
            case R.id.ll_five:
                break;
        }
    }

    private void showItemCchoice(final AdapterView adapterView, ResponseGasoline responseGasoline) {
        SinglePicker<ResponseGasolineItem> picker = new SinglePicker<>(getActivity(), responseGasoline.getChild());
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<ResponseGasolineItem>() {
            @Override
            public void onItemPicked(int i, ResponseGasolineItem item) {

                switch (adapterView.getId()) {
                    case R.id.lv_A:
                        List<ResponseGasolineItem> child = mAList.getLast().getChild();
                        child.clear();
                        child.add(item);
                        mAList.getLast().setChild(child);
                        if (item.getLevel() == 6) {
                            mAdapterA.setHasNext(false);
                        }else{
                            RequestCenter.getArchitecture("",item.getParentCode(),"GB 50156-2012",LocationSelectFragment2.this);
                        }
                        mAdapterA.notifyDataSetChanged();
                        break;
                    case R.id.lv_B:
                        List<ResponseGasolineItem> child2 = mBList.getLast().getChild();
                        child2.clear();
                        child2.add(item);
                        mBList.getLast().setChild(child2);
                        if (item.getLevel() == 6) {
                            mAdapterB.setHasNext(false);
                        }else{
                            RequestCenter.getArchitecture("",item.getParentCode(),"GB 50156-2012",LocationSelectFragment2.this);
                        }
                        mAdapterB.notifyDataSetChanged();
                        break;
            }


        }
    });
        picker.show();
}

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject mainData = respose.getMainData();
            List<ResponseGasoline> responseGasolineList = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasoline.class);
            //当前是顶级请求返回结果
            if (mCurrentClickItem.equals("parent")) {
                mAList.addFirst(responseGasolineList.get(0));
                mBList.addFirst(responseGasolineList.get(1));
            } else if (mCurrentClickItem.equals("stationA")) {

                mAList.add(responseGasolineList.get(0));

            } else if (mCurrentClickItem.equals("stationB")) {
                mBList.add(responseGasolineList.get(0));
            }
            mAdapterA.notifyDataSetChanged();
            mAdapterB.notifyDataSetChanged();

        } else if (requestUrl.equals(RequestCenter.GET_DISTANCE)) {
//            JSONObject mainData = respose.getMainData();
//            ResponseGasolineResult responseGasolineResult = MyJSON.parseObject(mainData.getString("distance"), ResponseGasolineResult.class);
//            if (responseGasolineResult == null) {
//                ToastUtil.getInstance().toastInCenter(getActivity(), "暂未收录此内容");
//            } else {
//                Intent intent = new Intent(getActivity(), GasolineResultActivity.class);
//                intent.putExtra("result", responseGasolineResult);
//                intent.putExtra("oneCondition", (Serializable) conditionOneSelected);
//                intent.putExtra("twoCondition", (Serializable) conditionTwoSelected);
//                intent.putExtra("oneKey", oneKey);
//                intent.putExtra("twoKey", twoKey);
//                startActivity(intent);
//            }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_A);
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_B);
        switch (adapterView.getId()) {
            case R.id.lv_A:
                mCurrentClickItem = "stationA";

                if (!mAdapterA.isHasNext()) {
                    return;
                }

                if (mAList.get(i).getChild() != null || mAList.get(i).getChild().size() > 0) {

                    showItemCchoice(adapterView, mAList.get(i));

                }

                break;
            case R.id.lv_B:
                mCurrentClickItem = "stationB";
                if (!mAdapterB.isHasNext()) {
                    return;
                }

                if (mBList.get(i).getChild() != null || mBList.get(i).getChild().size() > 0) {
                    showItemCchoice(adapterView, mBList.get(i));
                }
                break;
        }
    }
}
