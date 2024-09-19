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
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Brand;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.SaleReportActivity;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBranch extends RecyclerView.Adapter<FilterBranch.MyViewHolder>{

    private Context mContext;
    private List<Branch> filterListDetails;
    private FilterChangeSaleReport filterCallback;

    public FilterBranch(Context mContext, List<Branch> detailList ,FilterChangeSaleReport filterCallback ) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback=filterCallback;
    }

    @Override
    public FilterBranch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterBranch.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterBranch.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Branch datum = filterListDetails.get(position);
        holder.filter.setText(datum.getBranchName().equals("")?"Without Branch":datum.getBranchName());

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
                Branch productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<Branch> isSelected = filterListDetails.stream().filter(Branch :: isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<Branch> isSelected = filterListDetails.stream().filter(Branch :: isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }
                filterCallback.filterChangedSaleReport(FilterTypeSaleReport.BRANCH);

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
