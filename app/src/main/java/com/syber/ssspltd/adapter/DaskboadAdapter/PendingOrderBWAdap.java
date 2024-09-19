package com.syber.ssspltd.adapter.DaskboadAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.DashboardAllData.TotalPendingOrderDetail;

import java.util.List;

public class PendingOrderBWAdap extends RecyclerView.Adapter<PendingOrderBWAdap.MyViewHolder>  {

    private Context mContext;
    private List<TotalPendingOrderDetail> saleReportDetails;

    public PendingOrderBWAdap(Context mContext, List<TotalPendingOrderDetail> detailList) {
        this.mContext = mContext;
        this.saleReportDetails = detailList;
    }

    @Override
    public PendingOrderBWAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tol_pending_recyclerview, parent, false);
        return new PendingOrderBWAdap.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingOrderBWAdap.MyViewHolder holder, final int position) {

        final TotalPendingOrderDetail datum = saleReportDetails.get(position);
        holder.BranchName_pending.setText(datum.getBranchName());
        holder.PendingAmt.setText(datum.getPendingAmt());

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
        return saleReportDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView saleBillNo,saleSubParty,saleDate,saleTransport,saleBiltyNo,saleLR_Date,salePuchaseNo,saleSupplier,salePCS,saleAmount;
        LinearLayout ll_sup,ll_sale;
        ImageView call;
        TextView BranchName_pending,PendingAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            BranchName_pending = itemView.findViewById(R.id.BranchName_pending);
            PendingAmt = itemView.findViewById(R.id.PendingAmt);

        }
    }
}
