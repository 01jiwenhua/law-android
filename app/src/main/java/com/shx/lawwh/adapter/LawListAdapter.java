package com.shx.lawwh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shx.lawwh.R;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.holder.LawListHolder;

import java.util.List;

/**
 * Created by adm on 2018/2/1.
 *
 * 法律法规界面列表adapter
 */

public class LawListAdapter  extends RecyclerView.Adapter<LawListHolder>{

    private List<LawResponse> lawDatas;

    public LawListAdapter(List<LawResponse> lawDatas) {
        this.lawDatas = lawDatas;
    }

    @Override
    public LawListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lawlist,parent,false);
        LawListHolder holder=new LawListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LawListHolder holder, int position) {
        LawResponse lawResponse = lawDatas.get(position);
        holder.getTitleTv().setText(lawResponse.getLawName());
        holder.getOrderTv().setText(lawResponse.getIssueNo());
        holder.getOverviewTv().setText(lawResponse.getDescription());
    }

    @Override
    public int getItemCount() {
        return lawDatas==null ? 0 : lawDatas.size();
    }
}
