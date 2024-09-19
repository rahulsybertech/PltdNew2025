package com.syber.ssspltd.NewFilterAdapter.LedgerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.NewFilterResponse.AccountType;
import com.syber.ssspltd.NewFilterResponse.AdjustmentType;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.LedgerActivity;

import java.util.List;
import java.util.stream.Collectors;

public class AdjustmentTypeAdapter extends RecyclerView.Adapter<AdjustmentTypeAdapter.MyViewHolder> {
    private Context mContext;
    private List<AdjustmentType> filterListDetails;
    private FilterCallback filterCallback;

    public AdjustmentTypeAdapter(Context mContext, List<AdjustmentType> detailList, FilterCallback filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
    }

    @Override
    public AdjustmentTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new AdjustmentTypeAdapter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(AdjustmentTypeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final AdjustmentType datum = filterListDetails.get(position);
        holder.filter.setText(datum.getAdjustmentName().equals("") ? "Without Branch" : datum.getAdjustmentName());

        if (datum.isSelected()) {
            holder.filter.setChecked(true);
        } else {
            holder.filter.setChecked(false);
        }
        holder.filter.setTag(position);

        holder.filter.setOnClickListener(v -> {
            Integer pos = (Integer) holder.filter.getTag();
            AdjustmentType productDetails1 = filterListDetails.get(pos);
            if (productDetails1.isSelected()) {
                productDetails1.setSelected(false);
                holder.filter.setChecked(false);
                holder.filter.setTag(position);
                List<AdjustmentType> sis = filterListDetails.stream().filter(AdjustmentType :: isSelected).collect(Collectors.toList());
                LedgerActivity.count_aduj.setText(sis.size()+"");
            } else {
                productDetails1.setSelected(true);
                holder.filter.setChecked(true);
                holder.filter.setTag(position);
                List<AdjustmentType> sis = filterListDetails.stream().filter(AdjustmentType :: isSelected).collect(Collectors.toList());
                LedgerActivity.count_aduj.setText(sis.size()+"");
            }
            filterCallback.filterChanged(FilterType.ADJUSTMENT);
            //notifyDataSetChanged();
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
