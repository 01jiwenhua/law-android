package com.shx.lawwh.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shx.lawwh.R;

/**
 * Created by adm on 2018/2/1.
 */

public class LawListHolder extends RecyclerView.ViewHolder {
    private TextView titleTv,orderTv,dateTv,overviewTv;

    public LawListHolder(View itemView) {
        super(itemView);
        titleTv= (TextView) itemView.findViewById(R.id.tv_title);
        orderTv= (TextView) itemView.findViewById(R.id.tv_order);
        dateTv= (TextView) itemView.findViewById(R.id.tv_date);
        overviewTv = (TextView) itemView.findViewById(R.id.tv_overview);
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public void setTitleTv(TextView titleTv) {
        this.titleTv = titleTv;
    }

    public TextView getOrderTv() {
        return orderTv;
    }

    public void setOrderTv(TextView orderTv) {
        this.orderTv = orderTv;
    }

    public TextView getDateTv() {
        return dateTv;
    }

    public void setDateTv(TextView dateTv) {
        this.dateTv = dateTv;
    }

    public TextView getOverviewTv() {
        return overviewTv;
    }

    public void setOverviewTv(TextView overviewTv) {
        this.overviewTv = overviewTv;
    }
}
