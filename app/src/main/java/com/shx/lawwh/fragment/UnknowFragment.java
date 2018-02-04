package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.ChemicalsSeaarchResultActivity;
import com.shx.lawwh.activity.UnknownDetailsActivity;
import com.shx.lawwh.adapter.UnknowParamsAdapter;
import com.shx.lawwh.adapter.UnknowParamsDetailsAdapter;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.entity.response.ResponseJobList;
import com.shx.lawwh.entity.response.UnknownParams;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.view.NoScrollGridView;
import com.shx.lawwh.view.NoScrollListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.SinglePicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xuan on 2017/12/24.
 */
@SuppressWarnings("ResourceType")
public class UnknowFragment extends Fragment implements HttpCallBack, AdapterView.OnItemClickListener, View.OnClickListener {

    private NoScrollListView mLhRLv;
    private NoScrollListView mJkwhLv;
    private UnknowParamsAdapter mAdapter;
    private UnknowParamsAdapter mAdapter2;
    private List<UnknownParams> LhList = new ArrayList<>();
    private List<UnknownParams> jkwhList = new ArrayList<>();
    private Button mBtnQuery;
    public static Map<String, UnknownParams> checkMap;
    public static Map<String, UnknownParams> checkMap1;

    private static int currentListId;
    private ChemicalsRequest mRequest = new ChemicalsRequest();

    private List<UnknownParams> mList;

    public UnknowParamsAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(UnknowParamsAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public UnknowParamsAdapter getmAdapter2() {
        return mAdapter2;
    }

    public void setmAdapter2(UnknowParamsAdapter mAdapter2) {
        this.mAdapter2 = mAdapter2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unknown, null);
        checkMap = new HashMap<>();
        checkMap1 = new HashMap<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogManager.getInstance().showProgressDialog(getContext());
        RequestCenter.getUnknowparams(this);
        mLhRLv = (NoScrollListView) view.findViewById(R.id.lhtx);
//        mLhRLv.setId(0);
        mJkwhLv = (NoScrollListView) view.findViewById(R.id.jkwh);
//        mJkwhLv.setId(1);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mLhRLv.setOnItemClickListener(this);
        mJkwhLv.setOnItemClickListener(this);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_UNKNOWPARAMS)) {
            if (object.size() > 0) {
                LhList = MyJSON.parseArray(object.getString("lhList"), UnknownParams.class);
                mAdapter = new UnknowParamsAdapter(LhList, getContext());
                mLhRLv.setAdapter(mAdapter);
                jkwhList = MyJSON.parseArray(object.getString("jkwhList"), UnknownParams.class);
                mAdapter2 = new UnknowParamsAdapter(jkwhList, getContext());
                mJkwhLv.setAdapter(mAdapter2);
            }
        } else if (requestUrl.equals(RequestCenter.GET_UNKNOWPARAMS_DETAILS)) {
            if (object.size() > 0) {
                mList = MyJSON.parseArray(object.getString("list"), UnknownParams.class);
                SinglePicker<UnknownParams> picker = new SinglePicker<>(getActivity(), mList);
                picker.setCanceledOnTouchOutside(false);
                picker.setSelectedIndex(1);
                picker.setCycleDisable(true);
                picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<UnknownParams>() {
                    @Override
                    public void onItemPicked(int index, UnknownParams item) {
                        if (currentListId == 0) {
                            checkMap.put(item.getCategoryCode(), item);
                        } else {
                            checkMap1.put(item.getCategoryCode(), item);
                        }

                        mAdapter.notifyDataSetChanged();
                        mAdapter2.notifyDataSetChanged();
                    }
                });
                picker.show();


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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UnknownParams unknownParams = (UnknownParams) parent.getAdapter().getItem(position);
//        Intent intent = new Intent(getContext(), UnknownDetailsActivity.class);
//        intent.putExtra("code", unknownParams.getCategoryCode());
//        startActivityForResult(intent, 100);
        currentListId = parent.getId();
        RequestCenter.getUnknowparamsDetails(unknownParams.getCategoryCode(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                UnknownParams unknownParams = (UnknownParams) data.getSerializableExtra("details");
                checkMap.put(unknownParams.getCategoryCode(), unknownParams);

                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();


            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                if (checkMap.containsKey("B_STATUS")) {
                    mRequest.setStatus(checkMap.get("B_STATUS").getName());
                }
                if (checkMap.containsKey("B_COLOR")) {
                    mRequest.setColor(checkMap.get("B_COLOR").getName());
                }
                if (checkMap.containsKey("B_SMELL")) {
                    mRequest.setSmell(checkMap.get("B_SMELL").getName());
                }
                if (checkMap.containsKey("B_TASTE")) {
                    mRequest.setTaste(checkMap.get("B_TASTE").getName());
                }
                if (checkMap.containsKey("B_SPECIFIC_AIR")) {
                    mRequest.setSpecific_air(checkMap.get("B_SPECIFIC_AIR").getName());
                }
                if (checkMap.containsKey("B_SPECIFIC_WATER")) {
                    mRequest.setSpecific_water(checkMap.get("B_SPECIFIC_WATER").getName());
                }
                if (checkMap.containsKey("B_PH")) {
                    mRequest.setPh(checkMap.get("B_PH").getName());
                }
                if (checkMap.containsKey("B_TRANSPARENCY")) {
                    mRequest.setTransparency(checkMap.get("B_TRANSPARENCY").getName());
                }
                if (checkMap1.containsKey("B_NERVOUS")) {
                    mRequest.setNervous(checkMap1.get("B_NERVOUS").getName());
                }
                if (checkMap1.containsKey("B_EYE")) {
                    mRequest.setEye(checkMap1.get("B_EYE").getName());
                }
                if (checkMap1.containsKey("B_EAR")) {
                    mRequest.setEar(checkMap1.get("B_EAR").getName());
                }
                if (checkMap1.containsKey("B_MOUTH_THROAT")) {
                    mRequest.setMouth_throat(checkMap1.get("B_MOUTH_THROAT").getName());
                }
                if (checkMap1.containsKey("B_CARDIOVASCULAR")) {
                    mRequest.setCardiovascular(checkMap1.get("B_CARDIOVASCULAR").getName());
                }
                if (checkMap1.containsKey("B_RESPIRATORY")) {
                    mRequest.setRespiratory(checkMap1.get("B_RESPIRATORY").getName());
                }
                if (checkMap1.containsKey("B_GASTRO_URINARY")) {
                    mRequest.setGastro_urinary(checkMap1.get("B_GASTRO_URINARY").getName());
                }
                if (checkMap1.containsKey("B_SKIN")) {
                    mRequest.setSkin(checkMap1.get("B_SKIN").getName());
                }
                Intent intent = new Intent(getContext(), ChemicalsSeaarchResultActivity.class);
                intent.putExtra("request", mRequest);
                intent.putExtra("character", (Serializable) checkMap);
                intent.putExtra("endanger", (Serializable) checkMap1);
                startActivity(intent);
                break;
        }
    }
}
