package com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.N_CourierReport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.Interface.FilterChangedCourier;
import com.syber.ssspltd.Interface.OnCheckChange;
import com.syber.ssspltd.Interface.OnCheckChangesCourier;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.CourierNo;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.CourierReportActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;

import java.util.List;
import java.util.stream.Collectors;

public class FilterCourierNoAdap extends RecyclerView.Adapter<FilterCourierNoAdap.MyViewHolder>{

    private Context mContext;
    private List<CourierNo> filterListDetails;
    FilterChangedCourier filterCallback;


    public FilterCourierNoAdap(Context mContext, List<CourierNo> detailList, FilterChangedCourier filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
    }

    @Override
    public FilterCourierNoAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterCourierNoAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final CourierNo datum = filterListDetails.get(position);
        holder.filter.setText(datum.getCourierNumber().equals("")?"Without Courier No":datum.getCourierNumber());
//        holder.filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if(datum.isSelected()){
            holder.filter.setChecked(true);
        }else{
            holder.filter.setChecked(false);
        }
        holder.filter.setTag(position);


        holder.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos=(Integer) holder.filter.getTag();
                CourierNo productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<CourierNo> isSelected = filterListDetails.stream().filter(CourierNo::isSelected).collect(Collectors.toList());
                    try {
                        CourierReportActivity.count_CNo.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                   // onCheckChange.onCheckChangeReferesh(FilterTypeCourierReport.COURIER_NO);
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<CourierNo> isSelected = filterListDetails.stream().filter(CourierNo::isSelected).collect(Collectors.toList());
                    try {
                        CourierReportActivity.count_CNo.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                   // onCheckChange.onCheckChangeReferesh(FilterTypeCourierReport.COURIER_NO);

                }
                filterCallback.filterChangedCourier(FilterTypeCourierReport.COURIER_NO);
                notifyDataSetChanged();
            }

        });
    }


    @Override
    public int getItemCount() {
        return filterListDetails.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox filter;

        public MyViewHolder(View itemView) {
            super(itemView);

            filter = itemView.findViewById(R.id.filter);

        }
    }
}
