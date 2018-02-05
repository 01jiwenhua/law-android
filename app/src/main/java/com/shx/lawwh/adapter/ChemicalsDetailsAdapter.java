package com.shx.lawwh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.ChemicalsDetailsResponse;
import com.shx.lawwh.entity.response.DetailsParams;

import java.util.List;

/**
 * Created by xuan on 2017/12/25.
 */

public class ChemicalsDetailsAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ChemicalsDetailsResponse> mList;

    public ChemicalsDetailsAdapter(Context mContext, List<ChemicalsDetailsResponse> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_group,null);
        TextView textView= (TextView) view.findViewById(R.id.tv_name);
        ChemicalsDetailsResponse chemicalsDetailsResponse=mList.get(groupPosition);
        textView.setText(chemicalsDetailsResponse.getTitle());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_child,null);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView value= (TextView) view.findViewById(R.id.value);
        ChemicalsDetailsResponse chemicalsDetailsResponse=mList.get(groupPosition);
        DetailsParams params=chemicalsDetailsResponse.getList().get(childPosition);
        name.setText(params.getKey().substring(2));
        value.setText(TextUtils.isEmpty(params.getValue())?"暂无资料":params.getValue());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
