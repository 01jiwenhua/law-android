package com.shx.lawwh.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shx.lawwh.R;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.utils.DateUtil;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/18.
 */

public class LawBaseAdapter extends BaseQuickAdapter<LawResponse, LawBaseAdapter.ViewHolder> {
    private LawRequest mLawRequest;
    private boolean isLight=false;//是否需要高亮
    public LawBaseAdapter( @Nullable List<LawResponse> data) {
        super(R.layout.item_law,data);
    }


    @Override
    protected void convert(ViewHolder helper, LawResponse item) {

       // 是否需要部分文字高亮
        if(isLight){
            if(mLawRequest!=null){
                String lawName=item.getLawName();
                String issueNo=item.getIssueNo();
                String description=item.getDescription();
//                if(!TextUtils.isEmpty(mLawRequest.getIssue_no())){
//
//                    issueNo=issueNo.replace(mLawRequest.getIssue_no(),"<font color='#FF0000'>"+mLawRequest.getIssue_no()+"</font>");
//
//
//                }
                if(!TextUtils.isEmpty(mLawRequest.getName())){
                    //标题高亮
                    lawName=lawName.replace(mLawRequest.getName(),"<font color='#FF0000'>"+mLawRequest.getName()+"</font>");
                }
                if(!TextUtils.isEmpty(mLawRequest.getDescription())){
                    //内容高亮
                    description=description.replace(mLawRequest.getDescription(),"<font color='#FF0000'>"+mLawRequest.getDescription()+"</font>");
                }
               helper.name.setText(Html.fromHtml(lawName));
               helper.des.setText(Html.fromHtml(TextUtils.isEmpty(description)?"暂无摘要": description));
                helper.order.setText(issueNo);
                helper.date.setText(DateUtil.parseDate(item.getPublishTime()));
            }
        }else{
            helper.name.setText(item.getLawName());
            helper.des.setText(TextUtils.isEmpty(item.getDescription())?"暂无摘要":item.getDescription());
            helper.order.setText(item.getIssueNo());
            helper.date.setText(DateUtil.parseDate(item.getPublishTime()));
        }

    }
    /**
     * 设置文字高亮
     * @param lawRequest
     */
    public void setLight(boolean isLight,LawRequest lawRequest){
        mLawRequest=lawRequest;
        this.isLight=isLight;
    }
    class ViewHolder extends BaseViewHolder{
        private TextView name;
        private TextView des;
        private TextView order;
        private TextView date;

        public ViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.tv_name);
            des= (TextView) view.findViewById(R.id.tv_des);
            order= (TextView) view.findViewById(R.id.tv_order);
            date= (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
