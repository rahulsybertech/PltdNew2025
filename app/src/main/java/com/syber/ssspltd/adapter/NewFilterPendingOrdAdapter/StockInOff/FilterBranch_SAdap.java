package com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.StockInOff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.Interface.FilterChangedStockInOffice;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.Branch;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.PendingOrderActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.BranchAdapter;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListResult;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBranch_SAdap extends RecyclerView.Adapter<FilterBranch_SAdap.MyViewHolder>{

    private Context mContext;
    private List<Branch> filterListDetails;
    private FilterChangedStockInOffice filterCallback;

    public FilterBranch_SAdap(Context mContext, List<Branch> detailList,FilterChangedStockInOffice filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback= filterCallback;
    }

    @Override
    public FilterBranch_SAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterBranch_SAdap.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterBranch_SAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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

                    List<Branch> isSelected2 = filterListDetails.stream().filter(Branch::isSelected).collect(Collectors.toList());
                    try {
                        StockInOfficeActivity.countbranch.setText(isSelected2.size()+"");
                    }catch (Exception e){

                    }
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);

                    List<Branch> isSelected2 = filterListDetails.stream().filter(Branch::isSelected).collect(Collectors.toList());
                    try {
                        StockInOfficeActivity.countbranch.setText(isSelected2.size()+"");
                    }catch (Exception e){

                    }

                }
                filterCallback.filterChangedStockInOffice(FilterTypeStockInOffice.BRANCH);
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
