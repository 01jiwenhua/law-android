package com.shx.lawwh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shx.lawwh.R;
import com.shx.lawwh.base.OnRecyclerViewItemClickListener;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.entity.response.ResponseMessage;
import com.shx.lawwh.utils.DateUtil;

import java.util.List;

/**
 * Created by adm on 2018/2/6.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements View.OnClickListener {

    private List<ResponseMessage> mDatas;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener=null;

    public MessageAdapter(List<ResponseMessage> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        view.setOnClickListener(this);
        MessageViewHolder viewHolder=new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        ResponseMessage responseMessage = mDatas.get(position);
        holder.dateTV.setText(DateUtil.parseDate(responseMessage.getPublishTime()));
        holder.titleTV.setText(responseMessage.getTitle());
        holder.contentTv.setText(responseMessage.getContent());
        holder.itemView.setTag(responseMessage);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view, (ResponseMessage) view.getTag());
        }
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTV;
        private TextView titleTV;
        private TextView contentTv;


        public MessageViewHolder(View itemView) {
            super(itemView);
            dateTV= (TextView) itemView.findViewById(R.id.tv_date);
            titleTV= (TextView) itemView.findViewById(R.id.tv_title);
            contentTv= (TextView) itemView.findViewById(R.id.tv_content);
        }


        public TextView getDateTV() {
            return dateTV;
        }

        public void setDateTV(TextView dateTV) {
            this.dateTV = dateTV;
        }

        public TextView getTitleTV() {
            return titleTV;
        }

        public void setTitleTV(TextView titleTV) {
            this.titleTV = titleTV;
        }

        public TextView getContentTv() {
            return contentTv;
        }

        public void setContentTv(TextView contentTv) {
            this.contentTv = contentTv;
        }
    }


}
