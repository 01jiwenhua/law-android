package com.shx.lawwh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shx.lawwh.R;
import com.shx.lawwh.activity.ArchitecturalDesignActivity;
import com.shx.lawwh.activity.GasolineActivity;
import com.shx.lawwh.activity.OilActivity;

/**
 * Created by zhou on 2018/2/5.
 * 防火间距-标准查询
 */

public class StandardSearchFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
      view.findViewById(R.id.ll_first).setOnClickListener(this);
      view.findViewById(R.id.ll_second).setOnClickListener(this);
      view.findViewById(R.id.ll_third).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_first:
                startActivity(new Intent(getActivity(), ArchitecturalDesignActivity.class));
                break;
            case R.id.ll_second:
                startActivity(new Intent(getActivity(), OilActivity.class));
                break;
            case R.id.ll_third:
                startActivity(new Intent(getActivity(), GasolineActivity.class));
                break;
        }
    }
}
