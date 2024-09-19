package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.PendingOrderItemDetailsActivity;
import com.syber.ssspltd.response.PendingOrderReport.PendingOrderReportResult;

import java.io.Serializable;
import java.util.List;

public class PendingOrderReportAdapter  extends RecyclerView.Adapter<PendingOrderReportAdapter.MyViewHolder>{
    private Context mContext;
    private List<PendingOrderReportResult> pendingOrderDetails;

    public PendingOrderReportAdapter(Context mContext, List<PendingOrderReportResult> detailList) {
        this.mContext = mContext;
        this.pendingOrderDetails = detailList;
    }

    @Override
    public PendingOrderReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_recyclerview, parent, false);
        return new PendingOrderReportAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingOrderReportAdapter.MyViewHolder holder, final int position) {

        final PendingOrderReportResult datum = pendingOrderDetails.get(position);
        holder.pendingSupplier_Name.setText(datum.getSupplier());
        holder.pendingOrder_no.setText(datum.getOrderNo());
        holder.pendingSub_party.setText(datum.getSubParty());
        holder.pending_date.setText(datum.getDate());
        holder.pendingItem.setText(datum.getItems());
        holder.pendingType.setText(datum.getPcs());
        holder.pendingQTY.setText(datum.getQty());
        holder.pendingAmt.setText(datum.getAmount());
        holder.marketer_pending.setText(datum.getMarketer());
        holder.itemView.setOnClickListener(v->{
            if (datum.getOrderdetail().size()>0) {
                mContext.startActivity(new Intent(mContext, PendingOrderItemDetailsActivity.class)
                        .putExtra("orderlist",datum));
            }else {
                Toast.makeText(mContext, "No Details found for this order", Toast.LENGTH_SHORT).show();
            }
        });

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
        return pendingOrderDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pendingSupplier_Name,pendingOrder_no,pendingSub_party,pending_date,pendingItem,pendingType,pendingQTY,pendingAmt,marketer_pending;
        TextView netAmt_pending;
        public MyViewHolder(View itemView) {
            super(itemView);
            pendingSupplier_Name = itemView.findViewById(R.id.pendingSupplier_Name);
            pendingOrder_no = itemView.findViewById(R.id.pendingOrder_no);
            pendingSub_party = itemView.findViewById(R.id.pendingSub_party);
            pending_date=itemView.findViewById(R.id.pending_date);
            pendingItem = itemView.findViewById(R.id.pendingItem);
            pendingType = itemView.findViewById(R.id.pendingType);
            pendingQTY = itemView.findViewById(R.id.pendingQTY);
            pendingAmt = itemView.findViewById(R.id.pendingAmt);
            marketer_pending = itemView.findViewById(R.id.marketer_pending);
        }
    }
}
