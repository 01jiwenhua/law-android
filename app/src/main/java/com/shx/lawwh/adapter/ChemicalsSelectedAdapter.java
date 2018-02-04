package com.shx.lawwh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.UnknownParams;

import java.util.List;

/**
 * Created by adm on 2018/2/4.
 */

public class ChemicalsSelectedAdapter extends BaseAdapter {

    private List<UnknownParams> selectedDatas;
    private Context mContext;


    public ChemicalsSelectedAdapter(List<UnknownParams> selectedDatas, Context context) {
        this.selectedDatas = selectedDatas;
        this.mContext = context;
    }

    @Override

    public int getCount() {
        return selectedDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return selectedDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChemicalsSelectedAdapter.ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_chemicals_selected,null);
            viewHolder=new ChemicalsSelectedAdapter.ViewHolder();
            convertView.setTag(viewHolder);
        }
        viewHolder= (ChemicalsSelectedAdapter.ViewHolder) convertView.getTag();

        UnknownParams params = selectedDatas.get(position);
        viewHolder.keyTv= (TextView) convertView.findViewById(R.id.tv_key);
        viewHolder.valueTv= (TextView) convertView.findViewById(R.id.tv_value);
        viewHolder.keyTv.setText(params.getCategoryName());
        viewHolder.valueTv.setText(params.getName());
        return convertView;
    }

    class ViewHolder{
        TextView keyTv;
        TextView valueTv;
    }
}
