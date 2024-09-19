package com.syber.ssspltd.adapter.FilterAdapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.SaleReportActivity;
import com.syber.ssspltd.response.BranchListResponse.FilterListResult;

import java.util.List;
import java.util.stream.Collectors;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.MyViewHolder> {

    private Context mContext;
    private List<FilterListResult> filterListDetails;
    Boolean selectFalse = false;
    String falseSelect;

    public BranchListAdapter(Context mContext, List<FilterListResult> detailList) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
    }

    @Override
    public BranchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new BranchListAdapter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(BranchListAdapter.MyViewHolder holder, final int position) {

        final FilterListResult datum = filterListDetails.get(position);
        holder.filter.setText(datum.getFilterName().equals("")?"Without Branch":datum.getFilterName());
//        holder.filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if(datum.isSelected()){
            holder.filter.setChecked(true);
            //  holder.size.setCheckMarkDrawable(R.drawable.ic_check_in);
        }else{
            holder.filter.setChecked(false);

            //   holder.size.setCheckMarkDrawable(R.drawable.ic_baseline_radio_button_unchecked_24);
        }
        holder.filter.setTag(position);

        List<FilterListResult> isSelected = filterListDetails.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
        try {
            SaleReportActivity.countbranch.setText(isSelected.size()+"");
        }catch (Exception e){

        }

        holder.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos=(Integer) holder.filter.getTag();
                FilterListResult productDetails1 = filterListDetails.get(pos);

                if(productDetails1.isSelected()){
                    productDetails1.setSelected(false);
                    holder.filter.setChecked(false);
                    List<FilterListResult> isSelected = filterListDetails.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }
                }else{
                    productDetails1.setSelected(true);
                    holder.filter.setChecked(true);
                    List<FilterListResult> isSelected = filterListDetails.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                    try {
                        SaleReportActivity.countbranch.setText(isSelected.size()+"");
                    }catch (Exception e){

                    }

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
