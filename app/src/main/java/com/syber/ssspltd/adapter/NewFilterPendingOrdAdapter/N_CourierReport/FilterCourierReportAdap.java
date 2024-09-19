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
import com.syber.ssspltd.Interface.OnCheckChangesCourier;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.Courier;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.CourierReportActivity;
import com.syber.ssspltd.activitys.PendingOrderActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;

import java.util.List;
import java.util.stream.Collectors;

public class FilterCourierReportAdap extends RecyclerView.Adapter<FilterCourierReportAdap.MyViewHolder>{


    private Context mContext;
    private List<Courier> filterListDetails;
    FilterChangedCourier filterCallback;


    public FilterCourierReportAdap(Context mContext, List<Courier> detailList,FilterChangedCourier filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
    }

    @Override
    public FilterCourierReportAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterCourierReportAdap.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterCourierReportAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Courier datum = filterListDetails.get(position);
        holder.filter.setText(datum.getCourierName().equals("")?"Without Courier Name":datum.getCourierName());

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
                Courier productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<Courier> isSelected = filterListDetails.stream().filter(Courier::isSelected).collect(Collectors.toList());
                    try {
                        CourierReportActivity.count_Cname.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<Courier> isSelected = filterListDetails.stream().filter(Courier::isSelected).collect(Collectors.toList());
                    try {
                        CourierReportActivity.count_Cname.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }

                }
                filterCallback.filterChangedCourier(FilterTypeCourierReport.COURIER_NAME);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox filter;

        public MyViewHolder(View itemView) {
            super(itemView);

            filter = itemView.findViewById(R.id.filter);

        }
    }
}
