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
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.Brand;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.PendingOrderActivity;
import com.syber.ssspltd.activitys.StockInOfficeActivity;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.SupplierAdapter;
import com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListResult;

import java.util.List;
import java.util.stream.Collectors;

public class FilterBrand_SAdap extends RecyclerView.Adapter<FilterBrand_SAdap.MyViewHolder>{
    private Context mContext;
    private List<Brand> filterListDetails;
    private FilterChangedStockInOffice filterCallback;

    public FilterBrand_SAdap(Context mContext, List<Brand> detailList,FilterChangedStockInOffice filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback=filterCallback;
    }

    @Override
    public FilterBrand_SAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterBrand_SAdap.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterBrand_SAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Brand datum = filterListDetails.get(position);
        holder.filter.setText(datum.getBrandName().equals("")?"Without Supplier":datum.getBrandName());

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
                Brand productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<Brand> isSelected = filterListDetails.stream().filter(Brand::isSelected).collect(Collectors.toList());
                    try {
                        StockInOfficeActivity.countbrand.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<Brand> isSelected = filterListDetails.stream().filter(Brand::isSelected).collect(Collectors.toList());
                    try {
                        StockInOfficeActivity.countbrand.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                    filterCallback.filterChangedStockInOffice(FilterTypeStockInOffice.BRAND_NAME);


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
