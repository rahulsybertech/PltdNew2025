package com.syber.ssspltd.adapter.DaskboadAdapter.StockInOfficeBWAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.DashboardAllData.StockInOfficeBW.StockInOfficeDetail;

import java.util.List;

public class StockInOfficeBWAdap extends RecyclerView.Adapter<StockInOfficeBWAdap.MyViewHolder>  {
    private Context mContext;
    private List<StockInOfficeDetail> stockInOfficeDetails;

    public StockInOfficeBWAdap(Context mContext, List<StockInOfficeDetail> detailList) {
        this.mContext = mContext;
        this.stockInOfficeDetails = detailList;
    }

    @Override
    public StockInOfficeBWAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tol_pending_recyclerview, parent, false);
        return new StockInOfficeBWAdap.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockInOfficeBWAdap.MyViewHolder holder, final int position) {

        final StockInOfficeDetail datum = stockInOfficeDetails.get(position);
        holder.BranchName_pending.setText(datum.getBranchName());
        holder.PendingAmt.setText(datum.getOfficeStockAmt());

//        if (datum.getDebitAmt().equals("")){
//            holder.dr_amt.setVisibility(View.GONE);
//            holder.dr_txt.setVisibility(View.GONE);
//        }else {
//            holder.dr_amt.setVisibility(View.VISIBLE);
//            holder.dr_txt.setVisibility(View.VISIBLE);
//            holder.dr_amt.setText(datum.getDebitAmt());
//        }
//
//        holder.bal_name.setText(tt);

    }


    @Override
    public int getItemCount() {
        return stockInOfficeDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView BranchName_pending,PendingAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            BranchName_pending = itemView.findViewById(R.id.BranchName_pending);
            PendingAmt = itemView.findViewById(R.id.PendingAmt);
        }
    }
}
