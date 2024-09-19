package com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.Interface.FilterChangeSaleReport;
import com.syber.ssspltd.NewFilter.PendingOrder.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Transporter;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.PendingOrderActivity;
import com.syber.ssspltd.activitys.SaleReportActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterBranchAdap;

import java.util.List;
import java.util.stream.Collectors;

public class FilterTesnportAdpter extends RecyclerView.Adapter<FilterTesnportAdpter.MyViewHolder>{


    private Context mContext;
    private List<Transporter> filterListDetails;
    private FilterChangeSaleReport filterCallback;

    public FilterTesnportAdpter(Context mContext, List<Transporter> detailList,FilterChangeSaleReport filterCallback ) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback=filterCallback;
    }

    @Override
    public FilterTesnportAdpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterTesnportAdpter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterTesnportAdpter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Transporter datum = filterListDetails.get(position);
        holder.filter.setText(datum.getTransporterName().equals("")?"Without Transport":datum.getTransporterName());

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
                Transporter productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<Transporter> isSelected = filterListDetails.stream().filter(Transporter::isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.counttransport.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }

                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<Transporter> isSelected = filterListDetails.stream().filter(Transporter::isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.counttransport.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }
                filterCallback.filterChangedSaleReport(FilterTypeSaleReport.TRANSPORT);

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
