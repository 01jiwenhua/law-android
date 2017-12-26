package com.shx.lawwh.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shx.lawwh.R;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.ChemicalsResponse;
import com.shx.lawwh.entity.response.LawResponse;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/18.
 */

public class KnownAdapter extends BaseQuickAdapter<ChemicalsResponse, KnownAdapter.ViewHolder> {
    private ChemicalsRequest mChemicalsRequest;
    private boolean isLight=false;//是否需要高亮
    public KnownAdapter(@Nullable List<ChemicalsResponse> data) {
        super(R.layout.item_chemicals,data);
    }

    @Override
    protected void convert(ViewHolder helper, ChemicalsResponse item) {

       // 是否需要部分文字高亮
//        if(isLight){
//            if(mLawRequest!=null){
//                if(TextUtils.isEmpty(mLawRequest.getKeyword())){
//                    return;
//                }
//                if(mLawRequest.getKeywordType().equals("标题")){
//                    //标题高亮
//                    item.setLaw_name(item.getLaw_name().replace(mLawRequest.getKeyword(),"<font color='#FF0000'>"+mLawRequest.getKeyword()+"</font>"));
//
//                }else{
//                    //内容高亮
//                    item.setDescription(item.getDescription().replace(mLawRequest.getKeyword(),"<font color='#FF0000'>"+mLawRequest.getKeyword()+"</font>"));
//                }
//               helper.name.setText(Html.fromHtml(item.getLaw_name()));
//               helper.des.setText(Html.fromHtml(TextUtils.isEmpty(item.getDescription())?"暂无摘要":item.getDescription()));
//            }
//        }else{
            helper.name.setText(item.getNameCn());
//        }

    }

//    public void setLight(boolean isLight,LawRequest lawRequest){
//        m=lawRequest;
//        this.isLight=isLight;
//    }
    class ViewHolder extends BaseViewHolder{
        private TextView name;
        public ViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
