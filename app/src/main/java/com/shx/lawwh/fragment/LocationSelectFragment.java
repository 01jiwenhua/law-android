package com.shx.lawwh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shx.lawwh.R;

/**
 * Created by zhou on 2018/2/6.
 * 站址选择
 */

public class LocationSelectFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_location_select,container,false);
        return view;
    }
}
