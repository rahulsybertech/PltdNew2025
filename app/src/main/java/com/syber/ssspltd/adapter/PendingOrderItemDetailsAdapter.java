package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.PendingOrderReport.Orderdetail;
import com.syber.ssspltd.response.PendingOrderReport.PendingOrderReportResult;

import java.util.List;

public class PendingOrderItemDetailsAdapter extends RecyclerView.Adapter<PendingOrderItemDetailsAdapter.MyViewHolder>{
    private Context mContext;
    private List<Orderdetail> pendingOrderDetails;

    public PendingOrderItemDetailsAdapter(Context mContext, List<Orderdetail> detailList) {
        this.mContext = mContext;
        this.pendingOrderDetails = detailList;
    }

    @Override
    public PendingOrderItemDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_details_list, parent, false);
        return new PendingOrderItemDetailsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingOrderItemDetailsAdapter.MyViewHolder holder, final int position) {

        final Orderdetail datum = pendingOrderDetails.get(position);
        holder.srno.setText(datum.getSRNO());
        holder.pendingItem.setText(datum.getItemName());
        holder.pendingQTY.setText(datum.getQty());
        holder.pendingAmt.setText(datum.getAmount());
    }


    @Override
    public int getItemCount() {
        return pendingOrderDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srno,pendingOrder_no,pendingItem,pendingQTY,pendingAmt;
        public MyViewHolder(View itemView) {
            super(itemView);
            srno = itemView.findViewById(R.id.srno);
            pendingOrder_no = itemView.findViewById(R.id.pendingOrder_no);
            pendingItem = itemView.findViewById(R.id.pendingItem);
            pendingQTY = itemView.findViewById(R.id.pendingQTY);
            pendingAmt = itemView.findViewById(R.id.pendingAmt);
        }
    }
}
