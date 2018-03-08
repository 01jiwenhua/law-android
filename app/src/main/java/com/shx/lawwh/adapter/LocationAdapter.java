package com.shx.lawwh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.ResponseGasoline;
import com.shx.lawwh.utils.StringUtil;

import java.util.LinkedList;

/**
 * Created by admin on 2018/2/22.
 */

public class LocationAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<ResponseGasoline> mList;
    private boolean isLast;

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public LocationAdapter(Context mContext, LinkedList<ResponseGasoline> mList, boolean isLast) {
        this.mContext = mContext;
        this.mList = mList;
        this.isLast = isLast;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_station,null);
            holder=new ViewHolder();
            holder.mKey= (TextView) view.findViewById(R.id.tv_key);
            holder.mValue= (TextView) view.findViewById(R.id.tv_value);
            holder.mIcon= (ImageView) view.findViewById(R.id.iv_icon);
            holder.mNext= (ImageView) view.findViewById(R.id.iv_next);
            holder.mLayout= (LinearLayout) view.findViewById(R.id.ll_one);
            view.setTag(holder);
        }
        holder= (ViewHolder) view.getTag();
        if(i==0){
            holder.mKey.setText(mList.get(i).getParent().getName());
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            holder.mIcon.setVisibility(View.GONE);
            holder.mKey.setText(StringUtil.toChinese(String.valueOf(i))+"级选项");
            holder.mLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if(mList.get(i).getChild()==null||mList.get(i).getChild().size()==0){
        }else {
            holder.mValue.setText(mList.get(i).getChild().get(0).getName());
        }
        //如果没有下一级
        if(isLast&&mList.size()==i+1){
//                holder.mValue.setText(mList.get(i).getChild().get(0).getName());
//                holder.mNext.setVisibility(View.VISIBLE);
            holder.mValue.setTextColor(Color.parseColor("#666666"));
        }else{
//            holder.mValue.setText("");
//            holder.mNext.setVisibility(View.GONE);
            holder.mValue.setTextColor(Color.parseColor("#3BA0F3"));
        }
        return view;
    }


    private class ViewHolder{
        private TextView mKey;
        private TextView mValue;
        private ImageView mIcon;
        private ImageView mNext;
        private LinearLayout mLayout;
    }
}
