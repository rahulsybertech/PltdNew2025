package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.response.SaleServiceRespo.SaleServiceReportResult;


import java.util.List;

public class SaleServiceAdapter  extends RecyclerView.Adapter<SaleServiceAdapter.MyViewHolder> {

    private Context mContext;
    private List<SaleServiceReportResult> SaleServiceDetails;

    public SaleServiceAdapter(Context mContext, List<SaleServiceReportResult> detailList) {
        this.mContext = mContext;
        this.SaleServiceDetails = detailList;
    }

    @Override
    public SaleServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saleservice_recylerview, parent, false);
        return new SaleServiceAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaleServiceAdapter.MyViewHolder holder, final int position) {

        final SaleServiceReportResult datum = SaleServiceDetails.get(position);
        holder.saleSer_billNo.setText(datum.getBillNo());
        holder.date_saleSer.setText(datum.getDate());
        holder.subParty_saleSer.setText(datum.getSubParty());
        holder.Customer_saleSer.setText(datum.getCustomerName());
        holder.netAmt_saleSer.setText(datum.getNetAmt());

        holder.saleSer_billNo.setOnClickListener(v -> {
            if (!datum.getPDFPath().equals("")) {
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getPDFPath()));

            } else {
                Toast.makeText(mContext, "PDF File Not Available", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return SaleServiceDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView saleSer_billNo,bookingStation_saleSer,date_saleSer,subParty_saleSer,Customer_saleSer,netAmt_saleSer;

        public MyViewHolder(View itemView) {
            super(itemView);

            saleSer_billNo = itemView.findViewById(R.id.saleSer_billNo);
      //      bookingStation_saleSer = itemView.findViewById(R.id.bookingStation_saleSer);
            date_saleSer = itemView.findViewById(R.id.date_saleSer);
            subParty_saleSer = itemView.findViewById(R.id.subParty_saleSer);
            Customer_saleSer = itemView.findViewById(R.id.Customer_saleSer);
            netAmt_saleSer = itemView.findViewById(R.id.netAmt_saleSer);
        }
    }
}
