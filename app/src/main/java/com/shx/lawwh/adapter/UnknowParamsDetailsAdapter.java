package com.shx.lawwh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.UnknownParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuan on 2017/12/25.
 */

public class UnknowParamsDetailsAdapter extends BaseAdapter{
    private List<UnknownParams> mList;
    private Context mContext;
    public Map<String,UnknownParams> paramsMap=new HashMap<>();
    public UnknowParamsDetailsAdapter(List mList, Context mContext) {
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
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_params_details,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();

        UnknownParams params=mList.get(position);
        viewHolder.name= (TextView) convertView.findViewById(R.id.name);
        if(paramsMap.containsKey(params.getCategoryCode())){

        }else{
            viewHolder.name.setText(params.getName());
        }


        return convertView;
    }
    class ViewHolder{
        private TextView name;
    }
}
