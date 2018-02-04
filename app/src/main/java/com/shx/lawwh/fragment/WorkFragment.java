package com.shx.lawwh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shx.lawwh.R;

/**
 * Created by adm on 2018/2/4.
 */

public class WorkFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_work,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_list);
        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        return view;
    }
}
