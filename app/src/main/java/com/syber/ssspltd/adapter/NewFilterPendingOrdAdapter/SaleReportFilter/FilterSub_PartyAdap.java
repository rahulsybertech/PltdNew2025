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
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.SubParty;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Transporter;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.SaleReportActivity;

import java.util.List;
import java.util.stream.Collectors;

public class FilterSub_PartyAdap  extends RecyclerView.Adapter<FilterSub_PartyAdap.MyViewHolder>{
    private Context mContext;
    private List<SubParty> filterListDetails;
    private FilterChangeSaleReport filterCallback;

    public FilterSub_PartyAdap(Context mContext, List<SubParty> detailList,FilterChangeSaleReport filterCallback ) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback=filterCallback;
    }

    @Override
    public FilterSub_PartyAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new FilterSub_PartyAdap.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(FilterSub_PartyAdap.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final SubParty datum = filterListDetails.get(position);
        holder.filter.setText(datum.getSubPartyName().equals("")?"Without SubParty":datum.getSubPartyName());

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
                SubParty productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    holder.filter.setTag(position);
                    List<SubParty> isSelected = filterListDetails.stream().filter(SubParty ::isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countsub_party.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }


                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    holder.filter.setTag(position);
                    List<SubParty> isSelected = filterListDetails.stream().filter(SubParty ::isSelected).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countsub_party.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }

                }
                filterCallback.filterChangedSaleReport(FilterTypeSaleReport.SUB_PARTY);
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
