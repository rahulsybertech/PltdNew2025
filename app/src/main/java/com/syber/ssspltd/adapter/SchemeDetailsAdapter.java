package com.syber.ssspltd.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.syber.ssspltd.R;
import com.syber.ssspltd.model.scheme.scheme_detail.SchemeDetails;

import java.util.ArrayList;
import java.util.List;

public class SchemeDetailsAdapter extends RecyclerView.Adapter<SchemeDetailsAdapter.MyViewHolder> {

    boolean setTrue = false;
    private Context mContext;
    private List<SchemeDetails> saleReportDetails;

    public SchemeDetailsAdapter(Context mContext, ArrayList<SchemeDetails> detailList) {
        this.mContext = mContext;
        this.saleReportDetails = detailList;
    }

    @Override
    public SchemeDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_details_adapter, parent, false);
        return new SchemeDetailsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SchemeDetailsAdapter.MyViewHolder holder, final int position) {

        final SchemeDetails datum = saleReportDetails.get(position);
        holder.sNo.setText(String.valueOf(position + 1));
        holder.supplierName.setText(datum.getSupplierName());
        holder.purchaseNo.setText(datum.getPurchaseSno());
        holder.tvSalePartyName.setText(datum.getSalePartyName()+" "+datum.getCustomerName());
        holder.tvNickName.setText(datum.getNickName());
        holder.tvBalanceAmt.setText("Balance Amt:"+"₹ " + String.format("%.2f", datum.getSaleAmount()));
        holder.tvDis.setText("Dis:"+String.valueOf(datum.getDiscount()));
        holder.saleNo.setText(datum.getSaleBillNo());

        holder.tvDisAmt.setText("Dis Amt:"+"₹ " + String.format("%.2f", datum.getDiscountAmount()));
       /* holder.saleSubParty.setText(datum.getSubParty());
        holder.saleDate.setText(datum.getBillDate());
        holder.saleTransport.setText(datum.getTransport());
        holder.saleLR_Date.setText(datum.getLRDate());
        holder.netAmt_sale.setText(datum.getSAmount());
        */
    }


    @Override
    public int getItemCount() {
        Log.e("Size", saleReportDetails.size() + "");
        return saleReportDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sNo, purchaseNo, saleDate, saleNo, supplierName, saleLR_Date, netAmt_sale, videoLink,tvSalePartyName,tvNickName,tvBalanceAmt,tvDis,tvDisAmt;
        LinearLayout ll_sup, videoLinkLL;
        ImageView plusClick;
        TextView dr_amt, bal_name;
        RecyclerView reclerItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            videoLink = itemView.findViewById(R.id.videoLink);
            sNo = itemView.findViewById(R.id.sNo);
            purchaseNo = itemView.findViewById(R.id.purchaseNo);
            tvSalePartyName = itemView.findViewById(R.id.tvSalePartyName);
            tvBalanceAmt = itemView.findViewById(R.id.tvBalanceAmt);
            tvDis = itemView.findViewById(R.id.tvDis);
            tvDisAmt = itemView.findViewById(R.id.tvDisAmt);
            tvNickName = itemView.findViewById(R.id.tvNickName);
            saleDate = itemView.findViewById(R.id.saleDate);
            saleNo = itemView.findViewById(R.id.saleNo);
            supplierName = itemView.findViewById(R.id.supplierName);
            saleLR_Date = itemView.findViewById(R.id.saleLR_Date);
            netAmt_sale = itemView.findViewById(R.id.netAmt_sale);
            plusClick = itemView.findViewById(R.id.plusClick);
            reclerItem = itemView.findViewById(R.id.reclerItem);
        }
    }
}

