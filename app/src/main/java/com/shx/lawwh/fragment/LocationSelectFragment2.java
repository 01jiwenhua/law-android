package com.shx.lawwh.fragment;

import android.content.Intent;
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
import com.shx.lawwh.activity.GasolineResultActivity;
import com.shx.lawwh.activity.LocationPickerActivity;
import com.shx.lawwh.adapter.LocationAdapter;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.databinding.FragmentLocationSelect2Binding;
import com.shx.lawwh.entity.response.ResponseGasoline;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.entity.response.ResponseGasolineResult;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.shx.lawwh.R.id.btn_search;

/**
 * 在没完全弄好之前先用LocationSelectFragment
 * Created by xuan on 2018/2/6.
 * 站址选择
 */

public class LocationSelectFragment2 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, HttpCallBack {
    private FragmentLocationSelect2Binding mBinding;
    private String mCurrentClickItem = "parent";//用来记录当前请求发起的地方，默认第一次请求是获取两个建筑物的，此后会分成a,b两种
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
        mBinding.btnSearch.setOnClickListener(this);
        mBinding.lvA.setAdapter(mAdapterA);
        mBinding.lvB.setAdapter(mAdapterB);
        RequestCenter.getArchitectureV2("站址选择", "", "GB 50156-2012", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_search:
                RequestCenter.getDistance(mAList.getLast().getParent().getId(), mBList.getLast().getParent().getId(), this);
                break;
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE_V2)) {
            JSONObject mainData = respose.getMainData();
            List<ResponseGasoline> responseGasolineList = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasoline.class);
            //当前是顶级请求返回结果
            if (mCurrentClickItem.equals("parent")) {
                mAList.addFirst(responseGasolineList.get(0));
                mBList.addFirst(responseGasolineList.get(1));
            }
            mAdapterA.notifyDataSetChanged();
            mAdapterB.notifyDataSetChanged();
        } else if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject data = respose.getMainData();
            List<ResponseGasolineItem> items = MyJSON.parseArray(data.getString("architecture"), ResponseGasolineItem.class);
            if (mCurrentClickItem.equals("stationA")) {
                //构造新的一行数据
                ResponseGasoline responseGasoline = new ResponseGasoline();
                //key是上一级的value
                responseGasoline.setParent(mAList.getLast().getChild().get(0));
                //value为下一级的数据
                responseGasoline.setChild(items);
                mAList.add(responseGasoline);
            } else if (mCurrentClickItem.equals("stationB")) {
                ResponseGasoline responseGasoline = new ResponseGasoline();
                responseGasoline.setParent(mBList.getLast().getChild().get(0));
                responseGasoline.setChild(items);
                mBList.add(responseGasoline);
            }
            mAdapterA.notifyDataSetChanged();
            mAdapterB.notifyDataSetChanged();
        } else if (requestUrl.equals(RequestCenter.GET_DISTANCE)) {
            JSONObject mainData = respose.getMainData();
            ResponseGasolineResult responseGasolineResult = MyJSON.parseObject(mainData.getString("distance"), ResponseGasolineResult.class);
            if (responseGasolineResult == null) {
                ToastUtil.getInstance().toastInCenter(getActivity(), "暂未收录此内容");
            } else {
                //去两个list最后一个数据的parent就可以了，fullneme是全部的请求参数传到下一个页面就可以
                Intent intent = new Intent(getActivity(), GasolineResultActivity.class);
                intent.putExtra("result", responseGasolineResult);
                intent.putExtra("oneKey", mAList.getFirst().getParent().getName());
                intent.putExtra("oneValue", mAList.getFirst().getChild().get(0).getName());
                intent.putExtra("twoKey", mBList.getFirst().getParent().getName());
                intent.putExtra("twoValue", mBList.getFirst().getChild().get(0).getName());
                intent.putExtra("AFullName", mAList.getLast().getParent().getFullName());
                intent.putExtra("BFullName", mBList.getLast().getParent().getFullName());
                startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ResponseGasolineItem item = (ResponseGasolineItem) data.getSerializableExtra("result");
                List<ResponseGasolineItem> childlist = mAList.getLast().getChild();
                childlist.clear();
                childlist.add(item);
                mAList.getLast().setChild(childlist);
                mAdapterA.notifyDataSetChanged();
                if(item.getLevel()==6){
                    ResponseGasoline responseGasoline = new ResponseGasoline();
                    responseGasoline.setParent(mAList.getLast().getChild().get(0));
                    responseGasoline.setChild(null);
                    mAList.add(responseGasoline);
                    mAdapterA.notifyDataSetChanged();
                    return;
                }
                RequestCenter.getArchitecture("", mAList.getLast().getChild().get(0).getCode(), "GB 50156-2012", this);
            }
            if (requestCode == 2) {
                ResponseGasolineItem item = (ResponseGasolineItem) data.getSerializableExtra("result");
                List<ResponseGasolineItem> childlist = mBList.getLast().getChild();
                childlist.clear();
                childlist.add(item);
                mBList.getLast().setChild(childlist);
                mAdapterB.notifyDataSetChanged();
                //如果level==6不再往下请求了
                if(item.getLevel()==6){
                    //构造最后一行数据
                    ResponseGasoline responseGasoline = new ResponseGasoline();
                    //key是上一行的value
                    responseGasoline.setParent(mBList.getLast().getChild().get(0));
                    responseGasoline.setChild(null);
                    mBList.add(responseGasoline);
                    mAdapterB.notifyDataSetChanged();
                    return;
                }
                RequestCenter.getArchitecture("", mBList.getLast().getChild().get(0).getCode(), "GB 50156-2012", this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_A);
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_B);
        switch (adapterView.getId()) {
            case R.id.lv_A:
                mCurrentClickItem = "stationA";
                //当前点击的是哪一行则把他后面的所有数据清空
                List subList = mAList.subList(i+1, mAList.size());
                subList.clear();
                if(mAList.get(i).getChild()==null||mAList.get(i).getChild().size()<=0){
                    //如果没有下一级了不再响应点击事件，即最后一行点击无效果
                    return;
                }
                Intent intent = new Intent(getActivity(), LocationPickerActivity.class);
                ResponseGasoline item = (ResponseGasoline) mAdapterA.getItem(i);
                intent.putExtra("parentCode", item.getParent().getCode());
                startActivityForResult(intent, 1);

                break;
            case R.id.lv_B:
                mCurrentClickItem = "stationB";
                //同上
                List subListb = mBList.subList(i+1, mBList.size());
                subListb.clear();
                if(mBList.get(i).getChild()==null||mBList.get(i).getChild().size()<=0){
                    return;
                }
                Intent intent2 = new Intent(getActivity(), LocationPickerActivity.class);
                ResponseGasoline item2 = (ResponseGasoline) mAdapterB.getItem(i);
                intent2.putExtra("parentCode", item2.getParent().getCode());
                startActivityForResult(intent2, 2);
                break;
        }
    }
}
