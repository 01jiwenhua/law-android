package com.shx.lawwh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.OnRecyclerViewItemClickListener;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.holder.LawListHolder;

import java.util.List;

/**
 * Created by adm on 2018/2/1.
 *
 * 法律法规界面列表adapter
 */

public class NewLawAdapter extends BaseAdapter{
    private List<LawResponse> lawDatas;
    private Context mContext;
    public NewLawAdapter(List<LawResponse> lawDatas,Context context) {
        this.lawDatas = lawDatas;
        this.mContext=context;
    }





    @Override
    public int getCount() {
        return lawDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return lawDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        LawResponse lawResponse=lawDatas.get(position);
        if(convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_newlaw,null);
            viewholder=new Viewholder();
            viewholder.content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewholder);


        }
        viewholder= (Viewholder) convertView.getTag();
        viewholder.content.setText(lawResponse.getLawName()+"    "+lawResponse.getIssueNo());
        return convertView;
    }
    private class Viewholder{
        TextView content;
    }
}
