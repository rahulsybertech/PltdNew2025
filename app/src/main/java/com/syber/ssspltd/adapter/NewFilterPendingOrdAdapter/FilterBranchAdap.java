package com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.Interface.FilterChangedPending;
import com.syber.ssspltd.NewFilter.PendingOrder.Branch;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.PendingOrderActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.BranchAdapter;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListResult;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBranchAdap extends RecyclerView.Adapter<FilterBranchAdap.MyViewHolder>{


    private Context mContext;
    private List<Branch> filterListDetails;
    private FilterChangedPending filterCallback;

    public FilterBranchAdap(Context mContext, List<Branch> detailList ,FilterChangedPending filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
    }

    @Override
    public FilterBranchAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterBranchAdap.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterBranchAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
                    List<Branch> isSelected = filterListDetails.stream().filter(Branch::isSelected).collect(Collectors.toList());
                    try {
                        PendingOrderActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }

                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<Branch> isSelected = filterListDetails.stream().filter(Branch::isSelected).collect(Collectors.toList());
                    try {
                        PendingOrderActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                    filterCallback.filterChangedPending(FilterTypePendingOrder.BRANCH);

                }
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
