package com.shx.lawwh.fragment;

import android.annotation.SuppressLint;
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
import com.shx.lawwh.databinding.FragmentTwoitemBinding;
import com.shx.lawwh.entity.response.ResponseGasoline;
import com.shx.lawwh.entity.response.ResponseGasolineItem;
import com.shx.lawwh.entity.response.ResponseGasolineResult;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;

import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.shx.lawwh.R.id.btn_search;

/**
 * Created by adm on 2018/2/25.
 */

@SuppressLint("ValidFragment")
public class FireProofCommonFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, HttpCallBack {

    private FragmentTwoitemBinding mBinding;
    private String mCurrentClickItem = "parent";//用来记录当前请求发起的地方，默认第一次请求是获取两个建筑物的，此后会分成a,b两种
    private LinkedList<ResponseGasoline> mAList;
    private LinkedList<ResponseGasoline> mBList;
    private LocationAdapter mAdapterA;
    private LocationAdapter mAdapterB;
    private boolean mAisLast;
    private boolean mBisLast;
    private String name;
    private String standard;
    //标识是一个Item，还是两个Item
    private boolean isOne=false;

    @SuppressLint("ValidFragment")
    public FireProofCommonFragment(String name, String standard){
        super();
        this.name=name;
        this.standard=standard;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_twoitem, container, false);
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
        mAdapterA = new LocationAdapter(getContext(), mAList, false);
        mAdapterB = new LocationAdapter(getContext(), mBList, false);
        mBinding.lvA.setOnItemClickListener(this);
        mBinding.lvB.setOnItemClickListener(this);
        mBinding.btnSearch.setOnClickListener(this);
        mBinding.lvA.setAdapter(mAdapterA);
        mBinding.lvB.setAdapter(mAdapterB);
        RequestCenter.getArchitectureV2(name, "", standard, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_search:
                if(isOne){
                    RequestCenter.getDistance(mAList.getLast().getChild().get(0).getId(), mAList.getLast().getChild().get(0).getId(), this);
                }else {
                    RequestCenter.getDistance(mAList.getLast().getChild().get(0).getId(), mBList.getLast().getChild().get(0).getId(), this);
                }
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
                if(responseGasolineList.size()==1) {
                    mAList.addFirst(responseGasolineList.get(0));
                    isOne=true;
                }else if(responseGasolineList.size()==2){
                    mAList.addFirst(responseGasolineList.get(0));
                    mBList.addFirst(responseGasolineList.get(1));
                    isOne=false;
                }
            }
            mAdapterA.notifyDataSetChanged();
            mAdapterB.notifyDataSetChanged();
        } else if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject data = respose.getMainData();
            List<ResponseGasolineItem> items = MyJSON.parseArray(data.getString("architecture"), ResponseGasolineItem.class);
            if (mCurrentClickItem.equals("stationA")) {
                //构造新的一行数据
//                ResponseGasoline responseGasoline = new ResponseGasoline();
                //key是上一级的value
//                responseGasoline.setParent(mAList.getLast().getChild().get(0));
                //构造新的数据，parent是上一行的
                if(items==null||items.size()<=0){
                    mAisLast=true;
                    mAdapterA.setLast(mAisLast);
                    mAdapterA.notifyDataSetChanged();
                    return false;
                }
                ResponseGasoline responseGasoline = new ResponseGasoline();
                responseGasoline.setParent(mAList.getLast().getChild().get(0));
                responseGasoline.setChild(items);
                mAList.add(responseGasoline);
                mAdapterA.notifyDataSetChanged();
                //value为下一级的数据
//                responseGasoline.setChild(items);
//                mAList.add(responseGasoline);
            } else if (mCurrentClickItem.equals("stationB")) {
//                ResponseGasoline responseGasoline = new ResponseGasoline();
//                responseGasoline.setParent(mBList.getLast().getChild().get(0));
                if(items==null||items.size()<=0){
                    mBisLast=true;
                    mAdapterB.setLast(mBisLast);
                    mAdapterB.notifyDataSetChanged();
                    return false;
                }
                //构造最后一行数据
                ResponseGasoline responseGasoline = new ResponseGasoline();
                //key是上一行的value
                responseGasoline.setParent(mBList.getLast().getChild().get(0));
                responseGasoline.setChild(items);
                mBList.add(responseGasoline);
                mAdapterB.notifyDataSetChanged();
//                responseGasoline.setChild(items);
//                mBList.add(responseGasoline);
            }
        } else if (requestUrl.equals(RequestCenter.GET_DISTANCE)) {
            JSONObject mainData = respose.getMainData();
            ResponseGasolineResult responseGasolineResult = MyJSON.parseObject(mainData.getString("distance"), ResponseGasolineResult.class);
            if (responseGasolineResult == null) {
                ToastUtil.getInstance().toastInCenter(getActivity(), "该设施间的间距不存在");
            } else {
                Intent intent = new Intent(getActivity(), GasolineResultActivity.class);
                intent.putExtra("result", responseGasolineResult);
                if(isOne){
                    intent.putExtra("oneKey", mAList.getFirst().getParent().getName());
                    intent.putExtra("oneValue", mAList.getFirst().getChild().get(0).getName());
                    intent.putExtra("AFullName", mAList.getLast().getChild().get(0).getFullName());
                }else {
                    //去两个list最后一个数据的parent就可以了，fullneme是全部的请求参数传到下一个页面就可以
                    intent.putExtra("oneKey", mAList.getFirst().getParent().getName());
                    intent.putExtra("oneValue", mAList.getFirst().getChild().get(0).getName());
                    intent.putExtra("twoKey", mBList.getFirst().getParent().getName());
                    intent.putExtra("twoValue", mBList.getFirst().getChild().get(0).getName());
                    intent.putExtra("AFullName", mAList.getLast().getChild().get(0).getFullName());
                    intent.putExtra("BFullName", mBList.getLast().getChild().get(0).getFullName());
                }
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
                if(mAisLast){

                    return;
                }
                RequestCenter.getArchitecture("", mAList.getLast().getChild().get(0).getCode(), standard, this);
            }
            if (requestCode == 2) {
                ResponseGasolineItem item = (ResponseGasolineItem) data.getSerializableExtra("result");
                List<ResponseGasolineItem> childlist = mBList.getLast().getChild();
                childlist.clear();
                childlist.add(item);
                mBList.getLast().setChild(childlist);
                mAdapterB.notifyDataSetChanged();
                //如果level==6不再往下请求了
                if(mBisLast){

                    return;
                }
                RequestCenter.getArchitecture("", mBList.getLast().getChild().get(0).getCode(), standard, this);
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
                mAisLast=false;
                List subList = mAList.subList(i+1, mAList.size());
                subList.clear();
                if(mAList.get(i).getChild()==null||mAList.get(i).getChild().size()<=0){
                    //如果没有下一级了不再响应点击事件，即最后一行点击无效果
                    return;
                }
                Intent intent = new Intent(getActivity(), LocationPickerActivity.class);
                ResponseGasoline item = (ResponseGasoline) mAdapterA.getItem(i);
                intent.putExtra("parentCode", item.getParent().getCode());
                intent.putExtra("standard", standard);
                startActivityForResult(intent, 1);

                break;
            case R.id.lv_B:
                mCurrentClickItem = "stationB";
                mBisLast=false;
                //同上
                List subListb = mBList.subList(i+1, mBList.size());
                subListb.clear();
                if(mBisLast){
                    return;
                }
                Intent intent2 = new Intent(getActivity(), LocationPickerActivity.class);
                ResponseGasoline item2 = (ResponseGasoline) mAdapterB.getItem(i);
                intent2.putExtra("parentCode", item2.getParent().getCode());
                intent2.putExtra("standard", standard);
                startActivityForResult(intent2, 2);
                break;
        }
    }
}
