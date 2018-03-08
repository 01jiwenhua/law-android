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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.GasolineResultActivity;
import com.shx.lawwh.activity.LocationPickerActivity;
import com.shx.lawwh.adapter.LocationAdapter;
import com.shx.lawwh.databinding.FragmentLayoutBinding;
import com.shx.lawwh.databinding.FragmentLocationSelectBinding;
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
import com.shx.lawwh.utils.LogGloble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.SinglePicker;

import static android.app.Activity.RESULT_OK;
import static com.shx.lawwh.R.id.btn_search;

/**
 * Created by zhou on 2018/2/6.
 * 站内平面布局
 */

@SuppressLint("ValidFragment")
public class LayoutFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, HttpCallBack {

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


    @SuppressLint("ValidFragment")
    public LayoutFragment(String name, String standard) {
        super();
        this.name = name;
        this.standard = standard;
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
        mAList = new LinkedList<ResponseGasoline>();
        mBList = new LinkedList<ResponseGasoline>();
        LogGloble.d("listinit", mAList + "");
        LogGloble.d("listinit", mBList + "");
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
                RequestCenter.getDistance(mAList.getLast().getParent().getId(), mAList.getLast().getParent().getId(), this);
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
                ResponseGasoline responseGasolineA = new ResponseGasoline();
                responseGasolineA.setParent(responseGasolineList.get(0).getParent());
                responseGasolineA.setChild(responseGasolineList.get(0).getChild());
                responseGasolineA.getParent().setName("站内设备A");
                mAList.addFirst(responseGasolineA);
                RequestCenter.getArchitectureV2(name, "", standard, new HttpCallBack() {
                    @Override
                    public boolean doSuccess(ZCResponse respose, String requestUrl) {
                        JSONObject mainData = respose.getMainData();
                        List<ResponseGasoline> responseGasolineListB = MyJSON.parseArray(mainData.getString("architecture"), ResponseGasoline.class);
                        //当前是顶级请求返回结果
                        ResponseGasoline responseGasolineB = new ResponseGasoline();
                        responseGasolineB.setParent(responseGasolineListB.get(0).getParent());
                        responseGasolineB.setChild(responseGasolineListB.get(0).getChild());
                        responseGasolineB.getParent().setName("站内设备B");
                        mBList.addFirst(responseGasolineB);
                        mAdapterB.notifyDataSetChanged();
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
                });
//
//
            }
            mAdapterA.notifyDataSetChanged();

        } else if (requestUrl.equals(RequestCenter.GET_ARCHITECTURE)) {
            JSONObject data = respose.getMainData();
            List<ResponseGasolineItem> items = MyJSON.parseArray(data.getString("architecture"), ResponseGasolineItem.class);
            if (mCurrentClickItem.equals("stationA")) {
                //构造新的一行数据
//                ResponseGasoline responseGasoline = new ResponseGasoline();
                //key是上一级的value
//                responseGasoline.setParent(mAList.getLast().getChild().get(0));
                if (items == null || items.size() <= 0) {
                    mAisLast = true;
                    mAdapterA.setLast(mAisLast);
                    mAdapterA.notifyDataSetChanged();
                    return false;
                }
                ResponseGasoline responseGasoline = new ResponseGasoline();
                responseGasoline.setParent(mAList.getLast().getChild().get(0));
                responseGasoline.setChild(items);
                mAList.add(responseGasoline);
                mAdapterA.notifyDataSetChanged();
            } else if (mCurrentClickItem.equals("stationB")) {
//                ResponseGasoline responseGasoline = new ResponseGasoline();
//                responseGasoline.setParent(mBList.getLast().getChild().get(0));
                if (items == null || items.size() <= 0) {
                    mBisLast = true;
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
            }
            LogGloble.d("list", mAList.hashCode() + "");
            LogGloble.d("list", mBList.hashCode() + "");
        } else if (requestUrl.equals(RequestCenter.GET_DISTANCE)) {
            JSONObject mainData = respose.getMainData();
            ResponseGasolineResult responseGasolineResult = MyJSON.parseObject(mainData.getString("distance"), ResponseGasolineResult.class);
            if (responseGasolineResult == null) {
                ToastUtil.getInstance().toastInCenter(getActivity(), "该设施间的间距不存在");
            } else {
                Intent intent = new Intent(getActivity(), GasolineResultActivity.class);
                intent.putExtra("result", responseGasolineResult);
                //去两个list最后一个数据的parent就可以了，fullneme是全部的请求参数传到下一个页面就可以
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
                if (mAisLast) {

                    return;
                }
                RequestCenter.getArchitecture("", mAList.getLast().getChild().get(0).getCode(), standard, this);
            } else if (requestCode == 2) {
                ResponseGasolineItem item = (ResponseGasolineItem) data.getSerializableExtra("result");
                List<ResponseGasolineItem> childlistb = mBList.getLast().getChild();
                childlistb.clear();
                childlistb.add(item);
                mBList.getLast().setChild(childlistb);
                mAdapterB.notifyDataSetChanged();
                //如果level==6不再往下请求了
                if (mBisLast) {

                    return;
                }
                RequestCenter.getArchitecture("", mBList.getLast().getChild().get(0).getCode(), standard, this);
            }
        }
        mAdapterA.notifyDataSetChanged();
        mAdapterB.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_A);
        LogGloble.d("onItemClick", adapterView.getId() + ";" + R.id.lv_B);
        switch (adapterView.getId()) {
            case R.id.lv_A:
                mCurrentClickItem = "stationA";
                mAisLast = false;
                //当前点击的是哪一行则把他后面的所有数据清空
                List subList = mAList.subList(i + 1, mAList.size());
                subList.clear();
                if (mAList.get(i).getChild() == null || mAList.get(i).getChild().size() <= 0) {
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
                mBisLast = false;
                //同上
                List subListb = mBList.subList(i + 1, mBList.size());
                subListb.clear();
                if (mBList.get(i).getChild() == null || mBList.get(i).getChild().size() <= 0) {
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
