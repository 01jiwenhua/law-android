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

import java.util.LinkedList;

/**
 * Created by admin on 2018/2/22.
 */

public class LocationAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<ResponseGasoline> mList;
    private boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public LocationAdapter(Context mContext, LinkedList<ResponseGasoline> mList, boolean hasNext) {
        this.mContext = mContext;
        this.mList = mList;
        this.hasNext = hasNext;
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

        holder.mKey.setText(mList.get(i).getParent().getName());
        if(i==0){
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            holder.mIcon.setVisibility(View.GONE);
            holder.mLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if(mList.get(i).getChild()!=null&&mList.get(i).getChild().size()>0){

                holder.mValue.setText(mList.get(i).getChild().get(0).getName());
                holder.mNext.setVisibility(View.VISIBLE);
                holder.mValue.setTextColor(Color.parseColor("#3BA0F3"));
        }else{
            holder.mValue.setText("");
            holder.mNext.setVisibility(View.GONE);
            holder.mValue.setTextColor(Color.parseColor("#555555"));
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
