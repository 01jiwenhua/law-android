package com.shx.lawwh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.UnknownParams;
import com.shx.lawwh.fragment.UnknowFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuan on 2017/12/25.
 */

public class UnknowParamsAdapter extends BaseAdapter {
    private List<UnknownParams> mList;
    private Context mContext;

    public UnknowParamsAdapter(List mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_unknown_params, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        UnknownParams params = mList.get(position);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.select = (TextView) convertView.findViewById(R.id.tv_select);
        viewHolder.name.setText(params.getCategoryName());
        viewHolder.name.setTextColor(mContext.getResources().getColor(R.color.colorTextGray));
        if (UnknowFragment.checkMap.containsKey(params.getCategoryCode())) {
            viewHolder.select.setText(UnknowFragment.checkMap.get(params.getCategoryCode()).getName());
            viewHolder.select.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        } else if (UnknowFragment.checkMap1.containsKey(params.getCategoryCode())) {
            viewHolder.select.setText(UnknowFragment.checkMap1.get(params.getCategoryCode()).getName());
            viewHolder.select.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        } else {
            viewHolder.select.setText("");
        }

        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView select;
    }
}
